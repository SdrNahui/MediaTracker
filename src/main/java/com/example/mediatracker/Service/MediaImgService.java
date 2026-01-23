package com.example.mediatracker.Service;

import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MediaImgService {
    private static final Path IMG_DIR = Paths.get("data/img");

    public MediaImgService() throws IOException {
        Files.createDirectories(IMG_DIR);
    }

    public String guardarImg(File archivo, String imgPathViejo) throws IOException {
        eliminarLocalImg(imgPathViejo);
        String extension = archivo.getName().substring(archivo.getName().lastIndexOf("."));
        String nombre = "Media_" + System.currentTimeMillis() + extension;
        Path destino = IMG_DIR.resolve(nombre);
        Files.copy(archivo.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
        return nombre;
    }

    public void eliminarLocalImg(String path) {
        if (path == null || path.isBlank()) {
            return;
        }
        if (path.startsWith("http")) {
            return;
        }
        try {
            Files.deleteIfExists(IMG_DIR.resolve(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image cargarImg(String path, double w, double h) {
        if (path == null || path.isBlank()) {
            return defaultImage(w, h);
        }
        if (path.startsWith("http")) {
            return new Image(path, w, h, true, true, true);
        }
        return new Image(IMG_DIR.resolve(path).toUri().toString(), w, h, true, true, true);
    }

    private Image defaultImage(double w, double h) {
        var url = getClass().getResource("/com/example/mediatracker/imagenes/fallback_v1.png");
        if (url == null) {
            throw new IllegalStateException("Fallback imagen no encotrada");
        }
        return new Image(url.toExternalForm(), w, h, true, true, true);
    }
}
