package com.example.fitness_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.ArrayList;

public class AddExerciseActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public String exercise_name;
    public static ArrayList<WorkoutSet> Todays_Exercise_Sets = new ArrayList<WorkoutSet>();
    public AddExerciseWorkoutSetAdapter workoutSetAdapter2;
    public static int Clicked_Set = 0;
    public static EditText et_reps;
    public static EditText et_weight;
    public ImageButton plus_reps;
    public ImageButton minus_reps;
    public ImageButton plus_weight;
    public ImageButton minus_weight;
    public Button bt_save;
    public Button bt_clear;

    public long START_TIME_IN_MILLIS = 180000;
    public CountDownTimer countDownTimer;
    public boolean TimerRunning;
    public long TimeLeftInMillis = START_TIME_IN_MILLIS;

    // Для таймера
    public EditText et_seconds;
    public ImageButton minus_seconds;
    public ImageButton plus_seconds;
    public Button bt_start;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        et_reps = findViewById(R.id.et_reps);
        et_weight = findViewById(R.id.et_seconds);
        plus_reps = findViewById(R.id.plus_reps);
        minus_reps = findViewById(R.id.minus_reps);
        plus_weight = findViewById(R.id.plus_weight);
        minus_weight = findViewById(R.id.minus_weight);
        bt_clear = findViewById(R.id.bt_clear);
        bt_save = findViewById(R.id.bt_save);

        initActivity();
        initrecyclerView();
    }
    public void clickSave(View view)
    {
        if(et_reps.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Ошибка! заполните пропуски",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Double reps = Double.parseDouble(et_reps.getText().toString());
            Double weight = Double.parseDouble(et_weight.getText().toString());
            WorkoutSet workoutSet = new WorkoutSet(MainActivity.date_selected,exercise_name, MainActivity.getExerciseCategory(exercise_name),reps,weight);

            if(reps == 0 || reps < 0 || weight < 0)
            {
                Toast.makeText(getApplicationContext(),"Ошибка ввода данных!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Нахождение если workout day уже есть
                int position = MainActivity.getDayPosition(MainActivity.date_selected);

                // если workout day существует
                if(position >= 0)
                {
                    MainActivity.Workout_Days.get(position).addSet(workoutSet);
                }
                else
                {
                    WorkoutDay workoutDay = new WorkoutDay();
                    workoutDay.addSet(workoutSet);
                    MainActivity.Workout_Days.add(workoutDay);
                }

                updateTodaysExercises();
            }
        }
        AddExerciseActivity.Clicked_Set = Todays_Exercise_Sets.size()-1;
    }

    public void clickClear(View view)
    {
        if(Todays_Exercise_Sets.isEmpty())
        {
            bt_clear.setText("Нет данных");
            et_reps.setText("");
            et_weight.setText("");
        }
        else
        {
            LayoutInflater inflater = LayoutInflater.from(this);
            View view1 = inflater.inflate(R.layout.delete_set_dialog,null);
            AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view1).create();

            Button bt_yes = view1.findViewById(R.id.bt_yes3);
            Button bt_no = view1.findViewById(R.id.bt_no3);

            bt_no.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    alertDialog.dismiss();
                }
            });
            bt_yes.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    WorkoutSet to_be_removed_set = Todays_Exercise_Sets.get(Clicked_Set);

                    //Нахождение подхода и удаление
                    for(int i = 0; i < MainActivity.Workout_Days.size(); i++)
                    {
                        if(MainActivity.Workout_Days.get(i).getSets().contains(to_be_removed_set))
                        {
                            //Если последний подход,то удаляется весь объект
                            if(MainActivity.Workout_Days.get(i).getSets().size() == 1)
                            {
                                MainActivity.Workout_Days.remove(MainActivity.Workout_Days.get(i));
                            }
                            else
                            {
                                MainActivity.Workout_Days.get(i).removeSet(to_be_removed_set);
                                break;
                            }

                        }
                    }

                    updateTodaysExercises();
                    alertDialog.dismiss();
                    AddExerciseActivity.Clicked_Set = Todays_Exercise_Sets.size()-1;
                }
            });
            alertDialog.show();
        }
    }
    public static void UpdateViewOnClick()
    {
        WorkoutSet clicked_set = Todays_Exercise_Sets.get(AddExerciseActivity.Clicked_Set);
        et_weight.setText(clicked_set.getWeight().toString());
        et_reps.setText(String.valueOf(clicked_set.getReps().intValue()));


    }
    @Override
    protected void onStop() {
        super.onStop();
        MainActivity.sortWorkoutDaysDate();
        MainActivity.saveWorkoutData(getApplicationContext());
    }
    public void clickPlusWeight(View view)
    {
        if(!et_weight.getText().toString().isEmpty())
        {
            Double weight = Double.parseDouble(et_weight.getText().toString());
            weight = weight + 1;
            et_weight.setText(weight.toString());
        }
        else
        {
            et_weight.setText("0.0");
        }

    }
    public void clickPlusReps(View view)
    {
        if(!et_reps.getText().toString().isEmpty())
        {
            int reps = Integer.parseInt(et_reps.getText().toString());
            reps = reps + 1;
            et_reps.setText(String.valueOf(reps));
        }
        else
        {
            et_reps.setText("0");
        }

    }

    public void clickMinusWeight(View view)
    {
        if(!et_weight.getText().toString().isEmpty())
        {
            Double weight = Double.parseDouble(et_weight.getText().toString());
            weight = weight - 1;
            if(weight < 0)
            {
                weight = 0.0;
            }
            et_weight.setText(weight.toString());
        }
    }

    public void clickMinusReps(View view)
    {
        if(!et_reps.getText().toString().isEmpty())
        {
            int reps = Integer.parseInt(et_reps.getText().toString());
            reps = reps - 1;
            if(reps < 0)
            {
                reps = 0;
            }
            et_reps.setText(String.valueOf(reps));
        }

    }
    public void initActivity()
    {
        Intent in = getIntent();
        exercise_name = in.getStringExtra("exercise");
        getSupportActionBar().setTitle(exercise_name);
    }

    public void updateTodaysExercises()
    {
        Todays_Exercise_Sets.clear();
        for(int i = 0; i < MainActivity.Workout_Days.size(); i++)
        {
            // Если дата выбрана
            if(MainActivity.Workout_Days.get(i).getDate().equals(MainActivity.date_selected))
            {
                for(int j  = 0; j < MainActivity.Workout_Days.get(i).getSets().size(); j++)
                {
                    //  Если упражнение выбрано
                    if(exercise_name.equals(MainActivity.Workout_Days.get(i).getSets().get(j).getExercise()))
                    {
                        Todays_Exercise_Sets.add(MainActivity.Workout_Days.get(i).getSets().get(j));
                    }
                }
            }
        }
        if(Todays_Exercise_Sets.isEmpty())
        {
            bt_clear.setText("Нет данных");
        }
        else
        {
            bt_clear.setText("Удалить");
        }
        workoutSetAdapter2.notifyDataSetChanged();

    }
    public void initrecyclerView()
    {
        Todays_Exercise_Sets.clear();

        // Нахождения подходов для определенного дня и упражнения
        for(int i = 0; i < MainActivity.Workout_Days.size(); i++)
        {
            if(MainActivity.Workout_Days.get(i).getDate().equals(MainActivity.date_selected))
            {
                for(int j  = 0; j < MainActivity.Workout_Days.get(i).getSets().size(); j++)
                {
                    if(exercise_name.equals(MainActivity.Workout_Days.get(i).getSets().get(j).getExercise()))
                    {
                        Todays_Exercise_Sets.add(MainActivity.Workout_Days.get(i).getSets().get(j));
                    }
                }
            }
        }
        recyclerView = findViewById(R.id.recycler_view);
        workoutSetAdapter2 = new AddExerciseWorkoutSetAdapter(this,Todays_Exercise_Sets);
        recyclerView.setAdapter(workoutSetAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initEditTexts();

        if(Todays_Exercise_Sets.isEmpty())
        {
            bt_clear.setText("Нет данных");
        }
        else
        {
            bt_clear.setText("Удалить");
        }
        AddExerciseActivity.Clicked_Set = Todays_Exercise_Sets.size() - 1;

    }

    public void initEditTexts()
    {
        Double max_weight = 0.0;
        int max_reps = 0;

        if(max_reps == 0 || max_weight == 0.0)
        {
            et_reps.setText("");
            et_weight.setText("");
        }else
        {
            et_reps.setText(String.valueOf(max_reps));
            et_weight.setText(max_weight.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_exercise_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.timer)
        {
            LayoutInflater inflater = LayoutInflater.from(AddExerciseActivity.this);
            View view = inflater.inflate(R.layout.timer_dialog,null);
            AlertDialog alertDialog = new AlertDialog.Builder(AddExerciseActivity.this).setView(view).create();

            et_seconds = view.findViewById(R.id.et_seconds);
            minus_seconds = view.findViewById(R.id.minus_seconds);
            plus_seconds = view.findViewById(R.id.plus_seconds);
            bt_start = view.findViewById(R.id.bt_start);
            if(!TimerRunning)
            {
                loadSeconds();
            }
            else
            {
                updateCountDownText();
            }

            bt_start.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(TimerRunning)
                    {
                        pauseTimer();
                    }
                    else
                    {
                        saveSeconds();
                        startTimer();
                    }

                }
            });

            minus_seconds.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(!et_seconds.getText().toString().isEmpty())
                    {
                        Double seconds  = Double.parseDouble(et_seconds.getText().toString());
                        seconds = seconds - 1;
                        if(seconds < 0)
                        {
                            seconds = 0.0;
                        }
                        int seconds_int = seconds.intValue();
                        et_seconds.setText(String.valueOf(seconds_int));
                    }
                }
            });

            plus_seconds.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(!et_seconds.getText().toString().isEmpty())
                    {
                        Double seconds  = Double.parseDouble(et_seconds.getText().toString());
                        seconds = seconds + 1;
                        if(seconds < 0)
                        {
                            seconds = 0.0;
                        }
                        int seconds_int = seconds.intValue();
                        et_seconds.setText(String.valueOf(seconds_int));
                    }
                }
            });

            alertDialog.show();
        }


        return super.onOptionsItemSelected(item);
    }
    public void startTimer()
    {
        countDownTimer = new CountDownTimer(TimeLeftInMillis, 1000)
        {
            @Override
            public void onTick(long MillisUntilFinish)
            {
                TimeLeftInMillis = MillisUntilFinish;
                updateCountDownText();
            }

            @Override
            public void onFinish()
            {
                TimerRunning = false;
                bt_start.setText("Начать");
            }
        }.start();

        TimerRunning = true;
        bt_start.setText("Пауза");

    }


    public void loadSeconds()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        String seconds = sharedPreferences.getString("seconds","0");

        START_TIME_IN_MILLIS = Integer.parseInt(seconds) * 1000;
        TimeLeftInMillis = START_TIME_IN_MILLIS;

        et_seconds.setText(seconds);
    }

    public void saveSeconds()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!et_seconds.getText().toString().isEmpty())
        {
            String seconds = et_seconds.getText().toString();

            START_TIME_IN_MILLIS = Integer.parseInt(seconds) * 1000;
            TimeLeftInMillis = START_TIME_IN_MILLIS;

            editor.putString("seconds",et_seconds.getText().toString());
            editor.apply();
        }
    }

    public void pauseTimer()
    {
        countDownTimer.cancel();
        TimerRunning = false;
        bt_start.setText("Начать");
    }


    public void updateCountDownText()
    {
        int seconds = (int) TimeLeftInMillis / 1000;
        et_seconds.setText(String.valueOf(seconds));
    }

}