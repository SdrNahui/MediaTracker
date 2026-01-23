package com.example.mediatracker.Repository;

import com.example.mediatracker.Model.AudioVisual;
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

public class JsonRepository implements IRepository<AudioVisual> {
    private static final Path DATA_DIR = Paths.get("data");
    private static final Path FILE_PATH = DATA_DIR.resolve("mediaTracker.json");
    private final Gson gson;
    public JsonRepository(){
        this.gson = new GsonBuilder().registerTypeAdapter(AudioVisual.class, new AudioVisualAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();
        try {
            Files.createDirectories(DATA_DIR);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear la carpeta data/", e);
        }
    }
    @Override
    public List<AudioVisual> load() {
        if(!Files.exists(FILE_PATH)){
            return new ArrayList<>();
        }
        try(Reader reader = Files.newBufferedReader(FILE_PATH)){
            Type listType = new TypeToken<List<AudioVisual>>(){}.getType();
            return gson.fromJson(reader,listType);
        } catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void save(List<AudioVisual> data) {
        try(Writer writer = Files.newBufferedWriter(FILE_PATH)){
            gson.toJson(data,writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
