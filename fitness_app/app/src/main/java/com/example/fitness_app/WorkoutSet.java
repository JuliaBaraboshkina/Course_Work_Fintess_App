package com.example.fitness_app;

public class WorkoutSet {

    private String Date;
    private String Exercise;
    private String Category;
    private Double Reps;
    private Double Weight;

    public WorkoutSet(String Date, String Exercise, String Category, Double Reps, Double Weight)
    {
        this.Date = Date;
        this.Exercise = Exercise;
        this.Category = Category;
        this.Reps = Reps;
        this.Weight = Weight;
    }
    public void setWeight(Double Weight)
    {
        this.Weight = Weight;
    }

    public String getDate()
    {
        return this.Date;
    }
    public String getExercise()
    {
        return this.Exercise;
    }
    public String getCategory()
    {
        return this.Category;
    }
    public Double getReps()
    {
        return this.Reps;
    }
    public Double getWeight()
    {
        return this.Weight;
    }

}
