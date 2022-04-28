package com.example.fitness_app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class WorkoutDay {

    private ArrayList<WorkoutExercise> Exercises;
    private ArrayList<WorkoutSet> Sets;
    private String Date;
    private int Reps;

    public WorkoutDay()
    {
        Sets = new ArrayList<WorkoutSet>();
        Exercises = new ArrayList<WorkoutExercise>();
        Date = "0000-00-00";
        Reps = 0;
    }

    public void addSet(WorkoutSet Set)
    {
        this.getSets().add(Set);
        UpdateData();
    }

    public void removeSet(WorkoutSet Set)
    {

        assert this.Sets.size() > 1 : "в этом случае не следует вызывать remove Set, а вместо этого удалять весь объект целиком";
        this.getSets().remove(Set);
        UpdateData();

    }

    public void UpdateData()
    {
        if(Sets.isEmpty())
        {
            Sets.clear();
            Exercises.clear();
            Date = "0000-00-00";
            Reps = 0;
            return;
        }

        Date = Sets.get(0).getDate();

        ArrayList day_sets = Sets;
        ArrayList<WorkoutExercise> Day_Exercises = new ArrayList<WorkoutExercise>();
        Set<String> Exercises_Set = new TreeSet<String>();

        for(int j = 0; j < day_sets.size(); j++)
        {
            WorkoutSet temp_set = (WorkoutSet) day_sets.get(j);
            Exercises_Set.add(temp_set.getExercise());
        }

        Iterator<String> itt = Exercises_Set.iterator();
        while (itt.hasNext())
        {
            String exercise_name = itt.next();
            WorkoutExercise day_exercise = new WorkoutExercise();

            day_exercise.setExercise(exercise_name);
            Double exercise_max_reps = 0.0;
            Double exercise_max_weight = 0.0;
            Double exercise_total_reps = 0.0;
            Double exercise_total_sets = 0.0;
            String exercise_date = "";

            ArrayList<WorkoutSet> exercise_sets = new ArrayList<WorkoutSet>();

            for(int j = 0; j < day_sets.size(); j++)
            {
                WorkoutSet temp_set = (WorkoutSet) day_sets.get(j);
                if(temp_set.getExercise().equals(exercise_name))
                {
                    exercise_total_reps = exercise_total_reps + temp_set.getReps();
                    exercise_total_sets = exercise_total_sets + 1;
                    exercise_sets.add(temp_set);
                    exercise_date = temp_set.getDate();

                    if(temp_set.getReps() > exercise_max_reps)
                    {
                        exercise_max_reps = temp_set.getReps();
                    }
                    if(temp_set.getWeight() > exercise_max_weight)
                    {
                        exercise_max_weight = temp_set.getWeight();
                    }
                }

                day_exercise.setMaxReps(exercise_max_reps);
                day_exercise.setMaxWeight(exercise_max_weight);
                day_exercise.setTotalReps(exercise_total_reps);
                day_exercise.setTotalSets(exercise_total_sets);
                day_exercise.setSets(exercise_sets);
                day_exercise.setDate(exercise_date);
            }
            Day_Exercises.add(day_exercise);
        }

        Exercises = Day_Exercises;
        Reps = 0;

        for(int i = 0; i < Exercises.size(); i++)
        {
            Reps = Reps + (int)Math.round(Exercises.get(i).getTotalReps());
        }

    }


    public ArrayList<WorkoutSet> getSets() {
        return Sets;
    }
    public void setExercises(ArrayList<WorkoutExercise> exercises) {
        this.Exercises = exercises;
        Reps = 0;
        for(int i = 0; i < Exercises.size(); i++)
        {
            Reps = Reps + (int)Math.round(Exercises.get(i).getTotalReps());
        }

    }
    public void setDate(String date) {
        Date = date;
    }
    public String getDate() {
        return Date;
    }
    public ArrayList<WorkoutExercise> getExercises() {
        return Exercises;
    }

}
