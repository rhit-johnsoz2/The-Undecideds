package com.undecideds.ui;

import com.undecideds.services.DeleteServiceList;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.generic.ReadService;
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


public class PatientWindow {
    int id;
    String name;
    public void launch(int id, String name){
        this.id = id;
        this.name = name;
        JFrame framePatient = new JFrame(name + "'s information");
        framePatient.setSize(700, 500);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel home = DoctorViewingPatientWindow.launchHome(false, id, name);
        JPanel viewChronic = viewChronicMethod();
        JPanel viewMyDocs = viewMyDoctors();
        JPanel viewPastTreatments = pastTreatments();
        JPanel viewCurTreatments = curTreatments();
        JPanel removeMyDocs = removeMyDocs();
        JPanel viewAcute = viewAcute();

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("Acute Symptoms", null, viewAcute, "");
        tabbedPane.addTab("Chronic Symptoms", null, viewChronic, "");
        tabbedPane.addTab("View Doctors", null, viewMyDocs, "");
        tabbedPane.addTab("Remove Doctors", null, removeMyDocs, "");
        tabbedPane.addTab("Past Treatments", null, viewPastTreatments, "");
        tabbedPane.addTab("Current Treatments", null, viewCurTreatments, "");

        framePatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePatient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        framePatient.setVisible(true);
    }

    public JPanel viewAcute(){
        JPanel viewAcute = new JPanel();
        viewAcute.setLayout(new BoxLayout(viewAcute, BoxLayout.Y_AXIS));
        ResultSet rs = ReadServiceList.ACUTE_FROM_PATIENT.ExecuteQuery(new Object[]{id});

        HashSet<String> hiddenIDs = new HashSet<String>();
        hiddenIDs.add("PatientID");

        HashMap<String, ReadService> idMatch = new HashMap<>();
        idMatch.put("SYMPTOM ID", ReadServiceList.GET_SYMPTOMS);
        HashMap<String, InputWidget> widgets = InsertServiceList.INSERT_ACUTE.buildUIWidgets(idMatch, true);
        JTable patients = (JTable) TableBuilder.buildTableRaw(rs, hiddenIDs);
        widgets.replace("PERSON ID", new InputWidget("PERSON ID") {
            @Override
            public Container generateWidget() {
                return new Container();
            }

            @Override
            public Object getValue() {
                return id;
            }
        });
        Container runButton = InsertServiceList.INSERT_ACUTE.buildActivateButton("Add", widgets, new ResultListener() {
            @Override
            public void onResult(int result) {
                ResultSet rs = ReadServiceList.ACUTE_FROM_PATIENT.ExecuteQuery(new Object[]{id});
                HashSet<String> hiddenIDs = new HashSet<String>();
                hiddenIDs.add("PatientID");
                patients.setModel(TableBuilder.getTableModel(rs,hiddenIDs));
            }
        });
        JPanel Wpanel = new JPanel();
        for (String key : widgets.keySet()) {
            Wpanel.add(widgets.get(key).generateWidget());
        }

        viewAcute.add(new JScrollPane(patients));
        viewAcute.add(Wpanel);
        viewAcute.add(runButton);
        return viewAcute;
    }

    public JPanel viewChronicMethod(){
        JPanel viewChronic = new JPanel();
        viewChronic.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.CHRONIC_FROM_PATIENT.ExecuteQuery(new Object[]{id});

        HashSet<String> hiddenIDs = new HashSet<String>();
        hiddenIDs.add("PatientID");
        JTable patients = (JTable) TableBuilder.buildTableRaw(rs, hiddenIDs);
        viewChronic.add(new JScrollPane(patients));
        return viewChronic;
    }

    public JPanel viewMyDoctors(){
        JPanel viewAvalibleDoctors = new JPanel();
        viewAvalibleDoctors.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.DOCTORS_FROM_PATIENT.ExecuteQuery(new Object[]{id});

        HashSet<String> hiddenIDs = new HashSet<String>();
        hiddenIDs.add("PatientID");
        JTable patients = (JTable) TableBuilder.buildTableRaw(rs, hiddenIDs);
        viewAvalibleDoctors.add(new JScrollPane(patients));

        return viewAvalibleDoctors;
    }

    public JPanel removeMyDocs(){
        JPanel removeDoc = new JPanel();
        removeDoc.setLayout(new GridLayout(2,1));
        HashMap<String, ReadService> idMatch2 = new HashMap<>();
        idMatch2.put("DOCTOR ID", ReadServiceList.GET_DOCTOR_NAMES);
        idMatch2.put("PATIENT ID", ReadServiceList.GET_PATIENT_NAMES);
        HashMap<String, InputWidget> widgets2 = DeleteServiceList.DELETE_DOCTORFOR.buildUIWidgets(idMatch2, true);
        JPanel Wpanel2 = new JPanel();
        widgets2.replace("PATIENT ID", new InputWidget("PATIENT ID") {
            @Override
            public Container generateWidget() {
                return new JPanel();
            }

            @Override
            public Object getValue() {
                return id;
            }
        });
        for(String key : widgets2.keySet()){
            Wpanel2.add(widgets2.get(key).generateWidget());
        }
        Container runButton2 = DeleteServiceList.DELETE_DOCTORFOR.buildActivateButton("Remove",widgets2, new ResultListener(){
            @Override
            public void onResult(int result) {
                // DO ON RUN
            }});
        removeDoc.add(Wpanel2);
        removeDoc.add(runButton2);
        return removeDoc;
    }

    public JPanel pastTreatments(){
        JPanel viewPastTreatments = new JPanel();
        viewPastTreatments.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.GET_PAST_TREATMENTS.ExecuteQuery(new Object[]{id});

        viewPastTreatments.add(TableBuilder.buildTable(rs));
        return viewPastTreatments;
    }

    public JPanel curTreatments(){
        JPanel viewcurTreatments = new JPanel();
        viewcurTreatments.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.GET_CURRENT_TREATMENTS.ExecuteQuery(new Object[]{id});
        viewcurTreatments.add(TableBuilder.buildTable(rs));
        return viewcurTreatments;
    }


}
