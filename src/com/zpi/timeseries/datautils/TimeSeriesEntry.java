package com.company;

import java.util.ArrayList;
import java.util.Arrays;

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

    public int getAmountInt() {
        return amountInt;
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
