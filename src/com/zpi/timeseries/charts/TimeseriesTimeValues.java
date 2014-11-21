package com.zpi.charts;

import java.util.ArrayList;

import org.jfree.data.time.Day;

public class TimeseriesTimeValues {
	private ArrayList<Day> xValues;
	private ArrayList<Double> yValues;
	private String name;
	private int size;
	private Day startPoint;
	
	/*****************************************************************************************/
	/* 									KONSTRUKTORY 										 */
	/*****************************************************************************************/
	
	public TimeseriesTimeValues(String name, ArrayList<Day> xValues, ArrayList<Double> yValues){
		
		
		this.name = name;
		this.xValues = xValues;
		this.yValues = yValues;
		//----------
		
		size = xValues.size();
	}
	
	/*****************************************************************************************/
	/* 									GETTERY I SETTERY 								 	 */
	/*****************************************************************************************/
	
	/* SETTERY: */
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setValues(ArrayList<Day> xVal, ArrayList<Double> yVal){
		this.xValues = xVal;
		this.yValues = yVal;
	}
	
	public void setStartPoint(Day startPoint){
		this.startPoint = startPoint;
	}
	
	/* GETTERY */
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Day> getXValues(){
		return this.xValues;
	}
	
	public ArrayList<Double> getYValues(){
		return this.yValues;
	}
	
	public int getSize(){
		return this.size;
	}

	public Day getStartPoint(){
		return this.startPoint;
	}
}
