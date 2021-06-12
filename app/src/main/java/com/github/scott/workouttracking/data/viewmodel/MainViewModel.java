package com.github.scott.workouttracking.data.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.scott.workouttracking.common.DBUtil;
import com.github.scott.workouttracking.data.model.History;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainViewModel extends ViewModel {
    private String user = "";
    private MutableLiveData<Boolean> readData = new MutableLiveData<>();
    private List<History> histories = new ArrayList<>();

    private MutableLiveData<Boolean> moveWorkout = new MutableLiveData<Boolean>();

    public void onClickMoveWorkout() {
        getMoveWorkout().postValue(true);
    }

    public MutableLiveData<Boolean> getMoveWorkout() {
        return moveWorkout;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public MutableLiveData<Boolean> getReadData() {
        return readData;
    }

    public void setReadData(MutableLiveData<Boolean> readData) {
        this.readData = readData;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public void setMoveWorkout(MutableLiveData<Boolean> moveWorkout) {
        this.moveWorkout = moveWorkout;
    }

    public void getList() {
        histories.clear();
        DBUtil dbUtil = new DBUtil();
        dbUtil.readData(dbUtil.HISTORY_COLLECTION, new DBUtil.DBListener() {
            @Override
            public void result(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot item : task.getResult()) {
                        Map<String, Object> data = item.getData();

                        String userId = String.valueOf(data.get("user"));
                        String sumDistance = String.valueOf(data.get("sumDistance")) + " M";
                        String averageSpeed = String.valueOf(data.get("averageSpeed")) + " m/s";
                        String calories = String.valueOf(data.get("calories")) + " cal";

                        History history = new History(userId, sumDistance, averageSpeed, calories);
                        histories.add(history);
                    }


                    readData.postValue(true);
                }
            }
        });
    }
}
