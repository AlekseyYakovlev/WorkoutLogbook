package ru.spb.yakovlev.workoutlogbook.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
            holder.lastRecordReps.setText(String.valueOf(workout.getLastRecordReps()));
            holder.lastRecordDate.setText(workout.getFormattedDate());


            holder.menuIcon.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_detail, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.action_show: {
                            Uri address = Uri.parse("http://sportwiki.to/%D0%9E%D1%82%D0%B6%D0%B8%D0%BC%D0%B0%D0%BD%D0%B8%D1%8F");
                            Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);
                            startActivity(openLinkIntent);
                            return true;
                        }
                        case R.id.action_share: {
                            String sbQuestion = String.format(getString(R.string.snack_bar_share_question), workout.getTitle());
                            String messageText = String.format(getString(R.string.messageText), workout.getTitle(), workout.getLastRecordReps());
                            String messageSubject = String.format(getString(R.string.messageSubject), workout.getTitle());
                            Snackbar.make(getActivity().getCurrentFocus(), sbQuestion, Snackbar.LENGTH_LONG)
                                    .setAction(getText(R.string.action_share), v1 -> {
                                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                                        sendIntent.setType("text/plain");
                                        sendIntent.putExtra("EXTRA_TEXT", messageText);
                                        sendIntent.putExtra("EXTRA_SUBJECT", messageSubject);
                                        startActivity(sendIntent);
                                        Toast.makeText(context, messageText, Toast.LENGTH_SHORT).show();
                                    })
                                    .setActionTextColor(Color.BLUE)
                                    .show();
                            return true;
                        }
                        default:
                            return false;
                    }
                });
            });
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
        TextView lastRecordReps;
        TextView lastRecordDate;
        ImageView menuIcon;

        public WorkoutViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_view_card_view);
            titleText = itemView.findViewById(R.id.item_view_title_text_view);
            descriptionTextView = itemView.findViewById(R.id.item_view_description_text_view);
            lastRecordReps = itemView.findViewById(R.id.item_view_reps_text_view);
            lastRecordDate = itemView.findViewById(R.id.item_view_record_date_text_view);
            menuIcon = itemView.findViewById(R.id.item_view_context_menu);
        }
    }

}
