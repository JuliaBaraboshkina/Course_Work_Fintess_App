package com.example.fitness_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DayActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public DayExerciseAdapter workoutExerciseAdapter;
    String date_clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        initActivity();
    }

    public void initActivity()
    {
        recyclerView = findViewById(R.id.recycler_view_day);
        Intent mIntent = getIntent();
        Bundle extras = mIntent.getExtras();
        date_clicked = extras.getString("date");
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = parser.parse(date_clicked);
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM YYYY");
            String formated_date = formatter.format(date);
            getSupportActionBar().setTitle(formated_date);

            ArrayList<WorkoutExercise> Today_Execrises = new ArrayList<WorkoutExercise>();

            for(int i = 0; i < MainActivity.Workout_Days.size(); i++)
            {
                if(date_clicked.equals(MainActivity.Workout_Days.get(i).getDate()))
                {
                    Today_Execrises = MainActivity.Workout_Days.get(i).getExercises();
                }
            }
            workoutExerciseAdapter = new DayExerciseAdapter(this, Today_Execrises);
            recyclerView.setAdapter(workoutExerciseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        } catch (ParseException e) {
            System.out.println(" ");
        }

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        initActivity();
    }
}