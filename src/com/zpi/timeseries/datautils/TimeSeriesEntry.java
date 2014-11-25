package com.zpi.timeseries.datautils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kamil on 16.11.14.
 */
public class TimeSeriesEntry {
    private String date;
    private String amount;
    private String week;
    private String month;

    private int amountInt;

    public TimeSeriesEntry(String date, String amount, String week, String month) {
        this.date = date;
        this.amount = amount;
        this.week = week;
        this.month = month;
        this.amountInt = Integer.parseInt(amount);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }
    
    public Date getDateObject() throws ParseException {
    	DateFormat _formatter = new SimpleDateFormat("dd-MM-yy");
    	Date _date = _formatter.parse(this.date);
		return _date;
    }
    
    public int getDay(int mode) throws ParseException {
        
    	Calendar c = Calendar.getInstance(); 
  
    	DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
    	Date d = formatter.parse(this.date);
    
    	c.setTime(d);
		return c.get(mode);
		
    }


    public void setAmount(String amount) {
        this.amount = amount;
        this.amountInt = Integer.parseInt(amount);
    }

    public String getAmount() {
        return amount;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() throws ParseException {
        
    	Calendar c = Calendar.getInstance(); 
   
    	DateFormat formatter = new SimpleDateFormat("dd-mm-yy");
    	Date d = formatter.parse(this.date);
    	
    	c.setTime(d);
		return c.get(Calendar.YEAR);
		
    }
    
    public int getAmountInt() {
        return amountInt;
    }
    
    public double getAmountDouble() {
        return Double.parseDouble(amount);
    }

    public void setAmountInt(int amountDouble) {
        this.amountInt = amountDouble;
    }

    public String toString() {
        return "Date: " + date + ", amount: " + amount + ", week: " + week + ", month: " + month;
    }

    public ArrayList<Double> getDoubleBinaryAmount() {
        ArrayList<Double> binaryAmount = new ArrayList<Double>();
        String binary = Integer.toBinaryString(amountInt);
        for(int i = 0; i < binary.length(); i++) {
            binaryAmount.add(Double.parseDouble(binary.substring(i,i+1)));
        }

        return binaryAmount;
    }

    public double getDoubleAmount() {
        return Double.parseDouble(amount);
    }
}
