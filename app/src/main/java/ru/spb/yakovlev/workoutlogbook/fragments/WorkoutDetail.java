package ru.spb.yakovlev.workoutlogbook.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import ru.spb.yakovlev.workoutlogbook.R;
import ru.spb.yakovlev.workoutlogbook.model.Workout;
import ru.spb.yakovlev.workoutlogbook.model.WorkoutList;


public class WorkoutDetail extends Fragment {
    //TODO: Починить перенос значений из фрагмента WorkoutDetails в карточки ресайкла
    // Создать интерфейс и через него возвращать?
    // Или сделать через БД?


    private static final String TAG = "WorkoutDetailFragment";
    private static final String WORKOUT_INDEX = "WORKOUT_INDEX";
    private static final String REP_COUNT_STATE = "WORK_INDEX_STATE";

    public final static String ARG_POSITION = "position";

    private Button plusButton;
    private Button minusButton;
    private Button saveButton;
    private TextView repeatCountTextView;
    private SeekBar weightSeekBar;
    private TextView weightTextView;

    private Workout workout;

    private int repeatCount;
    private int currentIndex;


    int mCurrentPosition = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    private void fetchWorkout(View view) {

    }

    private void initView(View view) {
        repeatCountTextView = view.findViewById(R.id.detail_workout_repeat_count_text_view);
        repeatCountTextView.setText(String.valueOf(repeatCount));

        minusButton = view.findViewById(R.id.detail_workout_minus_button);
        plusButton = view.findViewById(R.id.detail_workout_plus_button);
        saveButton = view.findViewById(R.id.detail_workout_save_button);

        TextView title = view.findViewById(R.id.detail_workout_title);
        title.setText(workout.getTitle());

        TextView description = view.findViewById(R.id.detail_workout_description_text_view);
        description.setText(workout.getDescription());

        TextView lastRecordsRepeats = view.findViewById(R.id.detail_workout_last_record_text_view);
        lastRecordsRepeats.setText(String.format(getString(R.string.last_record), workout.getLastRecordReps()));
        TextView lastRecordsDate = view.findViewById(R.id.detail_workout_last_record_date_text_view);
        lastRecordsDate.setText(String.format(getString(R.string.last_record_date), workout.getFormattedDate()));

        weightSeekBar = view.findViewById(R.id.detail_workout_seek_bar);
        weightTextView = view.findViewById(R.id.weight_title_text_view);

        ImageView exercisePic = view.findViewById(R.id.detail_workout_image_view);
        exercisePic.setImageResource(workout.getImageResId());

        addListeners();
    }

    private void addListeners() {
        plusButton.setOnClickListener(v -> {
            repeatCount++;
            repeatCountTextView.setText(String.valueOf(repeatCount));
        });

        minusButton.setOnClickListener(v -> {
            repeatCount--;
            if (repeatCount < 0) repeatCount = 0;
            repeatCountTextView.setText(String.valueOf(repeatCount));
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateWorkoutView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateWorkoutView(mCurrentPosition);
        }
    }


    public void updateWorkoutView(int position) {

        workout = WorkoutList.getInstance(getContext()).getWorkouts().get(position);
        initView(getActivity().findViewById(R.id.container_workout_detail_scroll));
        addListeners();

        mCurrentPosition = position;
    }


}
