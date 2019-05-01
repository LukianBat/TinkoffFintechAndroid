package ru.tinkoff.ru.seminar.core;

import android.app.Application;

import ru.tinkoff.ru.seminar.R;
import ru.tinkoff.ru.seminar.core.core.di.AppComponent;
import ru.tinkoff.ru.seminar.core.core.di.DaggerAppComponent;
import ru.tinkoff.ru.seminar.core.core.di.module.AppModule;
import ru.tinkoff.ru.seminar.core.core.di.module.RetrofitModule;

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .retrofitModule(new RetrofitModule(getResources().getString(R.string.url), getResources().getString(R.string.api_key)))
                .build();
    }
}
