package ru.tinkoff.ru.seminar.core.core.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tinkoff.ru.seminar.core.feature.data.WeatherApi;
import ru.tinkoff.ru.seminar.core.feature.domain.WeatherDeserializer;
import ru.tinkoff.ru.seminar.core.feature.domain.interactor.WeatherService;
import ru.tinkoff.ru.seminar.core.feature.domain.model.WeatherList;

@Module
public class RetrofitModule {
    String url;
    String apiKey;

    public RetrofitModule(String url, String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit() {
        Gson deserializer = new GsonBuilder()
                .registerTypeAdapter(WeatherList.class, new WeatherDeserializer())
                .create();
        return new Retrofit.Builder()
                .client(new OkHttpClient.Builder().addNetworkInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(deserializer))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    WeatherService providesWeatherService(Retrofit retrofit) {
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        return new WeatherService(weatherApi, apiKey);
    }

}
