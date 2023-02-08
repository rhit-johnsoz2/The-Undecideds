package com.undecideds.ui.builders;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TableBuilder {

    public static Container buildTable(ResultSet rs){
        return buildTable(rs, new HashSet<String>());
    }

    public static Container buildTable(ResultSet rs, HashSet<String> hidden){
        Container container = new JPanel(new GridLayout(1, 1));
        try{
            ResultSetMetaData rsmd = rs.getMetaData();

            ArrayList<Integer> headerIDs = new ArrayList<>();
            ArrayList<String> headers = new ArrayList<>();
            HashMap<String, ArrayList<Object>> data = new HashMap<>();


            for(int i = 0; i < rsmd.getColumnCount(); i++){
                String header = rsmd.getColumnName(i + 1);
                if(!hidden.contains(header)){
                    headers.add(header);
                    headerIDs.add(i + 1);
                    data.put(header, new ArrayList<>());
                }
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

            JTable table = new JTable(data_arr, headers_arr);
            container.add(new JScrollPane(table));
            return container;
        }catch(Exception e){
            container.add(new JLabel("Table has not been fetched. If this is an error, please view the stack trace."));
            e.printStackTrace();
            return container;
        }
    }



}
