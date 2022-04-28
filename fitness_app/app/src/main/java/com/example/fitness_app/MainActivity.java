package com.example.fitness_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.DatePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;



public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , DatePickerDialog.OnDateSetListener{

    public static ArrayList<WorkoutDay> Workout_Days = new ArrayList<WorkoutDay>();
    public static ArrayList<Exercise> KnownExercises = new ArrayList<Exercise>();
    public static String date_selected;
    public static HashMap<String,Double> MaxRepsPRs = new HashMap<String,Double>();
    public static HashMap<String,Double> MaxWeightPRs = new HashMap<String,Double>();
    public ViewPager2 viewPager2;
    public static ArrayList<WorkoutDay> Infinite_Workout_Days = new ArrayList<WorkoutDay>();

    public static final int READ_REQUEST_CODE = 42;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onCreateStuff();
    }


    public void onCreateStuff()
    {
        initActivity();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        Date date_clicked = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date_selected = dateFormat.format(date_clicked);
    }


    // Выбор дня
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
    {
        i1++;
        String year = String.valueOf(i);
        String month;
        String day;

        month = String.format("%02d", i1);
        day = String.format("%02d", i2);

        String date_clicked = year+"-"+month+"-"+day;
        MainActivity.date_selected = date_clicked;

        Intent in = new Intent(getApplicationContext(), DayActivity.class);
        Bundle mBundle = new Bundle();

        mBundle.putString("date", date_clicked);
        in.putExtras(mBundle);
        startActivity(in);

    }

    public void initActivity()
    {

        getSupportActionBar().setTitle("Фитнесс приложение");

            loadWorkoutData();
            loadKnownExercisesData();
            initViewPager();
        }


    @Override
    protected void onRestart()
    {
        super.onRestart();

        // ПолученикWorkoutDays
        loadWorkoutData();

        // Получение извезтных упражнений
        loadKnownExercisesData();

        //После загрузки данных инициализация  ViewPager
        initViewPager();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    // инициализация  ViewPager
    public void initViewPager()
    {
        if(Infinite_Workout_Days.isEmpty())
        {
            Infinite_Workout_Days.clear();

            // Дата начала и дата окончания
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, -5);
            Date startDate = c.getTime();
            c.add(Calendar.YEAR, +10);
            Date endDate = c.getTime();

            // Создание календаря
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime())
            {
                //Преобразование даты в string формат
                String date_str = new SimpleDateFormat("yyyy-MM-dd").format(date);

                WorkoutDay today = new WorkoutDay();
                today.setDate(date_str);
                Infinite_Workout_Days.add(today);
            }
        }

        viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.setAdapter(new ViewPagerWorkoutDayAdapter(this,Infinite_Workout_Days));
        viewPager2.setCurrentItem((Infinite_Workout_Days.size()+1)/2); // Navigate to today
    }



    // Returns индекс дня
    public static int getDayPosition(String Date)
    {
        for(int i = 0; i < MainActivity.Workout_Days.size(); i++)
        {
            if(MainActivity.Workout_Days.get(i).getDate().equals(Date))
            {
               return i;
            }
        }
        return -1;
    }


    // когда File explorer останавливается, эта функция запускается
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                saveWorkoutData(this);
            }
        }
    }

    public void initKnownExercises()
    {
        KnownExercises.clear();
        KnownExercises.add(new Exercise("Альпинист","пресс"));
        KnownExercises.add(new Exercise("Скручивание","пресс"));
        KnownExercises.add(new Exercise("Подъем ног к верху","пресс"));
        KnownExercises.add(new Exercise("Повороты","пресс"));
        KnownExercises.add(new Exercise("Махи ногами","пресс"));
        KnownExercises.add(new Exercise("Подтягивания с отягощением","спина"));
        KnownExercises.add(new Exercise("Тяга гантели в наклоне одной рукой","спина"));
        KnownExercises.add(new Exercise("Тяга блока к поясу","спина"));
        KnownExercises.add(new Exercise("Паучьи сгибания","бицепс"));
        KnownExercises.add(new Exercise("Подъем гантелей с хватом <молоток>","бицепс"));
        KnownExercises.add(new Exercise("Выпады в сторону","ноги"));
        KnownExercises.add(new Exercise("Приседание плие","ноги"));
        KnownExercises.add(new Exercise("Приседания с узкой постановкой ног","ноги"));
        KnownExercises.add(new Exercise("Наклоны со штангой на плечах","ноги"));
        KnownExercises.add(new Exercise("Выпригивания из глубокого полуприседа","ноги"));
    }

    public static void calculatePersonalRecords()
    {
        MainActivity.MaxRepsPRs.clear();
        MainActivity.MaxWeightPRs.clear();

        for(int i = 0; i < MainActivity.KnownExercises.size(); i++)
        {
            MainActivity.MaxRepsPRs.put((MainActivity.KnownExercises.get(i).getName()),0.0);
            MainActivity.MaxWeightPRs.put((MainActivity.KnownExercises.get(i).getName()),0.0);
        }

        for(int i = 0; i < MainActivity.KnownExercises.size(); i++)
        {
            for(int j = 0; j < MainActivity.Workout_Days.size(); j++)
            {
                for(int k = 0; k < MainActivity.Workout_Days.get(j).getExercises().size(); k++)
                {
                    if(MainActivity.Workout_Days.get(j).getExercises().get(k).getExercise().equals(MainActivity.KnownExercises.get(i).getName()))
                    {


                        // Максимальное Количество Повторений
                        if(MaxRepsPRs.get(MainActivity.KnownExercises.get(i).getName()) < (MainActivity.Workout_Days.get(j).getExercises().get(k).getMaxReps()))
                        {
                            MainActivity.Workout_Days.get(j).getExercises().get(k).setMaxRepsPR(true);
                            MaxRepsPRs.put(MainActivity.KnownExercises.get(i).getName(),MainActivity.Workout_Days.get(j).getExercises().get(k).getMaxReps());
                        }

                        // Максимальный вес
                        if(MaxWeightPRs.get(MainActivity.KnownExercises.get(i).getName()) < (MainActivity.Workout_Days.get(j).getExercises().get(k).getMaxWeight()))
                        {
                            MainActivity.Workout_Days.get(j).getExercises().get(k).setMaxWeightPR(true);
                            MaxWeightPRs.put(MainActivity.KnownExercises.get(i).getName(),MainActivity.Workout_Days.get(j).getExercises().get(k).getMaxWeight());
                        }

                    }
                }
            }
        }
    }

    public static void saveWorkoutData(Context ct)
    {
        SharedPreferences sharedPreferences = ct.getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Workout_Days);
        editor.putString("workouts",json);
        editor.apply();
    }
    //известные упражнения
    public void loadWorkoutData()
    {
        if(Workout_Days.isEmpty())
        {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("workouts",null);
            Type type = new TypeToken<ArrayList<WorkoutDay>>(){}.getType();
            Workout_Days = gson.fromJson(json,type);


            // Если ранее сохраненных записей нет, создание нового объекта
            if(Workout_Days == null)
            {
                Workout_Days = new ArrayList<WorkoutDay>();
            }
        }


    }
    public void loadKnownExercisesData()
    {
        if(KnownExercises.isEmpty())
        {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("known_exercises",null);
            Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();
            KnownExercises = gson.fromJson(json,type);


            // Если ранее сохраненных записей нет, создание нового объекта
            if(KnownExercises == null || KnownExercises.isEmpty())
            {
                KnownExercises = new ArrayList<Exercise>();
                initKnownExercises();
            }
        }
    }

    // сортировка пузырьков
    public static void sortWorkoutDaysDate()
    {
        Collections.sort(MainActivity.Workout_Days, new Comparator<WorkoutDay>() {
            @Override
            public int compare(WorkoutDay workoutDay, WorkoutDay t1)
            {
                String date1 = workoutDay.getDate();
                String date2 = t1.getDate();
                Date date_object1 = new Date();
                Date date_object2 = new Date();

                try {
                    date_object1 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
                    date_object2 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
                }
                catch (Exception e)
                {
                    System.out.println(" ");
                }
                return date_object1.compareTo(date_object2);
            }
        });
    }

    public static String getExerciseCategory(String Exercise)
    {
        for(int i = 0; i < KnownExercises.size(); i++)
        {
            if(KnownExercises.get(i).getName().equals(Exercise))
            {
                return KnownExercises.get(i).getBodyPart();
            }
        }
        return "";
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.home)
        {
            Intent in = new Intent(this,MainActivity.class);
            startActivity(in);
            overridePendingTransition(0,0);
        }
        else if(item.getItemId() == R.id.exercises)
        {
            Intent in = new Intent(this,ExercisesActivity.class);
            startActivity(in);
            overridePendingTransition(0,0);
        }
        else if(item.getItemId() == R.id.diary)
        {
            Intent in = new Intent(this,DiaryActivity.class);
            in.putExtra("date", date_selected);
            startActivity(in);
            overridePendingTransition(0,0);
        }
        else if(item.getItemId() == R.id.charts)
        {
            Intent in = new Intent(this,ChartsActivity.class);
            startActivity(in);
            overridePendingTransition(0,0);
        }
        else if(item.getItemId() == R.id.description)
        {
            Intent in = new Intent(this,Instruction.class);
            startActivity(in);
            overridePendingTransition(0,0);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.home)
        {
            viewPager2.setCurrentItem((Infinite_Workout_Days.size()+1)/2);
        }
        return super.onOptionsItemSelected(item);
    }
}
