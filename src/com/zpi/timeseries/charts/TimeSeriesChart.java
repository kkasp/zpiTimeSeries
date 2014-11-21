package com.zpi.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class TimeSeriesChart {
	
	String chartTitle;
	String xLabel;
	String yLabel;
	int height;
	int width;
	ArrayList<TimeseriesDoubleValues> values;
	ArrayList<TimeseriesTimeValues> timeValues;
	
	Color plotColor;
	Color backgroundColor;
	PlotOrientation orientation;
	
	/*****************************************************************************************/
	/* 									KONSTRUKTORY 										 */
	/*****************************************************************************************/
	
	public TimeSeriesChart(TimeseriesDoubleValues val){
		this.chartTitle = val.getName();
		this.xLabel = "X";
		this.yLabel = "Y";
		values = new ArrayList<TimeseriesDoubleValues>();
		values.add(val);
		height = 700;
		width= 1000;
		
		plotColor = Color.gray;
		backgroundColor = Color.white;
		orientation = PlotOrientation.VERTICAL;
	}
	
	public TimeSeriesChart(TimeseriesTimeValues val){
		
		this.chartTitle = val.getName();
		this.xLabel = "X";
		this.yLabel = "Y";
		timeValues = new ArrayList<TimeseriesTimeValues>();
		timeValues.add(val);
		height = 700;
		width= 1000;
		
		plotColor = Color.gray;
		backgroundColor = Color.white;
		orientation = PlotOrientation.VERTICAL;
	}
	
	/* Budowa wykresu line: */
	public void createTimeSeriesDoubleChart(){
		
		XYSeriesCollection dataset = createDataset();
		JFreeChart chart;
		
		
		// stworzenie wykresu z danych:
		chart = ChartFactory.createXYLineChart( this.chartTitle, this.xLabel, this.yLabel, dataset, orientation, true, true, false );
		
		chart.setBackgroundPaint(backgroundColor);

        // costomizacja:
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        
        // ustawienie wyœwietlania kolejnych liczb ca³kowitych na osi X
        TickUnitSource ticks = NumberAxis.createIntegerTickUnits();
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setStandardTickUnits(ticks);
        /*NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setStandardTickUnits(ticks);*/
		
        // ustawienia lini oraz figur
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);
        plot.setRenderer(renderer);
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        
        Stroke stroke = new BasicStroke( 3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        renderer.setBaseOutlineStroke(stroke);
        
        // wyœwietlanie opisów punktów:
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        XYItemLabelGenerator generator =
            new StandardXYItemLabelGenerator(
                StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT,
                format, format);
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);
        
        
		ChartFrame chartFrame =new ChartFrame(this.chartTitle, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(this.width, this.height);
		
	}
	
	public void createTimeSeriesTimeChart(){
		
		XYDataset dataset = createTimeDataset();
		JFreeChart chart;
		
		chart = ChartFactory.createTimeSeriesChart(this.chartTitle, this.xLabel, this.yLabel, dataset, true, true, false );
		chart.setBackgroundPaint(backgroundColor);

        // costomizacja:
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        
        // ustawienia lini oraz figur
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);
        plot.setRenderer(renderer);
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        
        Stroke stroke = new BasicStroke( 3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        renderer.setBaseOutlineStroke(stroke);
        
        // wyœwietlanie opisów punktów:
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        XYItemLabelGenerator generator =
            new StandardXYItemLabelGenerator(
                StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT,
                format, format);
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);
        
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
	        XYLineAndShapeRenderer renderer2 = (XYLineAndShapeRenderer) r;
	        renderer2.setBaseShapesVisible(true);
	        renderer2.setBaseShapesFilled(true);
	        renderer2.setDrawSeriesLineAsPath(true);
        }
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-YYYY"));
        
        ChartFrame chartFrame =new ChartFrame(this.chartTitle, chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(this.width, this.height);
	}
	
	public XYSeriesCollection createDataset(){
		
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		double xValue = 0;
		
		// stworzenie dataset
		for(int i = 0; i < this.values.size(); i++){
			ArrayList<Double> numberValues = this.values.get(i).getValues();
			
			String name = values.get(i).getName();
			XYSeries series = new XYSeries(name);
			
			xValue = this.values.get(i).getStartPoint();
			for(int j = 0; j < values.get(i).getSize(); j++){
				
				series.add(xValue, numberValues.get(j));
				xValue++;
			}
			
			dataset.addSeries(series);
		}
		
		return dataset;
	}
	
	public TimeSeriesCollection createTimeDataset(){
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		
		// stworzenie dataset
		for(int i = 0; i < this.timeValues.size(); i++){
			ArrayList<Day> xValues = this.timeValues.get(i).getXValues();
			ArrayList<Double> yValues = this.timeValues.get(i).getYValues();
			
			String name = timeValues.get(i).getName();
			TimeSeries series = new TimeSeries(name);
			
			for(int j = 0; j < timeValues.get(i).getSize(); j++){
				
				series.add(xValues.get(j), yValues.get(j));
			}
			
			dataset.addSeries(series);
		}
		
		return dataset;
	}
	
	public void addValues(TimeseriesDoubleValues val){
		this.values.add(val);
	}
	
	public void addTimeValues(TimeseriesTimeValues val){
		this.timeValues.add(val);
	}
	
	public void removeValues(int index){
		this.values.remove(index);
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
	
	
	/*****************************************************************************************/
	/* 										TESTY											 */
	/*****************************************************************************************/
	
	public static void main(String[] args){
		
		TimeSeriesChart chart;
		ArrayList<Double> values = new ArrayList<Double>();
		ArrayList<Double> values2 = new ArrayList<Double>();
		String name1 = "wykres 1";
		Random random = new Random();
		
		// Stworzenie danych testowych
		for(int i= 0; i < 50; i++){
			values.add(random.nextDouble()*100);
			values2.add(random.nextDouble()*55);
		}
		
		chart = new TimeSeriesChart(new TimeseriesDoubleValues(name1,values,0));
		chart.addValues(new TimeseriesDoubleValues("wykres2", values2, 50));
		chart.setChartTitle("Wykres dla szeregu czasowego");
		chart.setXLabel("Dni");
		chart.setYLabel("Wartoœæ wskaŸnika");
		chart.createTimeSeriesDoubleChart();
		
		
		ArrayList<Day> test1 = new ArrayList<Day>();
		ArrayList<Double> test1double = new ArrayList<Double>();
		for(int i=0;i<31;i++){
			test1.add(new Day(i+1,3, 2014));
			test1double.add(random.nextDouble() * 50);
		}
		
		chart = new TimeSeriesChart(new TimeseriesTimeValues("testowy wykres 1", test1, test1double));
		
		test1= new ArrayList<Day>();
		test1double = new ArrayList<Double>();
		for(int i=0;i<25;i++){
			test1.add(new Day(i+1 , 3, 2014));
			test1double.add(random.nextDouble() * 60);
		}
		chart.addTimeValues(new TimeseriesTimeValues("testowy wykres 2", test1, test1double));
		chart.setChartTitle("Wykres dla szeregu czasowego");
		chart.setXLabel("Dni");
		chart.setYLabel("Wartoœæ wskaŸnika");
		chart.createTimeSeriesTimeChart();
		
		/*chart.TimeSeriesChart();
		chart.createXYAreaChart();*/
	}
}
