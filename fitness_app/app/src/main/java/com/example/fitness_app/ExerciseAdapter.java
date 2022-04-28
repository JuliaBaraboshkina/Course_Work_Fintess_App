package com.example.fitness_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Адаптер для Exercise
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> implements Filterable {

    Context ct;
    ArrayList<Exercise> Exercises;
    ArrayList<Exercise> Exercises_Full;
    public ExerciseAdapter(Context ct, ArrayList<Exercise> Exercises)
    {
        this.ct = ct;
        this.Exercises = new ArrayList<>(Exercises);
        this.Exercises_Full = new ArrayList<>(Exercises);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(this.ct);
        View view = inflater.inflate(R.layout.exercise_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.tv_exercise_name.setText(Exercises.get(position).getName());
        holder.tv_exercise_bodypart.setText(Exercises.get(position).getBodyPart());
        holder.cardview_exercise_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent in = new Intent(ct,AddExerciseActivity.class);
                in.putExtra("exercise",holder.tv_exercise_name.getText().toString());
                ct.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return this.Exercises.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence)
        {
            ArrayList<Exercise> Exercises_Filtered = new ArrayList<Exercise>();

            if(charSequence == null || charSequence.length() == 0)
            {
                Exercises_Filtered.addAll(Exercises_Full);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(int i = 0; i < Exercises.size(); i++)
                {
                    if(Exercises.get(i).getName().toLowerCase().contains(filterPattern))
                    {
                        Exercises_Filtered.add(Exercises.get(i));
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = Exercises_Filtered;
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults)
        {
            Exercises.clear();
            Exercises.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends  RecyclerView.ViewHolder implements  AdapterView.OnItemSelectedListener {
        TextView tv_exercise_name;
        TextView tv_exercise_bodypart;
        CardView cardview_exercise_1;
        String new_exercise_category;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_exercise_name = itemView.findViewById(R.id.tv_date);
            tv_exercise_bodypart = itemView.findViewById(R.id.exercise_bodypart);
            cardview_exercise_1 = itemView.findViewById(R.id.cardview_exercise_1);

        }
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            new_exercise_category = adapterView.getItemAtPosition(i).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {
            new_exercise_category = "";
        }
    }
}
