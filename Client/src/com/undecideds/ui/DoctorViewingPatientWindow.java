package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.builders.GenHistogram;
import com.undecideds.ui.builders.TableBuilder;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;

public class DoctorViewingPatientWindow {

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

    public static JPanel launchViewHistory(boolean isDoctor, int patientID) {
        JPanel viewHistory = new JPanel(false);
        viewHistory.setLayout(new GridLayout());

        ResultSet rs = ReadServiceList.ACUTE_FROM_PATIENT.ExecuteQuery(new Object[]{patientID});  if(!isDoctor){
            HashSet<String> hidden = new HashSet<String>();
            hidden.add("PatientID");
            viewHistory.add(TableBuilder.buildTable(rs, hidden));
        }else {
            viewHistory.add(TableBuilder.buildTable(rs));
        }
        return viewHistory;
    }

    public static JPanel launchaddSymptom(boolean isDoctor, int patientID) {
        JPanel addSymptom = new JPanel(false);
        HashMap<String, ReadService> idMatch = new HashMap<>();
        idMatch.put("SYMPTOM ID", ReadServiceList.GET_SYMPTOMS);

        HashMap<String, InputWidget> widgets = InsertServiceList.INSERT_ACUTE.buildUIWidgets(idMatch, true);
        widgets.replace("PERSON ID", new InputWidget("PERSON ID") {
            @Override
            public Container generateWidget() {
                return new Container();
            }

            @Override
            public Object getValue() {
                return patientID;
            }
        });
        Container runButton = InsertServiceList.INSERT_ACUTE.buildActivateButton("Add", widgets, new ResultListener() {
            @Override
            public void onResult(int result) {
            }
        });
        JPanel Wpanel = new JPanel();
        for (String key : widgets.keySet()) {
            Wpanel.add(widgets.get(key).generateWidget());
        }
        Wpanel.setLayout(new BoxLayout(Wpanel, BoxLayout.PAGE_AXIS));
        Wpanel.add(runButton);
        addSymptom.add(Wpanel);
        return addSymptom;
    }

}
