package com.zpi.timeseries.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JComponent;

import org.jfree.data.time.Day;

import com.zpi.timeseries.charts.BarChart;
import com.zpi.timeseries.charts.BarChartValues;
import com.zpi.timeseries.charts.TimeSeriesChart;
import com.zpi.timeseries.charts.TimeseriesTimeValues;
import com.zpi.timeseries.charts.XYChart;
import com.zpi.timeseries.charts.XYChartValues;
import com.zpi.timeseries.corellation.Correlation;
import com.zpi.timeseries.datautils.TimeSeriesDataGenerator;
import com.zpi.timeseries.datautils.TimeSeriesDataReader;
import com.zpi.timeseries.datautils.TimeSeriesEntry;
import com.zpi.timeseries.neuralnetwork.NeuralNetwork;
import com.zpi.timeseries.neuralnetwork.learning.HebbLearner;
import com.zpi.timeseries.neuralnetwork.transferprocessors.LinearTransferProcessor;
import com.zpi.timeseries.neuralnetwork.transferprocessors.SigmoidTransferProcessor;
import com.zpi.timeseries.neuralnetwork.transferprocessors.SignumTransferProcessor;

public class MainWindow implements ActionListener {
	
	private Frame mainFrame;

	//sciezka do pliku zapisu/odczytu danych
	TextField filePath;
	
	//Jaki typ wykresu?
	enum chartTypeValues {BAR, XY, LINE};
	chartTypeValues chartTypeValue;
	
	//Tydzien, miesiac, czy zakres?
	TimeRangeOption timeRangeOption = TimeRangeOption.Week;
	
	//Z tego mozemu brac tydzien, miesiac lub zakres
	//Przy 2 pierwszych opcjach druga data jest nieaktywna
	//Zakres daty od
	DateTextField dateFrom;
	//Zakres daty do
	DateTextField dateTo;
	
	//Zakres drugiego wykresu do korelacji
	//Dzialanie jak wyzej
	//Zakres daty od (do korelacji)
	DateTextField dateFromCorrelation;
	//Zakres daty do (do korelacji)
	DateTextField dateToCorrelation;
	
	//
	TimeSeriesDataReader reader = new TimeSeriesDataReader();
	
	boolean panelsLoaded = false;
	boolean corelationButtonAdded = false;
	
	public static void main(String[] args) {
		MainWindow window = new MainWindow();
		window.prepareGUI();
	}

	private void prepareGUI() {
		
		mainFrame = new Frame("ZPI TimeSeries");
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new GridLayout(8, 0));
		mainFrame.setBackground(Color.lightGray);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		mainFrame.setVisible(true);
		mainFrame.setMenuBar(new MainMenuBar(this));

