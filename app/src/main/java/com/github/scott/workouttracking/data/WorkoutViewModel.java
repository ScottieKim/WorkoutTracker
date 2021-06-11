package com.github.scott.workouttracking.data;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class WorkoutViewModel extends ViewModel {
    private MutableLiveData<Boolean> back = new MutableLiveData<Boolean>();
    private MutableLiveData<Boolean> start = new MutableLiveData<Boolean>();
    private MutableLiveData<String> end = new MutableLiveData<String>();
    private int sumDistance = 0;

    private List<LatLng> latlngList = new ArrayList<>();
    private List<Location> locationList = new ArrayList<>();

    public void onClickBack() {
        getBack().postValue(true);
    }

    public void onClickStart() {
        getStart().postValue(true);
    }

    public void onClickEnd() {
        getEnd().postValue(sumDistance + " M");
    }

    public MutableLiveData<Boolean> getBack() {
        return back;
    }

    public MutableLiveData<Boolean> getStart() {
        this.sumDistance = 0;
        this.latlngList.clear();
        this.locationList.clear();
        return start;
    }

    public MutableLiveData<String> getEnd() {
        sumDistance();
        return end;
    }

    public void setLatLng(LatLng item) {
        if (!latlngList.contains(item)) {
            this.latlngList.add(item);
        }
    }

    public void setLocation(Location item) {
        if (!locationList.contains(item)) {
            this.locationList.add(item);
        }
    }

    public List<LatLng> getLatLngList() {
        return latlngList;
    }

    public String sumDistance() {
        for (int i = 0; i < locationList.size() - 1; i++) {
            Location distance1 = locationList.get(i);
            Location distance2 = locationList.get(i + 1);
            int diff = Math.round(distance1.distanceTo(distance2));
            sumDistance += diff;
        }
        // Log.e("DISTANCE", sumDistance + "");
        return sumDistance + " M";
    }
}
