package com.undecideds.ui;

import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.generic.EncryptionService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;

import javax.management.remote.JMXConnectorFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class CreateAccountWindow {
    public void launch(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        HashMap<String, ReadService> idMatch = new HashMap<>();
        idMatch.put("INSURED BY", ReadServiceList.GET_HCP_NAMES);
        HashMap<String, InputWidget> widgets = InsertServiceList.INSERT_PERSON.buildUIWidgets(idMatch, true);
        widgets.replace("ROLE", new InputWidget("ROLE") {
            JComboBox comboBox;
            @Override
            public Container generateWidget() {
                JPanel panel = new JPanel(new GridLayout(1, 2));
                comboBox = new JComboBox(new String[]{"DR", "PA"});
                panel.add(new JLabel("Role"));
                panel.add(comboBox);
                return panel;
            }

            @Override
            public Object getValue() {
                return comboBox.getModel().getSelectedItem();
            }
        });
        widgets.replace("PASSWORD", new InputWidget("PASSWORD") {
            JPasswordField pass = new JPasswordField();
            @Override
            public Container generateWidget() {
                JPanel panel = new JPanel(new GridLayout(1, 2));
                panel.add(new JLabel("Password"));
                panel.add(pass);
                return panel;
            }

            @Override
            public Object getValue() {
                System.out.println(pass.getText());
                return EncryptionService.Encrypt(pass.getText());
            }
        });
        for(String key : widgets.keySet()){
            panel.add(widgets.get(key).generateWidget());
        }

        panel.add(InsertServiceList.INSERT_PERSON.buildActivateButton("Create", widgets, new ResultListener() {
            @Override
            public void onResult(int result) {
                if(result == 0){
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }else{
                    JFrame err_popup = new JFrame();
                    err_popup.add(new JLabel("Error creating account: " + InsertServiceList.INSERT_PERSON.codeMeaning(result)));
                    err_popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    err_popup.pack();
                    err_popup.setVisible(true);
                }
            }
        }));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
