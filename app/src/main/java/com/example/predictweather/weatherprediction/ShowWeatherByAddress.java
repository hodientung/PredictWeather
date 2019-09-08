package com.example.predictweather.weatherprediction;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.predictweather.R;
import com.example.predictweather.manage_and_show_weather.WeatherAsyncTask;

public class ShowWeatherByAddress extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_current);
        Intent intent=getIntent();
        String s=intent.getStringExtra("address");
        WeatherAsyncTask task=new WeatherAsyncTask(this,s);
        task.execute();
    }
}
