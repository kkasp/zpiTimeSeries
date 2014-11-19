package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.opencsv.CSVWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 * Created by kamil on 16.11.14.
 */
public class TimeSeriesDataGenerator {
    private TimeSeriesEntryGenerator entryGenerator = new TimeSeriesEntryGenerator();
    private ArrayList<TimeSeriesEntry> entriesList;

    public void generateNewEntries(GregorianCalendar start, GregorianCalendar end) {
        entriesList = entryGenerator.generateTimeSeriesValue(start, end);
    }

    public boolean generateXMLData() {
        boolean ret = true;

        try {
            DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //Creating root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("timeSeries");
            doc.appendChild(rootElement);

            //Generating entries data
            int i = 0;

            for(TimeSeriesEntry timeSeriesEntry : entriesList) {
                //Creating entry node, assigning id, and adding it to the root element
                Element entryElement = doc.createElement("entry");
                rootElement.appendChild(entryElement);
                Attr id = doc.createAttribute("id");
                id.setValue(""+(++i));
                entryElement.setAttributeNode(id);

                //Adding date to entry node
                Element date = doc.createElement("date");
                date.appendChild(doc.createTextNode(timeSeriesEntry.getDate()));
                entryElement.appendChild(date);

                //Adding amount to entry node
                Element amount = doc.createElement("amount");
                amount.appendChild(doc.createTextNode(timeSeriesEntry.getAmount()));
                entryElement.appendChild(amount);

                //Adding week to entry node
                Element week = doc.createElement("week");
                week.appendChild(doc.createTextNode(timeSeriesEntry.getWeek()));
                entryElement.appendChild(week);

                //Adding month to entry node
                Element month = doc.createElement("month");
                month.appendChild(doc.createTextNode(timeSeriesEntry.getMonth()));
                entryElement.appendChild(month);

            }

            //Writing data into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/home/kamil/Studia/timeSeriesTestData.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException ex) {
            System.out.println("Error creating doc: " + ex.toString());
            ret = false;
        } catch (TransformerException ex) {
            System.out.println("Error creating transformer: " + ex.toString());
            ret = false;
        }



        return ret;
    }

    public boolean generateCSVData() {
        boolean ret = true;

        try {
            CSVWriter writer = new CSVWriter(new FileWriter("/home/kamil/Studia/timeSeriesTestData.csv"));
            for(TimeSeriesEntry entry : entriesList) {
                String[] newEntry = (entry.getDate()+"#"+entry.getAmount()+"#"+entry.getWeek()+"#"+entry.getMonth()).split("#");
                writer.writeNext(newEntry);
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("Error creating doc: " + ex.toString());
            ret = false;
        }

        return ret;
    }
}
