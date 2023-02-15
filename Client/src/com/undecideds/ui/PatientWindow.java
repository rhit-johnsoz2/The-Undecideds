package com.undecideds.ui;

import com.undecideds.services.DeleteServiceList;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.generic.ReadService;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

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
        JPanel viewHistory = viewHistory(false, id);
        JPanel addSymptom = DoctorViewingPatientWindow.launchaddSymptom(false, id);
        JPanel viewChronic = viewChronicMethod();
        JPanel viewMyDocs = viewMyDoctors();
        JPanel viewPastTreatments = pastTreatments();
        JPanel viewCurTreatments = curTreatments();
        JPanel removeMyDocs = removeMyDocs();
        JPanel viewAcute = viewAcute();

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("viewHistory", null, viewHistory, "");
        tabbedPane.addTab("addSymptom", null, addSymptom, "");
        tabbedPane.addTab("View Acute", null, viewAcute, "");
        tabbedPane.addTab("View Chronic", null, viewChronic, "");
        tabbedPane.addTab("View Doctors", null, viewMyDocs, "");
        tabbedPane.addTab("Remove Doctors", null, removeMyDocs, "");
        tabbedPane.addTab("Past Treatments", null, viewPastTreatments, "");
        tabbedPane.addTab("Current Treatments", null, viewCurTreatments, "");

        framePatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePatient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        framePatient.setVisible(true);
    }

    public JPanel viewAcute(){
        JPanel viewAcute = new JPanel();
        viewAcute.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.ACUTE_FROM_PATIENT.ExecuteQuery(new Object[]{id});
        viewAcute.add(TableBuilder.buildTable(rs));
        return viewAcute;
    }

    public JPanel viewChronicMethod(){
        JPanel viewChronic = new JPanel();
        viewChronic.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.CHRONIC_FROM_PATIENT.ExecuteQuery(new Object[]{id});
        viewChronic.add(TableBuilder.buildTable(rs));
        return viewChronic;
    }

    public JPanel viewMyDoctors(){
        JPanel viewAvalibleDoctors = new JPanel();
        viewAvalibleDoctors.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.GET_DOCTORS_UNDER_PATIENTS.ExecuteQuery(new Object[]{id});
        viewAvalibleDoctors.add(TableBuilder.buildTable(rs));
        return viewAvalibleDoctors;
    }

    public JPanel removeMyDocs(){
        JPanel removeDoc = new JPanel();
        removeDoc.setLayout(new GridLayout(2,1));
        HashMap<String, ReadService> idMatch2 = new HashMap<>();
        idMatch2.put("DOCTOR ID", ReadServiceList.GET_DOCTOR_NAMES);
        idMatch2.put("PATIENT ID", ReadServiceList.GET_PATIENT_NAMES);
        HashMap<String, InputWidget> widgets2 = DeleteServiceList.DELETE_DOCTORFOR.buildUIWidgets(idMatch2, true);
        JPanel Wpanel2 = new JPanel();
        widgets2.replace("PATIENT ID", new InputWidget("PATIENT ID") {
            @Override
            public Container generateWidget() {
                return new JPanel();
            }

            @Override
            public Object getValue() {
                return id;
            }
        });
        for(String key : widgets2.keySet()){
            Wpanel2.add(widgets2.get(key).generateWidget());
        }
        Container runButton2 = DeleteServiceList.DELETE_DOCTORFOR.buildActivateButton("Remove",widgets2, new ResultListener(){
            @Override
            public void onResult(int result) {
                // DO ON RUN
            }});
        removeDoc.add(Wpanel2);
        removeDoc.add(runButton2);
        return removeDoc;
    }

    public JPanel pastTreatments(){
        JPanel viewPastTreatments = new JPanel();
        viewPastTreatments.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.GET_PAST_TREATMENTS.ExecuteQuery(new Object[]{id});
        viewPastTreatments.add(TableBuilder.buildTable(rs));
        return viewPastTreatments;
    }

    public JPanel curTreatments(){
        JPanel viewcurTreatments = new JPanel();
        viewcurTreatments.setLayout(new GridLayout());
        ResultSet rs = ReadServiceList.GET_CURRENT_TREATMENTS.ExecuteQuery(new Object[]{id});
        viewcurTreatments.add(TableBuilder.buildTable(rs));
        return viewcurTreatments;
    }

    public static JPanel viewHistory(boolean isDoctor, int patientID) {
        JPanel viewHistory = new JPanel();
        viewHistory.setLayout(new BoxLayout(viewHistory, BoxLayout.PAGE_AXIS));
        ResultSet rs = ReadServiceList.ACUTE_FROM_PATIENT.ExecuteQuery(new Object[]{patientID});
        JTable table;
        JComponent tableNullable;
        HashSet<String> hidden = new HashSet<String>();
        if(!isDoctor){
            hidden.add("PatientID");
        }
        tableNullable = TableBuilder.buildTableRaw(rs, hidden);
        if(tableNullable instanceof JTable){
            table = (JTable) tableNullable;
        }else{
            viewHistory.add(tableNullable);
            return viewHistory;
        }

        HashMap<String, Object> inputValues = new HashMap<>();
        viewHistory.add(new JScrollPane().add(table));
        JButton selectorButton = new JButton("View Symptom Treatments");

        selectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    JFrame popUpWindow = new JFrame();
                    popUpWindow.setSize(400,400);
                    ResultSet rs1 = ReadServiceList.SYMPTOM_GET_ID_FROM_NAME.ExecuteQuery(new Object[]{inputValues.get("name")});
//                    ResultSet rs = ReadServiceList.GET_SIDEEFFECT_OF_TREATMENT.ExecuteQuery(new Object[]{ReadService. (rs1)});
                    HashSet<String> hiddenPT2 = new HashSet<String>();
                    hiddenPT2.add("ID");
//                    JComponent potentialTreatments = TableBuilder.buildTableRaw(rs, hiddenPT2);
//                    popUpWindow.add(potentialTreatments);
                    popUpWindow.setVisible(true);
        }});
        viewHistory.add(selectorButton);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(table.getSelectedRow() != -1){

                    inputValues.clear();
                    for(int i = 0; i < table.getColumnCount(); i++) {
                        System.out.println(table.getColumnName(i) + " : " + table.getValueAt(table.getSelectedRow(), i));
                        inputValues.put(table.getColumnName(i), table.getValueAt(table.getSelectedRow(), i));
                    }
                    selectorButton.setEnabled(true);

                }else{
                    selectorButton.setEnabled(false);
                }
            }
        });
        return viewHistory;
    }


}
