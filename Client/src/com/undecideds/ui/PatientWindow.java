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
        JPanel home = new JPanel(false);
        JPanel viewHistory = new JPanel(false);
        JPanel addSymptom = new JPanel(false);

        Container hist = new GenHistogram().GenHistogram(id, GenHistogram.GraphType.WEEKLY);
        Container hist2 = new GenHistogram().GenHistogram(id, GenHistogram.GraphType.MONTHLY);
        Container hist3 = new GenHistogram().GenHistogram(id, GenHistogram.GraphType.ANNUAL);

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("viewHistory", null, viewHistory, "");
        tabbedPane.addTab("addSymptom", null, addSymptom, "");

        home.setLayout(new GridLayout(2,4));
        JLabel hello = new JLabel("Hello " + name + " back to Symptom Tracker!");
        JLabel curSympts = new JLabel(" Current Symptoms");

        //Home Page Stuff//

        home.add(hello);
        home.add(curSympts);
        home.add(new JLabel());
        home.add(hist);
        home.add(hist2);
        home.add(hist3);

        //view History//
        viewHistory.setLayout(new GridLayout());
        JLabel viewHistoryText = new JLabel("View History");


        ResultSet rs = ReadServiceList.ACUTE_FROM_PATIENT.ExecuteQuery(new Object[]{id});
        viewHistory.add(TableBuilder.buildTable(rs));

        //Add Symptom

        //JFrame popup = new JFrame();
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
                return id;
            }
        });

        Container runButton = InsertServiceList.INSERT_ACUTE.buildActivateButton("Add", widgets, new ResultListener(){
            @Override
            public void onResult(int result) {
                // DO ON RUN
                //popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //popup.dispatchEvent(new WindowEvent(popup, WindowEvent.WINDOW_CLOSING));
            }
        });
        JPanel Wpanel = new JPanel();
        for(String key : widgets.keySet()){
            Wpanel.add(widgets.get(key).generateWidget());
        }
        Wpanel.setLayout(new BoxLayout(Wpanel, BoxLayout.PAGE_AXIS));
        Wpanel.add(runButton);
//        popup.add(Wpanel);
//        popup.pack();

//        JButton launchPopup = new JButton("Add Needs");
//        launchPopup.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                popup.setVisible(true);
//            }
//        });
        addSymptom.add(Wpanel);


        framePatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePatient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        framePatient.setVisible(true);
    }
}
