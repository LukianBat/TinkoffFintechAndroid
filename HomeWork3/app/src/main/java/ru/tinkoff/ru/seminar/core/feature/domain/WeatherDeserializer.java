package ru.tinkoff.ru.seminar.core.feature.domain;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ru.tinkoff.ru.seminar.core.feature.domain.model.Weather;
import ru.tinkoff.ru.seminar.core.feature.domain.model.WeatherList;


public class WeatherDeserializer implements JsonDeserializer<WeatherList> {
    @Override
    public WeatherList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        ArrayList<Weather> weatherArray = new ArrayList<>();

        if (jsonObject.has("list")) {
            for (JsonElement jsonElement : jsonObject.get("list").getAsJsonArray()) {
                weatherArray.add(new Weather(jsonElement.getAsJsonObject()
                        .get("weather")
                        .getAsJsonArray()
                        .get(0)
                        .getAsJsonObject()
                        .get("description")
                        .getAsString(),
                        jsonElement.getAsJsonObject().get("dt").getAsLong(),
                        jsonElement.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsFloat(),
                        jsonElement.getAsJsonObject().get("wind").getAsJsonObject().get("speed").getAsFloat()));
            }
        } else {
            weatherArray.add(new Weather(
                    jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString(),
                    jsonObject.get("dt").getAsLong(),
                    jsonObject.get("main").getAsJsonObject().get("temp").getAsFloat(),
                    jsonObject.get("wind").getAsJsonObject().get("speed").getAsFloat()));
        }
        return new WeatherList(weatherArray);
    }
}
