package com.zpi.charts;

import java.util.ArrayList;

public class TimeseriesDoubleValues {
	
	private ArrayList<Double> values;
	private String name;
	private int size;
	private int startPoint;
	
	/*****************************************************************************************/
	/* 									KONSTRUKTORY 										 */
	/*****************************************************************************************/
	
	public TimeseriesDoubleValues(String name, ArrayList<Double> values, int startPoint){
		
		
		this.name = name;
		this.values = values;
		//----------
		
		size = values.size();
		this.startPoint = startPoint;
	}
	
	/*****************************************************************************************/
	/* 									GETTERY I SETTERY 								 	 */
	/*****************************************************************************************/
	
	/* SETTERY: */
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setValues(ArrayList<Double> val){
		this.values = val;
	}
	
	public void setStartPoint(int startPoint){
		this.startPoint = startPoint;
	}
	
	/* GETTERY */
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Double> getValues(){
		return this.values;
	}
	
	public int getSize(){
		return this.size;
	}

	public int getStartPoint(){
		return this.startPoint;
	}
}
