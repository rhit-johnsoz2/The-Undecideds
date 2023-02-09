package com.undecideds;

import com.undecideds.cli.CLIApplication;
import com.undecideds.services.*;
import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.EncryptionService;
import com.undecideds.ui.ClientWindow;
import com.undecideds.ui.LoginWindow;
import com.undecideds.ui.cuduibuilder.DateLabelFormatter;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Properties;

import com.undecideds.services.generic.EncryptionService;
import com.undecideds.ui.ClientWindow;
import com.undecideds.ui.builders.TableBuilder;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.HashSet;

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
        
//        HashMap<String, InputWidget> test = InsertServiceList.INSERT_NEEDS.buildUIWidgets();
//
//        // My widget tests
//        JFrame testFrame = new JFrame();
//        JPanel testPanel = new JPanel(new GridLayout(5, 5));
//        JButton testButton = new JButton("GET DATE");
//
//        testButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println(test.get("STARTING DATE").getValue());
//            }
//        });
//        testPanel.add(test.get("PATIENT ID").generateWidget());
//        testPanel.add(test.get("TREATMENT ID").generateWidget());
//        testPanel.add(test.get("STARTING DATE").generateWidget());
//        testPanel.add(test.get("ENDING DATE").generateWidget());
//        //testPanel.add(testButton);
//
//        testPanel.add(InsertServiceList.INSERT_NEEDS.buildActivateButton("execute query", test, new ResultListener() {
//            @Override
//            public void onResult(int result) {
//                System.out.println("SPROC RESULT: " + result);
//            }
//        }));
//
//        testFrame.add(testPanel);
//        testFrame.setSize(500, 300);
//        testFrame.setVisible(true);
//        // end
//
//        ResultSet rs = ReadServiceList.CHRONIC_FROM_PATIENT.ExecuteQuery(new Object[]{
//                5
//        });
//
//        JFrame test2 = new JFrame();
//        test2.add(TableBuilder.buildTable(rs));
//        test2.setSize(500, 500);
//        test2.setVisible(true);


//        JFrame testWindow = new JFrame();
//        HashMap<String, String> nameMatch = new HashMap<>();
//        nameMatch.put("ID", "PERSON ID");
//        nameMatch.put("fname",  "FNAME");
//        nameMatch.put("lname", "LNAME");
//        nameMatch.put("login", "LOGIN");
//        nameMatch.put("password", "PASSWORD");
//        nameMatch.put("role", "ROLE");
//        nameMatch.put("hcpID", "INSURED BY");
//
//        testWindow.add(TableBuilder.buildTableWithCUD(ReadServiceList.GET_PERSONS, nameMatch, InsertServiceList.INSERT_PERSON, UpdateServiceList.UPDATE_PERSON, DeleteServiceList.DELETE_PERSON));
//
//
//        testWindow.setSize(500, 500);
//        testWindow.setVisible(true);



        if(guiEnabled){
            LoginWindow window = new LoginWindow();
            window.launch();
        }else{
            CLIApplication clip = new CLIApplication();
            clip.Launch(args);
        }
    }
}