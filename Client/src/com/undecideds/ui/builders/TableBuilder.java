package com.undecideds.ui.builders;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

public class TableBuilder {


    public static Container buildTableWithCUD(ReadService readService, HashMap<String, String> name_match, CUDService create, CUDService update, CUDService delete, HashSet<String> fixedOnUpdate, boolean canUpdate, boolean canDelete, HashMap<String, ReadService> idProviders){
        try {
            ResultSet rs = readService.ExecuteQuery(new Object[]{});
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            JTable table = (JTable) buildTableRaw(rs, new HashSet<String>());

            JButton createButton = new JButton("Insert new");
            JButton updateButton = new JButton("Update");
            JButton deleteButton = new JButton("Delete");
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);

            HashMap<String, Object> inputValues = new HashMap<>();

            createButton.addActionListener(new ActionListener() {
                void updateTable(){
                    try {
                        ResultSet rs = readService.ExecuteQuery(new Object[]{});
                        table.setModel(getTableModel(rs, new HashSet<String>()));
                    }catch (Exception e){
                        System.out.println("fatal error refetching table");
                        e.printStackTrace();
                        System.exit(501);
                    }
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame create_popup = new JFrame("insert new item");
                    HashMap<String, InputWidget> widgets = create.buildUIWidgets(idProviders, true);
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                    Container submit = create.buildActivateButton("Submit", widgets, new ResultListener() {
                        @Override
                        public void onResult(int result) {
                            if(result == 0) {
                                updateTable();
                                create_popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                create_popup.dispatchEvent(new WindowEvent(create_popup, WindowEvent.WINDOW_CLOSING));
                            }else{
                                create_popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                create_popup.dispatchEvent(new WindowEvent(create_popup, WindowEvent.WINDOW_CLOSING));
                                JFrame error_popup = new JFrame();
                                JPanel panel = new JPanel(new GridLayout(1, 1));
                                panel.add(new JLabel("Error executing procedure: " + create.codeMeaning(result)));
                                error_popup.add(panel);
                                error_popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                error_popup.pack();
                                error_popup.setVisible(true);
                            }
                        }
                    });
                    for(String key : widgets.keySet()){
                        panel.add(widgets.get(key).generateWidget());
                    }
                    panel.add(submit);
                    create_popup.add(panel);
                    create_popup.setSize(500, 500);
                    create_popup.pack();
                    create_popup.setVisible(true);
                }
            });

            updateButton.addActionListener(new ActionListener() {
                void updateTable(){
                    try {
                        ResultSet rs = readService.ExecuteQuery(new Object[]{});
                        table.setModel(getTableModel(rs, new HashSet<String>()));
                    }catch (Exception e){
                        System.out.println("fatal error refetching table");
                        e.printStackTrace();
                        System.exit(501);
                    }
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame update_popup = new JFrame("edit item");

                    HashMap<String, InputWidget> widgets = update.buildUIWidgets(inputValues, idProviders);


                    HashMap<String, InputWidget> insertWidgets = create.buildUIWidgets(idProviders, true);
                    for(String key : widgets.keySet()){
                        if(!insertWidgets.containsKey(key) || fixedOnUpdate.contains(key)){
                            widgets.get(key).generateWidget();
                            final Object value = widgets.get(key).getValue();
                            widgets.replace(key, new InputWidget(widgets.get(key).getArgumentID()){
                                @Override
                                public Container generateWidget() {
                                    return new JPanel();
                                }

                                @Override
                                public Object getValue() {
                                    return value;
                                }
                            });
                        }
                    }

                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                    Container submit = update.buildActivateButton("Submit", widgets, new ResultListener() {
                        @Override
                        public void onResult(int result) {
                            if(result == 0) {
                                updateTable();
                                update_popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                update_popup.dispatchEvent(new WindowEvent(update_popup, WindowEvent.WINDOW_CLOSING));
                            }else{
                                update_popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                update_popup.dispatchEvent(new WindowEvent(update_popup, WindowEvent.WINDOW_CLOSING));
                                JFrame error_popup = new JFrame();
                                JPanel panel = new JPanel(new GridLayout(1, 1));
                                panel.add(new JLabel("Error executing procedure: " + update.codeMeaning(result)));
                                error_popup.add(panel);
                                error_popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                error_popup.pack();
                                error_popup.setVisible(true);
                            }
                        }
                    });
                    for(String key : widgets.keySet()){
                        panel.add(widgets.get(key).generateWidget());
                    }
                    panel.add(submit);
                    update_popup.add(panel);
                    update_popup.setSize(500, 500);
                    update_popup.pack();
                    update_popup.setVisible(true);
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                void updateTable(){
                    try {
                        ResultSet rs = readService.ExecuteQuery(new Object[]{});
                        table.setModel(getTableModel(rs, new HashSet<String>()));
                    }catch (Exception e){
                        System.out.println("fatal error refetching table");
                        e.printStackTrace();
                        System.exit(501);
                    }
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame delete_popup = new JFrame("delete item");

                    HashMap<String, InputWidget> widgets = delete.buildUIWidgets(inputValues, idProviders);
                    for(String key : widgets.keySet()){
                        widgets.get(key).generateWidget();
                        final Object value = widgets.get(key).getValue();
                        widgets.replace(key, new InputWidget(widgets.get(key).getArgumentID()){
                            @Override
                            public Container generateWidget() {
                                return new JPanel();
                            }

                            @Override
                            public Object getValue() {
                                return value;
                            }
                        });
                    }

                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                    Container submit = delete.buildActivateButton("Submit", widgets, new ResultListener() {
                        @Override
                        public void onResult(int result) {
                            if(result == 0) {
                                updateTable();
                                delete_popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                delete_popup.dispatchEvent(new WindowEvent(delete_popup, WindowEvent.WINDOW_CLOSING));
                            }else{
                                delete_popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                delete_popup.dispatchEvent(new WindowEvent(delete_popup, WindowEvent.WINDOW_CLOSING));
                                JFrame error_popup = new JFrame();
                                JPanel panel = new JPanel(new GridLayout(1, 1));
                                panel.add(new JLabel("Error executing procedure: " + delete.codeMeaning(result)));
                                error_popup.add(panel);
                                error_popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                error_popup.pack();
                                error_popup.setVisible(true);
                            }
                        }
                    });
                    JButton reject = new JButton("Reject changes");
                    reject.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            delete_popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            delete_popup.dispatchEvent(new WindowEvent(delete_popup, WindowEvent.WINDOW_CLOSING));
                        }
                    });
                    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
                    buttonPanel.add(reject);
                    buttonPanel.add(submit);
                    panel.add(new JLabel("Are you sure?"));
                    panel.add(buttonPanel);
                    delete_popup.add(panel);
                    delete_popup.setSize(500, 500);
                    delete_popup.pack();
                    delete_popup.setVisible(true);
                }
            });

            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(table.getSelectedRow() != -1){

                        inputValues.clear();
                        for(int i = 0; i < table.getColumnCount(); i++) {
                            inputValues.put(name_match.get(table.getColumnName(i)), table.getValueAt(table.getSelectedRow(), i));
                        }


                        updateButton.setEnabled(true);
                        deleteButton.setEnabled(true);

                    }else{
                        updateButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                    }
                }
            });


            JScrollPane tablePane = new JScrollPane(table);
            JPanel buttonPanel = new JPanel(new GridLayout(1, 1 + (canUpdate ? 1 : 0) + (canDelete ? 1 : 0)));

            panel.add(tablePane);

            if(canDelete) {
                buttonPanel.add(deleteButton);
            }
            if(canUpdate) {
                buttonPanel.add(updateButton);
            }
            buttonPanel.add(createButton);

            panel.add(buttonPanel);

            return panel;
        }catch (Exception e){
            Container errContainer = new JPanel(new GridLayout(1, 1));
            errContainer.add(new JLabel("Table has not been fetched. If this is an error, please view the stack trace."));
            e.printStackTrace();
            return errContainer;
        }
    }

    public static Container buildTable(ResultSet rs){
        return buildTable(rs, new HashSet<String>());
    }

    public static Container buildTable(ResultSet rs, HashSet<String> hidden){
        Container container = new JPanel(new GridLayout(1, 1));
        container.add(new JScrollPane( buildTableRaw(rs, hidden)));
        return container;
    }

    public static JComponent buildTableRaw(ResultSet rs, HashSet<String> hidden){
        DefaultTableModel model = getTableModel(rs, hidden);
        if(model != null) {
            JTable table = new JTable(model);
            for(String s : hidden){
                table.getColumn(s).setMinWidth(0);
                table.getColumn(s).setMaxWidth(0);
            }
            return table;
        }else{
            return new JLabel("Table has not been fetched. If this is an error, please view the stack trace.");
        }
    }

    private static DefaultTableModel getTableModel(ResultSet rs, HashSet<String> hidden){
        try{
            ResultSetMetaData rsmd = rs.getMetaData();

            ArrayList<Integer> headerIDs = new ArrayList<>();
            ArrayList<String> headers = new ArrayList<>();
            HashMap<String, ArrayList<Object>> data = new HashMap<>();


            for(int i = 0; i < rsmd.getColumnCount(); i++){
                String header = rsmd.getColumnName(i + 1);

                headers.add(header);
                headerIDs.add(i + 1);
                data.put(header, new ArrayList<>());

            }


            while(rs.next()){
                for(int i = 0; i < headers.size(); i++) {
                    data.get(headers.get(i)).add(rs.getObject(headerIDs.get(i)));
                }
            }

            String[] headers_arr = new String[headers.size()];
            Object[][] data_arr = new Object[data.get(headers.get(0)).size()][];
            for(int i = 0; i < headers.size(); i ++){
                headers_arr[i] = headers.get(i);
            }
            for(int i = 0; i < data.get(headers.get(0)).size(); i++){
                data_arr[i] = new Object[headers.size()];
                for(int j = 0; j < headers.size(); j++){
                    data_arr[i][j] = data.get(headers.get(j)).get(i);
                }
            }

            DefaultTableModel model = new DefaultTableModel(data_arr, headers_arr){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }};



            return model;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static Container buildTableWithCUD(ReadService readService, HashMap<String, String> name_match, CUDService create, CUDService update, CUDService delete) {
        return buildTableWithCUD(readService, name_match, create, update, delete, new HashSet<String>(), true, true, new HashMap<>());
    }

    public static Container buildTableWithCUD(ReadService readService, HashMap<String, String> name_match, CUDService create, CUDService update, CUDService delete, String[] fixedOnUpdate) {
        HashSet<String> fixed = new HashSet<>(Arrays.asList(fixedOnUpdate));
        return buildTableWithCUD(readService, name_match, create, update, delete, fixed, true, true, new HashMap<>());
    }

    public static Container buildTableWithCUD(ReadService readService, HashMap<String, String> name_match, CUDService create, CUDService update, CUDService delete, boolean canUpdate, boolean canDelete) {
        return buildTableWithCUD(readService, name_match, create, update, delete, new HashSet<String>(), canUpdate, canDelete, new HashMap<>());
    }

    public static Container buildTableWithCUD(ReadService readService, HashMap<String, String> name_match, CUDService create, CUDService update, CUDService delete, String[] fixedOnUpdate, boolean canUpdate, boolean canDelete) {
        HashSet<String> fixed = new HashSet<>(Arrays.asList(fixedOnUpdate));
        return buildTableWithCUD(readService, name_match, create, update, delete, fixed, canUpdate, canDelete, new HashMap<>());
    }

    public static Container buildTableWithCUD(ReadService readService, HashMap<String, String> name_match, CUDService create, CUDService update, CUDService delete, String[] fixedOnUpdate, boolean canUpdate, boolean canDelete, String[] args, ReadService[] IDproviders){
        HashSet<String> fixed = new HashSet<>(Arrays.asList(fixedOnUpdate));
        HashMap<String, ReadService> idMatch = new HashMap<>();
        for(int i = 0; i < args.length; i++){
            idMatch.put(args[i], IDproviders[i]);
        }
        return buildTableWithCUD(readService, name_match, create, update, delete, fixed, canUpdate, canDelete, idMatch);
    }

    public static HashMap<String, String> buildNameMatch(String[] map){
        HashMap<String, String> nameMatch = new HashMap<>();
        for(int i = 0; i < map.length; i += 2){
            nameMatch.put(map[i], map[i+1]);
        }
        return nameMatch;
    }


}
