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
            ResultSet rs = ReadServiceList.ACUTE_FROM_PATIENT.ExecuteQuery(new Object[]{id});
            double cutOffDate = System.currentTimeMillis();
            double[] occurences = new double[1];
            switch (gt) {
                case WEEKLY -> {
                    cutOffDate -= 7L * 24 * 60 * 60 * 1000;
                    occurences = new double[7];
                }
                case MONTHLY -> {
                    cutOffDate -= 30L * 24 * 60 * 60 * 1000;
                    occurences = new double[30];
                }
                case ANNUAL -> {
                    cutOffDate -= 365L * 24 * 60 * 60 * 1000;
                    occurences = new double[365];
                }
            }
            int i = 0;
            while (rs.next()) {
                if(rs.getDate("Symptom Date").getTime() < cutOffDate) {
                    break;
                }
                occurences[i] = rs.getDate("Symptom Date").getTime();
                i++;
            }

            HistogramDataset dataset = new HistogramDataset();
            dataset.addSeries("Frequency of symptom", occurences, occurences.length);
            JFreeChart histogram = ChartFactory.createHistogram(String.valueOf(rs.getInt("Symptom ID")),
                    "Severity", "Frequency", dataset);
            ChartPanel chartPanel = new ChartPanel(histogram);
            return chartPanel;

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
