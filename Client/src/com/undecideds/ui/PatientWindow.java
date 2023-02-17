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
        JPanel viewAcute = viewAcute();

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("Acute Symptoms", null, viewAcute, "");
        tabbedPane.addTab("Chronic Symptoms", null, viewChronic, "");
        tabbedPane.addTab("View Doctors", null, viewMyDocs, "");
        tabbedPane.addTab("Past Treatments", null, viewPastTreatments, "");
        tabbedPane.addTab("Current Treatments", null, viewCurTreatments, "");

        framePatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePatient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        framePatient.setVisible(true);
    }

    public JPanel viewMyDoctors(){
        JPanel viewAvalibleDoctors = new JPanel();
        viewAvalibleDoctors.setLayout(new BoxLayout(viewAvalibleDoctors, BoxLayout.PAGE_AXIS));
        ResultSet rs = ReadServiceList.DOCTORS_FROM_PATIENT.ExecuteQuery(new Object[]{id});

        HashSet<String> hiddenIDs = new HashSet<String>();
        hiddenIDs.add("PatientID");
        JTable patients = (JTable) TableBuilder.buildTableRaw(rs, hiddenIDs);
        viewAvalibleDoctors.add(new JScrollPane(patients));

        HashMap<String, ReadService> idMatch2 = new HashMap<>();
        idMatch2.put("PATIENT ID", ReadServiceList.GET_PATIENT_NAMES);
        HashMap<String, InputWidget> widgets = DeleteServiceList.DELETE_DOCTORFOR.buildUIWidgets(idMatch2, true);
        JPanel Wpanel = new JPanel();

        widgets.replace("PATIENT ID", new InputWidget("PATIENT ID") {
            @Override
            public Container generateWidget() {
                return new JPanel();
            }
            @Override
            public Object getValue() {
                return id;
            }
        });
        widgets.put("DOCTORS ID", ReadService.generateComboWidget("DOCTORS ID", ReadServiceList.DOCTORS_FROM_PATIENT, new Object[]{id}));
        for(String key : widgets.keySet()){
            Wpanel.add(widgets.get(key).generateWidget());
        }

        Container runButton2 = DeleteServiceList.DELETE_DOCTORFOR.buildActivateButton("Remove",widgets, new ResultListener(){
            @Override
            public void onResult(int result) {
                // DO ON RUN
            }});
        viewAvalibleDoctors.add(Wpanel);
        viewAvalibleDoctors.add(runButton2);

        return viewAvalibleDoctors;
    }

    public static JPanel launchHome(boolean isDoctor, int patientID, String patientName) {
        JPanel home = new JPanel(false);
        Container hist = new GenHistogram().GenHistogram(patientID, GenHistogram.GraphType.WEEKLY);
        Container hist2 = new GenHistogram().GenHistogram(patientID, GenHistogram.GraphType.MONTHLY);
        Container hist3 = new GenHistogram().GenHistogram(patientID, GenHistogram.GraphType.ANNUAL);

        home.setLayout(new GridLayout(2, 4));
        if (isDoctor) {
            JLabel doctorView = new JLabel("You are in doctor view.");
            home.add(doctorView);
        }
        JLabel hello = new JLabel("Hello " + patientName + " back to Symptom Tracker!");
        JLabel curSympts = new JLabel(" Current Symptoms");
        home.add(hello);
        home.add(curSympts);
        home.add(new JLabel());
        home.add(hist);
        home.add(hist2);
        home.add(hist3);
        return home;
    }
}
