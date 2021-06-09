package com.github.scott.workouttracking.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.scott.workouttracking.common.DBUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends ViewModel {
    private final DBUtil dbUtil = new DBUtil();
    private MutableLiveData<String> showToast = new MutableLiveData<>();
    private MutableLiveData<Boolean> moveSignup = new MutableLiveData<>();
    private MutableLiveData<Boolean> moveMain = new MutableLiveData<>();
    public String id = "";
    public String password = "";
    public int gender = 1; // 1: man; 2: woman

    // 회원가입
    public void onClickSignup() {
        Log.e("TTTTT", "id: " + id + "     pwd: " + password + "    gender: " + gender);

        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("password", password);
        data.put("gender", gender);
        dbUtil.saveData(data);
        showToast.postValue("가입이 완료되었습니다.");
    }

    // 로그인
    public void onClickLogin() {

    }

    // 회원가입 화면 이동
    public void onClickMoveSignup() {
        moveSignup.postValue(true);
    }

    public MutableLiveData<String> getShowToast() {
        return showToast;
    }

    public MutableLiveData<Boolean> getMoveSignup() {
        return moveSignup;
    }

    public MutableLiveData<Boolean> getMoveMain() {
        return moveMain;
    }
}
