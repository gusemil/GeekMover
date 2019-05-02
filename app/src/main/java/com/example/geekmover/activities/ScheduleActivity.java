package com.example.geekmover.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.geekmover.R;
import com.example.geekmover.Schedule;
import com.example.geekmover.UserData;
import com.example.geekmover.data.Day;
import com.example.geekmover.data.IExercise;
import com.example.geekmover.data.Jog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<Day> days = UserData.getInstance().getSchedule().getDays();

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        CalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setMinDate(Calendar.getInstance().getTime().getTime());
        calendarView.setMaxDate(days.get(days.size()-1).getDate().getTime());

        final TextView dateText = findViewById(R.id.dateText);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

                calendar.set(year, month, dayOfMonth);

                for (Day day : days) {
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

                    if (fmt.format(day.getDate()).equals(fmt.format(calendar.getTime()))) {
                        dateText.setText("Day has been planned\n" + calendar.getTime().toString());

                        if(day.getExercises().length > 0) {
                            String text = "Exercises for the day:\n";
                            for(IExercise exercise : day.getExercises())
                                text += exercise.toString() + "\n";

                            dateText.setText(text);
                        }
                        else{
                            dateText.setText("No exercises for this day, rest well!");
                        }

                        break;
                    }else{
                        dateText.setText("Day has not been planned");
                    }
                }

            }
        });

    }
}