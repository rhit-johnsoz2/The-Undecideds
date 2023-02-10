package com.undecideds.ui;



import com.undecideds.services.InsertServiceList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

import static com.undecideds.services.structs.Argument.ArgumentType.DATE;

public class ClientWindow{
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

        JFrame mainFrame = new JFrame();
        mainFrame.setSize(350,300);
        JTextField passwordBox = new JTextField();
        JTextField userNameBox = new JTextField();
        JButton confirm = new JButton("Confirm");
        JLabel userName = new JLabel("Username");
        JLabel password = new JLabel("Password");
        JLabel lmsg = new JLabel();

        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String pass = passwordBox.getText();
                String user = userNameBox.getText();

                mainFrame.dispose();
                if(pass.equals("patient") && user.equals("patient")){
                    System.out.println("Pat login");
                    viewPatient();
                }else if(pass.equals("doctor") && user.equals("doctor")){
                    System.out.println("Doc Stuff");
                    viewDoctor();
                }else if(pass.equals("admin") && user.equals("admin")) {
                    System.out.println("Admin Stuff");
                } else{
                    lmsg.setText("Invalid User ID or Password");
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();

        {
            mainFrame.setLayout(layout);
            gbc.ipady = 40;
            gbc.ipadx = 50;
            gbc.gridx = 0;
            gbc.gridy = 0;
            mainFrame.add(userName, gbc);
            gbc.ipady = 40;
            gbc.ipadx = 50;
            gbc.gridx = 0;
            gbc.gridy = 1;
            mainFrame.add(password, gbc);
            gbc.ipadx = 175;
            gbc.ipady = 40;
            gbc.gridx = 1;
            gbc.gridy = 0;
            mainFrame.add(userNameBox, gbc);
            gbc.ipadx = 175;
            gbc.ipady = 40;
            gbc.gridx = 1;
            gbc.gridy = 1;
            mainFrame.add(passwordBox, gbc);
            gbc.ipady = 40;
            gbc.ipadx = 100;
            gbc.gridx = 1;
            gbc.gridy = 2;
            mainFrame.add(confirm, gbc);
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 2;
            mainFrame.add(lmsg, gbc);
        }

        mainFrame.setVisible(true);
    }
    
    
@Deprecated
public class ClientWindow {

