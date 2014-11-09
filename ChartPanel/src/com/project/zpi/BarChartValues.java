package com.project.zpi;

import java.util.ArrayList;

public class BarChartValues {
	
	String name;
	ArrayList<String> labels;
	ArrayList<Double> values;
	int size;
	
	/*****************************************************************************************/
	/* 									KONSTRUKTORY 										 */
	/*****************************************************************************************/
	
	public BarChartValues(String name, ArrayList<Double> values, ArrayList<String> labels){
		
		
		this.name = name;
		if(values.size() == labels.size()){	// obciêcie czêœci list jeœli nie s¹ równe.
			
			this.values = values;
			this.labels = labels;
			
		} else{
			if(values.size() < labels.size()){
				
				this.values = values;
				this.labels = (ArrayList<String>) labels.subList(0, values.size());
				
			} else{
				this.values = (ArrayList<Double>) values.subList(0, labels.size());
				this.labels = labels;
			}
		}
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
	
	public void setLabels(ArrayList<String> labels){
		this.labels = labels;
	}
	
	public void setValues(ArrayList<Double> values){
		this.values = values;
	}
	
	/* GETTERY */
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Double> getValues(){
		return this.values;
	}
	
	public ArrayList<String> getLabels(){
		return this.labels;
	}
	
	public int getSize(){
		return this.size;
	}
}
