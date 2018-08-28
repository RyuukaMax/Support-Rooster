package com.mock.fathi.supportrooster.tools;

import android.graphics.Color;

import com.alamkanak.weekview.WeekViewEvent;
import com.mock.fathi.supportrooster.models.Employees.Engineers;
import com.mock.fathi.supportrooster.models.WorkShifts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GenerateSchedule {
    private static final int NUM_OF_WEEKS = 2;
    private static final int NUM_OF_DAYS_PER_WEEK = 5;
    private static final int SHIFTS_PER_DAY = 2;
    private long weekViewEventCounter = 0;
    private List<Engineers> engList;
    private List<WeekViewEvent> weekViewEvents = new ArrayList<>();
    // Saves the number of shifts each engineer has been assigned
    private HashMap<Engineers, Integer> shiftCounter = new HashMap<>();
    /*
     * For schedule array, each index represents a day in the subsequent week:
     * - index 0, Week 1 Day 1
     * - index 4, Week 1 Day 5
     * - index 6, Week 2 Day 2
     */
    private List<WorkShifts> schedule = new ArrayList<>();

    public GenerateSchedule(List<Engineers> engineersList) {
        this.engList = new ArrayList<>(engineersList);

        int total_workdays = NUM_OF_DAYS_PER_WEEK * NUM_OF_WEEKS;
        int i = 0;
        int total_hours_per_shift = 24 / SHIFTS_PER_DAY;

        // Calendar object
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, 7);

        while(i < total_workdays) {
            try {
                GenerateScheduleDaily(cal, total_hours_per_shift);
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                // Bad algorithm. Redo schedule
                i = 0;
                this.weekViewEventCounter = 0;
                this.engList.clear();
                this.engList = new ArrayList<>(engineersList);
                this.shiftCounter.clear();
                this.schedule.clear();
                this.weekViewEvents.clear();

                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                cal.set(Calendar.HOUR, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.add(Calendar.DATE, 7);
            }
        }
    }

    public List<WeekViewEvent> getGeneratedScheduleAsWeekViewEvent() {
        return weekViewEvents;
    }

    private void GenerateScheduleDaily(Calendar cal, int hoursPerShift) throws ConcurrentModificationException, IndexOutOfBoundsException, IllegalArgumentException {
        int random = 0;
        List<Engineers> engListDaily = new ArrayList<>(this.engList);
        WorkShifts workShifts = new WorkShifts(SHIFTS_PER_DAY);

        // Remove engineers who has worked on previous day
        shiftOncePerConsecutiveDays.applyRule(engListDaily, null);

        for(int i = 0; i < SHIFTS_PER_DAY; i++) {
            if(engListDaily.size() > 0) {
                random = new Random().nextInt(engListDaily.size());
                Engineers engineer = engListDaily.get(random);
                workShifts.addShifts(engineer);

                Calendar start = (Calendar) cal.clone();
                Calendar end = (Calendar) cal.clone();
                end.add(Calendar.HOUR, hoursPerShift);
                WeekViewEvent event = new WeekViewEvent(
                        weekViewEventCounter,
                        engineer.getName() + "- Shift:" + i,
                        start, end);
                // Create random color
                Random rand = new Random();
                event.setColor(Color.rgb(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
                weekViewEvents.add(event);
                // Increment days for calendar
                weekViewEventCounter++;
                cal.add(Calendar.HOUR, hoursPerShift);

                // Remove engineer who has worked on that day from the daily list
                shiftOncePerDay.applyRule(engListDaily, engineer);

                // Remove engineers who has worked twice on a full rotation
                shiftTwicePerRotation.applyRule(engList, engineer);
            } else {
                throw new IllegalArgumentException();
            }
        }
        schedule.add(workShifts);
    }

    /********* RULES SECTION *********/
    public interface ScheduleRule {
        void applyRule(List<Engineers> list, Object o);
    }

    private ScheduleRule shiftOncePerDay = new ScheduleRule() {
        @Override
        public void applyRule(List<Engineers> list, Object o) {
            Engineers engineer = (Engineers) o;
            list.remove(engineer);
        }
    };

    private ScheduleRule shiftOncePerConsecutiveDays = new ScheduleRule() {
        @Override
        public void applyRule(List<Engineers> list, Object o) {
            if(schedule.size() > 0) {
                // Get the previous work shift day
                List<Engineers> previousShift = schedule.get(schedule.size() - 1).getShifts();
                // Remove all engineers in previous shift from current list
                list.removeAll(previousShift);
            }
        }
    };

    private ScheduleRule shiftTwicePerRotation = new ScheduleRule() {
        @Override
        public void applyRule(List<Engineers> list, Object o) {
            Engineers engineer = (Engineers) o;
            // Increments shift counter every time an engineer is assigned to a shift
            // If an engineer has a shift counter of 2, remove engineer from list
            if(shiftCounter.containsKey(engineer)) {
                list.remove(engineer);
                shiftCounter.remove(engineer);
            } else {
                shiftCounter.put(engineer, 1);
            }
        }
    };
}


