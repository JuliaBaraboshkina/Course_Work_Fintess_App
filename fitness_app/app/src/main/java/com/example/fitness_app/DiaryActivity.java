package com.example.fitness_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DiaryActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public RecyclerView recyclerView;
    public DiaryAdapter diaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initActivity();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initActivity();
    }


    public void initActivity() {
        Intent in = getIntent();
        String date_clicked = in.getStringExtra("date");

        // Если день существует, scroll до него, иначе scroll до последнего дня
        int position = -1;

        if (date_clicked != null) {
            position = MainActivity.getDayPosition(date_clicked);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.diary);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recycler_view);

        if (MainActivity.Workout_Days.isEmpty()) {
            Toast.makeText(this, "Пустой дневник", Toast.LENGTH_SHORT).show();
        } else {
            MainActivity.calculatePersonalRecords();

            diaryAdapter = new DiaryAdapter(this, MainActivity.Workout_Days);
            recyclerView.setAdapter(diaryAdapter);
            LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true); // last argument (true) is flag for reverse layout
            lm.setReverseLayout(true);
            lm.setStackFromEnd(true);
            recyclerView.setLayoutManager(lm);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
            overridePendingTransition(0, 0);
        } else if (item.getItemId() == R.id.exercises) {
            Intent in = new Intent(this, ExercisesActivity.class);
            startActivity(in);
            overridePendingTransition(0, 0);
        } else if (item.getItemId() == R.id.diary) {
            Intent in = new Intent(this, DiaryActivity.class);
            startActivity(in);
            overridePendingTransition(0, 0);
        } else if (item.getItemId() == R.id.charts) {
            Intent in = new Intent(this, ChartsActivity.class);
            startActivity(in);
            overridePendingTransition(0, 0);
        }
        else if(item.getItemId() == R.id.description)
        {
            Intent in = new Intent(this,Instruction.class);
            startActivity(in);
            overridePendingTransition(0,0);
        }
        return true;
    }

}

