package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
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

public class DoctorWindow {

    public void launch() {
        JFrame frameDoctor = new JFrame();
        frameDoctor.setSize(700, 500);

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel home = new JPanel(false);
        JPanel assignTreatment = new JPanel(false);
        JPanel addPatient = new JPanel(false);
        JPanel viewPatient = new JPanel(false);

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("Assign Treatment", null, assignTreatment, "");
        tabbedPane.addTab("Add Patient", null, addPatient, "");
        tabbedPane.addTab("View Patient", null, viewPatient, "");

        JLabel helloDoctor = new JLabel("Hello Dr. Johnson");
        JLabel currentPatients = new JLabel("Current Patient Quick View");
        JLabel Jorg = new JLabel("Jorg");

        {

            {
                home.setLayout(layout);
                gbc.gridx = 0;
                gbc.gridy = 0;
                home.add(helloDoctor, gbc);
                gbc.gridx = 0;
                gbc.gridy = 1;
                home.add(currentPatients, gbc);
                gbc.ipadx = 200;
                gbc.ipady = 200;
                gbc.gridx = 0;
                gbc.gridy = 2;
                home.add(genHistogram(id, PatientWindow.GraphType.MONTHLY), gbc);
            }
        }

        JLabel selectPatient = new JLabel("Select Patient:");
        JLabel treatment = new JLabel("SelectTreatment:");
        JButton confirm = new JButton("Confrim");
        String[] pat = {"Jorg Henson"};
        String[] treatments = {"Badvillll","Eye Drops"};
        JComboBox selectPatientDropDown = new JComboBox(pat);
        JComboBox selectTreatment = new JComboBox(treatments);

        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int result = InsertServiceList.INSERT_NEEDS.ExecuteQuery(new Object[]{
                        19, 2, java.sql.Date.valueOf(LocalDate.now()), null
                });

            }
        });

        {
            assignTreatment.setLayout(layout);
            gbc.ipadx = 100;
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 0;
            assignTreatment.add(selectPatient, gbc);
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.ipadx = 100;
            assignTreatment.add(selectPatientDropDown, gbc);
            gbc.ipadx = 100;
            gbc.ipady = 40;
            gbc.gridx = 1;
            gbc.gridy = 0;
            assignTreatment.add(treatment);
            gbc.ipadx = 100;
            gbc.ipady = 40;
            gbc.gridx = 1;
            gbc.gridy = 1;
            assignTreatment.add(selectTreatment, gbc);
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 3;
            assignTreatment.add(confirm, gbc);
        }

        addPatient.setLayout(layout);
        JLabel firstName = new JLabel("Enter Patient First Name:");
        JLabel lastName = new JLabel("Enter Patient Last Name:");
        JButton confirm2 = new JButton("Confrim");
        JTextField firstNameEnter = new JTextField();
        JTextField lastNameEnter = new JTextField();
        JButton go = new JButton("Go");
        JButton go2 = new JButton("Go");

        confirm2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int result = InsertServiceList.INSERT_PERFORMS.ExecuteQuery(new Object[]{
                        13, 19
                });
            }
        });

        {
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 0;
            addPatient.add(firstName, gbc);
            gbc.ipadx = 160;
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 1;
            addPatient.add(firstNameEnter, gbc);
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 2;
            addPatient.add(lastName, gbc);
            gbc.ipadx = 160;
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 3;
            addPatient.add(lastNameEnter, gbc);
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 4;
            addPatient.add(confirm2, gbc);
        }
        viewPatient.setLayout(layout);
        JLabel myPatients = new JLabel("My Patients:");
        {

            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 0;
            viewPatient.add(myPatients, gbc);
            gbc.ipadx = 200;
            gbc.ipady = 200;
            gbc.gridx = 0;
            gbc.gridy = 2;
            viewPatient.add(genHistogram(id,GraphType.MONTHLY),gbc);
        }

        frameDoctor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameDoctor.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frameDoctor.setVisible(true);

    }

    public Container genHistogram(int id , PatientWindow.GraphType gt){
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