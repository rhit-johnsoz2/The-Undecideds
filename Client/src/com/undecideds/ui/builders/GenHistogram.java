package com.undecideds.ui.builders;

import com.undecideds.services.ReadServiceList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class GenHistogram {

    public Container GenHistogram(int id , GraphType gt){
        try {
            ResultSet rs = ReadServiceList.DATE_FROM_SYMPTOM.ExecuteQuery(new Object[]{id});
            double cutOffDate = System.currentTimeMillis();
            double[] occurences = new double[1];
            String xAxis = "";
            String title = "";
            switch (gt) {
                case WEEKLY -> {
                    cutOffDate -= 7L * 24 * 60 * 60 * 1000;
                    occurences = new double[7];
                    xAxis = "Days per Week";
                    title = "Symptom Frequency per Week";
                }
                case MONTHLY -> {
                    cutOffDate -= 30L * 24 * 60 * 60 * 1000;
                    occurences = new double[30];
                    xAxis = "Days per Month";
                    title = "Symptom Frequency per Month";
                }
                case ANNUAL -> {
                    cutOffDate -= 365L * 24 * 60 * 60 * 1000;
                    occurences = new double[365];
                    xAxis = "Days per Year";
                    title = "Symptom Frequency per Year";
                }
            }
            int i = 0;
            while (rs.next()) {
                if(rs.getDate("symptomDate").getTime() < cutOffDate) {
                    break;
                }
                occurences[i] = rs.getDate("symptomDate").getTime();
                i++;
            }

            HistogramDataset dataset = new HistogramDataset();
            dataset.addSeries("Frequency of symptom", occurences, occurences.length);
            JFreeChart histogram = ChartFactory.createHistogram(title,
                    xAxis, "Frequency", dataset);
            ChartPanel chart = new ChartPanel(histogram);
            JPanel panel = new JPanel(new GridLayout(1,1));
            panel.add(chart);
            panel.setPreferredSize(new Dimension(400,400));
            return panel;


        }catch (Exception e){
            e.printStackTrace();
            JPanel panel = new JPanel();
            panel.add(new JLabel("Error Fetching Data, check Stack Trace"));
            return panel;
        }
    }

    public enum GraphType{
        WEEKLY,
        MONTHLY,
        ANNUAL
    }
}
