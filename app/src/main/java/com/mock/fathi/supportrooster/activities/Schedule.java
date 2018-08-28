package com.mock.fathi.supportrooster.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.mock.fathi.supportrooster.R;
import com.mock.fathi.supportrooster.models.Employees;
import com.mock.fathi.supportrooster.tools.GenerateSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Schedule extends AppCompatActivity {
    private List<Employees.Engineers> engineersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        engineersList = (List<Employees.Engineers>)getIntent().getSerializableExtra("engineers");
        // Get a reference for the week view in the layout.
        WeekView mWeekView = (WeekView) findViewById(R.id.weekView);
        final List<WeekViewEvent> weekViewEvents = new GenerateSchedule(engineersList).getGeneratedScheduleAsWeekViewEvent();

        MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                if (newMonth == weekViewEvents.get(0).getStartTime().get(Calendar.MONTH))
                    return weekViewEvents;
                else {
                    return new ArrayList<WeekViewEvent>();
                }
            }
        };

        mWeekView.setMonthChangeListener(mMonthChangeListener);
    }
}
