package com.example.predictweather.model;

import java.util.List;

public class WeatherForecastResult {
    private String cod;
    private double message;
    public List<myList> list;
    private City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public List<myList> getList() {
        return list;
    }

    public void setList(List<myList> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
