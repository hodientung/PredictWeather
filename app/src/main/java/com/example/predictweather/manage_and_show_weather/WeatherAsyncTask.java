package com.example.predictweather.manage_and_show_weather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.predictweather.MainActivity;
import com.example.predictweather.R;
import com.example.predictweather.model.ManagementWeatherJSon;
import com.example.predictweather.weatherprediction.MyInforWindowAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherAsyncTask extends AsyncTask<Void, Void, ManagementWeatherJSon> {
    ProgressDialog dialog;
    Activity activity;
    TypeOfPrediction typeOfPrediction;
    String a;
    double latitude;
    double longitude;
    NumberFormat numberFormat = new DecimalFormat("#0.0");
    Bitmap bitmap = null;
    Marker marker = null;
    GoogleMap map = null;

    public WeatherAsyncTask(Activity activity, String a) {
        this.activity = activity;
        this.typeOfPrediction = TypeOfPrediction.ADDRESS;
        this.a = a;
        this.dialog = new ProgressDialog(activity);
        this.dialog.setTitle("Đang tải thông tin ...");
        this.dialog.setMessage("Vui lòng chờ...");
        this.dialog.setCancelable(true);
    }

    public WeatherAsyncTask(Activity activity, double latitude, double longitude) {
        this.activity = activity;
        this.typeOfPrediction = TypeOfPrediction.COORDINATE;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dialog = new ProgressDialog(activity);
        this.dialog.setTitle("Đang tải thông tin ...");
        this.dialog.setMessage("Vui lòng chờ...");
        this.dialog.setCancelable(true);
    }

    public WeatherAsyncTask(Marker marker, GoogleMap map, Activity activity, double latitude, double longitude) {
        this(activity, latitude, longitude);
        this.marker = marker;
        this.map = map;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.show();
    }

    @Override
    protected ManagementWeatherJSon doInBackground(Void... voids) {
        ManagementWeatherJSon managementWeatherJSon = null;
        if (typeOfPrediction == TypeOfPrediction.COORDINATE) {
            managementWeatherJSon = ReadingWeather.predict(latitude, longitude);
        } else {
            managementWeatherJSon = ReadingWeather.predict(a);
        }
        String idIcon = managementWeatherJSon.getWeather().get(0).getIcon().toString();
        String urlIcon = "http://openweathermap.org/img/w/" + idIcon + ".png";
        try {
            URL urlConection = new URL(urlIcon);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConection.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return managementWeatherJSon;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ManagementWeatherJSon managementWeatherJSon) {
        super.onPostExecute(managementWeatherJSon);
        if (map != null) {
            map.setInfoWindowAdapter(new MyInforWindowAdapter(managementWeatherJSon, bitmap, marker, this.activity, latitude, longitude));
            marker.showInfoWindow();
            this.dialog.dismiss();
           return;
        }
        TextView tvTemperature = activity.findViewById(R.id.tv2);
        TextView tvAddress = activity.findViewById(R.id.tv1);
        ImageView imageView = activity.findViewById(R.id.tv3);
        TextView tvMaxTemp = activity.findViewById(R.id.tv4);
        TextView tvMinTemp = activity.findViewById(R.id.tv5);
        TextView tvWind = activity.findViewById(R.id.tv6);
        TextView tvCloudiness = activity.findViewById(R.id.tv7);
        TextView tvPressure = activity.findViewById(R.id.tv8);
        TextView tvHumidity = activity.findViewById(R.id.tv9);
        TextView tvSunrise = activity.findViewById(R.id.tv10);
        TextView tvSunset = activity.findViewById(R.id.tv11);
        double temperature = managementWeatherJSon.getMain().getTemp() - 273.15;
        String maxTemp = numberFormat.format(managementWeatherJSon.getMain().getTemp_max() - 273.15) + "°C";
        String minTemp = numberFormat.format(managementWeatherJSon.getMain().getTemp_min() - 273.15) + "°C";
        String wind = managementWeatherJSon.getWind().getSpeed() + " m/s";
        String cloudiness = managementWeatherJSon.getWeather().get(0).getMain();
        String pressure = managementWeatherJSon.getMain().getPressure() + " hpa";
        String humidity = managementWeatherJSon.getMain().getHumidity() + " %";
        Date timeSunrise = new Date(managementWeatherJSon.getSys().getSunrise() * 1000);
        String Sunrise = timeSunrise.getHours() + ":" + timeSunrise.getMinutes() + " AM";
        Date timeSunSet = new Date(managementWeatherJSon.getSys().getSunset() * 1000);
        String sunset = timeSunSet.getHours() + ":" + timeSunSet.getMinutes() + " PM";
        tvTemperature.setText(numberFormat.format(temperature) + "°C");
        imageView.setImageBitmap(bitmap);
        tvMaxTemp.setText(maxTemp);
        tvMinTemp.setText(minTemp);
        tvWind.setText(wind);
        tvCloudiness.setText(cloudiness);
        tvPressure.setText(pressure);
        tvHumidity.setText(humidity);
        tvSunrise.setText(Sunrise);
        tvSunset.setText(sunset);
        Geocoder geocoder;
        String strAdd = "";
        List<Address> addresses;
        geocoder = new Geocoder(this.activity, Locale.getDefault());
        if (typeOfPrediction == TypeOfPrediction.COORDINATE) {
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");
                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    strAdd = strReturnedAddress.toString();
                    Log.w("Currentlocation", strReturnedAddress.toString());
                } else {
                    Log.w("Currentlocation", "no Address returned!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.w("Currentlocation", "cannot get address!");
            }
            tvAddress.setText(strAdd);
        } else {
            try {
                addresses = geocoder.getFromLocationName(a, 1);
                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");
                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    strAdd = strReturnedAddress.toString();
                    Log.w("Currentlocation", strReturnedAddress.toString());
                } else {
                    Log.w("Currentlocation", "no Address returned!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.w("Currentlocation", "cannot get address!");
            }
            tvAddress.setText(strAdd);
        }
        this.dialog.dismiss();
    }
}
