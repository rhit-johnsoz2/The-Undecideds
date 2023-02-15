package com.undecideds.ui;

import com.undecideds.services.ReadServiceList;
import com.undecideds.ui.builders.TableBuilder;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

import static com.undecideds.services.ReadServiceList.GET_ACUTE;

public class PatientWindow {
    int id;
    String name;
    public void launch(int id, String name){
        this.id = id;
        this.name = name;
        JFrame framePatient = new JFrame();
        framePatient.setSize(700, 500);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel home = DoctorViewingPatientWindow.launchHome(false, id, name);
        JPanel viewHistory = DoctorViewingPatientWindow.launchViewHistory(false, id);
        JPanel addSymptom = DoctorViewingPatientWindow.launchaddSymptom(false, id);
        JPanel viewChronic = viewChronicMethod();

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("viewHistory", null, viewHistory, "");
        tabbedPane.addTab("addSymptom", null, addSymptom, "");
        tabbedPane.addTab("View Chronic", null, viewChronic, "");

        framePatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePatient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        framePatient.setVisible(true);
    }

    public JPanel viewChronicMethod(){
        JPanel viewChronic = new JPanel();
        viewChronic.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.CHRONIC_FROM_PATIENT.ExecuteQuery(new Object[]{id});
        viewChronic.add(TableBuilder.buildTable(rs));

        return viewChronic;
    }

    public JPanel viewMyDoctors(){
        JPanel viewChronic = new JPanel();
        viewChronic.setLayout(new GridLayout());
        /*ResultSet rs = ReadServiceList..ExecuteQuery(new Object[]{id});
        viewChronic.add(TableBuilder.buildTable(rs));*/
        return viewChronic;
    }


}
