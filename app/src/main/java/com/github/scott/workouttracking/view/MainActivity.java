package com.github.scott.workouttracking.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.scott.workouttracking.R;
import com.github.scott.workouttracking.data.History;
import com.github.scott.workouttracking.data.MainViewModel;
import com.github.scott.workouttracking.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getMoveWorkout().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bool) {
                Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
                intent.putExtra("user", viewModel.getUser());
                startActivityForResult(intent, 1);
            }
        });
        viewModel.setUser(getIntent().getStringExtra("user"));
        viewModel.getReadData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                ((ListAdapter) binding.recyclerView.getAdapter()).setList(viewModel.getHistories());

                if (viewModel.getHistories().isEmpty()) {
                    binding.empty.setVisibility(View.VISIBLE);
                } else {
                    binding.empty.setVisibility(View.GONE);
                }
            }
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);

        adapter = new ListAdapter();
        binding.recyclerView.setAdapter(adapter);

        viewModel.getList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                viewModel.getList();
            }
        }
    }
}