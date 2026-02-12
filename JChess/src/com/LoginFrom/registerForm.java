package com.LoginFrom;

import com.chess.JChess;
import com.chess.wp_chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class registerForm extends JDialog {

    ImageIcon icon = new ImageIcon("src/Images/registerIcon.png");
    private JPanel registerPanel;
    private JTextField nameField;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JPasswordField cPasswordField;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel backToLogin;

    public registerForm(JFrame parent) {
        super(parent);
        initComponents();
        setTitle("Create a New Account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(480, 420));
        setPreferredSize(new Dimension(480, 420));
        setMaximumSize(new Dimension(480, 420));
        setLocationRelativeTo(parent);
//        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        this.setIconImage(icon.getImage());

        nameField.setCaretColor(Color.WHITE);
        userNameField.setCaretColor(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        cPasswordField.setCaretColor(Color.WHITE);
        registerButton.addActionListener(e -> {
            registerUser();
        });
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
        backToLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                wp_chess wp = new wp_chess();
                wp.setLocationRelativeTo(null);
                JFrame.setDefaultLookAndFeelDecorated(true);
                wp.setVisible(true);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                backToLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backToLogin.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    private void initComponents() {
        registerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Create gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(20, 30, 45),
                                                         0, getHeight(), new Color(40, 50, 75));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);

        // Title Label with chess pieces
        JLabel titleLabel = new JLabel("♖ Create New Account ♜", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(220, 220, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        registerPanel.add(titleLabel, gbc);

        // Name Label with icon
        JLabel nameLabel = new JLabel("📝 Full Name:");
        nameLabel.setForeground(new Color(200, 200, 220));
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        registerPanel.add(nameLabel, gbc);

        // Name Field with enhanced styling
        nameField = createStyledTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        registerPanel.add(nameField, gbc);

        // Username Label with icon
        JLabel userLabel = new JLabel("👤 Username:");
        userLabel.setForeground(new Color(200, 200, 220));
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        registerPanel.add(userLabel, gbc);

        // Username Field with enhanced styling
        userNameField = createStyledTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        registerPanel.add(userNameField, gbc);

        // Password Label with icon
        JLabel passLabel = new JLabel("🔒 Password:");
        passLabel.setForeground(new Color(200, 200, 220));
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        registerPanel.add(passLabel, gbc);

        // Password Field with enhanced styling
        passwordField = createStyledPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        registerPanel.add(passwordField, gbc);

        // Confirm Password Label with icon
        JLabel cPassLabel = new JLabel("🔍 Confirm Password:");
        cPassLabel.setForeground(new Color(200, 200, 220));
        cPassLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        registerPanel.add(cPassLabel, gbc);

        // Confirm Password Field with enhanced styling
        cPasswordField = createStyledPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        registerPanel.add(cPasswordField, gbc);

        // Buttons Panel with enhanced styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        registerButton = createStyledButton("✓ Register", new Color(40, 120, 40), new Color(60, 140, 60));
        cancelButton = createStyledButton("✗ Cancel", new Color(120, 40, 40), new Color(140, 60, 60));

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 15, 15, 15);
        registerPanel.add(buttonPanel, gbc);

        // Back to Login Label with better styling
        backToLogin = new JLabel("<html><u>Already have an account? Back to Login</u></html>");
        backToLogin.setForeground(Color.WHITE);
        backToLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        backToLogin.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 15, 15, 15);
        registerPanel.add(backToLogin, gbc);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(18);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(new Color(240, 240, 250));
        field.setForeground(new Color(50, 50, 70));
        field.setCaretColor(new Color(100, 100, 120));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(18);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(new Color(240, 240, 250));
        field.setForeground(new Color(50, 50, 70));
        field.setCaretColor(new Color(100, 100, 120));
        return field;
    }

    private JButton createStyledButton(String text, Color normalColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                Color bgColor;
                if (getModel().isPressed()) {
                    bgColor = normalColor.darker();
                } else if (getModel().isRollover()) {
                    bgColor = hoverColor;
                } else {
                    bgColor = normalColor;
                }

                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                FontMetrics fm = g2d.getFontMetrics();
                Rectangle stringBounds = fm.getStringBounds(this.getText(), g2d).getBounds();
                int textX = (getWidth() - stringBounds.width) / 2;
                int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
                g2d.drawString(getText(), textX, textY);
            }
        };
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        new registerForm(null);
    }

    public void registerUser() {
        String name = nameField.getText();
        String userName = userNameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(cPasswordField.getPassword());

        if (name.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            customer c = new customer();
            c.connect();

            // Hash password for better security (simple conversion to int for now)
            int passwordHash = password.hashCode();

            c.insert(name, userName, passwordHash);

            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();

            // Open login window
            new login(null).setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
