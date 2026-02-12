package com.LoginFrom;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


public class home extends JFrame {
    private JPanel welcomePanel;

    home() {
        initComponents();
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

    private void initComponents() {
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.setBackground(new Color(45, 45, 45));
        welcomePanel.setPreferredSize(new Dimension(400, 200));

        JLabel welcomeLabel = new JLabel("Welcome to Chess!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
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
