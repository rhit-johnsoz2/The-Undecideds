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

        DoctorViewingPatientWindow view = new DoctorViewingPatientWindow();

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel home = launchHome(id,name);
        JPanel viewChronic = view.viewChronicMethod(id);
        JPanel viewMyDocs = viewMyDoctors();
        JPanel viewPastTreatments = view.pastTreatments(id);
        JPanel viewCurTreatments = view.curTreatments(id);
        JPanel viewAcute = view.viewAcute(id);

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
//        hiddenIDs.add("PatientID");
//        hiddenIDs.add("DoctorID");
        JTable patients = (JTable) TableBuilder.buildTableRaw(rs, hiddenIDs);
        viewAvalibleDoctors.add(new JScrollPane(patients));
        HashMap<String, InputWidget> widgets = new HashMap<>();
        JPanel Wpanel = new JPanel();

        widgets.put("PATIENT ID", new InputWidget("PATIENT ID") {
            @Override
            public Container generateWidget() {
                return new JPanel();
            }
            @Override
            public Object getValue() {
                return id;
            }
        });
        widgets.put("DOCTOR ID", ReadService.generateComboWidget("DOCTOR ID", ReadServiceList.DOCTORS_FROM_PATIENT, new Object[]{id}));
        for(String key : widgets.keySet()){
            Wpanel.add(widgets.get(key).generateWidget());
        }
        viewAvalibleDoctors.add(Wpanel);
        Container runButton2 = DeleteServiceList.DELETE_DOCTORFOR.buildActivateButton("Remove",widgets, new ResultListener(){
            @Override
            public void onResult(int result) {
                // DO ON RUN
            }});
        viewAvalibleDoctors.add(runButton2);

        return viewAvalibleDoctors;
    }

    public JPanel launchHome(int patientID, String patientName) {
        JPanel home = new JPanel(false);
        Container hist = new GenHistogram().GenHistogram(patientID, GenHistogram.GraphType.WEEKLY);
        Container hist2 = new GenHistogram().GenHistogram(patientID, GenHistogram.GraphType.MONTHLY);
        Container hist3 = new GenHistogram().GenHistogram(patientID, GenHistogram.GraphType.ANNUAL);

        home.setLayout(new BoxLayout(home, BoxLayout.PAGE_AXIS));
        JLabel hello = new JLabel("Hello " + patientName + " back to Symptom Tracker!");
        JLabel curSympts = new JLabel(" Current Symptoms");
        String rs = ReadService.getSingleton(ReadServiceList.GET_PATIENT_HCP.ExecuteQuery(new Object[]{id})).toString();

        JPanel histoPan = new JPanel();
        histoPan.setLayout(new BoxLayout(histoPan, BoxLayout.X_AXIS));
        JLabel myHealthCare = new JLabel("Current Healthcare Provider: " + rs);
        home.add(hello);
        home.add(curSympts);
        home.add(myHealthCare);
        home.add(Box.createRigidArea(new Dimension(50,100)));
        histoPan.add(Box.createRigidArea(new Dimension(10,167)));
        histoPan.add(hist);
        histoPan.add(Box.createRigidArea(new Dimension(10,167)));
        histoPan.add(hist2);
        histoPan.add(Box.createRigidArea(new Dimension(10,167)));
        histoPan.add(hist3);
        home.add(histoPan);
        return home;
    }
}
