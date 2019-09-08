package com.example.predictweather.manage_and_show_weather;

import com.example.predictweather.model.ManagementWeatherJSon;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ReadingWeather {
    public static ManagementWeatherJSon predict(String a){
        try {
            String location= URLEncoder.encode(a,"UTF-8");
            URL url=new URL("http://api.openweathermap.org/data/2.5/weather?q="+location+"&appid=f009e089ca62be88a16d974899f4b627");
            InputStreamReader inputStreamReader=new InputStreamReader(url.openStream(),"UTF-8");
            ManagementWeatherJSon managementWeatherJSon=new Gson().fromJson(inputStreamReader,ManagementWeatherJSon.class);
            String idIcon=managementWeatherJSon.getWeather().get(0).getIcon().toString();
            String urlIcon="http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);
            return managementWeatherJSon;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ManagementWeatherJSon predict(double lat,double lon){
        try {
            URL url=new URL("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=f009e089ca62be88a16d974899f4b627");
            InputStreamReader inputStreamReader=new InputStreamReader(url.openStream(),"UTF-8");
            ManagementWeatherJSon managementWeatherJSon=new Gson().fromJson(inputStreamReader,ManagementWeatherJSon.class);
            String idIcon=managementWeatherJSon.getWeather().get(0).getIcon().toString();
            String urlIcon="http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);
            return managementWeatherJSon;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ManagementWeatherJSon predictDaily(double lat,double lon,int cnt){
        try {
            URL url=new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lon+"&cnt="+cnt+"&appid=f009e089ca62be88a16d974899f4b627");
            InputStreamReader inputStreamReader=new InputStreamReader(url.openStream(),"UTF-8");
            ManagementWeatherJSon managementWeatherJSon=new Gson().fromJson(inputStreamReader,ManagementWeatherJSon.class);
            String idIcon=managementWeatherJSon.getWeather().get(0).getIcon().toString();
            String urlIcon="http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);
            return managementWeatherJSon;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
public static ManagementWeatherJSon predictDaily(String q,int cnt){
    try {
        String location= URLEncoder.encode(q, "UTF-8");
        URL url=new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q="+location+"&cnt="+cnt+"&appid=f009e089ca62be88a16d974899f4b627");
        InputStreamReader inputStreamReader=new InputStreamReader(url.openStream(),"UTF-8");
        ManagementWeatherJSon managementWeatherJSon=new Gson().fromJson(inputStreamReader,ManagementWeatherJSon.class);
        String idIcon=managementWeatherJSon.getWeather().get(0).getIcon().toString();
        String urlIcon="http://openweathermap.org/img/w/"+idIcon+".png";
        URL urlImage = new URL(urlIcon);
        return managementWeatherJSon;
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
}
}
