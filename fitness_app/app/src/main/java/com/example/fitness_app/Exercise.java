package com.example.fitness_app;

public class Exercise {
    private String Name;
    private String BodyPart;

    public Exercise(String name, String bodypart)
    {
        this.Name = name;
        this.BodyPart = bodypart;
    }

    public String getBodyPart() {
        return BodyPart;
    }


    public String getName() {
        return Name;
    }


}
