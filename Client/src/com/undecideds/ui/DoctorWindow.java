package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.builders.GenHistogram;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;

public class DoctorWindow {

    int currentId;
    String currentName;
    public void launch(int id, String name) {
        currentId = id;
        currentName = name;
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
                //GenHistogram newHisto = new GenHistogram();
                //home.add(newHisto.GenHistogram(1, GenHistogram.GraphType.WEEKLY), gbc);
            }
        }

//        JLabel selectPatient = new JLabel("Select Patient:");
//        JLabel treatment = new JLabel("SelectTreatment:");
//        JButton confirm = new JButton("Confirm");
//        String[] pat = {"Jorg Henson"};
//        String[] treatments = {"Badvillll","Eye Drops"};
//        JComboBox selectPatientDropDown = new JComboBox(pat);
//        JComboBox selectTreatment = new JComboBox(treatments);

//        confirm.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent arg0) {
//                int result = InsertServiceList.INSERT_NEEDS.ExecuteQuery(new Object[]{
//                        19, 2, java.sql.Date.valueOf(LocalDate.now()), null
//                });
//
//            }
//        });

        assignTreatment.setLayout(layout);
//            gbc.ipadx = 100;
//            gbc.ipady = 40;
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            assignTreatment.add(selectPatient, gbc);
//            gbc.ipady = 40;
//            gbc.gridx = 0;
//            gbc.gridy = 1;
//            gbc.ipadx = 100;
//            assignTreatment.add(selectPatientDropDown, gbc);
//            gbc.ipadx = 100;
//            gbc.ipady = 40;
//            gbc.gridx = 1;
//            gbc.gridy = 0;
//            assignTreatment.add(treatment);
//            gbc.ipadx = 100;
//            gbc.ipady = 40;
//            gbc.gridx = 1;
//            gbc.gridy = 1;
//            assignTreatment.add(selectTreatment, gbc);
//            gbc.ipady = 40;
//            gbc.gridx = 0;
//            gbc.gridy = 3;
//            assignTreatment.add(confirm, gbc);

            HashMap<String, ReadService> idMatch = new HashMap<>();
            idMatch.put("PATIENT ID", ReadServiceList.GET_PATIENT_NAMES);
            idMatch.put("TREATMENT ID", ReadServiceList.GET_TREATMENT_NAMES);
            HashMap<String, InputWidget> widgets = InsertServiceList.INSERT_NEEDS.buildUIWidgets(idMatch, true);
            JPanel Wpanel = new JPanel();
            for(String key : widgets.keySet()){
                Wpanel.add(widgets.get(key).generateWidget());
            }
            Container runButton = InsertServiceList.INSERT_NEEDS.buildActivateButton("Assign", widgets, new ResultListener(){
                @Override
                public void onResult(int result) {
                    // DO ON RUN
                }});
            Wpanel.add(runButton);
            Wpanel.setLayout(new BoxLayout(Wpanel, BoxLayout.PAGE_AXIS));
            assignTreatment.add(Wpanel);

        addPatient.setLayout(layout);
//        JLabel firstName = new JLabel("Enter Patient First Name:");
//        JLabel lastName = new JLabel("Enter Patient Last Name:");
//        JButton confirm2 = new JButton("Confirm");
//        JTextField firstNameEnter = new JTextField();
//        JTextField lastNameEnter = new JTextField();
//        JButton go = new JButton("Go");
//        JButton go2 = new JButton("Go");

        HashMap<String, ReadService> idMatch2 = new HashMap<>();
        idMatch2.put("DOCTOR ID", ReadServiceList.GET_DOCTOR_NAMES);
        idMatch2.put("PATIENT ID", ReadServiceList.GET_PATIENT_NAMES);
        HashMap<String, InputWidget> widgets2 = InsertServiceList.INSERT_DOCTORFOR.buildUIWidgets(idMatch2, true);
        JPanel Wpanel2 = new JPanel();
        for(String key : widgets2.keySet()){
            Wpanel2.add(widgets2.get(key).generateWidget());
        }
        Container runButton2 = InsertServiceList.INSERT_DOCTORFOR.buildActivateButton("Add", widgets2, new ResultListener(){
            @Override
            public void onResult(int result) {
                // DO ON RUN
            }});
        Wpanel2.add(runButton2);
        Wpanel2.setLayout(new BoxLayout(Wpanel2, BoxLayout.PAGE_AXIS));
        addPatient.add(Wpanel2);

//        confirm2.addActionListener(new ActionListener() {
////            public void actionPerformed(ActionEvent arg0) {
////                int result = InsertServiceList.INSERT_PERFORMS.ExecuteQuery(new Object[]{
////                        13, 19
////                });
////            }
////        });
//        {
//            gbc.ipady = 40;
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            addPatient.add(firstName, gbc);
//            gbc.ipadx = 160;
//            gbc.ipady = 40;
//            gbc.gridx = 0;
//            gbc.gridy = 1;
//            addPatient.add(firstNameEnter, gbc);
//            gbc.ipady = 40;
//            gbc.gridx = 0;
//            gbc.gridy = 2;
//            addPatient.add(lastName, gbc);
//            gbc.ipadx = 160;
//            gbc.ipady = 40;
//            gbc.gridx = 0;
//            gbc.gridy = 3;
//            addPatient.add(lastNameEnter, gbc);
//            gbc.ipady = 40;
//            gbc.gridx = 0;
//            gbc.gridy = 4;
//            addPatient.add(confirm2, gbc);
//        }

        viewPatient.setLayout(layout);
        JLabel myPatients = new JLabel("My Patients:");
//            gbc.ipady = 40;
//            gbc.gridx = 0;
//            gbc.gridy = 0;
        viewPatient.add(myPatients, gbc);
//            gbc.ipadx = 200;
//            gbc.ipady = 200;
//            gbc.gridx = 0;
//            gbc.gridy = 2;
            //GenHistogram newHisto2 = new GenHistogram();
            //viewPatient.add(newHisto2.GenHistogram(1, GenHistogram.GraphType.WEEKLY),gbc);

        frameDoctor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameDoctor.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frameDoctor.setVisible(true);

    }

}
/*
    JFrame popup = new JFrame();
    HashMap<String, ReadService> idMatch = new HashMap<>();
    idMatch.put("PATIENT ID", ReadServiceList.GET_PATIENT_NAMES);
    idMatch.put("TREATMENT ID", ReadServiceList.GET_TREATMENT_NAMES);
    HashMap<String, InputWidget> widgets = InsertServiceList.INSERT_NEEDS.buildUIWidgets(idMatch, true);
    Container runButton = InsertServiceList.INSERT_NEEDS.buildActivateButton("Add", widgets, new ResultListener(){
        @Override
        public void onResult(int result) {
            // DO ON RUN
            popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            popup.dispatchEvent(new WindowEvent(popup, WindowEvent.WINDOW_CLOSING));
        }});
    JPanel Wpanel = new JPanel();
    for(String key : widgets.keySet()){
        Wpanel.add(widgets.get(key).generateWidget());
    }
    Wpanel.setLayout(new BoxLayout(Wpanel, BoxLayout.PAGE_AXIS));
    Wpanel.add(runButton);
    popup.add(Wpanel);
    popup.pack();

    JButton launchPopup = new JButton("Add needs");
    launchPopup.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            popup.setVisible(true);
        }});
    home.add(launchPopup);
*/