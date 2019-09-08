package com.example.predictweather.weatherprediction;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.example.predictweather.R;
import com.example.predictweather.manage_and_show_weather.WeatherAsyncTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang tải Map ...");
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        progressDialog.dismiss();
                    }
                });
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                Location lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                if (lastLocation != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 13));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()))
                            .zoom(15)
                            .bearing(90)
                            .tilt(40)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    MarkerOptions option = new MarkerOptions();
                    option.position(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
                    option.title("your location").snippet("hồ diên tùng");
                    option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    Marker maker = mMap.addMarker(option);
                    WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(maker, mMap, MapsActivity.this, lastLocation.getLatitude(), lastLocation.getLongitude());
                    weatherAsyncTask.execute();
                }
                addEvents(mMap);
            }
        });

    }

    public void addEvents(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap == null) {
            return;
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                moveAndShowWeatherNewPlace(latLng, mMap);
            }
        });
    }

    private void moveAndShowWeatherNewPlace(LatLng latLng, GoogleMap googleMap) {
        mMap = googleMap;
        if (latLng != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(latLng.latitude, latLng.longitude), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latLng.latitude, latLng.longitude))
                    .zoom(15)
                    .bearing(90)
                    .tilt(40)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            MarkerOptions option = new MarkerOptions();
            option.position(new LatLng(latLng.latitude, latLng.longitude));
            option.title("your new place").snippet("hồ diên tùng");
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            Marker maker = mMap.addMarker(option);
            WeatherAsyncTask task = new WeatherAsyncTask(maker, mMap, MapsActivity.this, latLng.latitude, latLng.longitude);
            task.execute();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }
}
