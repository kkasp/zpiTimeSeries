package com.zpi.timeseries.charts;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class XYChart {
	
	String chartTitle;
	String frameTitle;
	String xLabel;
	String yLabel;
	int height;
	int width;
	ArrayList<XYChartValues> values;
	
	Color plotColor;
	Color backgroundColor;
	PlotOrientation orientation;
	
	/*****************************************************************************************/
	/* 									KONSTRUKTORY 										 */
	/*****************************************************************************************/
	
	public XYChart(XYChartValues val){
		
		this.chartTitle = val.getName();
		this.frameTitle = val.getName();
		this.xLabel = "X";
		this.yLabel = "Y";
		values = new ArrayList<XYChartValues>();
		values.add(val);
		height = 700;
		width= 1000;
		
		plotColor = Color.gray;
		backgroundColor = Color.white;
		orientation = PlotOrientation.VERTICAL;
	}
	
	
	/*****************************************************************************************/
	/* 										METODY											 */
	/*****************************************************************************************/
	
	public void addValues(XYChartValues val){
		this.values.add(val);
	}
	
	public void removeValues(int index){
		this.values.remove(index);
	}
	
	public XYSeriesCollection createDataset(){
		
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		// stworzenie dataset
		for(int i = 0; i < this.values.size(); i++){
			ArrayList<Point2D> numberValues = this.values.get(i).getValues();
			
			String name = values.get(i).getName();
			XYSeries series = new XYSeries(name);
			
			for(int j = 0; j < values.get(i).getSize(); j++){
				
				series.add(numberValues.get(j).getX(), numberValues.get(j).getY());
			}
			
			dataset.addSeries(series);
		}
		
		return dataset;
	}
	
	
	/* Budowa zwyk�ego wykresu: */
	public void createXYChart(){
		
		XYSeriesCollection dataset = createDataset();
		JFreeChart chart;
		
		
		// stworzenie wykresu z danych:
		chart = ChartFactory.createXYLineChart( this.chartTitle, this.xLabel, this.yLabel, dataset, orientation, true, true, false );
		
		ChartFrame chartFrame =new ChartFrame(this.chartTitle, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(this.width, this.height);
		
	}
	
	/* Budowa wykresu 3D: */
	public void createXYAreaChart(){
		
		XYSeriesCollection dataset = createDataset();
		JFreeChart chart;
		
		chart = ChartFactory.createXYAreaChart( this.chartTitle, this.xLabel, this.yLabel, dataset, orientation, true, true, false);
		
		ChartFrame chartFrame =new ChartFrame(this.chartTitle, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(this.width, this.height);
	}
	
	
	/*****************************************************************************************/
	/* 									GETTERY I SETTERY 								 	 */
	/*****************************************************************************************/
	
	/* SETTERY */
	
	public void setChartTitle(String title){
		this.chartTitle = title;
	}
	
	public void setXLabel(String label){
		this.xLabel = label;
	}
	
	public void setYLabel(String label){
		this.yLabel = label;
	}
	
	public void setChartHeight(int height){
		this.height = height;
	}
	
	public void setChartWidth(int width){
		this.width = width;
	}
	
	public void setPlotColor(Color col){
		this.plotColor = col;
	}
	
	public void setBackgroundColor(Color col){
		this.backgroundColor = col;
	}
	
	public void setPlotOrientation(PlotOrientation orientation){
		this.orientation = orientation;
	}
	
	public void setFrameTitle(String frameTitle){
		this.frameTitle = frameTitle;
	}
	
	/*   GETTERY */
	//---------------------------------------------------------------------------------------------
	
	public String getChartTitle(){
		return this.chartTitle;
	}
	
	public String getXLabel(){
		return this.xLabel;
	}
	
	public String getYLabel(){
		return this.yLabel;
	}
	
	public int getChartHeight(){
		return this.height;
	}
	
	public int getChartWidth(){
		return this.width;
	}
	
	public Color getPlotColor(){
		return this.plotColor;
	}
	
	public Color getBackgroundColor(){
		return this.backgroundColor;
	}
	
	public PlotOrientation getPlotOrientation(){
		return this.orientation;
	}
	
	public String getFrameTitle(){
		return this.frameTitle;
	}
	
	
	/*****************************************************************************************/
	/* 										TESTY											 */
	/*****************************************************************************************/
	
	public static void main(String[] main){
		
		XYChart chart;
		ArrayList<Point2D> values = new ArrayList<Point2D>();
		String name1 = "wykres 1";
		Random random = new Random();
		
		// Stworzenie danych testowych
		for(int i= 0; i < 50; i++){
			values.add(new Point2D.Double(i,random.nextDouble()));
		}
		
		chart = new XYChart(new XYChartValues(name1,values));
		chart.createXYChart();
		chart.createXYAreaChart();
		
		
		for(int i= 0; i < 50; i++){
			values.add(new Point2D.Double(i,random.nextDouble()));
		}
		
		chart = new XYChart(new XYChartValues("cos tam",values));
		for(int i= 60; i < 100; i++){
			values.add(new Point2D.Double(i,random.nextDouble()));
		}
		chart.addValues(new XYChartValues("wykres 2", values));
		chart.createXYChart();
	}
}
