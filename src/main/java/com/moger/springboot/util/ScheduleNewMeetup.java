package com.moger.springboot.util;

import java.util.Calendar;
import java.util.Date;

public class ScheduleNewMeetup {
    public String newMeetUpTime(){
        Date date = new Date(); // Current date and time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return "Starts in " + hour + " hours promptly";
    }
}
