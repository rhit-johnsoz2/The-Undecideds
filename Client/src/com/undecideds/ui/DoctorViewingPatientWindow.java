package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.builders.GenHistogram;
import com.undecideds.ui.builders.TableBuilder;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;

public class DoctorViewingPatientWindow {

    public JPanel viewAcute(int id){
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
        widgets.replace("SEVERITY",new InputWidget("SEVERITY"){
            JComboBox comboBox;
            @Override
            public Container generateWidget() {
                JPanel panel = new JPanel(new GridLayout(1, 2));
                comboBox = new JComboBox(new Integer[]{1,2,3,4,5,6,7,8,9,10});
                panel.add(new JLabel("Severity"));
                panel.add(comboBox);
                return panel;
            }

            @Override
            public Object getValue() {
                return comboBox.getModel().getSelectedItem();
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
        Wpanel.setLayout(new BoxLayout(Wpanel, BoxLayout.X_AXIS));
        for (String key : widgets.keySet()) {
            Wpanel.add(Box.createRigidArea(new Dimension(15,50)));
            Wpanel.add(widgets.get(key).generateWidget());
        }

        viewAcute.add(new JScrollPane(patients));
        viewAcute.add(Box.createRigidArea(new Dimension(50,100)));
        viewAcute.add(Wpanel);
        viewAcute.add(runButton);
        return viewAcute;
    }

    public JPanel viewChronicMethod(int id){
        JPanel viewChronic = new JPanel();
        viewChronic.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.CHRONIC_FROM_PATIENT.ExecuteQuery(new Object[]{id});

        HashSet<String> hiddenIDs = new HashSet<String>();
        hiddenIDs.add("PatientID");
        JTable patients = (JTable) TableBuilder.buildTableRaw(rs, hiddenIDs);
        viewChronic.add(new JScrollPane(patients));
        return viewChronic;
    }


    public JPanel pastTreatments(int id){
        JPanel viewPastTreatments = new JPanel();
        viewPastTreatments.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.GET_PAST_TREATMENTS.ExecuteQuery(new Object[]{id});

        viewPastTreatments.add(TableBuilder.buildTable(rs));
        return viewPastTreatments;
    }


    public JPanel curTreatments(int id){
        JPanel viewcurTreatments = new JPanel();
        viewcurTreatments.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.GET_CURRENT_TREATMENTS.ExecuteQuery(new Object[]{id});
        viewcurTreatments.add(TableBuilder.buildTable(rs));
        return viewcurTreatments;
    }


    public void launch(int doctorID, int patientId) {
        ResultSet rs = ReadServiceList.GET_PATIENT_NAMES.ExecuteQuery(new Object[]{});
        String patientName = "";
        try {
            while (rs.next()) {
                if(rs.getInt("ID") == patientId) {
                    patientName = rs.getString("fname") + " " + rs.getString("lname");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Information on " + patientName);
        frame.setSize(700, 500);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Home", null, launchHome(true, patientId, patientName), "");
        tabbedPane.addTab("Add Patient", null, launchViewHistory(true, patientId), "");
        tabbedPane.addTab("View Patients", null, launchaddSymptom(true, patientId), "");
        // TODO once Liz is done with Patient

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
