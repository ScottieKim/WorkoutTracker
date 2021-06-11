package com.github.scott.workouttracking.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.scott.workouttracking.R;
import com.github.scott.workouttracking.data.WorkoutViewModel;
import com.github.scott.workouttracking.databinding.ActivityWorkoutBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity implements LocationListener {
    private WorkoutViewModel viewModel;
    private ActivityWorkoutBinding binding;
    private GoogleMap googleMap = null;
    private LocationManager locationManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(WorkoutViewModel.class);
        viewModel.getBack().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });
        viewModel.getStart().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                setMap();
            }
        });
        viewModel.getEnd().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String sum) {
                binding.distance.setText(sum);

                List<LatLng> list = viewModel.getLatLngList();
                addMarker(list.get(list.size() - 1));
                locationManager.removeUpdates(WorkoutActivity.this);
            }
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_workout);
        binding.setViewModel(viewModel);
    }

    private void setMap() {
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (fragment != null) {
            fragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap map) {
                    googleMap = map;
                    enableMyLocation();
                }
            });

        }
    }

    private void setLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            return;
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (googleMap != null) {
                setLocation();
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            String[] str = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION};

            requestPermissions(str, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            setLocation();

        }
    }

    // Location Listener
    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Log.e("GPS", latitude + " , " + longitude);
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

        viewModel.setLatLng(latLng);
        viewModel.setLocation(location);

        PolylineOptions options = new PolylineOptions()
                .color(Color.RED)
                .width(10)
                .clickable(true)
                .addAll(viewModel.getLatLngList());
        googleMap.addPolyline(options);

        if (viewModel.getLatLngList().size() == 1) {
            addMarker(viewModel.getLatLngList().get(0));
        }
    }

    private void addMarker(LatLng latLng) {
        MarkerOptions marker = new MarkerOptions().position(latLng);
        googleMap.addMarker(marker);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
