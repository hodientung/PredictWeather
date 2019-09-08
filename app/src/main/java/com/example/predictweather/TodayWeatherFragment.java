package com.example.predictweather;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.predictweather.model.ManagementWeatherJSon;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeatherFragment extends Fragment {
    ImageView img_weather;
    TextView txt_city_name, txt_humidity, txt_sunrise, txt_sunset, txt_pressure, txt_temperature, txt_description, txt_date_time, txt_wind, txt_geo_coord;
    LinearLayout weather_panel;
    ProgressBar loading;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;


    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance() {
        if (instance == null) {
            instance = new TodayWeatherFragment();
        }
        return instance;
    }

    public TodayWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_today_weather, container, false);
        img_weather = (ImageView) itemView.findViewById(R.id.img_weather);
        txt_city_name = (TextView) itemView.findViewById(R.id.txt_city_name);
        txt_sunrise = (TextView) itemView.findViewById(R.id.txt_sunrise);
        txt_sunset = (TextView) itemView.findViewById(R.id.txt_sunset);
        txt_pressure = (TextView) itemView.findViewById(R.id.txt_pressure);
        txt_temperature = (TextView) itemView.findViewById(R.id.txt_temperature);
        txt_description = (TextView) itemView.findViewById(R.id.txt_description);
        txt_wind = (TextView) itemView.findViewById(R.id.txt_wind);
        txt_geo_coord = (TextView) itemView.findViewById(R.id.txt_geo_coord);
        txt_humidity = (TextView) itemView.findViewById(R.id.txt_humidity);
        txt_date_time = (TextView) itemView.findViewById(R.id.txt_date_time);
        weather_panel = (LinearLayout) itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar) itemView.findViewById(R.id.loading);
        getWeatherInformation();


        return itemView;
    }

    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ManagementWeatherJSon>() {
                    @Override
                    public void accept(ManagementWeatherJSon managementWeatherJSon) throws Exception {
                        Picasso.get().load(new StringBuilder("http://openweathermap.org/img/w/")
                                .append(managementWeatherJSon.getWeather().get(0).getIcon()).append(".png").toString()).into(img_weather);
                        txt_city_name.setText(managementWeatherJSon.getName());
                        txt_description.setText(new StringBuilder("weather in ").append(managementWeatherJSon.getName()).toString());
                        txt_temperature.setText(new StringBuilder(String.valueOf(managementWeatherJSon.getMain().getTemp())).append("°С").toString());
                        txt_date_time.setText(Common.convertUnixToDate(managementWeatherJSon.getDt()));
                        txt_pressure.setText(new StringBuilder(String.valueOf(managementWeatherJSon.getMain().getPressure())).append(" hpa").toString());
                        txt_humidity.setText(new StringBuilder(String.valueOf(managementWeatherJSon.getMain().getHumidity())).append(" %").toString());
                        txt_sunrise.setText(Common.convertUnixToHour(managementWeatherJSon.getSys().getSunrise()));
                        txt_sunrise.setText(Common.convertUnixToHour(managementWeatherJSon.getSys().getSunset()));
                        txt_geo_coord.setText(new StringBuilder(managementWeatherJSon.getCoord().toString()).toString());
                        weather_panel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);



                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(),""+throwable.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })

        );
    }

}
