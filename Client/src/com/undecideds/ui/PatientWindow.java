package com.undecideds.ui;


import com.undecideds.services.DeleteServiceList;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.UpdateServiceList;
import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.builders.GenHistogram;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;
import com.undecideds.ui.builders.TableBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

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

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("viewHistory", null, viewHistory, "");
        tabbedPane.addTab("addSymptom", null, addSymptom, "");

        framePatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePatient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        framePatient.setVisible(true);
    }
}
