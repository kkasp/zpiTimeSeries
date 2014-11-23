package com.zpi.timeseries.gui;

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
import java.io.File;
import java.util.GregorianCalendar;

import com.zpi.timeseries.datautils.TimeSeriesDataGenerator;
import com.zpi.timeseries.datautils.TimeSeriesDataReader;
import com.zpi.timeseries.datautils.TimeSeriesEntry;

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
	
	public static void main(String[] args) {
		MainWindow window = new MainWindow();
		window.prepareGUI();
	}

	private void prepareGUI() {
		
		mainFrame = new Frame("ZPI TimeSeries");
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new GridLayout(8, 0, 0, 0));
		mainFrame.setBackground(Color.lightGray);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		mainFrame.setVisible(true);
		mainFrame.setMenuBar(new MainMenuBar(this));
		
		{
			
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
				}
			});
			panel.add(loadButton);
			mainFrame.add(panel);
			
			
			//Wybor wykresu
			Panel chartChoicePanel = new Panel();
			chartChoicePanel.setLayout(new GridLayout(0,3));
			
			CheckboxGroup chartChoice = new CheckboxGroup();
			
			Checkbox barChartOption = new Checkbox(MainMenuCommands.barChartOptionTxt, chartChoice, true);
			barChartOption.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					chartTypeValue = chartTypeValues.BAR;
					System.out.println("Bar");
				}
			});
			chartChoicePanel.add(barChartOption);
			
			Checkbox xyChartOption = new Checkbox(MainMenuCommands.XYChartOptionTxt, chartChoice, false);
			xyChartOption.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					chartTypeValue = chartTypeValues.XY;
					System.out.println("XY");
				}
			});
			chartChoicePanel.add(xyChartOption);
			
			Checkbox lineChartOption = new Checkbox(MainMenuCommands.lineChartOptionTxt, chartChoice, false);
			lineChartOption.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					chartTypeValue = chartTypeValues.LINE;
					System.out.println("Line");
				}
			});
			chartChoicePanel.add(lineChartOption);
			mainFrame.add(chartChoicePanel);

			
			
			
			//Wybor koorelacji
			Panel coorelationPanel = new Panel();
			coorelationPanel.setLayout(new FlowLayout());
			
			Button corelationRandomButton = new Button(MainMenuCommands.corelationTxt);
			corelationRandomButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Corelation();
				}
			});
			
			coorelationPanel.add(corelationRandomButton);
			
			Panel dateChoicePanel = new Panel();
			dateChoicePanel.setLayout(new GridLayout(2,0));
			dateFromCorrelation = new DateTextField();
			dateToCorrelation = new DateTextField();
			dateChoicePanel.add(dateFromCorrelation);
			dateChoicePanel.add(dateToCorrelation);
			
			coorelationPanel.add(dateChoicePanel);

			mainFrame.add(coorelationPanel);
			
			//Wybor zakresu
			Panel rangeChoicePanel = new Panel();
			rangeChoicePanel.setLayout(new GridLayout(0,3));
			
			CheckboxGroup chartRangeChoice = new CheckboxGroup();
			
			Checkbox weekRangeOption = new Checkbox(MainMenuCommands.weekOptionTxt, chartRangeChoice, true);
			weekRangeOption.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					timeRangeOption = TimeRangeOption.Week;	
					dateTo.setEnabled(false);
					dateToCorrelation.setEnabled(false);
					System.out.println("Week");
				}
			});
			rangeChoicePanel.add(weekRangeOption);
			
			Checkbox monthRangeOption = new Checkbox(MainMenuCommands.monthOptionTxt, chartRangeChoice, false);
			monthRangeOption.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					timeRangeOption = TimeRangeOption.Month;
					dateTo.setEnabled(false);
					dateToCorrelation.setEnabled(false);
					System.out.println("Month");
				}
			});
			rangeChoicePanel.add(monthRangeOption);
			
			Checkbox customRangeOption = new Checkbox(MainMenuCommands.rangeOptionTxt, chartRangeChoice, false);
			customRangeOption.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					timeRangeOption = TimeRangeOption.Custom;	
					dateTo.setEnabled(true);
					dateToCorrelation.setEnabled(true);
					System.out.println("Custom");
				}
			});
			rangeChoicePanel.add(customRangeOption);
			mainFrame.add(rangeChoicePanel);
			
			mainFrame.revalidate();
			mainFrame.repaint();
			
		}
	
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
	}
	
	/**
	 * Laduje dane z pliku CSV lub xml
	 */
	public void LoadData()
	{

		String filepath = this.filePath.getText();
		String ext = filepath.substring(filepath.lastIndexOf(".")+1);
				
		TimeSeriesDataReader reader = new TimeSeriesDataReader();
		if(ext == "csv") {
	        for(TimeSeriesEntry entry : reader.readXMLData(filepath)) {
	            System.out.println(entry.getDoubleBinaryAmount().toString());
	        }
		} else if(ext == "xml") {
	        for(TimeSeriesEntry entry : reader.readXMLData(filepath)) {
	            System.out.println(entry.getDoubleBinaryAmount().toString());
	        }
		} else {
			new Exception("Unsupportable file extension. Try with csv or xml file.");
		}
		
	}
	
	public void Corelation()
	{
		
		//TODO: Corelation
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		System.out.println("Command: " + command);
		switch(command)
		{
		case MainMenuCommands.closeTxt:
			System.exit(0);
			break;
		}
	}

}
