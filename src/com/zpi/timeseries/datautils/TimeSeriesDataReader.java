package com.company;

import com.opencsv.CSVReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
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

}
