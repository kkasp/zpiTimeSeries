package com.zpi.timeseries.datautils;

import com.opencsv.CSVReader;
import com.zpi.timeseries.gui.TimeRangeOption;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kamil on 18.11.14.
 */
public class TimeSeriesDataReader {

	private ArrayList<TimeSeriesEntry> dataArray = new ArrayList<TimeSeriesEntry>();
    
    public ArrayList<TimeSeriesEntry> readXMLData(String file) {
        dataArray.clear();

        try {
            File xmlFile = new File(file);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList entryList = doc.getElementsByTagName("entry");

            for (int temp = 0; temp < entryList.getLength(); temp++) {
                Node entryNode = entryList.item(temp);

                if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element entry = (Element) entryNode;
                    String date = entry.getElementsByTagName("date").item(0).getTextContent();
                    String amount = entry.getElementsByTagName("amount").item(0).getTextContent();
                    String week = entry.getElementsByTagName("week").item(0).getTextContent();
                    String month = entry.getElementsByTagName("month").item(0).getTextContent();



                    dataArray.add(new TimeSeriesEntry(date, amount, week, month));
                }

            }

        } catch (Exception ex) {
            System.out.println("Error while reading xml file: " + ex);
            ex.printStackTrace();
        }
        return dataArray;
    }

    public ArrayList<TimeSeriesEntry> readCSVData(String file) {
        dataArray.clear();
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                dataArray.add(new TimeSeriesEntry(nextLine[0], nextLine[1], nextLine[2], nextLine[3]));
            }
        } catch (Exception ex) {
            System.out.println("Exception reading CSV file: " + ex);
            ex.printStackTrace();
        }
        return dataArray;
    }

    public ArrayList<TimeSeriesEntry> getFilteredDataArray(Date dateFrom, Date dateTo, TimeRangeOption timeRangeOption) {

    	ArrayList<TimeSeriesEntry> dataFilteredArray = new ArrayList<TimeSeriesEntry>();
    	
    	try {
    	
	    	//Ustawiona data od
	    	Calendar calendarDateFrom = Calendar.getInstance(); 
	    	calendarDateFrom.setTime(dateFrom);
	    	int fromSettedWeek = calendarDateFrom.get(Calendar.WEEK_OF_YEAR);
	    	int fromSettedMonth = calendarDateFrom.get(Calendar.MONTH);
	    	int fromSettedYear = calendarDateFrom.get(Calendar.YEAR);

	    	if(timeRangeOption == TimeRangeOption.Week) {
	            for(TimeSeriesEntry entry : dataArray)  {
	            	if(Integer.parseInt(entry.getWeek()) == fromSettedWeek && entry.getYear() == fromSettedYear) {
	            		dataFilteredArray.add(entry);	
	            		System.out.println(entry.toString());
	            	}
	            }
	    	} else if(timeRangeOption == TimeRangeOption.Month) {
	            for(TimeSeriesEntry entry : dataArray)  {
	            	if(Integer.parseInt(entry.getMonth()) == fromSettedMonth && entry.getYear() == fromSettedYear) {
	            		dataFilteredArray.add(entry);	
	            		System.out.println(entry.toString());
	            	}
	            }
	    	} else { //Custom
	            for(TimeSeriesEntry entry : dataArray)  {
	            	if(entry.getDateObject().after(dateFrom) && entry.getDateObject().before(dateTo)) {
	            		dataFilteredArray.add(entry);	
	            		System.out.println(entry.toString());
	            	}
//                	System.out.println(entry.getDay(Calendar.DAY_OF_YEAR) + "/" + entry.getYear());
//                	System.out.println(fromSettedDay + "/" + fromSettedYear);
//                	System.out.println(toSettedDay + "/" + toSettedYear);
//    		    	System.out.println("----------------------------------------------");
	            }
	    	}
        } catch (Exception ex) {
            System.out.println("Exception filtering the list: " + ex);
            ex.printStackTrace();
    	}

        return dataFilteredArray;
        
    }

    public ArrayList<TimeSeriesEntry> getDataArray() {
    	return dataArray;
    }
    
    public static double[] toDouble(List<TimeSeriesEntry> inputChunk) {
    	
		double[] inputTable = new double[inputChunk.size()];
		for (int j = 0; j < inputChunk.size(); j++) {
			inputTable[j] = inputChunk.get(j).getAmountDouble();
		}
    	
		return inputTable;
    	
    }
    
}
