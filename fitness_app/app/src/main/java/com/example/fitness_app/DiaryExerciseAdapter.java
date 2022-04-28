package com.example.fitness_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



// Адаптер для WorkoutExercise
public class DiaryExerciseAdapter extends RecyclerView.Adapter<DiaryExerciseAdapter.MyViewHolder> {

    Context ct;
    ArrayList<WorkoutExercise> Exercises;

    public DiaryExerciseAdapter(Context ct, ArrayList<WorkoutExercise> Exercises)
    {
        this.ct = ct;
        this.Exercises = new ArrayList<>(Exercises);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(this.ct);
        View view = inflater.inflate(R.layout.diary_exercise_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        // Получение названия упражнения
        String exercise_name = Exercises.get(position).getExercise();

        // Изменение TextView
        holder.tv_exercise_name.setText(exercise_name);
        int sets = (int)Math.round(Exercises.get(position).getTotalSets());
        holder.sets.setText(String.valueOf(sets));

        ArrayList<String> Records = initializePersonalRecordIcon(holder,position);

    }

    public ArrayList<String> initializePersonalRecordIcon(MyViewHolder holder, int position)
    {
        int records = 0;
        ArrayList<String> Records = new ArrayList<>();


        if(Exercises.get(position).isMaxRepsPR())
        {
            holder.pr_button.setVisibility(View.VISIBLE);
            records++;
            Records.add("Maximum Repetitions PR");
        }
        if(Exercises.get(position).isMaxWeightPR())
        {
            holder.pr_button.setVisibility(View.VISIBLE);
            records++;
            Records.add("Maximum Weight PR");
        }
        else
        {
            holder.pr_button.setVisibility(View.GONE);
        }

        if(records > 1)
        {
            holder.pr_button.setImageResource(R.drawable.progress);
            holder.pr_button.setVisibility(View.VISIBLE);
        }

        return Records;
    }

    @Override
    public int getItemCount()
    {
        return this.Exercises.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder
    {
        TextView tv_exercise_name;
        CardView cardView;
        TextView sets;
        ImageButton pr_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_exercise_name = itemView.findViewById(R.id.day);
            cardView = itemView.findViewById(R.id.cardview_exercise);
            sets = itemView.findViewById(R.id.sets);
            pr_button = itemView.findViewById(R.id.pr_button);
        }
    }
}
