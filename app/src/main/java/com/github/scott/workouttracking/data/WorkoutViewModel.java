package com.github.scott.workouttracking.data;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.scott.workouttracking.common.DBUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutViewModel extends ViewModel {
    private DBUtil dbUtil = new DBUtil();
    private MutableLiveData<Boolean> back = new MutableLiveData<Boolean>();
    private MutableLiveData<Boolean> start = new MutableLiveData<Boolean>();
    private MutableLiveData<String> end = new MutableLiveData<String>();
    private MutableLiveData<String> showToast = new MutableLiveData<String>();

    private int sumDistance = 0;
    private int averageSpeed = 0;
    private String user = "";

    private List<LatLng> latlngList = new ArrayList<>();
    private List<Location> locationList = new ArrayList<>();
    private Map<LatLng, Float> speedList = new HashMap<>();

    public void onClickBack() {
        getBack().postValue(false);
    }

    public void onClickStart() {
        getStart().postValue(true);
    }

    public void onClickSave() {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("sumDistance", sumDistance);
        map.put("averageSpeed", averageSpeed);

        dbUtil.readData(dbUtil.MEMBER_COLLECTION, new DBUtil.DBListener() {
            @Override
            public void result(Task<QuerySnapshot> data) {
                for (QueryDocumentSnapshot item : data.getResult()) {
                    Map<String, Object> d = item.getData();
                    if (String.valueOf(d.get("id")).equals(user)) {
                        String gender = String.valueOf(d.get("gender"));

                        int c = 0;
                        if (gender.equals("1")) {
                            c = (8 * sumDistance) / 100;
                        } else {
                            c = (6 * sumDistance) / 100;
                        }
                        map.put("calories", c);

                        dbUtil.saveData(map, dbUtil.HISTORY_COLLECTION);
                        showToast.postValue("기록이 저장되었습니다. ");
                        back.postValue(true);
                        break;
                    }

                }
            }
        });



    }

    public void onClickEnd() {
        sumDistance();
        averageSpeed();
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

    public MutableLiveData<String> getShowToast() {
        return showToast;
    }


    public MutableLiveData<String> getEnd() {
        return end;
    }

    public void setLatLng(LatLng item, float speed) {
        if (!latlngList.contains(item)) {
            this.latlngList.add(item);
        }

        speedList.put(item, speed);
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

    private float averageSpeed() {
        int sum = 0;

        for (LatLng key : speedList.keySet()) {
            sum += speedList.get(key);
        }

        averageSpeed = sum / speedList.keySet().size();
        return averageSpeed;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }
}