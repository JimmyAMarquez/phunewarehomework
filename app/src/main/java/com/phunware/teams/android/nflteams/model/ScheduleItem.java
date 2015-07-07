package com.phunware.teams.android.nflteams.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScheduleItem implements Serializable {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss Z";
    private static final String HR_DATE_FORMAT = "EEE MM/dd KK:mm a";
    private static final String HOURS_MINUTES_FORMAT = "KK:mm a";
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    @SerializedName("start_date")
    private Date mStartDate;
    @SerializedName("end_date")
    private Date mEndDate;

    public ScheduleItem() {

    }

    public ScheduleItem(Date startDate, Date endDate) {
        mStartDate = startDate;
        mEndDate = endDate;
    }

    public ScheduleItem(String startDate, String endDate) throws ParseException {
        mStartDate = FORMATTER.parse(startDate);
        mEndDate = FORMATTER.parse(endDate);
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public void setStartDate(String startDate) throws ParseException {
        mStartDate = FORMATTER.parse(startDate);
    }

    public void setEndDate(String endDate) throws ParseException {
        mEndDate = FORMATTER.parse(endDate);
    }

    public String getStartDateString() {
        return FORMATTER.format(mStartDate);
    }

    public String getEndDateString() {
        return FORMATTER.format(mEndDate);
    }

    public String getDecoratedDate() {
        //Simple Date Format will use device timezone (default timezone) to format the dates
        //this will make it easy for the app to show the schedule in the proper timezone
        SimpleDateFormat startDateFormat = new SimpleDateFormat(HR_DATE_FORMAT);
        SimpleDateFormat endDateFormat = new SimpleDateFormat(HOURS_MINUTES_FORMAT);
        return startDateFormat.format(getStartDate()) + " to " + endDateFormat.format(getEndDate());
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof ScheduleItem) {
            result = mStartDate.equals(((ScheduleItem) o).getStartDate())
                    && mEndDate.equals(((ScheduleItem) o).getEndDate());
        }
        return result;
    }

    @Override
    public int hashCode() {
        String s = getStartDateString() + getEndDateString();
        return s.hashCode();
    }

}