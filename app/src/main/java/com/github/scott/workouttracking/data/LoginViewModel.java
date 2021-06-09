package com.github.scott.workouttracking.data;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    public String id = "";
    public String password = "";
    public int gender = 1; // 1: man; 2: woman

    public void onClickJoin() {
        Log.e("TTTTT", "id: " + id + "     pwd: " + password + "    gender: " + gender);
    }

}
