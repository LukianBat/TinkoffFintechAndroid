package ru.tinkoff.ru.seminar.core.feature.domain.model;

import java.util.ArrayList;

public class WeatherList {
    public ArrayList<Weather> weatherList;

    public WeatherList(ArrayList<Weather> weatherList){
        this.weatherList = weatherList;
    }
}
