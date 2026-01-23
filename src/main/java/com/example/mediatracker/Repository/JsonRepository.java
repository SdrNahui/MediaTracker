package com.example.mediatracker.Repository;

import com.example.mediatracker.Model.AudioVisual;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonRepository implements IRepository<AudioVisual> {
    private final String filePath = "mediaTracker.json";
    private final Gson gson;
    public JsonRepository(){
        this.gson = new GsonBuilder().registerTypeAdapter(AudioVisual.class, new AudioVisualAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();
    }
    @Override
    public List<AudioVisual> load() {
        File file = new File(filePath);
        if(!file.exists()){
            return new ArrayList<>();
        }
        try(Reader reader = new FileReader(file)){
            Type listType = new TypeToken<List<AudioVisual>>(){}.getType();
            return gson.fromJson(reader,listType);
        } catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void save(List<AudioVisual> data) {
        try(Writer writer = new FileWriter(filePath)){
            gson.toJson(data,writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
