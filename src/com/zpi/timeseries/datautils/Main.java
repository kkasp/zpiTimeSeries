package com.zpi.timeseries.datautils;

import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {
        TimeSeriesDataGenerator generator = new TimeSeriesDataGenerator();
        generator.generateNewEntries(new GregorianCalendar(2013, 0, 1), new GregorianCalendar(2013, 11, 31));

        generator.generateCSVData();
        generator.generateXMLData();
        TimeSeriesDataReader reader = new TimeSeriesDataReader();
        for(TimeSeriesEntry entry : reader.readXMLData("/timeSeriesTestData.xml")) {
            System.out.println(entry.getDoubleBinaryAmount().toString());
        }


    }
}
