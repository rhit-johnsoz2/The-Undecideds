package com.undecideds.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        panel = new JPanel(new GridLayout(5, 1));
        panel.add(user_label);
        panel.add(username_text);
        panel.add(password_label);
        panel.add(password_text);
        message = new JLabel();
        panel.add(message);
        panel.add(submit);
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
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    void launchActivity(){
        String username = username_text.getText();
        String password = password_text.getText();
        if (username.trim().equals("doctor") && password.trim().equals("doctor")) {
            // TODO: launch doctorView
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } else if (username.trim().equals("patient") && password.trim().equals("patient")) {
            // TODO: launch patientView
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } else if(username.trim().equals("admin") && password.trim().equals("admin")){
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            AdminWindow adminWindow = new AdminWindow();
            adminWindow.launch();
        } else {
            message.setText(" Invalid user.. ");
        }
    }
}
