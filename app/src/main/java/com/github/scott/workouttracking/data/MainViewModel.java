package com.github.scott.workouttracking.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Boolean> moveWorkout = new MutableLiveData<Boolean>();

    public void onClickMoveWorkout() {
        getMoveWorkout().postValue(true);
    }

    public MutableLiveData<Boolean> getMoveWorkout() {
        return moveWorkout;
    }
}
