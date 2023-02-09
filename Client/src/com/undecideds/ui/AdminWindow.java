package com.undecideds.ui;

import com.undecideds.services.DeleteServiceList;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.UpdateServiceList;
import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.builders.TableBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class AdminWindow {

    public void launch(){
        JFrame frame = new JFrame();

        JTabbedPane context = new JTabbedPane();
        context.addTab("Person", null, buildTableView(ReadServiceList.GET_PERSONS,
                new CUDService[]{
                        InsertServiceList.INSERT_PERSON,
                        UpdateServiceList.UPDATE_PERSON,
                        DeleteServiceList.DELETE_PERSON
                },
                new String[]{
                    "ID", "PERSON ID",
                    "fname",  "FNAME",
                    "lname", "LNAME",
                    "login", "LOGIN",
                    "password", "PASSWORD",
                    "role", "ROLE",
                    "hcpID", "INSURED BY"
                }
        ));
        context.addTab("Not Person", null, buildTableView(ReadServiceList.GET_PERSONS,
                new CUDService[]{
                        InsertServiceList.INSERT_PERSON,
                        UpdateServiceList.UPDATE_PERSON,
                        DeleteServiceList.DELETE_PERSON
                },
                new String[]{
                        "ID", "PERSON ID",
                        "fname",  "FNAME",
                        "lname", "LNAME",
                        "login", "LOGIN",
                        "password", "PASSWORD",
                        "role", "ROLE",
                        "hcpID", "INSURED BY"
                }
        ));



        frame.add(context);
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private Container buildTableView(ReadService readService, CUDService[] services, String[] map){
        HashMap<String, String> nameMatch = new HashMap<>();
        for(int i = 0; i < map.length; i += 2){
            nameMatch.put(map[i], map[i+1]);
        }
        return TableBuilder.buildTableWithCUD(readService, nameMatch, services[0], services[1], services[2]);
    }
}
