package com.chess;

import com.LoginFrom.login;
import com.chess.gui.Table;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class JChess extends JFrame{
    public JChess(){
        dispose();
    }
    public static void main(String[] args) {
        setDefaultLookAndFeelDecorated(true);
        showLoginDialog();
    }
    public static void showLoginDialog() {

        login loginDialog = new login(null);
        setDefaultLookAndFeelDecorated(true);
        loginDialog.pack();
        loginDialog.setLocationRelativeTo(null);
        loginDialog.setVisible(true);
        if (loginDialog.isLoginButtonClicked()) {
            SwingUtilities.invokeLater(() -> {
              Timer timer = new Timer();
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
}
