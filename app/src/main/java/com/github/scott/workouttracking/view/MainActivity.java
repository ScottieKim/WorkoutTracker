package com.github.scott.workouttracking.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.scott.workouttracking.R;
import com.github.scott.workouttracking.data.MainViewModel;
import com.github.scott.workouttracking.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getMoveWorkout().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bool) {
                Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
                startActivity(intent);
            }
        });
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);
    }
}