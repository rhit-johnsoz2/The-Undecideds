package com.undecideds.services.structs;

import com.undecideds.ui.cuduibuilder.DateLabelFormatter;
import com.undecideds.ui.cuduibuilder.InputWidget;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Properties;

public class Argument {
    ArgumentType type;
    String argumentID;
    public Argument(ArgumentType type, String argumentID){
        this.type = type;
        this.argumentID = argumentID;
    }

    public boolean prepare(CallableStatement statement, int index, Object o){
        try{
            switch (type){
                case STRING -> {statement.setString(index, (String) o); return true;}
                case INT -> {statement.setInt(index, (int) o); return true;}
                case FLOAT -> {statement.setFloat(index, (float) o); return true;}
                case DATE -> {statement.setDate(index, (Date) o); return true;}
                case TIMESTAMP -> {statement.setTimestamp(index, (Timestamp) o); return true;}
                default -> {System.out.println("Error parsing argument, no type " + type.name());}
            }
        }catch (Exception e){
            System.out.println("Error parsing argument " + o.toString() + " doesn't match the object type " + type.name());
        }
        return false;
    }

    public InputWidget buildWidget() {
        JLabel label = new JLabel(argumentID);
        switch (type) {
            case STRING -> {
                return new InputWidget(argumentID){
                    JTextField text = new JTextField();
                    @Override
                    public Container generateWidget() {
                        Container con = new Panel(new GridLayout(1, 1));
                        con.add(label);
                        con.add(text);
                        return con;
                    }
                    @Override
                    public Object getValue() {
                        return text.getText();
                    }
                };
            }
            case INT -> {
                return new InputWidget(argumentID){
                    JTextField numbers = new JTextField();
                    @Override
                    public Container generateWidget() {
                        Container con = new Panel(new GridLayout(1, 1));
                        numbers.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                String value = numbers.getText();
                                numbers.setEditable(e.getKeyChar() >= '0' && e.getKeyChar() <= '9');
                            }
                        });
                        con.add(label);
                        con.add(numbers);
                        return con;
                    }
                    @Override
                    public Object getValue() {
                        return Integer.parseInt(numbers.getText());
                    }
                };
            }
            case FLOAT -> {
                return new InputWidget(argumentID){
                    JTextField text = new JTextField();
                    @Override
                    public Container generateWidget() {
                        Container con = new Panel(new GridLayout(1, 1));
                        con.add(label);
                        con.add(text);
                        return con;
                    }
                    @Override
                    public Object getValue() {
                        return Float.valueOf(text.getText());
                    }
                };
            }
            case DATE -> {
                return new InputWidget(argumentID){
                    JDatePickerImpl datePicker;
                    @Override
                    public Container generateWidget() {
                        Container con = new Panel(new GridLayout(1, 1));
                        con.add(label);

                        UtilDateModel model = new UtilDateModel();
                        Properties p = new Properties();
                        p.put("text.today", "Today");
                        p.put("text.month", "Month");
                        p.put("text.year", "Year");
                        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
                        con.add(datePicker);
                        return con;
                    }
                    @Override
                    public Object getValue() {
                        return datePicker.getModel().getValue();
                    }
                };
            }
            default -> {
                System.out.println("Error generating widget, no widget for type " + type.name());
            }
        }
        return null;
    }

    public enum ArgumentType{
        STRING,
        INT,
        FLOAT,
        DATE,
        TIMESTAMP
    }
}
