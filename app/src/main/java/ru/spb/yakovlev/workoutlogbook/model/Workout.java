package ru.spb.yakovlev.workoutlogbook.model;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Workout {
    private String title;
    private String description;
    private int lastRecordReps = 0;
    private Date lastRecordDate;
    private int imageResId;


    public Workout(Context context, int title, int description, int imageResId) {
        this.title = context.getString(title);
        this.description = context.getString(description);
        this.imageResId = imageResId;
        lastRecordDate = new Date();
    }

    public String getFormattedDate() {
        return new SimpleDateFormat("dd MMMM YYYY hh:mm:ss",
                new Locale("ru","RU")).format(lastRecordDate);
    }

    //Геттеры и сеттеры
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLastRecordReps() {
        return lastRecordReps;
    }

    public void setLastRecordReps(int lastRecordReps) {
        if (lastRecordReps > this.lastRecordReps) {
            this.lastRecordReps = lastRecordReps;
            lastRecordDate = new Date();
        }
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
