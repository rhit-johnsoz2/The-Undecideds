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
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;


public class PatientWindow {
    int id;
    String name;
    JTabbedPane tabbedPane;

    public void launch(int id, String name){
        this.id = id;
        this.name = name;
        JFrame framePatient = new JFrame(name + "'s information");
        framePatient.setSize(700, 500);



        tabbedPane = new JTabbedPane();
        refresh();


        framePatient.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                refresh();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
        framePatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePatient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        framePatient.setVisible(true);
    }

    public void refresh(){
        int selected = -1;
        if(tabbedPane.getTabCount() != 0){
            selected = tabbedPane.getSelectedIndex();
        }
        tabbedPane.removeAll();

        DoctorViewingPatientWindow shareViewComponents = new DoctorViewingPatientWindow(this);
        JPanel home = launchHome(id,name);
        JPanel viewChronic = shareViewComponents.viewChronic(false, id);
        JPanel viewMyDocs = viewMyDoctors();
        JPanel viewPastTreatments = shareViewComponents.pastTreatments(false, id);
        JPanel viewCurTreatments = shareViewComponents.currentTreatments(false, id);
        JPanel viewAcute = shareViewComponents.viewAcute(false, id);

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("Acute Symptoms", null, viewAcute, "");
        tabbedPane.addTab("Chronic Symptoms", null, viewChronic, "");
        tabbedPane.addTab("View Doctors", null, viewMyDocs, "");
        tabbedPane.addTab("Past Treatments", null, viewPastTreatments, "");
        tabbedPane.addTab("Current Treatments", null, viewCurTreatments, "");

        if(selected != -1){
            tabbedPane.setSelectedIndex(selected);
        }

    }

    public JPanel viewMyDoctors(){
        JPanel viewAvalibleDoctors = new JPanel();
        viewAvalibleDoctors.setLayout(new BoxLayout(viewAvalibleDoctors, BoxLayout.PAGE_AXIS));
        ResultSet rs = ReadServiceList.DOCTORS_FROM_PATIENT.ExecuteQuery(new Object[]{id});

        HashSet<String> hiddenIDs = new HashSet<String>();
        hiddenIDs.add("ID");
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
//        widgets.put("DOCTOR ID", ReadService.generateComboWidget("DOCTOR ID", ReadServiceList.DOCTORS_FROM_PATIENT, new Object[]{id}));
        for(String key : widgets.keySet()){
            Wpanel.add(widgets.get(key).generateWidget());
        }
        viewAvalibleDoctors.add(Wpanel);
        JButton runButton = new JButton("Remove Doctor");
        HashMap<String, Object> inputValues = new HashMap<>();
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteServiceList.DELETE_DOCTORFOR.ExecuteQuery(new Object[]{Integer.parseInt(inputValues.get("ID").toString()),id});
                refresh();
            }});
        if(patients.getRowCount() == 0){
            runButton2.getComponent(0).setEnabled(false);
        }
        viewAvalibleDoctors.add(runButton2);

        patients.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (patients.getSelectedRow() != -1) {

                    inputValues.clear();
                    for (int i = 0; i < patients.getColumnCount(); i++) {
                        System.out.println(patients.getColumnName(i) + " : " + patients.getValueAt(patients.getSelectedRow(), i));
                        inputValues.put(patients.getColumnName(i), patients.getValueAt(patients.getSelectedRow(), i));
                    }
                    runButton.setEnabled(true);

                } else {
                    runButton.setEnabled(false);
                }
            }
        });

        viewAvalibleDoctors.add(runButton);

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

