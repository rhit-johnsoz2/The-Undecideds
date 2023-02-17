package com.undecideds.services.generic;

import com.undecideds.services.DatabaseConnectionService;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.structs.Argument;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;
import com.undecideds.ui.cuduibuilder.ResultTableListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadService {
    public static final ArrayList<ReadService> READ_SERVICES = new ArrayList<>();
    StringBuilder template;
    Argument[] arguments;
    String sprocName;
    public ReadService(String sprocName, Argument[] arguments){
        template = new StringBuilder("{? = call " + sprocName + "(");
        this.arguments = arguments;
        for(int i = 0; i < arguments.length; i++){
            if(i == arguments.length - 1){
                template.append("?)}");
            }else{
                template.append("?, ");
            }
        }
        if(arguments.length == 0){
            template.append(")}");
        }
        this.sprocName = sprocName;
        READ_SERVICES.add(this);
    }

    public ResultSet ExecuteQuery(Object[] params) {
        try{
            CallableStatement statement = DatabaseConnectionService.getConnection().prepareCall(template.toString());
            statement.registerOutParameter(1, Types.INTEGER);
            int paramNumber = 2;
            for(Argument a : arguments){
                a.prepare(statement, paramNumber, params[paramNumber - 2]);
                paramNumber++;
            }
            return statement.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeFromStrings(String[] strings) {
        Object[] args = new Object[arguments.length];
        for(int i = 0; i < args.length; i++){
            args[i] = arguments[i].parseArg(strings[i]);
        }
        return ExecuteQuery(args);
    }

    public HashMap<String, InputWidget> buildUIWidgets() {
        HashMap<String, InputWidget> widgets = new HashMap<>();
        for(Argument a : arguments) {
            InputWidget widget = a.buildWidget();
            widgets.put(widget.getArgumentID(), widget);
        }
        return widgets;
    }

    public Container buildActivateButton(String name, HashMap<String, InputWidget> sources, ResultTableListener resultListener){
        JPanel panel = new JPanel(new GridLayout(1, 1));
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet res;
                Object[] values = new Object[arguments.length];
                for(int i = 0; i < arguments.length; i++){
                    values[i] = sources.get(arguments[i].getArgumentID()).getValue();
                }
                res = ExecuteQuery(values);
                resultListener.onResult(res);
            }
        });
        panel.add(button);
        return panel;
    }

    public String getSprocName(){
        return sprocName;
    }

    @Override
    public String toString() {
        return template.toString();
    }

    public static ReadService getServiceFromName(String name){
        for(ReadService service : READ_SERVICES){
            if(name.equalsIgnoreCase(service.sprocName)){
                return service;
            }
        }
        return null;
    }

    public static Object getSingleton(ResultSet rs) {
        try {
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public static InputWidget generateComboWidget(String argId, ReadService readService, Object[] inputValues){
        return new InputWidget(argId){
            JComboBox comboBox;
            HashMap<String, Integer> map;
            @Override
            public Container generateWidget() {
                try {
                    map = new HashMap<>();
                    ResultSet rs = readService.ExecuteQuery(inputValues);
                    String inputName = "";
                    while (rs.next()) {
                        int id = rs.getInt("ID");
                        String name = rs.getString("NAME");
                        map.put(name, id);
                    }
                    comboBox = new JComboBox(map.keySet().toArray());
                    JPanel container = new JPanel(new GridLayout(1, 2));
                    container.add(new JLabel(argId));
                    container.add(comboBox);
                    return container;
                }catch (Exception e){
                    JPanel container = new JPanel();
                    container.add(new JLabel("Issue fetching ID map: " + e));
                    return container;
                }
            }

            @Override
            public Object getValue() {
                return map.get(comboBox.getModel().getSelectedItem().toString());
            }
        };
    }

}
