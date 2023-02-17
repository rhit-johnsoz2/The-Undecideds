package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.UpdateServiceList;
import com.undecideds.services.generic.ReadService;
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
                safeRefresh();
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
        HashSet<String> hidden = new HashSet<>();
        hidden.add("TREATMENT ID");
        JComponent tableNullable = TableBuilder.buildTableRaw(rs, hidden);
        JTable currentTreatmentTable;
        if(tableNullable instanceof JTable) {
            currentTreatmentTable = (JTable)tableNullable;
        }else{
            JPanel panel = new JPanel();
            panel.add(tableNullable);
            return panel;
        }

        viewcurTreatments.add(currentTreatmentTable);

        JButton addEndDateButton = new JButton("Add End Date");
        HashMap<String, Object> inputValues = new HashMap<>();
        addEndDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilDateModel model = new UtilDateModel();
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                UpdateServiceList.UPDATE_NEEDS.ExecuteQuery(new Object[]{id,inputValues.get("TREATMENT ID"),inputValues.get("STARTING DATE"),new JDatePickerImpl(datePanel, new DateLabelFormatter())});
            }
        });


        currentTreatmentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (currentTreatmentTable.getSelectedRow() != -1) {

                    inputValues.clear();
                    for (int i = 0; i < currentTreatmentTable.getColumnCount(); i++) {
                        System.out.println(currentTreatmentTable.getColumnName(i) + " : " + currentTreatmentTable.getValueAt(currentTreatmentTable.getSelectedRow(), i));
                        inputValues.put(currentTreatmentTable.getColumnName(i), currentTreatmentTable.getValueAt(currentTreatmentTable.getSelectedRow(), i));
                    }
                    currentTreatmentTable.setEnabled(true);

                } else {
                    currentTreatmentTable.setEnabled(false);
                }
            }
        });


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
