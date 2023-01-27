package com.undecideds.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.undecideds.services.InsertServiceList;

public class ClientWindow {
    public void launch(){
    	JFrame frame = new JFrame();
        frame.setSize(500,300);
        frame.setTitle("Symptom Tracker: Patient View");        
        JLabel header = new JLabel("Patient View");
        header.setBounds(50, 10, 140, 20);
        frame.add(header);
        
        //Add Acute
        String[] symptoms = {"Headache", "Cold"};
        JComboBox<String> symptom = new JComboBox<>(symptoms);
        symptom.setBounds(50, 50, 140, 20);
        String[] severities = {"1","2","3","4","5","6","7","8","9","10"};
        JComboBox<String> severity = new JComboBox<>(severities);
        severity.setBounds(220, 50, 70, 20);
        frame.add(symptom);
        frame.add(severity);
        JButton submit = new JButton("Submit");
        submit.setBounds(320, 50, 90, 20);
        frame.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        
        //Add Chronic
        String[] chronicSymptoms = {"Cough"};
        JComboBox<String> chronicSymptom = new JComboBox<>(chronicSymptoms);
        chronicSymptom.setBounds(50, 100, 70, 20);
        JButton submit2 = new JButton("Submit");
        submit2.setBounds(150, 100, 90, 20);
        frame.add(submit2);
        frame.add(chronicSymptom);
        frame.setLayout(null);
        frame.setVisible(true);    
        submit2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                int result = InsertServiceList.INSERT_CHRONIC.ExecuteQuery(new Object[]{
                		chronicSymptom.getItemAt(chronicSymptom.getSelectedIndex()), 
                		LocalDateTime.now()
                });
                System.out.println(InsertServiceList.INSERT_CHRONIC.codeMeaning(result));
            }
        });
        
            JTable j;
            String[][] data = {
                { "Cough", "3", "10:00:00PM" },
                { "Cough", "5", "10:00:00AM" },
                { "Cough", "8", "10:00:00PM" }
            };
            String[] columnNames = { "Name", "Roll Number", "Department" };
            j = new JTable(data, columnNames);
            j.setBounds(50, 150, 200, 50);
            frame.add(j);
            
            JLabel TableTitle = new JLabel("Recent Symptoms");
            TableTitle.setBounds(50, 125, 140, 20);

            frame.add(TableTitle);
        
            frame.setVisible(true);
        //////////    Doctor View    //////////
        
//        JFrame frame2 = new JFrame();
//        frame2.setSize(500,300);
//        frame2.setTitle("Symptom Tracker: Patient View");        
//        JLabel header2 = new JLabel("Doctor View");
//        header2.setBounds(20, 10, 140, 20);
//        frame2.add(header2);
//        frame2.setVisible(true); 
        
        
        
    }

}


