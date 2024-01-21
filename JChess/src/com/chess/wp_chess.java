package com.chess;

import com.LoginFrom.login;
import com.LoginFrom.registerForm;
import com.chess.gui.Table;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;
import java.util.Timer;
import java.util.TimerTask;

import static java.awt.EventQueue.invokeLater;

public class wp_chess extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    ImageIcon icon = new ImageIcon("C:\\Users\\tabis\\OneDrive\\Desktop\\JChess\\src\\Images\\frameLogoBottom.png");

    public wp_chess() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 720, 414);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);
        this.setIconImage(icon.getImage());

        JButton btnRegister = new JButton("Register");
        btnRegister.setFocusable(false);
        btnRegister.setForeground(Color.CYAN);
        btnRegister.setFont(new Font("Segoe UI Variable", Font.PLAIN, 18));
        btnRegister.setBackground(Color.BLACK);
        btnRegister.setBounds(456, 204, 143, 40);
        contentPane.add(btnRegister);

        btnRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onRegisterLabelClicked();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnRegister.setForeground(Color.WHITE);
                btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRegister.setForeground(Color.CYAN);
                btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        JButton btnNewButton = new JButton("Login");
        btnNewButton.setFocusable(false);
        btnNewButton.setBackground(new Color(0, 0, 0));
        btnNewButton.setForeground(Color.CYAN);
        btnNewButton.setFont(new Font("Segoe UI Variable", Font.PLAIN, 18));
        btnNewButton.setBounds(107, 204, 143, 40);
        contentPane.add(btnNewButton);
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                login loginDialog = new login(null);
                setDefaultLookAndFeelDecorated(true);
                loginDialog.pack();
                loginDialog.setLocationRelativeTo(null);
                loginDialog.setVisible(true);
                if (loginDialog.isLoginButtonClicked()) {
                    SwingUtilities.invokeLater(() -> {
                        java.util.Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Table table = new Table();
                                table.getGameFrame().dispose();
                                table.getGameFrame().setVisible(true);
                            }
                        }, 2000);
                    });
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnNewButton.setForeground(Color.WHITE);
                btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNewButton.setForeground(Color.CYAN);
                btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\tabis\\OneDrive\\Desktop\\JChess\\src\\Images\\chessFrame.png"));
        lblNewLabel.setBounds(10, 10, 686, 357);
        contentPane.add(lblNewLabel);

    }

    public static void main(String[] args) {
        invokeLater(() -> {
            try {
                wp_chess chess = new wp_chess();
                chess.setVisible(true);
            } catch (Exception e) {
                System.out.println("Wp-Chess not working!");
            }
        });
    }

    private void onRegisterLabelClicked() {
        dispose();
        new registerForm(new JFrame());
        dispose();
    }
}
