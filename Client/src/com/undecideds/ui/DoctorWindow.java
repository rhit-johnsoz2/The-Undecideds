package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.builders.GenHistogram;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class DoctorWindow {

    int currentId;
    String currentName;
    public void launch(int id, String name) {
        currentId = 21;
        currentName = name;
        JFrame frameDoctor = new JFrame();
        frameDoctor.setSize(700, 500);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel home = new JPanel(false);
        JPanel assignTreatment = new JPanel(false);
        JPanel addPatient = new JPanel(false);
        JPanel viewPatient = new JPanel(false);

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("Assign Treatment", null, assignTreatment, "");
        tabbedPane.addTab("Add Patient", null, addPatient, "");
        tabbedPane.addTab("View Patient", null, viewPatient, "");

        Container hist = new GenHistogram().GenHistogram(26, GenHistogram.GraphType.WEEKLY);
        Container hist2 = new GenHistogram().GenHistogram(22, GenHistogram.GraphType.WEEKLY);
        Container hist3 = new GenHistogram().GenHistogram(27, GenHistogram.GraphType.WEEKLY);

        JLabel helloDoctor = new JLabel("Hello Dr. Johnson");
        JLabel currentPatients = new JLabel("Current Patient Quick View");

        home.setLayout(new GridLayout(2,3));
        home.add(helloDoctor);
        home.add(currentPatients);
        home.add(new JLabel());
        home.add(hist);
        home.add(hist2);
        home.add(hist3);




            HashMap<String, ReadService> idMatch = new HashMap<>();
            idMatch.put("PATIENT ID", ReadServiceList.GET_PATIENT_NAMES);
            idMatch.put("TREATMENT ID", ReadServiceList.GET_TREATMENT_NAMES);
            HashMap<String, InputWidget> widgets = InsertServiceList.INSERT_NEEDS.buildUIWidgets(idMatch, true);
            JPanel Wpanel = new JPanel();
            for(String key : widgets.keySet()){
                Wpanel.add(widgets.get(key).generateWidget());
            }
            Container runButton = InsertServiceList.INSERT_NEEDS.buildActivateButton("Assign", widgets, new ResultListener(){
                @Override
                public void onResult(int result) {
                    // DO ON RUN
                }});
            Wpanel.add(runButton);
            Wpanel.setLayout(new BoxLayout(Wpanel, BoxLayout.PAGE_AXIS));
            assignTreatment.add(Wpanel);



        HashMap<String, ReadService> idMatch2 = new HashMap<>();
        idMatch2.put("DOCTOR ID", ReadServiceList.GET_DOCTOR_NAMES);
        idMatch2.put("PATIENT ID", ReadServiceList.GET_PATIENT_NAMES);
        HashMap<String, InputWidget> widgets2 = InsertServiceList.INSERT_DOCTORFOR.buildUIWidgets(idMatch2, true);
        JPanel Wpanel2 = new JPanel();
        for(String key : widgets2.keySet()){
            Wpanel2.add(widgets2.get(key).generateWidget());
        }
        Container runButton2 = InsertServiceList.INSERT_DOCTORFOR.buildActivateButton("Add", widgets2, new ResultListener(){
            @Override
            public void onResult(int result) {
                // DO ON RUN
            }});
        Wpanel2.add(runButton2);
        Wpanel2.setLayout(new BoxLayout(Wpanel2, BoxLayout.PAGE_AXIS));
        addPatient.add(Wpanel2);

        Container hist4 = new GenHistogram().GenHistogram(26, GenHistogram.GraphType.WEEKLY);
        Container hist5 = new GenHistogram().GenHistogram(22, GenHistogram.GraphType.WEEKLY);
        Container hist6 = new GenHistogram().GenHistogram(27, GenHistogram.GraphType.WEEKLY);

        JLabel myPatients = new JLabel("My Patients:");
        viewPatient.setLayout(new GridLayout(2,3));
        viewPatient.add(myPatients);
        viewPatient.add(new JLabel());
        viewPatient.add(new JLabel());
        viewPatient.add(hist4);
        viewPatient.add(hist5);
        viewPatient.add(hist6);

        frameDoctor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameDoctor.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frameDoctor.setVisible(true);

    }

}
/*
    JFrame popup = new JFrame();
    HashMap<String, ReadService> idMatch = new HashMap<>();
    idMatch.put("PATIENT ID", ReadServiceList.GET_PATIENT_NAMES);
    idMatch.put("TREATMENT ID", ReadServiceList.GET_TREATMENT_NAMES);
    HashMap<String, InputWidget> widgets = InsertServiceList.INSERT_NEEDS.buildUIWidgets(idMatch, true);
    Container runButton = InsertServiceList.INSERT_NEEDS.buildActivateButton("Add", widgets, new ResultListener(){
        @Override
        public void onResult(int result) {
            // DO ON RUN
            popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            popup.dispatchEvent(new WindowEvent(popup, WindowEvent.WINDOW_CLOSING));
        }});
    JPanel Wpanel = new JPanel();
    for(String key : widgets.keySet()){
        Wpanel.add(widgets.get(key).generateWidget());
    }
    Wpanel.setLayout(new BoxLayout(Wpanel, BoxLayout.PAGE_AXIS));
    Wpanel.add(runButton);
    popup.add(Wpanel);
    popup.pack();

    JButton launchPopup = new JButton("Add needs");
    launchPopup.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            popup.setVisible(true);
        }});
    home.add(launchPopup);
*/