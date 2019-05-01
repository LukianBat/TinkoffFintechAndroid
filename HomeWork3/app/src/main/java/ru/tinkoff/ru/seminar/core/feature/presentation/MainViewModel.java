package ru.tinkoff.ru.seminar.core.feature.presentation;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import ru.tinkoff.ru.seminar.core.App;
import ru.tinkoff.ru.seminar.core.core.domain.BaseCallback;
import ru.tinkoff.ru.seminar.core.feature.domain.interactor.WeatherService;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    @Inject
    WeatherService weatherService;

    MainViewModel() {
        App.getComponent().inject(this);
    }

    void getWeather(String city, BaseCallback<Single<Pair>> callback) {
        weatherService.getWeather(city, single -> callback.onSuccess(single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())));
    }

    @SuppressLint("SimpleDateFormat")
    String unixTimeToDate(Long unixTime) {
        Date df = new Date(unixTime * 1000);
        return new SimpleDateFormat().format(df);
    }

    float KelvinToCelsius(float degrees) {
        return (float) (degrees - 273.15);
    }
}
