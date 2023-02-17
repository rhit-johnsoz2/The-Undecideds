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
            long cutOffTime = System.currentTimeMillis();
            double[] occurences = new double[1];
            String xAxis = "";
            String title = "";
            switch (gt) {
                case WEEKLY -> {
                    cutOffTime -= 7L * 24 * 60 * 60 * 1000;
                    occurences = new double[7];
                    xAxis = "Time";
                    title = "Symptom Frequency over Week";
                }
                case MONTHLY -> {
                    cutOffTime -= 30L * 24 * 60 * 60 * 1000;
                    occurences = new double[30];
                    xAxis = "Time";
                    title = "Symptom Frequency over Month";
                }
                case ANNUAL -> {
                    cutOffTime -= 365L * 24 * 60 * 60 * 1000;
                    occurences = new double[365];
                    xAxis = "Time";
                    title = "Symptom Frequency over Year";
                }
            }

            long currentTime = System.currentTimeMillis();
            long divSize = (currentTime - cutOffTime) / occurences.length;
            while(rs.next()){
                long time = rs.getDate("symptomDate").getTime();
                if(time > cutOffTime && time < currentTime){
                    long diff = time - cutOffTime;
                    occurences[(int)(diff / divSize)] ++;
                }
            }

            HistogramDataset dataset = new HistogramDataset();
            dataset.addSeries("Frequency of symptom", occurences, occurences.length);
            JFreeChart histogram = ChartFactory.createHistogram(title,
                    xAxis, "Frequency", dataset);
            ChartPanel chart = new ChartPanel(histogram);
            JPanel panel = new JPanel(new GridLayout(1,1));
            panel.add(chart);
            panel.setPreferredSize(new Dimension(400,400));
            chart.setDomainZoomable(false);
            chart.setRangeZoomable(false);
            return panel;
        }catch (Exception e){
            e.printStackTrace();
            JPanel panel = new JPanel();
            panel.add(new JLabel("Error Fetching Data: " + e.getLocalizedMessage()));
            return panel;
        }
    }

    public enum GraphType{
        WEEKLY,
        MONTHLY,
        ANNUAL
    }
}
