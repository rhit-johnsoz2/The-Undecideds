package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.builders.TableBuilder;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;

public class DoctorViewingPatientWindow {

    public JPanel viewAcute(boolean isDoctor, int id){
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

    public JPanel viewChronic(boolean isDoctor, int id){
        JPanel viewChronic = new JPanel();
        viewChronic.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.CHRONIC_FROM_PATIENT.ExecuteQuery(new Object[]{id});

        HashSet<String> hiddenIDs = new HashSet<String>();
        hiddenIDs.add("PatientID");
        JTable patients = (JTable) TableBuilder.buildTableRaw(rs, hiddenIDs);
        viewChronic.add(new JScrollPane(patients));
        return viewChronic;
    }


    public JPanel pastTreatments(boolean isDoctor, int id){
        JPanel viewPastTreatments = new JPanel();
        viewPastTreatments.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.GET_PAST_TREATMENTS.ExecuteQuery(new Object[]{id});

        viewPastTreatments.add(TableBuilder.buildTable(rs));
        return viewPastTreatments;
    }


    public JPanel currentTreatments(boolean isDoctor, int id){
        JPanel viewcurTreatments = new JPanel();
        viewcurTreatments.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.GET_CURRENT_TREATMENTS.ExecuteQuery(new Object[]{id});
        viewcurTreatments.add(TableBuilder.buildTable(rs));
        return viewcurTreatments;
    }


    public void launch(int doctorID, int patientId, DoctorWindow host) {
        host.hide();
        String patientName = "";
        try {
            ResultSet rs = ReadServiceList.GET_PATIENT_NAMES.ExecuteQuery(new Object[]{});
            while (rs.next()) {
                if(rs.getInt("ID") == patientId) {
                    patientName = rs.getString("name");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Information on " + patientName);
        frame.setSize(700, 500);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("View Acute", null, viewAcute(true, patientId), "");
        tabbedPane.addTab("View Chronic", null, viewChronic(true, patientId), "");
        tabbedPane.addTab("View Current Treatments", null, currentTreatments(true, patientId), "");
        tabbedPane.addTab("View Treatment History", null, pastTreatments(true, patientId), "");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                host.show();
            }
        });

        frame.add(tabbedPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
