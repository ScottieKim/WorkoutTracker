package com.github.scott.workouttracking.view;

import android.content.Intent;
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
import com.github.scott.workouttracking.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);
        // TextView textView = (TextView)findViewById(R.id.textView);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getShowToast().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getMoveSignup().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean s) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        viewModel.getMoveMain().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(viewModel);

    }
}
