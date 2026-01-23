package com.example.mediatracker.Repository;

import com.example.mediatracker.Model.AudioVisual;
import com.example.mediatracker.Model.Pelicula;
import com.example.mediatracker.Model.Serie;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AudioVisualAdapter implements JsonSerializer<AudioVisual>, JsonDeserializer<AudioVisual> {
    @Override
    public JsonElement serialize(AudioVisual audioVisual, Type type,
                                 JsonSerializationContext context) {
        JsonObject jsonObject = context.serialize(audioVisual, audioVisual.getClass()).getAsJsonObject();
        jsonObject.addProperty("tipo", audioVisual.getTipo());
        return jsonObject;
    }

    @Override
    public AudioVisual deserialize(JsonElement jsonElement, Type type,
                                   JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        String tipo = obj.get("tipo").getAsString();
        return switch (tipo){
            case "serie" -> context.deserialize(obj, Serie.class);
            case "pelicula" -> context.deserialize(obj, Pelicula.class);
            default -> throw new JsonParseException("tipo desconocido:" + tipo);
        };
    }
}
