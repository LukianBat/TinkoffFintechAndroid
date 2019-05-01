package ru.tinkoff.ru.seminar.core.feature.data;



import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.tinkoff.ru.seminar.core.feature.domain.model.WeatherList;
import rx.Single;

public interface WeatherApi {
    @GET("weather/")
    Single<WeatherList> getCurrentWeather(@Query("q") String city, @Query("appid") String apiKey);

    @GET("forecast/")
    Single<WeatherList> getWeatherForecast(@Query("q") String city, @Query("appid") String apiKey);
}
