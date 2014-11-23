package com.zpi.timeseries.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChart {
	
	String chartTitle;
	String frameTitle;
	String xLabel;
	String yLabel;
	int height;
	int width;
	ArrayList<BarChartValues> values;
	
	Color plotColor;
	Color backgroundColor;
	PlotOrientation orientation;
	
	/*****************************************************************************************/
	/* 									KONSTRUKTORY 										 */
	/*****************************************************************************************/
	
	public BarChart(String title){
		
		this.chartTitle = title;
		this.frameTitle = title;
		this.xLabel = "X";
		this.yLabel = "Y";
		values = new ArrayList<BarChartValues>();
		height = 1000;
		width= 1000;
		
		plotColor = Color.gray;
		backgroundColor = Color.white;
		orientation = PlotOrientation.VERTICAL;
	}
	
	public BarChart(String title, BarChartValues val){
		this(title);
		this.values.add(val);
	}
	
	/*****************************************************************************************/
	/* 										METODY											 */
	/*****************************************************************************************/
	
	public void addValues(BarChartValues val){
		this.values.add(val);
	}
	
	public void removeValues(int index){
		this.values.remove(index);
	}
	
	public DefaultCategoryDataset createDataset(){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		// stworzenie dataset
		for(int i = 0; i < this.values.size(); i++){
			ArrayList<String> labels = this.values.get(i).getLabels();
			ArrayList<Double> numberValues = values.get(i).getValues();
			String name = values.get(i).getName();
			
			for(int j = 0; j < values.get(i).getSize(); j++){
				
				dataset.addValue(numberValues.get(j), name, labels.get(j));
			}
		}
		
		return dataset;
	}
	
	
	/* Budowa zwyk³ego wykresu: */
	public void createBarChart(){
		
		DefaultCategoryDataset dataset = createDataset();
		JFreeChart chart;
		
		
		// stworzenie wykresu z danych:
		chart = ChartFactory.createBarChart( this.chartTitle, this.xLabel, this.yLabel, dataset, orientation, true, true, false );
		chart.setBorderPaint(backgroundColor);
		
		
		// wyœwietlanie wartoœci na s³upkach:
		CategoryPlot p = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer)p.getRenderer();
		DecimalFormat decimalFormat = new DecimalFormat(".#");
		renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}",decimalFormat));
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition());
		renderer.setItemLabelsVisible(true);
		
		ChartFrame chartFrame =new ChartFrame(this.chartTitle, chart);
		chartFrame.setVisible(true);
		//chartFrame.setPreferredSize(new Dimension(this.width, this.height));
		chartFrame.setSize(this.width, this.height);
		
	}
	
	/* Budowa wykresu 3D: */
	public void create3DBarChart(){
		
		DefaultCategoryDataset dataset = createDataset();
		JFreeChart chart;
		
		chart = ChartFactory.createBarChart3D( this.chartTitle, this.xLabel, this.yLabel, dataset, orientation, true, true, false );
		chart.setBorderPaint(backgroundColor);
		
		
		// wyœwietlanie wartoœci na s³upkach:
		CategoryPlot p = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer)p.getRenderer();
		DecimalFormat decimalFormat = new DecimalFormat(".#");
		renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}",decimalFormat));
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition());
		renderer.setItemLabelsVisible(true);
		
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
		
		BarChart chart;
		ArrayList<Double> values = new ArrayList<Double>();
		ArrayList<Double> values2 = new ArrayList<Double>();
		ArrayList<String> labels = new ArrayList<String>();
		String name1 = "nazwa1";
		String name2 = "nazwa2";
		
		
		for(Integer i=1; i< 20; i++){
			Random rand = new Random();
			
			labels.add(i.toString());
			
			values.add(rand.nextDouble()*200);
			values2.add(rand.nextDouble() * 150);
		}
		
		// stworzenie wykresu:
		chart = new BarChart("Wykres 1", new BarChartValues(name1, labels, values));
		chart.createBarChart();
		chart.create3DBarChart();
		
		
		// wykres 2:
		chart.addValues(new BarChartValues(name2, labels, values2));
		chart.createBarChart();
		chart.create3DBarChart();
		
	}
}
