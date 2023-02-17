package com.undecideds.ui;

import com.undecideds.cli.migration.Table;
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
import javax.xml.transform.Result;
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
        frameDoctor.setTitle("Dashboard for Dr. " + name);
        frameDoctor.setSize(700, 500);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("My Treatments", null, myTreatments(), "");
        tabbedPane.addTab("Add Patient", null, addPatient(), "");
        tabbedPane.addTab("View Patient", null, viewPatient(), "");

        frameDoctor.add(tabbedPane);
        frameDoctor.setVisible(true);
    }

    public JPanel viewPatient() {
        //Make table of
        JPanel viewPatient = new JPanel();
        viewPatient.setLayout(new BoxLayout(viewPatient, BoxLayout.PAGE_AXIS));
        ResultSet rs = ReadServiceList.PATIENTS_FROM_DOCTOR.ExecuteQuery(new Object[]{currentId});
        HashSet<String> hidden = new HashSet<String>();
        hidden.add("DoctorID");
        hidden.add("ID");
        JTable patients;
        JComponent tableNullable = TableBuilder.buildTableRaw(rs, hidden);
        if (tableNullable instanceof JTable) {
            patients = (JTable) tableNullable;
        } else {
            viewPatient.add(tableNullable);
            return viewPatient;
        }
        viewPatient.add(new JScrollPane(patients));
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
        JButton selectorButton2 = new JButton("Remove Patient");
        selectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteServiceList.DELETE_DOCTORFOR.ExecuteQuery(new Object[]{currentId,Integer.parseInt(inputValues.get("ID").toString())});
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
        viewPatient.add(selectorButton2);
        viewPatient.add(selectorButton);
        return viewPatient;
    }

    public JPanel myTreatments(){
        JPanel myTreatmentsPanel = new JPanel(false);
        myTreatmentsPanel.setLayout(new BoxLayout(myTreatmentsPanel, BoxLayout.PAGE_AXIS));
        ResultSet rs = ReadServiceList.GET_TREATMENTS_FROM_DOCTOR.ExecuteQuery(new Object[]{currentId});
        Container table = TableBuilder.buildTable(rs);
        myTreatmentsPanel.add(TableBuilder.buildTable(rs));
        HashMap<String, ReadService> idMatch = new HashMap<>();
        idMatch.put("DOCTOR ID", ReadServiceList.GET_DOCTORS);
        idMatch.put("TREATMENT ID", ReadServiceList.GET_TREATMENTS);
        HashMap<String, InputWidget> widgets = InsertServiceList.INSERT_PERFORMS.buildUIWidgets(idMatch, true);

        widgets.replace("DOCTOR ID", new InputWidget("DOCTOR ID") {
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
        for(String key : widgets.keySet()){
            Wpanel2.add(widgets.get(key).generateWidget());
        }
        myTreatmentsPanel.add(Wpanel2);
        Container addTreatment = InsertServiceList.INSERT_PERFORMS.buildActivateButton("Add Treatment ", widgets, new ResultListener() {
            @Override
            public void onResult(int result) {
                try {
                    // update TABLE


                }catch (Exception e){
                    System.out.println("fatal error refetching table");
                    e.printStackTrace();
                    System.exit(501);
                }
            }
        });
        myTreatmentsPanel.add(addTreatment);
        return myTreatmentsPanel;
    }
    public JPanel addPatient(){
        JPanel addPatient = new JPanel(false);
        addPatient.setLayout(new BoxLayout(addPatient, BoxLayout.PAGE_AXIS));
        HashMap<String, InputWidget> widgets = new HashMap<>();
        JPanel Wpanel2 = new JPanel();
        widgets.put("DOCTOR ID", new InputWidget("DOCTOR ID") {
            @Override
            public Container generateWidget() {
                return new Container();
            }

            @Override
            public Object getValue() {
                return currentId;
            }
        });
        widgets.put("PATIENT ID", ReadService.generateComboWidget("PATIENT ID", ReadServiceList.GET_PATIENTS_NOT_FROM_DOCTOR, new Object[]{currentId}));
        for(String key : widgets.keySet()){
            Wpanel2.add(widgets.get(key).generateWidget());
        }
        Container runButton2 = InsertServiceList.INSERT_DOCTORFOR.buildActivateButton("Add", widgets, new ResultListener(){
            @Override
            public void onResult(int result) {
                // DO ON RUN
            }});
        Wpanel2.add(runButton2);
        Wpanel2.setLayout(new BoxLayout(Wpanel2, BoxLayout.PAGE_AXIS));
        addPatient.add(Wpanel2);
        return addPatient;
    }
}

