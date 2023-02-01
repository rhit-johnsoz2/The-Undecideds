package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class ClientWindow implements ActionListener{
    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit, cancel;
    public void launch(){
            // Username Label
            user_label = new JLabel();
            user_label.setText("User Name :");
            userName_text = new JTextField();
            // Password Label
            password_label = new JLabel();
            password_label.setText("Password :");
            password_text = new JPasswordField();
            // Submit
            submit = new JButton("SUBMIT");
            panel = new JPanel(new GridLayout(3, 1));
            panel.add(user_label);
            panel.add(userName_text);
            panel.add(password_label);
            panel.add(password_text);
            message = new JLabel();
            panel.add(message);
            panel.add(submit);
            // Adding the listeners to components..
            submit.addActionListener(this);
            JFrame frame = new JFrame();
            frame.add(panel, BorderLayout.CENTER);
            frame.setTitle("Please Login Here !");
            frame.setSize(450,350);
            frame.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            String userName = userName_text.getText();
            String password = password_text.getText();
            if (userName.trim().equals("admin") && password.trim().equals("admin")) {
                message.setText(" Hello " + userName + "");
//                this.PatientPage();
                this.DoctorPage();
            } else {
                message.setText(" Invalid user.. ");
            }
        }
    public void PatientPage(){
        JFrame frame = new JFrame();
        frame.setSize(500,300);
        frame.setTitle("Symptom Tracker: Patient View");

//        /* Add Acute */
        String[] symptoms = {"Headache", "Cold"};
        JComboBox<String> symptom = new JComboBox<>(symptoms);
        String[] severities = {"1","2","3","4","5","6","7","8","9","10"};
        JComboBox<String> severity = new JComboBox<>(severities);
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String selectedSymptom = "You selected " + symptom.getItemAt(symptom.getSelectedIndex());
            String selectedSeverity = "You selected " + severity.getItemAt(severity.getSelectedIndex());
            System.out.println(selectedSymptom);
            System.out.println(selectedSeverity);

            int result = InsertServiceList.INSERT_ACUTE.ExecuteQuery(new Object[]{
                    symptom.getItemAt(symptom.getSelectedIndex()),
                    severity.getItemAt(severity.getSelectedIndex()),
                    LocalDateTime.now()
            });
            System.out.println(InsertServiceList.INSERT_ACUTE.codeMeaning(result));
        });



//        /* Add Chronic */
        String[] chronicSymptoms = {"Cough"};
        JComboBox<String> chronicSymptom = new JComboBox<>(chronicSymptoms);
        JButton submit2 = new JButton("Submit");
        submit2.addActionListener(e -> {

            int result = InsertServiceList.INSERT_CHRONIC.ExecuteQuery(new Object[]{
                    chronicSymptom.getItemAt(chronicSymptom.getSelectedIndex()),
                    LocalDateTime.now()
            });
            System.out.println(InsertServiceList.INSERT_CHRONIC.codeMeaning(result));
        });

//        /* Symptom Table */
        JTable j;
        String[][] data = {
                { "Cough", "3", "10:00:00PM" },
                { "Cough", "5", "10:00:00AM" },
                { "Cough", "8", "10:00:00PM" }
        };
        String[] columnNames = { "Name", "Roll Number", "Department" };
        j = new JTable(data, columnNames);
        j.setBounds(50, 150, 200, 50);
        JLabel TableTitle = new JLabel("Recent Symptoms");
        TableTitle.setBounds(50, 125, 140, 20);

        JLabel header = new JLabel("Patient View");
        JPanel Panel = new JPanel();
        Panel.setLayout(new BoxLayout(Panel, BoxLayout.X_AXIS));
        JPanel ChronicPanel = new JPanel();
        ChronicPanel.setLayout(new BoxLayout(ChronicPanel, BoxLayout.X_AXIS));

        GridBagLayout layout = new GridBagLayout();
        Panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 0;
        Panel.add(header,gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        Panel.add(symptom,gbc);
        gbc.ipady = 20;
        gbc.gridx = 1;
        gbc.gridy = 1;
        Panel.add(severity,gbc);
        gbc.ipady = 20;
        gbc.gridx = 2;
        gbc.gridy = 1;
        Panel.add(submit, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 2;
        Panel.add(chronicSymptom, gbc);
        gbc.ipady = 20;
        gbc.gridx = 1;
        gbc.gridy = 2;
        Panel.add(submit2,gbc);

        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 3;
        Panel.add(TableTitle,gbc);

        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 4;
        Panel.add(j,gbc);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(Panel);
        frame.add(controlPanel);
        frame.setVisible(true);

        frame.setVisible(true);
    }

    public void DoctorPage(){
        int doctorID = 5;
        JFrame frame = new JFrame();
        frame.setSize(500,300);
        frame.setTitle("Symptom Tracker: Doctor View");

        JLabel header = new JLabel("Doctor View");
        JPanel DoctorPanel = new JPanel();

        JLabel addPatient = new JLabel("Add a Patient:");

        GridBagLayout layout = new GridBagLayout();
        DoctorPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        JTextField patientFillIn = new JTextField();
        JButton submit1 = new JButton("Submit");
        submit.addActionListener(e -> {
            String patientID = "You selected " + patientFillIn.getText();
            System.out.println(patientID);
            int result = InsertServiceList.INSERT_DOCTORFOR.ExecuteQuery(new Object[]{
                doctorID, patientFillIn.getText()
            });
        System.out.println(InsertServiceList.INSERT_DOCTORFOR.codeMeaning(result));
        });

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 0;
        DoctorPanel.add(header,gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        DoctorPanel.add(addPatient,gbc);
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 2;
        DoctorPanel.add(patientFillIn,gbc);
        gbc.ipady = 20;
        gbc.gridx = 1;
        gbc.gridy = 2;
        DoctorPanel.add(submit, gbc);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(DoctorPanel);
        frame.add(controlPanel);
        frame.setVisible(true);

        frame.setVisible(true);
    }


}




