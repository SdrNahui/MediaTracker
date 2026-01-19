package com.example.mediatracker.Controller;

import com.example.mediatracker.Service.IService;
import com.example.mediatracker.Service.ISetMain;
import com.example.mediatracker.Service.ISetService;
import com.example.mediatracker.Service.ModoFormulario;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainController implements ISetService {
    @FXML private StackPane contenedor;
    @FXML private VBox contenedorMenu;
    @FXML private ImageView iconMenu;
    private IService service;
    private MenuController menuController;
    private boolean menuVisible = false;
    @Override
    public void setService(IService service) {
        this.service = service;
        irHome();
    }
    @FXML public void initialize(){
        cargarMenu();
        contenedorMenu.setTranslateY(-1000);
        configurarAnimacionesMenu();
    }
    public void irHome(){
        cargar("homeView.fxml");
    }
    public void irAgregar(){
        service.setModoFormulario(ModoFormulario.AGREGAR);
        cargar("agregarView.fxml");
    }
    public void irEditar(){
        service.setModoFormulario(ModoFormulario.EDITAR);
        cargar("agregarView.fxml");
    }
    public void irLista(){
        service.seleccionado(null);
        cargar("listaView.fxml");
    }
    public void irDetalles(){
        cargar("detallesGeneralView.fxml");
    }
        public void irAgregarCap(){
        service.setModoFormulario(ModoFormulario.AGREGAR);
        cargar("agregarCapituloView.fxml");
    }
    public void irEditarCap(){
        service.setModoFormulario(ModoFormulario.EDITAR);
        cargar("agregarCapituloView.fxml");
    }

    private void cargar(String nombre){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mediatracker/" + nombre));
            Parent vista =loader.load();
            Object controller = loader.getController();
            if(controller instanceof ISetMain){
                ((ISetMain) controller).setMain(this);
            }
            if(controller instanceof ISetService){
                ((ISetService) controller).setService(service);
            }
            contenedor.getChildren().setAll(vista);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void cargarMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/com/example/mediatracker/menuView.fxml"));
            Parent menu = loader.load();
            menuController = loader.getController();  // <-- guardamos referencia
            menuController.setMain(this);
            contenedorMenu.getChildren().setAll(menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void mostrarMenu(){
        if(menuVisible){ return;
        }
        if (menuController != null){
            menuController.setTextos(true);
        }
        TranslateTransition t =new TranslateTransition(Duration.millis(200), contenedorMenu);
        t.setToY(0);
        t.play();
        menuVisible = true;
    }
    private void ocultarMenu() {
        if (!menuVisible) {
            return;
        }
        if (menuController != null) {
            menuController.setTextos(false);
        }
        TranslateTransition t = new TranslateTransition(Duration.millis(200), contenedorMenu);
        t.setToY(-1000);
        t.play();
        menuVisible = false;
    }
    private void configurarAnimacionesMenu() {
        iconMenu.setOnMouseClicked(e -> {
            if (menuVisible) ocultarMenu();
            else mostrarMenu();
        });
        contenedor.setOnMouseClicked(e -> {
            if (menuVisible) ocultarMenu();
        });
    }
}
