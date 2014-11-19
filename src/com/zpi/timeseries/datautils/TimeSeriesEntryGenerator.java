package com.zpi.timeseries.datautils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by kamil on 16.11.14.
 */
public class TimeSeriesEntryGenerator {

    private ArrayList<TimeSeriesEntry> entries = new ArrayList<TimeSeriesEntry>();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private Random random = new Random();
    private String date;

    public ArrayList<TimeSeriesEntry> generateTimeSeriesValue(GregorianCalendar start, GregorianCalendar end) {
        GregorianCalendar dateEntry = start;
        int amountEntry;

        while(dateEntry.before(end)) {
            amountEntry = (random.nextInt(Integer.MAX_VALUE) % 10) * 4;
            amountEntry = amountEntry * (dateEntry.get(Calendar.DAY_OF_WEEK)  == Calendar.SATURDAY || dateEntry.get(Calendar.DAY_OF_WEEK)  == Calendar.FRIDAY  ? 4 : 1);

            sdf.setCalendar(dateEntry);
            date = sdf.format(dateEntry.getTime());

            entries.add(new TimeSeriesEntry(date, Integer.toString(amountEntry), Integer.toString(dateEntry.get(Calendar.WEEK_OF_YEAR)), Integer.toString(dateEntry.get(Calendar.MONTH))));
            dateEntry.add(Calendar.DAY_OF_MONTH,1);
        }

        return entries;
    }

}