		//Zrodlo danych
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(3,0));
		//Generowanie losowych
		Button generateRandomButton = new Button(MainMenuCommands.generateTxt);
		generateRandomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerateRandom();
			}
		});
		
		panel.add(generateRandomButton);
		filePath = new TextField(System.getProperty("user.dir") + "\\timeSeriesTestData.xml");
		
		panel.add(filePath);
		//Ladowanie istniejacych
		Button loadButton = new Button(MainMenuCommands.loadDataTxt);
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoadData();
				if(panelsLoaded == false) {
					panelsLoaded = true;
					LoadPanels(mainFrame);
				}
			}
		});
		panel.add(loadButton);
		mainFrame.add(panel);
		
		mainFrame.revalidate();
		mainFrame.repaint();
		
	}
	
	private void LoadPanels(Frame mainFrame) {
		

		//Wybor wykresu
		Panel chartChoicePanel = new Panel();
		chartChoicePanel.setLayout(new GridLayout(0,3));
		
		CheckboxGroup chartChoice = new CheckboxGroup();
		
		Checkbox barChartOption = new Checkbox(MainMenuCommands.barChartOptionTxt, chartChoice, true);
		barChartOption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				chartTypeValue = chartTypeValues.BAR;
				//System.out.println("Bar");
			}
		});
		chartChoicePanel.add(barChartOption);
		
		Checkbox xyChartOption = new Checkbox(MainMenuCommands.XYChartOptionTxt, chartChoice, false);
		xyChartOption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				chartTypeValue = chartTypeValues.XY;
				//System.out.println("XY");
			}
		});
		chartChoicePanel.add(xyChartOption);
		
		
		Checkbox lineChartOption = new Checkbox(MainMenuCommands.lineChartOptionTxt, chartChoice, false);
		lineChartOption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				chartTypeValue = chartTypeValues.LINE;
				//System.out.println("Line");
			}
		});
		chartChoicePanel.add(lineChartOption);
		mainFrame.add(chartChoicePanel);
		
		
		//Imnicjalizacja
		dateFromCorrelation = new DateTextField();
		dateFromCorrelation.setText("01/09/2013");
		dateToCorrelation = new DateTextField();
		dateToCorrelation.setText("30/09/2013");
		dateToCorrelation.setEnabled(false);
		//Wybor zakresu
		Panel rangeChoicePanel = new Panel();
		rangeChoicePanel.setLayout(new GridLayout(0,3));
		
		CheckboxGroup chartRangeChoice = new CheckboxGroup();
		
		Checkbox weekRangeOption = new Checkbox(MainMenuCommands.weekOptionTxt, chartRangeChoice, true);
		weekRangeOption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				timeRangeOption = TimeRangeOption.Week;	
				dateToCorrelation.setEnabled(false);
				//System.out.println("Week");
			}
		});
		rangeChoicePanel.add(weekRangeOption);
		
		Checkbox monthRangeOption = new Checkbox(MainMenuCommands.monthOptionTxt, chartRangeChoice, false);
		monthRangeOption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				timeRangeOption = TimeRangeOption.Month;
				dateToCorrelation.setEnabled(false);
				//System.out.println("Month");
			}
		});
		rangeChoicePanel.add(monthRangeOption);
		
		Checkbox customRangeOption = new Checkbox(MainMenuCommands.rangeOptionTxt, chartRangeChoice, false);
		customRangeOption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				timeRangeOption = TimeRangeOption.Custom;	
				dateToCorrelation.setEnabled(true);
				//System.out.println("Custom");
			}
		});
		rangeChoicePanel.add(customRangeOption);
		mainFrame.add(rangeChoicePanel);
		

		//Wybor koorelacji
		Panel coorelationPanel = new Panel();
		coorelationPanel.setLayout(new FlowLayout());
		
		Panel dateChoicePanel = new Panel();
		dateChoicePanel.setLayout(new GridLayout(0,2));
		dateChoicePanel.add(dateFromCorrelation);
		dateChoicePanel.add(dateToCorrelation);
		coorelationPanel.add(dateChoicePanel);
		mainFrame.add(coorelationPanel);
		
		
		//Start
		Button startNeuronButton = new Button(MainMenuCommands.neuronStartTxt);
		startNeuronButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startNeuron();
			}
		});
		mainFrame.add(startNeuronButton);
		

		
		mainFrame.revalidate();
		mainFrame.repaint();

	}
	
	/**
	 * Generuje losowe dane i zapisuje je do pliku którego adres jest wpisany w polu
	 */
	public void GenerateRandom()
	{
        TimeSeriesDataGenerator generator = new TimeSeriesDataGenerator();
        generator.generateNewEntries(new GregorianCalendar(2013, 0, 1), new GregorianCalendar(2013, 11, 31));
        generator.generateCSVData();
        generator.generateXMLData();
        generator = null;
	}
	
	/**
	 * Laduje dane z pliku CSV lub xml
	 */
	public void LoadData()
	{

		String filepath = this.filePath.getText();
		String ext = filepath.substring(filepath.lastIndexOf(".")+1);

		if(ext.equals("csv")) {
			reader.readCSVData(filepath);
		} else if(ext.equals("xml")) {
			reader.readXMLData(filepath);
		} else {
			new Exception("Unsupportable file extension. Try with csv or xml file.");
			//System.out.println("Error");
		}
		
		//Pokaz
        for(TimeSeriesEntry entry : reader.getDataArray())  {
            //System.out.println(entry.toString());
        }
        
	}
	
	public void Corelation(ActionEvent e)
	{
		
		Button sourceButton = (Button) e.getSource();
		
		//Dane do korelacji na podstawie wybranych dat i wczytanego pliku
		ArrayList<TimeSeriesEntry> dataArray = reader.getFilteredDataArray(dateFromCorrelation.getDate(), dateToCorrelation.getDate(), timeRangeOption);

		//Policz korelacje
		double[] sourceChunk = TimeSeriesDataReader.toDouble(dataArray);
		double[] resultChunk = reader.getDataResultArray();
		double pearsonCorrelation = Correlation.pearson(sourceChunk, resultChunk);
		double spearmanCorrelation = Correlation.spearman(sourceChunk,resultChunk);
		
		sourceButton.setLabel(
				"Korelacja PEARSONA: " + String.valueOf(pearsonCorrelation) +
				"           " + 
				"Korelacja SPEARMANA:" + String.valueOf(spearmanCorrelation)
				);
		
	}

	public void startNeuron() {

		//Naucz siec caloscia danych
		List<TimeSeriesEntry> inputChunk;
		double[] resultChunkTemp = null;
		double[] resultChunk = null;
		ArrayList<TimeSeriesEntry> dataArray = reader.getDataArray();
		ArrayList<TimeSeriesEntry> resultDataArray;
		int size = 0;
		if(timeRangeOption == TimeRangeOption.Week) {
			size = 7;
		} else if(timeRangeOption == TimeRangeOption.Month) {
			size = 30;
		} else { //Zakres
			//Policz ilosc dni
			Calendar startCal=new GregorianCalendar();
			Calendar endCal=new GregorianCalendar();

			startCal.setTime(dateFromCorrelation.getDate());
			endCal.setTime(dateToCorrelation.getDate());

			endCal.add(Calendar.YEAR,-startCal.get(Calendar.YEAR));
			endCal.add(Calendar.MONTH,-startCal.get(Calendar.MONTH));
			endCal.add(Calendar.DATE,-startCal.get(Calendar.DATE));

			size = endCal.get(Calendar.DAY_OF_YEAR);

		}
		
		int i = 0;
		
		NeuralNetwork network = new NeuralNetwork(new int[] {size, size*size, size}, new SigmoidTransferProcessor(), new HebbLearner(.02));
		for(TimeSeriesEntry entry : dataArray)  {
			
			//Chunk
			inputChunk = new ArrayList<TimeSeriesEntry>(dataArray.subList(0, 0+size));
			double[] inputTable = TimeSeriesDataReader.toDouble(inputChunk);
			
			//Ostatni chunk to rozmiar danych
			resultChunkTemp = network.processInput(inputTable);
			if(resultChunkTemp[0] != 1.0) {
				resultChunk = resultChunkTemp;
			}

			i++;
			if(i > dataArray.size() - size) {
				break;
			}
			
    	}
		network.setAutonomicLearner(null);
		reader.setDataResultArray(resultChunk);

		//Wybierz zakres na podstawie wybranych dat
		dataArray = reader.getFilteredDataArray(dateFromCorrelation.getDate(), dateToCorrelation.getDate(), timeRangeOption);
		
		//Pokaz wykresy, parami data (x) i wartosc (y)
		ArrayList<String> labels = new ArrayList<String>();
		ArrayList<String> labelsResult = new ArrayList<String>();
		ArrayList<Double> values = new ArrayList<Double>();
		ArrayList<Point2D> valuesPoint2D = new ArrayList<Point2D>();
		ArrayList<Double> valuesResult = new ArrayList<Double>();
		ArrayList<Point2D> valuesResultPoint2D = new ArrayList<Point2D>();
		ArrayList<Day> labelsDays = new ArrayList<Day>();
		try {
			for(TimeSeriesEntry entry : dataArray)  {
				labels.add(entry.getDate());
				labelsDays.add(new Day(entry.getDay(Calendar.DAY_OF_MONTH) , 1+Integer.parseInt(entry.getMonth()), entry.getYear()));
				values.add(entry.getDoubleAmount());
				valuesPoint2D.add(new Point2D.Double(entry.getDay(Calendar.DAY_OF_YEAR), entry.getDoubleAmount()));
				//

        	}
			for (int j = 0; j < resultChunk.length; j++) {
				labelsResult.add(Integer.toString(j));
				valuesResult.add(resultChunk[j]);
				valuesResultPoint2D.add(new Point2D.Double(j, resultChunk[j]));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
        //£adowanie wykresu
        if(chartTypeValue == chartTypeValues.XY) {
        	XYChart chart = new XYChart(new XYChartValues("Wartoœæ w dziedzinie czasu", valuesPoint2D));
        	XYChart chartResult = new XYChart(new XYChartValues("Wartoœæ w dziedzinie czasu - neurony", valuesResultPoint2D));
        	chart.createXYChart();
        	chartResult.createXYChart();
        } else if(chartTypeValue == chartTypeValues.LINE) {
        	TimeSeriesChart chart = new TimeSeriesChart(new TimeseriesTimeValues("Wartoœæ w dziedzinie czasu", labelsDays, values));
        	TimeSeriesChart chartResult = new TimeSeriesChart(new TimeseriesTimeValues("Wartoœæ w dziedzinie czasu - neurony", labelsDays, valuesResult));
        	chart.setChartTitle("Wykres dla szeregu czasowego");
    		chart.setXLabel("Dni");
    		chart.setYLabel("Wartoœæ wskaŸnika");
    		chart.createTimeSeriesTimeChart();
    		chartResult.setChartTitle("Wykres dla szeregu czasowego");
    		chartResult.setXLabel("Dni");
    		chartResult.setYLabel("Wartoœæ wskaŸnika");
    		chartResult.createTimeSeriesTimeChart();
        } else { //BAR
        	BarChart chart = new BarChart("Wyniki", new BarChartValues("Wartoœæ w dziedzinie czasu", labels, values));
        	BarChart chartResult = new BarChart("Wyniki", new BarChartValues("Wartoœæ w dziedzinie czasu - neurony", labelsResult, valuesResult));
        	chart.create3DBarChart();
        	chartResult.create3DBarChart();
        }	
        
        if(corelationButtonAdded == false) {
        	
			Button corelationRandomButton = new Button(MainMenuCommands.corelationTxt);
			corelationRandomButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Corelation(e);
				}
			});
			mainFrame.add(corelationRandomButton);
			mainFrame.revalidate();
			mainFrame.repaint();
			
			corelationButtonAdded = true;
        
        }
        
	}
	

	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		//System.out.println("Command: " + command);
		switch(command)
		{
		case MainMenuCommands.closeTxt:
			System.exit(0);
			break;
		}
	}

}
