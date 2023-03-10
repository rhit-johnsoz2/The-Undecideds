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
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;

public class DoctorWindow {

    int currentId;
    String currentName;
    JTabbedPane tabbedPane;
    JFrame frameDoctor;
    DoctorWindow self;

    public void launch(int id, String name){
        self = this;
        currentId = id;
        currentName = name;
        frameDoctor = new JFrame();
        frameDoctor.setTitle("Dashboard for Dr. " + name);
        frameDoctor.setSize(700, 500);

        tabbedPane = new JTabbedPane();
        refresh();

        frameDoctor.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                refresh();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });

        frameDoctor.add(tabbedPane);
        frameDoctor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameDoctor.setVisible(true);
    }

    void refresh(){
        int selected = -1;
        if(tabbedPane.getTabCount() != 0){
            selected = tabbedPane.getSelectedIndex();
        }
        tabbedPane.removeAll();
        tabbedPane.addTab("My Treatments", null, myTreatments(), "");
        //tabbedPane.addTab("Add Patient", null, addPatient(), "");
        tabbedPane.addTab("View Patients", null, viewPatients(), "");
        if(selected != -1){
            tabbedPane.setSelectedIndex(selected);
        }
    }

    public JPanel viewPatients() {
        JPanel viewPatient = new JPanel();
        viewPatient.setLayout(new BoxLayout(viewPatient, BoxLayout.PAGE_AXIS));
        ResultSet rs = ReadServiceList.PATIENTS_FROM_DOCTOR.ExecuteQuery(new Object[]{currentId});
        HashSet<String> hidden = new HashSet<String>();
        hidden.add("DoctorID");
        hidden.add("ID");
        JTable patients;
        JComponent tableNullable = TableBuilder.buildTableRaw(rs, hidden);
        if (tableNullable instanceof JTable) {
            patients = (JTable) tableNullable;
        } else {
            viewPatient.add(tableNullable);
            return viewPatient;
        }
        viewPatient.add(new JScrollPane(patients));
        JButton doctorViewingPatientButton = new JButton("View Patient Information");
        HashMap<String, Object> inputValues = new HashMap<>();
        doctorViewingPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DoctorViewingPatientWindow patientView = new DoctorViewingPatientWindow();

                patientView.launch(currentId, (int)inputValues.get("ID"), self);
                refresh();
            }
        });
        JButton removePatientButton = new JButton("Remove Patient");
        removePatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteServiceList.DELETE_DOCTORFOR.ExecuteQuery(new Object[]{currentId,Integer.parseInt(inputValues.get("ID").toString())});
                ResultSet rs = ReadServiceList.PATIENTS_FROM_DOCTOR.ExecuteQuery(new Object[]{currentId});
                HashSet<String> hiddenIDs = new HashSet<String>();
                hiddenIDs.add("ID");
                hiddenIDs.add("DoctorID");
                patients.setModel(TableBuilder.getTableModel(rs, hiddenIDs));
                refresh();
            }
        });

        JButton addPatientButton = new JButton("Add Patient");
        patients.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (patients.getSelectedRow() != -1) {

                    inputValues.clear();
                    for (int i = 0; i < patients.getColumnCount(); i++) {
                        System.out.println(patients.getColumnName(i) + " : " + patients.getValueAt(patients.getSelectedRow(), i));
                        inputValues.put(patients.getColumnName(i), patients.getValueAt(patients.getSelectedRow(), i));
                    }
                    doctorViewingPatientButton.setEnabled(true);
                    removePatientButton.setEnabled(true);
                } else {
                    doctorViewingPatientButton.setEnabled(false);
                    removePatientButton.setEnabled(false);
                }
            }
        });
        removePatientButton.setEnabled(false);
        doctorViewingPatientButton.setEnabled(false);
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popup = new JFrame("add new patient");

                popup.add(addPatient(popup));

                popup.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        refresh();
                    }
                });

                popup.pack();
                popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                popup.setVisible(true);

            }
        });
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(addPatientButton);
        buttonPanel.add(removePatientButton);
        buttonPanel.add(doctorViewingPatientButton);

        viewPatient.add(buttonPanel);
        return viewPatient;
    }

    public JPanel myTreatments(){
        JPanel myTreatmentsPanel = new JPanel(false);
        myTreatmentsPanel.setLayout(new BoxLayout(myTreatmentsPanel, BoxLayout.PAGE_AXIS));
        ResultSet rs = ReadServiceList.GET_TREATMENTS_FROM_DOCTOR.ExecuteQuery(new Object[]{currentId});
        JTable patients = (JTable) TableBuilder.buildTableRaw(rs, new HashSet<>());
        JPanel Wpanel2 = new JPanel();
        HashMap<String, InputWidget> widgets = new HashMap<>();

        widgets.put("DOCTOR ID", new InputWidget("DOCTOR ID") {
            @Override
            public Container generateWidget() {
                return new JPanel();
            }

            @Override
            public Object getValue() {
                return currentId;
            }
        });

        widgets.put("TREATMENT ID", ReadService.generateComboWidget("TREATMENT ID", ReadServiceList.TREATMENTS_NOT_DONE_BY_DOCTOR, new Object[]{currentId}));


        for(String key : widgets.keySet()){
            Wpanel2.add(widgets.get(key).generateWidget());
        }

        Container addTreatment = InsertServiceList.INSERT_PERFORMS.buildActivateButton("Add Treatment", widgets, new ResultListener() {
            @Override
            public void onResult(int result) {
                try {
                    // update TABLE
                    refresh();

                }catch (Exception e){
                    System.out.println("fatal error re-fetching table");
                    e.printStackTrace();
                    System.exit(501);
                }
            }
        });
        myTreatmentsPanel.add(new JScrollPane(patients));
        myTreatmentsPanel.add(Wpanel2);
        myTreatmentsPanel.add(addTreatment);
        return myTreatmentsPanel;
    }

    public JPanel addPatient(JFrame host){
        JPanel addPatient = new JPanel(false);
        addPatient.setLayout(new BoxLayout(addPatient, BoxLayout.PAGE_AXIS));
        HashMap<String, InputWidget> widgets = new HashMap<>();
        JPanel Wpanel2 = new JPanel();
        widgets.put("DOCTOR ID", new InputWidget("DOCTOR ID") {
            @Override
            public Container generateWidget() {
                return new Container();
            }

            @Override
            public Object getValue() {
                return currentId;
            }
        });
        //for remove on doctors
        widgets.put("PATIENT ID", ReadService.generateComboWidget("PATIENT ID", ReadServiceList.GET_PATIENTS_NOT_FROM_DOCTOR, new Object[]{currentId}));
        for(String key : widgets.keySet()){
            Wpanel2.add(widgets.get(key).generateWidget());
        }
        Container runButton2 = InsertServiceList.INSERT_DOCTORFOR.buildActivateButton("Add", widgets, new ResultListener(){
            @Override
            public void onResult(int result) {
                if(result == -1){
                    JFrame err_popup = new JFrame("Error!");
                    err_popup.add(new JLabel("Check that your inputs are valid"));
                    err_popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    err_popup.pack();
                    err_popup.setVisible(true);
                }
                host.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                host.dispatchEvent(new WindowEvent(host, WindowEvent.WINDOW_CLOSING));
                refresh();
            }});
        Wpanel2.add(runButton2);
        Wpanel2.setLayout(new BoxLayout(Wpanel2, BoxLayout.PAGE_AXIS));
        addPatient.add(Wpanel2);
        return addPatient;
    }
    public void hide(){
        frameDoctor.setVisible(false);
    }
    public void show(){
        frameDoctor.setVisible(true);
    }
}

