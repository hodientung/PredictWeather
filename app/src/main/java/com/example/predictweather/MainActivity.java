package com.example.predictweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.predictweather.weatherprediction.MapsActivity;
import com.example.predictweather.weatherprediction.WeatherByAddress;
import com.example.predictweather.weatherprediction.WeatherCurrent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tva, tvb, tvc, tvd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectView();
    }

    private void connectView() {
        tva = findViewById(R.id.tva);
        tvb = findViewById(R.id.tvb);
        tvc = findViewById(R.id.tvc);
        tvd = findViewById(R.id.tvd);
        tva.setOnClickListener(this);
        tvb.setOnClickListener(this);
        tvc.setOnClickListener(this);
        tvd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tva: {
                if (!networkConnect()) {
                    Toast.makeText(MainActivity.this, "mở kết nối internet", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, WeatherCurrent.class);
                startActivity(intent);
                break;
            }
            case R.id.tvb: {
                if (!networkConnect()) {
                    Toast.makeText(MainActivity.this, "mở kết nối internet", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, WeatherByAddress.class);
                startActivity(intent);
                break;
            }
            case R.id.tvc: {
                if (!networkConnect()) {
                    Toast.makeText(MainActivity.this, "mở kết nối internet", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, ManHinh3.class);
                startActivity(intent);
                break;
            }
            case R.id.tvd: {
                if(!networkConnect())
                {
                    Toast.makeText(MainActivity.this,"Bạn cần mở kết nối internet",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent map=new Intent(MainActivity.this, MapsActivity.class) ;
                startActivity(map);
                break;
            }
        }
    }

    public Boolean networkConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        } else
            return true;
    }
}
