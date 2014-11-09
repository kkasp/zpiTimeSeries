package com.zpi.charts;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class XYChartValues {
	
	String name;
	ArrayList<Point2D> values;
	int size;
	
	/*****************************************************************************************/
	/* 									KONSTRUKTORY 										 */
	/*****************************************************************************************/
	
	public XYChartValues(String name, ArrayList<Point2D> values){
		
		
		this.name = name;
		this.values = values;
		//----------
		
		size = values.size();
	}
	
	/*****************************************************************************************/
	/* 									GETTERY I SETTERY 								 	 */
	/*****************************************************************************************/
	
	/* SETTERY: */
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setValues(ArrayList<Point2D> val){
		this.values = val;
	}
	
	/* GETTERY */
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Point2D> getValues(){
		return this.values;
	}
	
	public int getSize(){
		return this.size;
	}
}
