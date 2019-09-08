package com.example.predictweather;


import com.example.predictweather.model.ManagementWeatherJSon;
import com.example.predictweather.model.WeatherForecastResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<ManagementWeatherJSon> getWeatherByLatLng(@Query("lat") String lat,@Query("lon") String lon,@Query("appid") String appid,@Query("units") String unit);

    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByLatLng(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid, @Query("units") String unit);
}
