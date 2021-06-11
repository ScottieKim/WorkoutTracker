package com.github.scott.workouttracking.view;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.scott.workouttracking.R;
import com.github.scott.workouttracking.data.LoginViewModel;
import com.github.scott.workouttracking.databinding.ActivitySignupBindingImpl;

public class SignupActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    private ActivitySignupBindingImpl binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getBack().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        binding.setViewModel(viewModel);

        binding.RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                viewModel.gender = checkedId == binding.radioButtonMan.getId() ? 1 : 2;
            }
        });

        viewModel.getShowToast().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(SignupActivity.this, s, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
