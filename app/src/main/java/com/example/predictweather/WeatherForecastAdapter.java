package com.example.predictweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.predictweather.model.WeatherForecastResult;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.myViewHolder> {
    Context context;
    WeatherForecastResult weatherForecastResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_weather_forecast,parent,false);


        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        DecimalFormat decimalFormat=new DecimalFormat("#.000");
        Picasso.get().load(new StringBuilder("http://openweathermap.org/img/w/")
                .append(weatherForecastResult.list.get(position).weather.get(0).getIcon()).append(".png").toString()).into(holder.img_weather);
        holder.txt_date_name.setText(new StringBuilder(Common.convertUnixToDate(weatherForecastResult.list.get(position).dt)));
        holder.txt_description.setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).getDescription()));
        holder.txt_temperature.setText(new StringBuilder(String.valueOf( decimalFormat.format(weatherForecastResult.list.get(position).main.getTemp()-273.00))).append("°С"));
    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView txt_date_name, txt_description, txt_temperature;
        ImageView img_weather;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img_weather = (ImageView) itemView.findViewById(R.id.img_weather);
            txt_date_name = (TextView) itemView.findViewById(R.id.txt_date);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);
            txt_temperature = (TextView) itemView.findViewById(R.id.txt_temperature);

        }
    }
}
