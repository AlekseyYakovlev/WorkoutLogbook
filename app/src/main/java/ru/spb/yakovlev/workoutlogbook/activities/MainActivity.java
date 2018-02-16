package ru.spb.yakovlev.workoutlogbook.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.spb.yakovlev.workoutlogbook.R;
import ru.spb.yakovlev.workoutlogbook.fragments.WorkoutDetail;
import ru.spb.yakovlev.workoutlogbook.fragments.WorkoutListFragment;

public class MainActivity extends AppCompatActivity implements WorkoutListFragment.OnWorkoutSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkoutListFragment listFragment = new WorkoutListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_main, listFragment);
        fragmentTransaction.commit();

    }

    public void onWorkoutSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout
        WorkoutDetail workoutFrag = (WorkoutDetail)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container_workout_detail);

        if (workoutFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            workoutFrag.updateWorkoutView(position);
            //Toast.makeText(getApplicationContext(), "Позиция элемента " +  String.valueOf(position), Toast.LENGTH_SHORT).show();

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            WorkoutDetail newFragment = new WorkoutDetail();
            Bundle args = new Bundle();
            args.putInt(WorkoutDetail.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container_main, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}
