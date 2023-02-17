package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.UpdateServiceList;
import com.undecideds.services.generic.ReadService;
import com.undecideds.services.structs.Argument;
import com.undecideds.ui.builders.TableBuilder;
import com.undecideds.ui.cuduibuilder.DateLabelFormatter;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

public class DoctorViewingPatientWindow {

    public DoctorViewingPatientWindow(){

    }

    PatientWindow window;
    public DoctorViewingPatientWindow(PatientWindow patientWindow){
        this.window = patientWindow;
    }

    private void safeRefresh(){
        if(window != null){
            window.refresh();
        }else{
            refresh();
        }
    }

    public JPanel viewAcute(boolean isDoctor, int id) {
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
        widgets.replace("SEVERITY", new InputWidget("SEVERITY") {
            JComboBox comboBox;

            @Override
            public Container generateWidget() {
                JPanel panel = new JPanel(new GridLayout(1, 2));
                comboBox = new JComboBox(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
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
                patients.setModel(TableBuilder.getTableModel(rs, hiddenIDs));
                safeRefresh();
            }
        });
        JPanel Wpanel = new JPanel();
        Wpanel.setLayout(new BoxLayout(Wpanel, BoxLayout.X_AXIS));
        for (String key : widgets.keySet()) {
            Wpanel.add(Box.createRigidArea(new Dimension(15, 50)));
            Wpanel.add(widgets.get(key).generateWidget());
        }

        viewAcute.add(new JScrollPane(patients));
        if (!isDoctor){
            viewAcute.add(Box.createRigidArea(new Dimension(50, 100)));
            viewAcute.add(Wpanel);
            viewAcute.add(runButton);
        }
        return viewAcute;
    }

    public JPanel viewChronic(boolean isDoctor, int id){
        JPanel viewChronic = new JPanel();
        viewChronic.setLayout(new BoxLayout(viewChronic, BoxLayout.PAGE_AXIS));
        ResultSet rs = ReadServiceList.CHRONIC_FROM_PATIENT.ExecuteQuery(new Object[]{id});

        HashSet<String> hiddenIDs = new HashSet<String>();
        hiddenIDs.add("PatientID");
        JTable patients = (JTable) TableBuilder.buildTableRaw(rs, hiddenIDs);

        viewChronic.add(new JScrollPane(patients));

        if(isDoctor){
            JButton addChronic = new JButton("Add Chronic");
            addChronic.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("Add Chronic");
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

                    HashMap<String, InputWidget> widgets = new HashMap<>();
                    widgets.put("PERSON ID", new InputWidget("PERSON ID") {
                        @Override
                        public Container generateWidget() {
                            return new JPanel();
                        }

                        @Override
                        public Object getValue() {
                            return id;
                        }
                    });
                    widgets.put("SYMPTOM ID", ReadService.generateComboWidget("SYMPTOM ID", ReadServiceList.GET_CHRONIC_NOT_FROM_PATIENT, new Object[]{id}));
                    Container activateButton = InsertServiceList.INSERT_CHRONIC.buildActivateButton("Insert Chronic", widgets, new ResultListener() {
                        @Override
                        public void onResult(int result) {
                            safeRefresh();
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        }
                    });

                    for(String key : widgets.keySet()){
                        panel.add(widgets.get(key).generateWidget());
                    }
                    panel.add(activateButton);
                    frame.add(panel);
                    frame.pack();
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }
            });




