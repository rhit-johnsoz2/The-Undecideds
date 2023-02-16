package com.undecideds.services.structs;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.builders.TableBuilder;
import com.undecideds.ui.cuduibuilder.InputWidget;
import com.undecideds.ui.cuduibuilder.ResultListener;
import com.undecideds.ui.cuduibuilder.ResultTableListener;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.HashMap;

public class SprocContainer {
    ReadService rs = null;
    CUDService cuds = null;
    public SprocContainer(ReadService rs){
        this.rs = rs;
    }

    public SprocContainer(CUDService cuds){
        this.cuds = cuds;
    }

    public boolean isReadService(){
        return rs != null;
    }

    public ReadService getRs(){
        return rs;
    }

    public CUDService getCuds(){
        return cuds;
    }

    public void BuildExecuteWindow(){
        JFrame frame = new JFrame();
        HashMap<String, InputWidget> widgets = new HashMap<>();
        Container buttonContainer = new JPanel();
        if(rs != null){
            widgets = rs.buildUIWidgets();
            buttonContainer = rs.buildActivateButton("Execute S-proc", widgets, new ResultTableListener() {
                @Override
                public void onResult(ResultSet result) {
                    JFrame resultFrame = new JFrame("Result");
                    resultFrame.add(TableBuilder.buildTable(result));
                    resultFrame.setSize(500, 500);
                    resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    resultFrame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
            });
        }
        if(cuds != null){
            widgets = cuds.buildUIWidgets();
            buttonContainer = cuds.buildActivateButton("Execute S-proc", widgets, new ResultListener() {
                @Override
                public void onResult(int result) {
                    JFrame resultFrame = new JFrame("Result");
                    JLabel resultLabel = new JLabel(cuds.codeMeaning(result));
                    resultFrame.add(resultLabel);
                    resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    resultFrame.pack();
                    resultFrame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
            });
        }
        JPanel panel = new JPanel(new GridLayout(1, 1 + widgets.size()));
        for(String key : widgets.keySet()){
            panel.add(widgets.get(key).generateWidget());
        }
        panel.add(buttonContainer);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
