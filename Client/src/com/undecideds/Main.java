package com.undecideds;

import com.undecideds.cli.CLIApplication;
import com.undecideds.services.DatabaseConnectionService;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.generic.EncryptionService;
import com.undecideds.ui.ClientWindow;
import com.undecideds.ui.cuduibuilder.DateLabelFormatter;
import com.undecideds.ui.cuduibuilder.InputWidget;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Properties;

//Authors: 
// johnsoz2
// Cerasoml
// Liz Fogarty:sunglasses

public class Main {
    public static void main(String[] args){
        boolean guiEnabled = true;
        String encryptedPass = "";
        String user = "";
        for(String s : args){
            if(s.equals("-nogui")) {
                guiEnabled = false;
            }else if(s.startsWith("-password=")){
                encryptedPass = s.substring(s.indexOf("=") + 1);
            }else if(s.startsWith("-username=")){
                user = s.substring(s.indexOf("=") + 1);
            }else{
                System.out.println("unknown run config: " + s);
            }
        }
        System.out.println("attempting login with credentials:\n\tuser: " + user + "\n\tpass: " + EncryptionService.HiddenPass(encryptedPass));
        DatabaseConnectionService.InitDatabaseConnectionService("titan.csse.rose-hulman.edu", "SymptomTracker");
        boolean connected = DatabaseConnectionService.connect(user, EncryptionService.Decrypt(encryptedPass));
        if(!connected){
            System.out.println("Connection failed! Exiting . . .");
            System.exit(401);
        }

        HashMap<String, InputWidget> test = InsertServiceList.INSERT_NEEDS.buildUIWidgets();

        // My widget tests
        JFrame testFrame = new JFrame();
        JPanel testPanel = new JPanel(new GridLayout(5, 5));
        JButton testButton = new JButton("GET DATE");

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f1 = new JFrame();
                f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f1.setSize(300, 300);
                f1.setVisible(true);

//                Container conn = f1.getContentPane();
//                conn.setLayout(null);

                UtilDateModel model = new UtilDateModel();
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
                f1.add(datePicker);
            }
        });
        testPanel.add(test.get("PATIENT ID").generateWidget());
        testPanel.add(test.get("TREATMENT ID").generateWidget());
        testPanel.add(test.get("STARTING DATE").generateWidget());
        testPanel.add(test.get("ENDING DATE").generateWidget());
        testPanel.add(testButton);
        testFrame.add(testPanel);
        testFrame.setSize(500, 300);
        testFrame.setVisible(true);
        // end

        if(guiEnabled){
            ClientWindow window = new ClientWindow();
            //window.launch();
        }else{
            CLIApplication clip = new CLIApplication();
            clip.Launch(args);
        }
    }
}