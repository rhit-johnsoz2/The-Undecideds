package com.undecideds.services.generic;

import com.undecideds.services.DatabaseConnectionService;
import com.undecideds.services.structs.Argument;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.HashMap;

public class CUDService {
    StringBuilder template;
    Argument[] arguments;
    String[] resultCodes;
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
    }

    public int ExecuteQuery(Object[] params) {
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

    public HashMap<String, InputWidget> buildUIWidgets() {
        HashMap<String, InputWidget> widgets = new HashMap<>();
        for(Argument a : arguments) {
            InputWidget widget = a.buildWidget();
            widgets.put(widget.getArgumentID(), widget);
        }
        return widgets;
    }

    public HashMap<String, InputWidget> buildUIWidgets(HashMap<String, Object> inputValues) {
        HashMap<String, InputWidget> widgets = new HashMap<>();
        for(Argument a : arguments) {
            InputWidget widget = a.buildWidget(inputValues.get(a.getArgumentID()));
            widgets.put(widget.getArgumentID(), widget);
        }
        return widgets;
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
}
