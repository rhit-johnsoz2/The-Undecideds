package com.undecideds.ui;


import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.ui.builders.GenHistogram;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.undecideds.services.ReadServiceList.GET_ACUTE;

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
            GenHistogram newHisto = new GenHistogram();
            home.add(newHisto.GenHistogram(1, GenHistogram.GraphType.WEEKLY),gbc);
        }

        viewHistory.setLayout(layout);
        JLabel viewHistoryText = new JLabel("View History");

        {
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 0;
            viewHistory.add(viewHistoryText, gbc);
            gbc.ipadx = 200;
            gbc.ipady = 200;
            gbc.gridx = 0;
            gbc.gridy = 1;
            GenHistogram newHisto2 = new GenHistogram();
            viewHistory.add(newHisto2.GenHistogram(1, GenHistogram.GraphType.WEEKLY),gbc);
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
