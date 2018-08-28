package com.mock.fathi.supportrooster.models;

import java.util.ArrayList;
import java.util.List;

public class WorkShifts {
    /*
     * For shifts array, each index represents a shift in a day:
     * - Index 0, First shift of the day
     */
    private List<Employees.Engineers> shifts;

    public WorkShifts(int shiftsPerDay) {
        shifts = new ArrayList<>(shiftsPerDay);
    }

    /*
     * Add new shifts. Counter is used to ensure shifts are assigned properly based on dynamic shifts per day.
     */
    public void addShifts(Employees.Engineers engineer) {
        shifts.add(engineer);
    }

    public List<Employees.Engineers> getShifts() {
        return shifts;
    }
}
