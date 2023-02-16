package com.undecideds.ui;

import com.undecideds.services.ReadServiceList;
import com.undecideds.services.generic.EncryptionService;
import com.undecideds.services.generic.ReadService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class LoginWindow {

    JPanel panel;
    JLabel user_label;
    JLabel password_label;
    JLabel message;
    JTextField username_text;
    JPasswordField password_text;
    JButton submit;
    JFrame frame = new JFrame();

    public void launch() {
        user_label = new JLabel();
        user_label.setText("User Name :");
        username_text = new JTextField();
        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();
        submit = new JButton("SUBMIT");
        panel = new JPanel(new GridLayout(4, 1));
        panel.add(user_label);
        panel.add(username_text);
        panel.add(password_label);
        panel.add(password_text);
        message = new JLabel();
        panel.add(message);
        panel.add(submit);
        JButton newLoginButton = new JButton("Create New Account");
        newLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccountWindow createAccountWindow = new CreateAccountWindow();
                createAccountWindow.launch();
            }
        });
        panel.add(new JPanel());
        panel.add(newLoginButton);
        password_text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchActivity();
            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchActivity();
            }
        });
        submit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    launchActivity();
                }
            }
        });
        frame.add(panel, "Center");
        frame.setTitle("Please Login Here !");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    void launchActivity(){
        if(username_text.getText().trim().equals("admin") && password_text.getText().trim().equals("admin")){
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            AdminWindow adminWindow = new AdminWindow();
            adminWindow.launch();
            return;
        }
        String username = username_text.getText();
        String password = password_text.getText();

        Object o = ReadService.getSingleton(ReadServiceList.PASSWORD_FROM_LOGIN.ExecuteQuery(new Object[]{username}));
        if(o == null){
            launchLoginFail();
            return;
        }
        String correct = EncryptionService.Decrypt((String)o);

        if(correct.equals(password)){
            o = ReadService.getSingleton(ReadServiceList.ID_FROM_LOGIN.ExecuteQuery(new Object[]{username}));
            if(o == null){
                launchLoginFail("Login successful but failed to fetch ID");
                return;
            }
            int id = (int)o;
            o = ReadService.getSingleton(ReadServiceList.PERSON_NAME_FROM_ID.ExecuteQuery(new Object[]{id}));
            if(o == null){
                launchLoginFail("Login successful but failed to fetch Name");
                return;
            }
            String name = (String)o;
            o = ReadService.getSingleton(ReadServiceList.PERSON_ROLE_FROM_ID.ExecuteQuery(new Object[]{id}));
            if(o == null){
                launchLoginFail("Login successful but failed to fetch Role");
                return;
            }
            String role = (String)o;

            if(role.equals("DR")){
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                DoctorWindow doctorWindow = new DoctorWindow();
                doctorWindow.launch();
                return;
            }else if(role.equals("PA")){
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                PatientWindow patientWindow = new PatientWindow();
                patientWindow.launch();
                return;
            }
        }
        launchLoginFail();
    }
    void launchLoginFail(){
        JFrame frame = new JFrame();
        frame.add(new JLabel("Incorrect username or password"));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
    void launchLoginFail(String s){
        JFrame frame = new JFrame();
        frame.add(new JLabel(s));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
