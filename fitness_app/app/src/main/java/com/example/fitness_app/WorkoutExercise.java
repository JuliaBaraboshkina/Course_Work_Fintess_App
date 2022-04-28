package com.example.fitness_app;

import java.util.ArrayList;

public class WorkoutExercise {

    private ArrayList<WorkoutSet> Sets;
    private String Date;
    private String Exercise;
    private Double MaxWeight;
    private Double MaxReps;
    private Double TotalReps;
    private Double TotalSets;
    private boolean MaxRepsPR;
    private boolean MaxWeightPR;


    public boolean isMaxRepsPR() {
        return MaxRepsPR;
    }

    public void setMaxRepsPR(boolean maxRepsPR) {
        MaxRepsPR = maxRepsPR;
    }

    public boolean isMaxWeightPR() {
        return MaxWeightPR;
    }

    public void setMaxWeightPR(boolean maxWeightPR) {
        MaxWeightPR = maxWeightPR;
    }

    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }
    public ArrayList<WorkoutSet> getSets() {
        return Sets;
    }
    public void setSets(ArrayList<WorkoutSet> sets) {
        Sets = sets;
    }
    public Double getTotalSets() {
        return TotalSets;
    }
    public void setTotalSets(Double totalSets) {
        TotalSets = totalSets;
    }
    public Double getMaxWeight() {
        return MaxWeight;
    }
    public void setMaxWeight(Double maxWeight) {
        MaxWeight = maxWeight;
    }
    public Double getMaxReps() {
        return MaxReps;
    }
    public void setMaxReps(Double maxReps) {
        MaxReps = maxReps;
    }
    public Double getTotalReps() {
        return TotalReps;
    }
    public void setTotalReps(Double totalReps) {
        TotalReps = totalReps;
    }
    public String getExercise() {
        return Exercise;
    }
    public void setExercise(String exercise) {
        Exercise = exercise;
    }
}