            viewChronic.add(addChronic);
        }




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
        viewcurTreatments.setLayout(new BoxLayout(viewcurTreatments, BoxLayout.PAGE_AXIS));
        ResultSet rs = ReadServiceList.GET_CURRENT_TREATMENTS.ExecuteQuery(new Object[]{id});
        HashSet<String> hidden = new HashSet<>();
        hidden.add("ID");
        JComponent tableNullable = TableBuilder.buildTableRaw(rs, hidden);
        JTable currentTreatmentTable;
        if(tableNullable instanceof JTable) {
            currentTreatmentTable = (JTable)tableNullable;
        }else{
            JPanel panel = new JPanel();
            panel.add(tableNullable);
            return panel;
        }

        viewcurTreatments.add(new JScrollPane(currentTreatmentTable));

        JPanel buttonPanel = new JPanel(new GridLayout(1, isDoctor ? 2 : 1));

        HashMap<String, Object> inputValues = new HashMap<>();

        JButton addEndDateButton = new JButton("Set End Date");
        JButton addTreatmentButton = new JButton("Schedule Treatment");
        JButton viewCostButton = new JButton("View Cost Options");

        if(isDoctor) {
            addEndDateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   JFrame datePopup = new JFrame("set end date");
                   JPanel panel = new JPanel();
                   panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                   InputWidget iw = (new Argument(Argument.ArgumentType.DATE, "END DATE")).buildWidget();
                   panel.add(iw.generateWidget());
                   JButton button = new JButton("Set end date");
                   button.addActionListener(new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                           UpdateServiceList.UPDATE_NEEDS.ExecuteQuery(new Object[]{id, inputValues.get("ID"), inputValues.get("Start Date"), iw.getValue()});
                           safeRefresh();
                           datePopup.dispatchEvent(new WindowEvent(datePopup, WindowEvent.WINDOW_CLOSING));
                       }
                   });
                   panel.add(button);
                   datePopup.add(panel);
                   datePopup.pack();
                   datePopup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                   datePopup.setVisible(true);
                }
            });

            addTreatmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("Add treatment");
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                    HashMap<String, ReadService> idMatch = new HashMap<>();
                    idMatch.put("TREATMENT ID", ReadServiceList.GET_TREATMENT_NAMES);
                    HashMap<String, InputWidget> widgets = InsertServiceList.INSERT_NEEDS_NO_END.buildUIWidgets(idMatch, true);
                    widgets.replace("PATIENT ID", new InputWidget("PATIENT ID") {
                        @Override
                        public Container generateWidget() {
                            return new JPanel();
                        }

                        @Override
                        public Object getValue() {
                            return patientId;
                        }
                    });
                    Container activateButton = InsertServiceList.INSERT_NEEDS_NO_END.buildActivateButton("Add treatment", widgets, new ResultListener() {
                        @Override
                        public void onResult(int result) {
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                            safeRefresh();
                        }
                    });

                    for(String key : widgets.keySet()){
                        panel.add(widgets.get(key).generateWidget());
                    }
                    panel.add(activateButton);
                    frame.add(panel);
                    frame.pack();
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }
            });

            buttonPanel.add(addTreatmentButton);
            buttonPanel.add(addEndDateButton);
        }else{
            viewCostButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame();
                    JPanel panel = new JPanel();

                    ResultSet rs = ReadServiceList.GET_DOCTOR_EXPENSES_FOR_TREATMENT.ExecuteQuery(new Object[]{inputValues.get("ID"), id});
                    Container result = TableBuilder.buildTable(rs);
                    panel.add(result);

                    frame.add(panel);
                    frame.setSize(500, 500);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }
            });
            buttonPanel.add(viewCostButton);


        }


        currentTreatmentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (currentTreatmentTable.getSelectedRow() != -1) {

                    inputValues.clear();
                    for (int i = 0; i < currentTreatmentTable.getColumnCount(); i++) {
                        inputValues.put(currentTreatmentTable.getColumnName(i), currentTreatmentTable.getValueAt(currentTreatmentTable.getSelectedRow(), i));
                    }
                    if(isDoctor) {
                        addEndDateButton.setEnabled(true);
                    }else {
                        viewCostButton.setEnabled(true);
                    }
                } else {
                    if(isDoctor) {
                        addEndDateButton.setEnabled(false);
                    }else {
                        viewCostButton.setEnabled(false);
                    }
                }
            }
        });

        if(isDoctor) {
            addEndDateButton.setEnabled(false);
        }else {
            viewCostButton.setEnabled(false);
        }

        viewcurTreatments.add(buttonPanel);

        return viewcurTreatments;
    }

    JTabbedPane tabbedPane;
    int patientId;
    public void launch(int doctorID, int patientId, DoctorWindow host) {
        this.patientId = patientId;
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


        tabbedPane = new JTabbedPane();
        refresh();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                host.show();
            }
        });

        frame.add(tabbedPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void refresh(){
        int selected = -1;
        if(tabbedPane.getTabCount() != 0){
            selected = tabbedPane.getSelectedIndex();
        }
        tabbedPane.removeAll();
        tabbedPane.addTab("View Acute", null, viewAcute(true, patientId), "");
        tabbedPane.addTab("View Chronic", null, viewChronic(true, patientId), "");
        tabbedPane.addTab("View Current Treatments", null, currentTreatments(true, patientId), "");
        tabbedPane.addTab("View Treatment History", null, pastTreatments(true, patientId), "");
        if(selected != -1){
            tabbedPane.setSelectedIndex(selected);
        }
    }
}
