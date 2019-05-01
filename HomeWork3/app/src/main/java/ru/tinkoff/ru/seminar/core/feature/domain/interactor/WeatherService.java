package ru.tinkoff.ru.seminar.core.feature.domain.interactor;


import android.support.v4.util.Pair;

import ru.tinkoff.ru.seminar.core.core.domain.BaseCallback;
import ru.tinkoff.ru.seminar.core.feature.data.WeatherApi;
import rx.Single;

public class WeatherService {
    private WeatherApi weatherApi;
    private String apiKey;

    public WeatherService(WeatherApi weatherApi, String apiKey) {
        this.weatherApi = weatherApi;
        this.apiKey = apiKey;
    }

    public void getWeather(String city, BaseCallback<Single<Pair>> callback) {
        callback.onSuccess(Single.zip(
                weatherApi.getCurrentWeather(city, apiKey),
                weatherApi.getWeatherForecast(city, apiKey),
                Pair::new));
    }
}