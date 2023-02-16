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
                                numbers.setEditable(e.isActionKey() || e.getKeyCode() == KeyEvent.VK_BACK_SPACE || (e.getKeyChar() >= '0' && e.getKeyChar() <= '9'));
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
                        Date date = new Date(datePicker.getModel().getYear() - 1900, datePicker.getModel().getMonth(), datePicker.getModel().getDay());
                        return date;
                    }
                };
            }
            case TIMESTAMP -> {
                return new InputWidget(argumentID) {
                    @Override
                    public Container generateWidget() {
                        return new JPanel(new GridLayout(1, 1));
                    }

                    @Override
                    public Object getValue() {
                        return new Timestamp(System.currentTimeMillis());
                    }
                };
            }
            default -> {
                System.out.println("Error generating widget, no widget for type " + type.name());
            }
        }
        return null;
    }

    public InputWidget buildWidget(Object inputValue) {
        JLabel label = new JLabel(argumentID);
        switch (type) {
            case STRING -> {
                return new InputWidget(argumentID){
                    JTextField text = new JTextField((String) inputValue);
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
                System.out.println(inputValue);
                return new InputWidget(argumentID){
                    JTextField numbers = new JTextField(((Integer) inputValue).toString());
                    @Override
                    public Container generateWidget() {
                        Container con = new Panel(new GridLayout(1, 1));
                        numbers.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                String value = numbers.getText();
                                numbers.setEditable(e.isActionKey() || e.getKeyCode() == KeyEvent.VK_BACK_SPACE || (e.getKeyChar() >= '0' && e.getKeyChar() <= '9'));
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
                    JTextField text = new JTextField(((Float) inputValue).toString());
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

                        UtilDateModel model = new UtilDateModel((java.util.Date) inputValue);
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
                        Date date = new Date(datePicker.getModel().getYear(), datePicker.getModel().getMonth(), datePicker.getModel().getDay());
                        return date;
                    }
                };
            }
            case TIMESTAMP -> {
                return new InputWidget(argumentID) {
                    @Override
                    public Container generateWidget() {
                        System.out.println("Warning, timestamp passed default value with no defined default value behavior");
                        return new JPanel(new GridLayout(0, 0));
                    }

                    @Override
                    public Object getValue() {
                        return new Timestamp(System.currentTimeMillis());
                    }
                };
            }
            default -> {
                System.out.println("Error generating widget, no widget for type " + type.name());
            }
        }
        return null;
    }

    public Object parseArg(String s){
        try{
            switch (type){
                case STRING -> {return s;}
                case INT -> {return Integer.parseInt(s);}
                case FLOAT -> {return Float.parseFloat(s);}
                case DATE -> {
                    try{
                        if (s.equalsIgnoreCase("now")){
                            return new Date(System.currentTimeMillis());
                        }
                        return Date.valueOf(s);
                    }catch (Exception e){
                        if(s.matches("^[1]*[0-9][/][1-3]*[0-9][/][12][019][0-9][0-9]$")){
                            String[] subS = s.split("/");
                            return new Date(Integer.parseInt(subS[2]) - 1900, Integer.parseInt(subS[0]), Integer.parseInt(subS[1]));
                        }
                        break;
                    }
                }
                case TIMESTAMP -> {return s.equalsIgnoreCase("now") ? new Timestamp(System.currentTimeMillis()) : Timestamp.valueOf(s);}
                default -> {System.out.println("Error parsing argument, no type " + type.name());}
            }
        }catch (Exception e){
            System.out.println("Error parsing argument \"" + s + "\" doesn't match the object type " + type.name());
            return false;
        }
        System.out.println("Error parsing argument \"" + s + "\" doesn't match the object type " + type.name());
        return false;
    }

    public String getArgumentID() {
        return argumentID;
    }

    public enum ArgumentType{
        STRING,
        INT,
        FLOAT,
        DATE,
        TIMESTAMP
    }
}
