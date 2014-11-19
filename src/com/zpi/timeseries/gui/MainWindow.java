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

public class MainWindow implements ActionListener {
	
	private Frame mainFrame;

	//sciezka do pliku zapisu/odczytu danych
	TextField filePath;
	
	//Czy wyswietlac barchart? jesli nie to XYChart 
	boolean barChart = true;
	
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
		mainFrame.setLayout(new GridLayout(0, 3));
		mainFrame.setBackground(Color.lightGray);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		mainFrame.setVisible(true);
		
		mainFrame.setMenuBar(new MainMenuBar(this));
		
		
		{
			Panel panel = new Panel();
			
			Button generateRandomButton = new Button(MainMenuCommands.generateTxt);
			generateRandomButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GenerateRandom();
				}
			});
			panel.add(generateRandomButton);
			filePath = new TextField("path");
			panel.add(filePath);
			
			mainFrame.add(panel);
		}
		{
			Panel panel = new Panel();
			panel.setLayout(new GridLayout(3,0));
			
			Button loadButton = new Button(MainMenuCommands.loadDataTxt);
			loadButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					LoadData();
				}
			});
			panel.add(loadButton);
			
			{
				Panel chartChoicePanel = new Panel();
				chartChoicePanel.setLayout(new GridLayout(2,0));
				
				CheckboxGroup chartChoice = new CheckboxGroup();
				
				Checkbox barChartOption = new Checkbox(MainMenuCommands.barChartOptionTxt, chartChoice, true);
				barChartOption.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						barChart = true;
						System.out.println("Bar");
					}
				});
				chartChoicePanel.add(barChartOption);
				
				Checkbox xyChartOption = new Checkbox(MainMenuCommands.XYChartOptionTxt, chartChoice, false);
				xyChartOption.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						barChart = false;
						System.out.println("XY");
					}
				});
				chartChoicePanel.add(xyChartOption);
				
				
				panel.add(chartChoicePanel);
			}
			
			{
				Panel rangeChoicePanel = new Panel();
				rangeChoicePanel.setLayout(new GridLayout(3,0));
				
				CheckboxGroup chartChoice = new CheckboxGroup();
				
				Checkbox weekRangeOption = new Checkbox(MainMenuCommands.weekOptionTxt, chartChoice, true);
				weekRangeOption.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						timeRangeOption = TimeRangeOption.Week;	
						dateTo.setEnabled(false);
						dateToCorrelation.setEnabled(false);
						System.out.println("Week");
					}
				});
				rangeChoicePanel.add(weekRangeOption);
				
				Checkbox monthRangeOption = new Checkbox(MainMenuCommands.monthOptionTxt, chartChoice, false);
				monthRangeOption.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						timeRangeOption = TimeRangeOption.Month;
						dateTo.setEnabled(false);
						dateToCorrelation.setEnabled(false);
						System.out.println("Month");
					}
				});
				rangeChoicePanel.add(monthRangeOption);
				
				Checkbox customRangeOption = new Checkbox(MainMenuCommands.rangeOptionTxt, chartChoice, false);
				customRangeOption.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						timeRangeOption = TimeRangeOption.Custom;	
						dateTo.setEnabled(true);
						dateToCorrelation.setEnabled(true);
						System.out.println("Custom");
					}
				});
				rangeChoicePanel.add(customRangeOption);
				
				panel.add(rangeChoicePanel);
				
				
				Panel dateChoicePanel = new Panel();
				dateChoicePanel.setLayout(new GridLayout(2,0));
				
				dateFrom = new DateTextField();
				dateTo = new DateTextField();
				dateChoicePanel.add(dateFrom);
				dateChoicePanel.add(dateTo);
				panel.add(dateChoicePanel);
			}
			
			
			mainFrame.add(panel);
		}
		{
			Panel panel = new Panel();
			panel.setLayout(new FlowLayout());
			
			Button corelationRandomButton = new Button(MainMenuCommands.corelationTxt);
			corelationRandomButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Corelation();
				}
			});
			
			panel.add(corelationRandomButton);
			
			Panel dateChoicePanel = new Panel();
			dateChoicePanel.setLayout(new GridLayout(2,0));
			
			dateFromCorrelation = new DateTextField();
			dateToCorrelation = new DateTextField();
			dateChoicePanel.add(dateFromCorrelation);
			dateChoicePanel.add(dateToCorrelation);
			panel.add(dateChoicePanel);
			
			mainFrame.add(panel);
		}
	
	}
	
	public void GenerateRandom()
	{
		//TODO: Generate random
		
	}
	
	public void LoadData()
	{
		//TODO: LoadData
		
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
