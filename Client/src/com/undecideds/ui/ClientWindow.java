package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ClientWindow implements ActionListener {
    JPanel panel;
    JLabel user_label;
    JLabel password_label;
    JLabel message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit;
    JFrame frame = new JFrame();

    public ClientWindow() {
    }

    public void launch() {
        this.user_label = new JLabel();
        this.user_label.setText("User Name :");
        this.userName_text = new JTextField();
        this.password_label = new JLabel();
        this.password_label.setText("Password :");
        this.password_text = new JPasswordField();
        this.submit = new JButton("SUBMIT");
        this.panel = new JPanel(new GridLayout(5, 1));
        this.panel.add(this.user_label);
        this.panel.add(this.userName_text);
        this.panel.add(this.password_label);
        this.panel.add(this.password_text);
        this.message = new JLabel();
        this.panel.add(this.message);
        this.panel.add(this.submit);
        this.submit.addActionListener(this);
        this.frame.add(this.panel, "Center");
        this.frame.setTitle("Please Login Here !");
        this.frame.setSize(500, 300);
        this.frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String userName = this.userName_text.getText();
        String password = this.password_text.getText();
        if (userName.trim().equals("doctor") && password.trim().equals("doctor")) {
            this.DoctorPage();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } else if (userName.trim().equals("patient") && password.trim().equals("patient")) {
            PatientPage();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } else {
            this.message.setText(" Invalid user.. ");
        }

    }

    public void PatientPage() {
        JFrame frame = new JFrame();
        frame.setSize(500, 300);
        frame.setTitle("Symptom Tracker: Patient View");
        String[] symptoms = new String[]{"Cold", "Headache"};
        JComboBox<String> symptom = new JComboBox(symptoms);
        String[] severities = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        JComboBox<String> severity = new JComboBox(severities);
        JButton submit = new JButton("Submit");
        submit.addActionListener((e) -> {
            int result = InsertServiceList.INSERT_ACUTE.ExecuteQuery(new Object[]{2,1, Integer.valueOf(severity.getItemAt(severity.getSelectedIndex())), Timestamp.from(Instant.now())});
            System.out.println(InsertServiceList.INSERT_ACUTE.codeMeaning(result));
        });
        String[] chronicSymptoms = new String[]{"Cough"};
        JComboBox<String> chronicSymptom = new JComboBox(chronicSymptoms);
        JButton submit2 = new JButton("Submit");
        submit2.addActionListener((e) -> {
            int result = InsertServiceList.INSERT_CHRONIC.ExecuteQuery(new Object[]{2, 2});
            System.out.println(InsertServiceList.INSERT_CHRONIC.codeMeaning(result));
        });
        String[][] data = new String[][]{{"Cough", "3", "10:00:00PM"}, {"Cough", "5", "10:00:00AM"}, {"Cough", "8", "10:00:00PM"}};
        String[] columnNames = new String[]{"Name", "Roll Number", "Department"};
        JTable j = new JTable(data, columnNames);
        j.setBounds(50, 150, 200, 50);
        JLabel TableTitle = new JLabel("Recent Symptoms");
        TableTitle.setBounds(50, 125, 140, 20);
        JLabel header = new JLabel("Patient View");
        JPanel Panel = new JPanel();
        Panel.setLayout(new BoxLayout(Panel, 0));
        JPanel ChronicPanel = new JPanel();
        ChronicPanel.setLayout(new BoxLayout(ChronicPanel, 0));
        GridBagLayout layout = new GridBagLayout();
        Panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 0;
        Panel.add(header, gbc);
        gbc.fill = 2;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        Panel.add(symptom, gbc);
        gbc.ipady = 20;
        gbc.gridx = 1;
        gbc.gridy = 1;
        Panel.add(severity, gbc);
        gbc.ipady = 20;
        gbc.gridx = 2;
        gbc.gridy = 1;
        Panel.add(submit, gbc);
        gbc.fill = 2;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 2;
        Panel.add(chronicSymptom, gbc);
        gbc.ipady = 20;
        gbc.gridx = 1;
        gbc.gridy = 2;
        Panel.add(submit2, gbc);
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 3;
        Panel.add(TableTitle, gbc);
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 4;
        Panel.add(j, gbc);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(Panel);
        frame.add(controlPanel);
        frame.setVisible(true);
        frame.setVisible(true);
    }

    public void DoctorPage() {
        int doctorID = 5;
        JFrame frame = new JFrame();
        frame.setSize(700, 300);
        frame.setTitle("Symptom Tracker: Doctor View");
        JLabel header = new JLabel("Doctor View");
        JPanel DoctorPanel = new JPanel();
        JLabel addPatient = new JLabel("Add a Patient:");
        GridBagLayout layout = new GridBagLayout();
        DoctorPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        JTextField patientFillIn = new JTextField();
        new JButton("Submit");
        JLabel TreatmentHeader = new JLabel("Select Treatment for Patient");
        JLabel DateHeader = new JLabel("Enter Date in yyyy-mm-dd");
        String[] Treatments = new String[]{"Drink Water", "Tylenol"};
        JComboBox<String> Treatment = new JComboBox(Treatments);
        String[] patients = new String[]{"Max C", "Zach J"};
        JComboBox<String> MyPatient = new JComboBox(patients);
        JTextField EndDate = new JTextField();
        JButton submit2 = new JButton("Submit");
        submit.addActionListener((e) -> {
            int result = InsertServiceList.INSERT_DOCTORFOR.ExecuteQuery(new Object[]{3, 2});
            System.out.println(InsertServiceList.INSERT_DOCTORFOR.codeMeaning(result));
        });

            submit2.addActionListener((e2) -> {
            int result2 = InsertServiceList.INSERT_NEEDS.ExecuteQuery(new Object[]{3, 2, Date.valueOf(EndDate.getText()), null});
            System.out.println(InsertServiceList.INSERT_NEEDS.codeMeaning(result2));
        });
        gbc.fill = 2;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 0;
        DoctorPanel.add(header, gbc);
        gbc.fill = 2;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        DoctorPanel.add(addPatient, gbc);
        gbc.ipady = 27;
        gbc.gridx = 0;
        gbc.gridy = 2;
        DoctorPanel.add(patientFillIn, gbc);
        gbc.ipady = 20;
        gbc.gridx = 1;
        gbc.gridy = 2;
        DoctorPanel.add(this.submit, gbc);
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 3;
        DoctorPanel.add(TreatmentHeader, gbc);
        gbc.ipady = 20;
        gbc.gridx = 2;
        gbc.gridy = 3;
        DoctorPanel.add(DateHeader, gbc);
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 4;
        DoctorPanel.add(Treatment, gbc);
        gbc.ipady = 20;
        gbc.gridx = 1;
        gbc.gridy = 4;
        DoctorPanel.add(MyPatient, gbc);
        gbc.ipady = 27;
        gbc.ipadx = 100;
        gbc.gridx = 2;
        gbc.gridy = 4;
        DoctorPanel.add(EndDate, gbc);
        gbc.ipady = 20;
        gbc.gridx = 5;
        gbc.gridy = 4;
        DoctorPanel.add(submit2, gbc);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(DoctorPanel);
        frame.add(controlPanel);
        frame.setVisible(true);
        frame.setVisible(true);
    }
}




