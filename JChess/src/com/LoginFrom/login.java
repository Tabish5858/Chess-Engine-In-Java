package com.LoginFrom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class login extends JDialog {
    private JPanel contentPane;
    private JTextField userName;
    private JPasswordField password;
    private JButton loginButton;
    private JLabel registerLabel;
    private boolean isLoginButtonClicked;
    private static String playerOneName;
    ImageIcon icon  = new ImageIcon("C:\\Users\\tabis\\OneDrive\\Desktop\\JChess\\src\\Images\\loginIcon.png");
    public login(JFrame parent) {
        super(parent);
        setDefaultLookAndFeelDecorated(true);
        setContentPane(contentPane);
        setModal(true);
//        setLocationRelativeTo(null);
        setLocation(450,100);
        setTitle("Login");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImage(icon.getImage());

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onRegisterLabelClicked();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setForeground(Color.WHITE);
                registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setForeground(Color.BLUE);
                registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        loginButton.addActionListener(e -> {
            customer c = new customer();
            c.connect();
            String username = userName.getText();
            char[] passwordChars = password.getPassword();
            String password = new String(passwordChars);
            int i = c.select(username, password);
            if (i == -1) {
                JOptionPane.showMessageDialog(contentPane, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(i==-2) {
                JOptionPane.showMessageDialog(contentPane, "The Username or Password can't be Empty","Invalid Inputs",JOptionPane.ERROR_MESSAGE);
            }
            else {
                dispose();
                System.out.println("Welcome Back : "+username);
                Home_open();
                isLoginButtonClicked = true;
                playerOneName=username;
            }
        });
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                super.mouseClicked(e);
                loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                loginButton.setBackground(Color.WHITE);
                loginButton.setForeground(Color.BLACK);

            }
            @Override
            public void mouseExited(final MouseEvent e) {
                super.mouseClicked(e);
                loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                loginButton.setBackground(Color.BLACK);
                loginButton.setForeground(Color.WHITE);
            }
        });
    }
    public void Home_open() {
        dispose();
        home h = new home();
        h.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        h.pack();
        h.setVisible(true);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                login frame = new login(null);
                setDefaultLookAndFeelDecorated(true);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                System.out.println("Login frame is not working!");
            }
        });
    }
    private void onRegisterLabelClicked() {
        dispose();
        new registerForm(new JFrame());
        dispose();
    }
    public boolean isLoginButtonClicked() {
        return isLoginButtonClicked;
    }
    public static String getPlayerOneName(){
        return playerOneName;
    }
}
