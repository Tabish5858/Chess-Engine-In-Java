package com.LoginFrom;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


public class home extends JFrame {
    private JPanel welcomePanel;

    home() {
        setTitle("Welcome Back!");
        setContentPane(welcomePanel);
        setLocation(550,300);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dispose();
            }
        }, 2000);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                home frame = new home();
//                frame.setLocationRelativeTo(null);
                frame.pack();
                frame.setVisible(true);
            } catch (Exception e) {
                System.out.println("Home Frame Is Not Working!");
            }
        });
    }
}
