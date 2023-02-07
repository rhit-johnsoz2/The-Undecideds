package com.undecideds;

import com.undecideds.cli.CLIApplication;
import com.undecideds.services.DatabaseConnectionService;
import com.undecideds.services.ReadServiceList;
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

        ResultSet rs = ReadServiceList.CHRONIC_FROM_PATIENT.ExecuteQuery(new Object[]{
                5
        });

        JFrame test = new JFrame();
        HashSet<String> hidden = new HashSet<>();
        test.add(TableBuilder.buildTable(rs, hidden));
        test.setSize(500, 500);
        test.setVisible(true);

        if(guiEnabled){
            ClientWindow window = new ClientWindow();
            window.launch();
        }else{
            CLIApplication clip = new CLIApplication();
            clip.Launch(args);
        }
    }
}
