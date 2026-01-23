package com.example.mediatracker.Repository;

import com.example.mediatracker.Model.Actividad;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActividadRepository implements IRepository<Actividad>{
    private static final Path DATA_DIR = Paths.get("data");
    private static final Path FILE_PATH = DATA_DIR.resolve("Actividades.json");
    private final Gson gson;
    public ActividadRepository(){
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,
                new LocalDateAdapter()).setPrettyPrinting().create();
        try {
            Files.createDirectories(DATA_DIR);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear la parta en data/", e);
        }
    }
    @Override
    public List<Actividad> load() {
        if(!Files.exists(FILE_PATH)){
            return new ArrayList<>();
        }
        try(Reader reader = Files.newBufferedReader(FILE_PATH)){
            Type listType = new TypeToken<List<Actividad>>(){}.getType();
            return gson.fromJson(reader,listType);
        } catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void save(List<Actividad> data) {
        try(Writer writer = Files.newBufferedWriter(FILE_PATH)){
            gson.toJson(data,writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