    public void viewPatient(){
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
        JLabel hello = new JLabel("Hello Jorg Hansen");
        JLabel curSympts = new JLabel("Current Symptoms");



        double[] values = {10,11,3,11,12,13,15};
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("Frequency of symptom", values, 7);

        JFreeChart histogram = ChartFactory.createHistogram("Cough Histogram",
                "Severity", "Frequency", dataset);
        ChartPanel chartPanel = new ChartPanel(histogram);

        {
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 0;
            home.add(hello, gbc);
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 1;
            home.add(curSympts, gbc);
            gbc.ipadx = 200;
            gbc.ipady = 200;
            gbc.gridx = 0;
            gbc.gridy = 2;
            home.add(chartPanel,gbc);
        }

        viewHistory.setLayout(layout);
        JLabel viewHistoryText = new JLabel("View History");

        double[] values2 = {10,11,3,11,12,13,15};
        HistogramDataset dataset2 = new HistogramDataset();
        dataset.addSeries("Frequency of symptom", values, 7);

        JFreeChart histogram2 = ChartFactory.createHistogram("Cough Histogram",
                "Severity", "Frequency", dataset);
        ChartPanel chartPanel2 = new ChartPanel(histogram);

        {
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 0;
            viewHistory.add(viewHistoryText, gbc);
            gbc.ipadx = 200;
            gbc.ipady = 200;
            gbc.gridx = 0;
            gbc.gridy = 1;
            viewHistory.add(chartPanel2,gbc);
        }
        addSymptom.setLayout(layout);
        JLabel selectSympt = new JLabel("Select Symptom:");
        JLabel selectSev = new JLabel("Select Severity:");
        JButton confirm2 = new JButton("Confrim");
        String[] symps = {"Cold","Flu","Migraine"};
        String[] sev = {"1","2","3","4","5"};
        JComboBox selectSymptom = new JComboBox(symps);
        JComboBox selectSeverity = new JComboBox(sev);
        JTextField lastNameEnter = new JTextField();

        confirm2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                int result = InsertServiceList.INSERT_ACUTE.ExecuteQuery(new Object[]{
                        19, 1, 3, java.sql.Date.valueOf(LocalDate.now())
                });
            }
        });

        {
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
        gbc.gridx = 0;
        gbc.gridy = 2;
        addSymptom.add(selectSev,gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 3;
        addSymptom.add(selectSeverity,gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 4;
        addSymptom.add(confirm2,gbc);}

        framePatient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        framePatient.setVisible(true);
    }

    public void viewDoctor() {
        JFrame frameDoctor = new JFrame();
        frameDoctor.setSize(700, 500);

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();

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
        JLabel Jorg = new JLabel("Jorg");

        {
            double[] values = {10, 11, 3, 11, 12, 13, 15};
            HistogramDataset dataset = new HistogramDataset();
            dataset.addSeries("Frequency of symptom", values, 7);

            JFreeChart histogram = ChartFactory.createHistogram("Jorg Henson",
                    "Severity", "Frequency", dataset);
            ChartPanel jorrView = new ChartPanel(histogram);

            {
            home.setLayout(layout);
            gbc.gridx = 0;
            gbc.gridy = 0;
            home.add(helloDoctor, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            home.add(currentPatients, gbc);
            gbc.ipadx = 200;
            gbc.ipady = 200;
            gbc.gridx = 0;
            gbc.gridy = 2;
            home.add(jorrView, gbc);
        }
    }

        JLabel selectPatient = new JLabel("Select Patient:");
        JLabel treatment = new JLabel("SelectTreatment:");
        JButton confirm = new JButton("Confrim");
        String[] pat = {"Jorg Henson"};
        String[] treatments = {"Badvillll","Eye Drops"};
        JComboBox selectPatientDropDown = new JComboBox(pat);
        JComboBox selectTreatment = new JComboBox(treatments);

        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int result = InsertServiceList.INSERT_NEEDS.ExecuteQuery(new Object[]{
                        19, 2, java.sql.Date.valueOf(LocalDate.now()), null
                });

            }
        });

        {
        assignTreatment.setLayout(layout);
        gbc.ipadx = 100;
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 0;
        assignTreatment.add(selectPatient, gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 100;
        assignTreatment.add(selectPatientDropDown, gbc);
        gbc.ipadx = 100;
        gbc.ipady = 40;
        gbc.gridx = 1;
        gbc.gridy = 0;
        assignTreatment.add(treatment);
        gbc.ipadx = 100;
        gbc.ipady = 40;
        gbc.gridx = 1;
        gbc.gridy = 1;
        assignTreatment.add(selectTreatment, gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 3;
        assignTreatment.add(confirm, gbc);
    }

        addPatient.setLayout(layout);
        JLabel firstName = new JLabel("Enter Patient First Name:");
        JLabel lastName = new JLabel("Enter Patient Last Name:");
        JButton confirm2 = new JButton("Confrim");
        JTextField firstNameEnter = new JTextField();
        JTextField lastNameEnter = new JTextField();
        JButton go = new JButton("Go");
        JButton go2 = new JButton("Go");

        confirm2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int result = InsertServiceList.INSERT_PERFORMS.ExecuteQuery(new Object[]{
                        13, 19
                });
            }
        });

        {
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 0;
        addPatient.add(firstName, gbc);
        gbc.ipadx = 160;
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 1;
        addPatient.add(firstNameEnter, gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 2;
        addPatient.add(lastName, gbc);
        gbc.ipadx = 160;
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 3;
        addPatient.add(lastNameEnter, gbc);
        gbc.ipady = 40;
        gbc.gridx = 0;
        gbc.gridy = 4;
        addPatient.add(confirm2, gbc);
    }
        viewPatient.setLayout(layout);
        JLabel myPatients = new JLabel("My Patients:");
        {
            double[] values = {10, 11, 3, 11, 12, 13, 15};
            HistogramDataset dataset = new HistogramDataset();
            dataset.addSeries("Frequency of symptom", values, 7);

            JFreeChart histogram = ChartFactory.createHistogram("Jorg Henson",
                    "Severity", "Frequency", dataset);
            ChartPanel jorrView = new ChartPanel(histogram);
            gbc.ipady = 40;
            gbc.gridx = 0;
            gbc.gridy = 0;
            viewPatient.add(myPatients, gbc);
            gbc.ipadx = 200;
            gbc.ipady = 200;
            gbc.gridx = 0;
            gbc.gridy = 2;
            viewPatient.add(jorrView,gbc);
        }

        frameDoctor.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frameDoctor.setVisible(true);

    }

}




