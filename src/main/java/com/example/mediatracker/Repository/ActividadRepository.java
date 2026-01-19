package com.example.mediatracker.Repository;

import com.example.mediatracker.Model.Actividad;
import com.example.mediatracker.Model.AudioVisual;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActividadRepository implements IRepository<Actividad>{
    private final String filePath = "Actividades.json";
    private final Gson gson;
    public ActividadRepository(){
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,
                new LocalDateAdapter()).setPrettyPrinting().create();
    }
    @Override
    public List<Actividad> load() {
        File file = new File(filePath);
        if(!file.exists()){
            return new ArrayList<>();
        }
        try(Reader reader = new FileReader(file)){
            Type listType = new TypeToken<List<Actividad>>(){}.getType();
            return gson.fromJson(reader,listType);

        } catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void save(List<Actividad> data) {
        try(Writer writer = new FileWriter(filePath)){
            gson.toJson(data,writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
