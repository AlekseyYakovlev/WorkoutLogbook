package ru.spb.yakovlev.workoutlogbook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.spb.yakovlev.workoutlogbook.R;
import ru.spb.yakovlev.workoutlogbook.model.Workout;
import ru.spb.yakovlev.workoutlogbook.model.WorkoutList;


public class WorkoutListFragment extends Fragment {
    private static final String TAG = "MainActivity";
    RecyclerView workoutRecyclerView;
    WorkoutAdapter adapter;
    List<Workout> workouts;

    OnWorkoutSelectedListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnWorkoutSelectedListener {
        void onWorkoutSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnWorkoutSelectedListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workouts = WorkoutList.getInstance(getContext()).getWorkouts();
        adapter = new WorkoutAdapter(workouts, getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment_layout, container, false);

        workoutRecyclerView = view.findViewById(R.id.workout_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        workoutRecyclerView.setAdapter(adapter);
        workoutRecyclerView.setLayoutManager(layoutManager);
        return view;
    }


    class WorkoutAdapter extends RecyclerView.Adapter<WorkoutViewHolder> {
        List<Workout> workouts;
        Context context;

        public WorkoutAdapter(List<Workout> workouts, Context context) {
            this.workouts = workouts;
            this.context = context;
        }

        @Override
        public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_list_item,
                    parent, false);
            return new WorkoutViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final WorkoutViewHolder holder, int position) {
            Workout workout = workouts.get(position);
            holder.titleText.setText(workout.getTitle());
            holder.descriptionTextView.setText(workout.getDescription());
            holder.cardView.setOnClickListener(v -> mCallback.onWorkoutSelected(position));
        }

        @Override
        public int getItemCount() {
            return (workouts != null) ? workouts.size() : 0;
        }
    }


    static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleText;
        TextView descriptionTextView;

        public WorkoutViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_view_card_view);
            titleText = itemView.findViewById(R.id.item_view_title_text_view);
            descriptionTextView = itemView.findViewById(R.id.item_view_description_text_view);
        }
    }

}
