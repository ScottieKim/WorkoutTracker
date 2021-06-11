package com.github.scott.workouttracking.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.scott.workouttracking.common.DBUtil;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends ViewModel {
    private final DBUtil dbUtil = new DBUtil();
    private MutableLiveData<String> showToast = new MutableLiveData<>();
    private MutableLiveData<Boolean> moveSignup = new MutableLiveData<>();
    private MutableLiveData<Boolean> moveMain = new MutableLiveData<>();
    private MutableLiveData<Boolean> back = new MutableLiveData<Boolean>();

    public String id = "";
    public String password = "";
    public int gender = 1; // 1: man; 2: woman

    // 회원가입
    public void onClickSignup() {
        // Log.e("TTTTT", "id: " + id + "     pwd: " + password + "    gender: " + gender);

        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("password", password);
        data.put("gender", gender);
        dbUtil.saveData(data, dbUtil.MEMBER_COLLECTION);
        showToast.postValue("가입이 완료되었습니다.");
    }

    // 로그인
    public void onClickLogin() {
        dbUtil.readData(dbUtil.MEMBER_COLLECTION, new DBUtil.DBListener() {
            @Override
            public void result(Task<QuerySnapshot> task) {
                boolean isSuccess = false;
                for (QueryDocumentSnapshot item : task.getResult()) {
                    Map<String, Object> data = item.getData();
                    if (String.valueOf(data.get("id")).equals(id) && String.valueOf(data.get("password")).equals(password)) {
                        isSuccess = true;
                    }
                }

                if (!isSuccess) {
                    showToast.postValue("로그인이 실패하였습니다. ");
                } else {
                    moveMain.postValue(true);
                }
            }
        });
    }

    // 회원가입 화면 이동
    public void onClickMoveSignup() {
        moveSignup.postValue(true);
    }

    public void onClickBack() {
        getBack().postValue(true);
    }

    public MutableLiveData<Boolean> getBack() {
        return back;
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
