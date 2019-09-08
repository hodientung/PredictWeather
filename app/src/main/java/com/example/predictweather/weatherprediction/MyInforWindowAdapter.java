package com.example.predictweather.weatherprediction;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.predictweather.R;
import com.example.predictweather.manage_and_show_weather.TypeOfPrediction;
import com.example.predictweather.model.ManagementWeatherJSon;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyInforWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    Marker maker = null;
    ManagementWeatherJSon openWeatherJSon = null;
    Bitmap myBitmap = null;
    NumberFormat format = new DecimalFormat("#0.0");
    double latitude;
    double longitude;

    public MyInforWindowAdapter(Activity context) {
        this.context = context;
    }

    public MyInforWindowAdapter(ManagementWeatherJSon openWeatherJSon, Bitmap myBitmap, Marker maker, Activity context) {
        this(context);
        this.maker = maker;
        this.openWeatherJSon = openWeatherJSon;
        this.myBitmap = myBitmap;
    }

    public MyInforWindowAdapter(ManagementWeatherJSon openWeatherJSon, Bitmap myBitmap, Marker maker, Activity context, double latitude, double longitude) {
        this(openWeatherJSon, myBitmap, maker, context);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = this.context.getLayoutInflater().inflate(R.layout.weather_current, null);

        TextView txtTemperature = (TextView) v.findViewById(R.id.tv2);
        TextView txtCurrentAddressName = (TextView) v.findViewById(R.id.tv1);
        ImageView imageView = (ImageView) v.findViewById(R.id.tv3);
        TextView txtMaxtemp = (TextView) v.findViewById(R.id.tv4);
        TextView txtMinTemp = (TextView) v.findViewById(R.id.tv5);
        TextView txtWind = (TextView) v.findViewById(R.id.tv6);
        TextView txtCloudliness = (TextView) v.findViewById(R.id.tv7);
        TextView txtPressure = (TextView) v.findViewById(R.id.tv8);
        TextView txtHumidty = (TextView) v.findViewById(R.id.tv9);
        double temperature = openWeatherJSon.getMain().getTemp() - 273.15;
        TextView txtSunrise = (TextView) v.findViewById(R.id.tv10);
        TextView txtSunset = (TextView) v.findViewById(R.id.tv11);
        String maxtemp = format.format(openWeatherJSon.getMain().getTemp_max() - 273.15) + "°C";
        String mintemp = format.format(openWeatherJSon.getMain().getTemp_min() - 273.15) + "°C";
        String wind = openWeatherJSon.getWind().getSpeed() + " m/s";
        String mesg = openWeatherJSon.getWeather().get(0).getMain();
        String cloudiness = mesg;
        String pressure = openWeatherJSon.getMain().getPressure() + " hpa";
        String humidity = openWeatherJSon.getMain().getHumidity() + " %";

        Date timeSunrise = new Date(openWeatherJSon.getSys().getSunrise() * 1000);
        String Sunrise = timeSunrise.getHours() + ":" + timeSunrise.getMinutes() + " AM";
        Date timeSunSet = new Date(openWeatherJSon.getSys().getSunset() * 1000);
        String sunset = timeSunSet.getHours() + ":" + timeSunSet.getMinutes();
        txtTemperature.setText(format.format(temperature) + "°C");
        imageView.setImageBitmap(myBitmap);
        txtMaxtemp.setText(maxtemp);
        txtMinTemp.setText(mintemp);
        txtWind.setText(wind);
        txtCloudliness.setText(cloudiness);
        txtPressure.setText(pressure);
        txtHumidty.setText(humidity);
        txtSunrise.setText(Sunrise);
        txtSunset.setText(sunset);

        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this.context, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            Address address = null;
            if (addresses.size() > 0)
                address = addresses.get(0);
            if (address != null) {
                txtCurrentAddressName.setText(address.getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        v.setBackgroundColor(Color.YELLOW);
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
