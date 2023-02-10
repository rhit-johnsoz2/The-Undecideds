package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class PatientWindow {

    public void launch(){
        JFrame framePatient = new JFrame();
        framePatient.setSize(700, 500);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel home = new JPanel(false);
        JPanel viewHistory = new JPanel(false);
        JPanel addSymptom = new JPanel(false);

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("viewHistory", null, viewHistory, "");
        tabbedPane.addTab("addSymptom", null, addSymptom, "");

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();

        home.setLayout(layout);
        JLabel hello = new JLabel("Hello Jorg Hansen");
        JLabel curSympts = new JLabel("Current Symptoms");



        double[] values = {10,11,3,11,12,13,15};
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("Frequency of symptom", values, 7);

        JFreeChart histogram = ChartFactory.createHistogram("Cough Histogram",
                "Severity", "Frequency", dataset);
        ChartPanel chartPanel = new ChartPanel(histogram);

        {
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 0;
            home.add(hello, gbc);
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 1;
            home.add(curSympts, gbc);
            gbc.ipadx = 200;
            gbc.ipady = 200;
            gbc.gridx = 0;
            gbc.gridy = 2;
            home.add(chartPanel,gbc);
        }

        viewHistory.setLayout(layout);
        JLabel viewHistoryText = new JLabel("View History");

        double[] values2 = {10,11,3,11,12,13,15};
        HistogramDataset dataset2 = new HistogramDataset();
        dataset.addSeries("Frequency of symptom", values, 7);

        JFreeChart histogram2 = ChartFactory.createHistogram("Cough Histogram",
                "Severity", "Frequency", dataset);
        ChartPanel chartPanel2 = new ChartPanel(histogram);

        {
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 0;
            viewHistory.add(viewHistoryText, gbc);
            gbc.ipadx = 200;
            gbc.ipady = 200;
            gbc.gridx = 0;
            gbc.gridy = 1;
            viewHistory.add(chartPanel2,gbc);
        }
        addSymptom.setLayout(layout);
        JLabel selectSympt = new JLabel("Select Symptom:");
        JLabel selectSev = new JLabel("Select Severity:");
        JButton confirm2 = new JButton("Confrim");
        String[] symps = {"Cold","Flu","Migraine"};
        String[] sev = {"1","2","3","4","5"};
        JComboBox selectSymptom = new JComboBox(symps);
        JComboBox selectSeverity = new JComboBox(sev);
        JTextField lastNameEnter = new JTextField();

        confirm2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                int result = InsertServiceList.INSERT_ACUTE.ExecuteQuery(new Object[]{
                        19, 1, 3, java.sql.Date.valueOf(LocalDate.now())
                });
            }
        });

        {
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 0;
            addSymptom.add(selectSympt,gbc);
            gbc.ipadx = 160;
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 1;
            addSymptom.add(selectSymptom,gbc);

            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 2;
            addSymptom.add(selectSev,gbc);
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 3;
            addSymptom.add(selectSeverity,gbc);
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 4;
            addSymptom.add(confirm2,gbc);}


        framePatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePatient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        framePatient.setVisible(true);
    }
}
