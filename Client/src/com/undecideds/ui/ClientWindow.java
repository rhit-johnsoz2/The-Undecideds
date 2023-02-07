package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class ClientWindow implements ActionListener {
    JPanel panel;
    JLabel user_label;
    JLabel password_label;
    JLabel message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit;
    JFrame frame = new JFrame();

    public ClientWindow() {
    }

    public void launch() {
        this.user_label = new JLabel();
        this.user_label.setText("User Name :");
        this.userName_text = new JTextField();
        this.password_label = new JLabel();
        this.password_label.setText("Password :");
        this.password_text = new JPasswordField();
        this.submit = new JButton("SUBMIT");
        this.panel = new JPanel(new GridLayout(5, 1));
        this.panel.add(this.user_label);
        this.panel.add(this.userName_text);
        this.panel.add(this.password_label);
        this.panel.add(this.password_text);
        this.message = new JLabel();
        this.panel.add(this.message);
        this.panel.add(this.submit);
        this.submit.addActionListener(this);
        this.frame.add(this.panel, "Center");
        this.frame.setTitle("Please Login Here !");
        this.frame.setSize(500, 300);
        this.frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String userName = this.userName_text.getText();
        String password = this.password_text.getText();
        if (userName.trim().equals("doctor") && password.trim().equals("doctor")) {
            doctorView();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } else if (userName.trim().equals("patient") && password.trim().equals("patient")) {
            patientView();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } else {
            this.message.setText(" Invalid user.. ");
        }

    }


    public void patientView(){
        JFrame framePatient = new JFrame();
        framePatient.setSize(700, 500);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel home = new JPanel(false);
        JPanel viewHistory = new JPanel(false);
        JPanel addSymptom = new JPanel(false);

        tabbedPane.addTab("Home", null, home, "");
        tabbedPane.addTab("viewHistory", null, viewHistory, "");
        tabbedPane.addTab("addSymptom", null, addSymptom, "");

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();

        home.setLayout(layout);
        JLabel hello = new JLabel("Hello Liz Fogarty");
        JLabel curSympts = new JLabel("Current Symptoms");
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 0;
        home.add(hello,gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 1;
        home.add(curSympts,gbc);


        viewHistory.setLayout(layout);
        JLabel viewHistoryText = new JLabel("View History");
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 0;
        viewHistory.add(viewHistoryText,gbc);

        addSymptom.setLayout(layout);
        JLabel selectSympt = new JLabel("Select Symptom:");
        JLabel selectSev = new JLabel("Select Severity:");
        JButton confirm2 = new JButton("Confrim");
        JComboBox selectSymptom = new JComboBox();
        JComboBox selectSeverity = new JComboBox();
        JTextField lastNameEnter = new JTextField();
        JButton go = new JButton("Go");
        JButton go2 = new JButton("Go");

        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 0;
        addSymptom.add(selectSympt,gbc);
        gbc.ipadx = 160;
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 1;
        addSymptom.add(selectSymptom,gbc);
        gbc.ipady = 40;
        gbc.gridx = 1;
        gbc.gridy = 1;
        addSymptom.add(go,gbc);

        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 2;
        addSymptom.add(selectSev,gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 3;
        addSymptom.add(selectSeverity,gbc);
        gbc.ipadx = 160;
        gbc.ipady = 40;
        gbc.gridx = 1;
        gbc.gridy = 3;
        addSymptom.add(go2,gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 4;
        addSymptom.add(confirm2,gbc);

        framePatient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        framePatient.setVisible(true);

    }
    public void doctorView(){
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

        JLabel helloDoctor = new JLabel("Hello Dr. Johnson");
        JLabel currentPatients = new JLabel("Current Patient Quick View");

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();
        home.setLayout(layout);
        gbc.gridx = 0;
        gbc.gridy = 0;
        home.add(helloDoctor, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        home.add(currentPatients, gbc);
        // patient tables go here

        JLabel selectPatient = new JLabel("Select Patient:");
        JLabel symptom = new JLabel("Symptom:");
        JLabel possibleTreatments = new JLabel("Possible Treatments:");
        JButton confirm = new JButton("Confrim");
        JComboBox selectPatientDropDown = new JComboBox();
        JComboBox selectSymptom = new JComboBox();

        assignTreatment.setLayout(layout);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 0;
        assignTreatment.add(selectPatient, gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 1;
        assignTreatment.add(selectPatientDropDown, gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 2;
        assignTreatment.add(symptom, gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 3;
        assignTreatment.add(selectSymptom, gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 4;
        assignTreatment.add(possibleTreatments, gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 5;
        assignTreatment.add(confirm, gbc);

        addPatient.setLayout(layout);
        JLabel firstName = new JLabel("Enter Patient First Name:");
        JLabel lastName = new JLabel("Enter Patient Last Name:");
        JButton confirm2 = new JButton("Confrim");
        JTextField firstNameEnter = new JTextField();
        JTextField lastNameEnter = new JTextField();
        JButton go = new JButton("Go");
        JButton go2 = new JButton("Go");

        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 0;
        addPatient.add(firstName,gbc);
        gbc.ipadx = 160;
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 1;
        addPatient.add(firstNameEnter,gbc);
        gbc.ipady = 40;
        gbc.gridx = 1;
        gbc.gridy = 1;
        addPatient.add(go,gbc);

        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 2;
        addPatient.add(lastName,gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 3;
        addPatient.add(lastNameEnter,gbc);
        gbc.ipadx = 160;
        gbc.ipady = 40;
        gbc.gridx = 1;
        gbc.gridy = 3;
        addPatient.add(go2,gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 4;
        addPatient.add(confirm2,gbc);

        viewPatient.setLayout(layout);
        JLabel myPatients = new JLabel("My Patients:");
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 0;
        viewPatient.add(myPatients,gbc);

        frameDoctor.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frameDoctor.setVisible(true);
    }

}




