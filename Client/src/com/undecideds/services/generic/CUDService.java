package com.undecideds.services.generic;

import com.undecideds.cli.CLIApplication;
import com.undecideds.services.DatabaseConnectionService;
import com.undecideds.services.structs.Argument;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;

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

public class CUDService {
    public static final ArrayList<CUDService> CUD_SERVICES = new ArrayList<>();
    StringBuilder template;
    Argument[] arguments;
    String[] resultCodes;
    String sprocName;
    public CUDService(String sprocName, Argument[] arguments, String[] resultCodes){
        template = new StringBuilder("{? = call " + sprocName + "(");
        this.arguments = arguments;
        this.resultCodes = resultCodes;
        for(int i = 0; i < arguments.length; i++){
            if(i == arguments.length - 1){
                template.append("?)}");
            }else{
                template.append("?, ");
            }
        }
        this.sprocName = sprocName;
        CUD_SERVICES.add(this);
    }

    public int ExecuteQuery(Object[] params) {
        for(Object o : params){
            System.out.println("input:" + o);
        }
        System.out.println(template.toString());
        try{
            CallableStatement statement = DatabaseConnectionService.getConnection().prepareCall(template.toString());
            statement.registerOutParameter(1, Types.INTEGER);
            int paramNumber = 2;
            for(Argument a : arguments){
                a.prepare(statement, paramNumber, params[paramNumber - 2]);
                paramNumber++;
            }
            statement.execute();
            return statement.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public HashMap<String, InputWidget> buildUIWidgets(HashMap<String, ReadService> idMatch, boolean flag) {
        HashMap<String, InputWidget> widgets = new HashMap<>();
        for(Argument a : arguments) {
            if(idMatch.containsKey(a.getArgumentID())){
                widgets.put(a.getArgumentID(), generateComboWidget(a.getArgumentID(), idMatch, null));
            }else {
                InputWidget widget = a.buildWidget();
                widgets.put(widget.getArgumentID(), widget);
            }
        }
        return widgets;
    }

    public HashMap<String, InputWidget> buildUIWidgets(HashMap<String, Object> inputValues, HashMap<String, ReadService> idMatch) {
        HashMap<String, InputWidget> widgets = new HashMap<>();
        for(Argument a : arguments) {
            if(idMatch.containsKey(a.getArgumentID())){
                widgets.put(a.getArgumentID(), generateComboWidget(a.getArgumentID(), idMatch, inputValues.get(a.getArgumentID())));
            }else {
                InputWidget widget = a.buildWidget(inputValues.get(a.getArgumentID()));
                widgets.put(widget.getArgumentID(), widget);
            }
        }
        return widgets;
    }

    public HashMap<String, InputWidget> buildUIWidgets(){
        return buildUIWidgets(new HashMap<String, ReadService>(), true);
    }

    public HashMap<String, InputWidget> buildUIWidgets(HashMap<String, Object> inputValues){
        return buildUIWidgets(inputValues, new HashMap<String, ReadService>());
    }

    public Container buildActivateButton(String name, HashMap<String, InputWidget> sources, ResultListener resultListener){
        JPanel panel = new JPanel(new GridLayout(1, 1));
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res;
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

    public String getSprocName() {
        return sprocName;
    }

    public void executeCLI(){
        Object[] args = new Object[arguments.length];
        for(int i = 0; i < args.length; i++){
            String s = CLIApplication.QueryUser(arguments[i].getArgumentID() + "= ");
            args[i] = arguments[i].parseArg(s);
        }
        int res = ExecuteQuery(args);
        System.out.println("Return code " + res + ": " + codeMeaning(res));
    }

    public int executeFromStrings(String[] strings) {
        Object[] args = new Object[arguments.length];
        for(int i = 0; i < args.length; i++){
            args[i] = arguments[i].parseArg(strings[i]);
        }
        return ExecuteQuery(args);
    }

    private InputWidget generateComboWidget(String argId, HashMap<String, ReadService> idMatch, Object inputValue){
        return new InputWidget(argId){
            JComboBox comboBox;
            HashMap<String, Integer> map;
            @Override
            public Container generateWidget() {
                try {
                    map = new HashMap<>();
                    ResultSet rs = idMatch.get(argId).ExecuteQuery(new Object[]{});
                    String inputName = "";
                    while (rs.next()) {
                        int id = rs.getInt("ID");
                        String name = rs.getString("NAME");
                        map.put(name, id);
                        if(inputValue != null && (int)inputValue == id){
                            inputName = name;
                        }
                    }
                    comboBox = new JComboBox(map.keySet().toArray());
                    if (inputValue != null){
                        comboBox.getModel().setSelectedItem(inputName);
                    }
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

    @Override
    public String toString() {
        return template.toString();
    }

    public String codeMeaning(int code){
        if(code == -1){
            return "Failed to parse params, check stacktrace";
        }
        return resultCodes[code];
    }

    public static CUDService getServiceFromName(String name){
        for(CUDService service : CUD_SERVICES){
            if(name.equalsIgnoreCase(service.sprocName)){
                return service;
            }
        }
        return null;
    }
}
