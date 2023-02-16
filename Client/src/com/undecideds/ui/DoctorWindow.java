package com.undecideds.ui;

import com.undecideds.services.DeleteServiceList;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.builders.GenHistogram;
import com.undecideds.ui.builders.TableBuilder;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;

public class DoctorWindow {

    int currentId;
    String currentName;

    public void launch(int id, String name){
        currentId = id;
        currentName = name;
        JFrame frameDoctor = new JFrame();
        frameDoctor.setSize(700, 500);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Home", null, home(), "");
        tabbedPane.addTab("My Treatments", null, myTreatments(), "");
        tabbedPane.addTab("Add Patient", null, addPatient(), "");
        tabbedPane.addTab("Remove Patient", null, removePatient(), "");
        tabbedPane.addTab("View Patient", null, viewPatient(), "");

        frameDoctor.add(tabbedPane);
        frameDoctor.setVisible(true);
    }

    public JPanel home(){
        JPanel homePanel = new JPanel(false);
        JLabel helloDoctor = new JLabel("Hello Dr. Johnson");
        homePanel.setLayout(new GridLayout(1,1));
        homePanel.add(helloDoctor);
        return homePanel;
    }

    public JPanel viewPatient() {
        //Make table of
        JPanel viewPatient = new JPanel();
        viewPatient.setLayout(new GridLayout(2,1));
        ResultSet rs = ReadServiceList.GET_ALL_PATIENTS_BY_DOCTOR.ExecuteQuery(new Object[]{currentId});
        JTable patients;
        JComponent tableNullable = TableBuilder.buildTableRaw(rs, new HashSet<>());
        if (tableNullable instanceof JTable) {
            patients = (JTable) tableNullable;
        } else {
            viewPatient.add(tableNullable);
            return viewPatient;
        }
        viewPatient.add(patients);
        JButton selectorButton = new JButton("View Symptom Treatments");
        HashMap<String, Object> inputValues = new HashMap<>();
        selectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popUpWindow = new JFrame();
                popUpWindow.setSize(400, 400);
                popUpWindow.add(DoctorViewingPatientWindow.launchViewHistory(false, Integer.parseInt(inputValues.get("ID").toString())));
                popUpWindow.setVisible(true);
            }
        });
        patients.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (patients.getSelectedRow() != -1) {

                    inputValues.clear();
                    for (int i = 0; i < patients.getColumnCount(); i++) {
                        System.out.println(patients.getColumnName(i) + " : " + patients.getValueAt(patients.getSelectedRow(), i));
                        inputValues.put(patients.getColumnName(i), patients.getValueAt(patients.getSelectedRow(), i));
                    }
                    selectorButton.setEnabled(true);

                } else {
                    selectorButton.setEnabled(false);
                }
            }
        });
        viewPatient.add(selectorButton);
        return viewPatient;
    }


    public JPanel myTreatments(){
        JPanel myTreatmentsPanel = new JPanel(false);
        myTreatmentsPanel.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.GET_TREATMENTS_FROM_DOCTOR.ExecuteQuery(new Object[]{currentId});

        myTreatmentsPanel.add(TableBuilder.buildTable(rs));
        return myTreatmentsPanel;
    }
    public JPanel addPatient(){
        JPanel addPatient = new JPanel(false);
        addPatient.setLayout(new GridLayout(1,2));
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
        return addPatient;
    }
    public JPanel removePatient(){
        JPanel removePatient = new JPanel(false);
        removePatient.setLayout(new GridLayout(2,1));
        HashMap<String, ReadService> idMatch = new HashMap<>();
        idMatch.put("DOCTOR ID", ReadServiceList.GET_DOCTORFOR);
        idMatch.put("PATIENT ID", ReadServiceList.GET_DOCTORFOR);
        HashMap<String, InputWidget> widgets = DeleteServiceList.DELETE_DOCTORFOR.buildUIWidgets(idMatch, true);
        JPanel Wpanel = new JPanel();
        for(String key : widgets.keySet()){
            Wpanel.add(widgets.get(key).generateWidget());
        }
        HashMap<String, ReadService> idMatch2 = new HashMap<>();
        idMatch2.put("DOCTOR ID", ReadServiceList.GET_DOCTOR_NAMES);
        idMatch2.put("PATIENT ID", ReadServiceList.GET_PATIENT_NAMES);
        HashMap<String, InputWidget> widgets2 = DeleteServiceList.DELETE_DOCTORFOR.buildUIWidgets(idMatch2, true);
        widgets2.replace("DOCTOR ID", new InputWidget("DOCTOR ID") {
            @Override
            public Container generateWidget() {
                return new JPanel();
            }

            @Override
            public Object getValue() {
                return currentId;
            }
        });
        JPanel Wpanel2 = new JPanel();
        for(String key : widgets2.keySet()){
            Wpanel2.add(widgets2.get(key).generateWidget());
        }
        Container runButton2 = DeleteServiceList.DELETE_DOCTORFOR.buildActivateButton("Remove", widgets2, new ResultListener(){
            @Override
            public void onResult(int result) {
                // DO ON RUN
            }});
        removePatient.add(Wpanel2);
        removePatient.add(runButton2);
        return removePatient;
    }


}

