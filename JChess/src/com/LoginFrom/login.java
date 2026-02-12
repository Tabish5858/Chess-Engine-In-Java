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
    ImageIcon icon  = new ImageIcon("src/Images/loginIcon.png");

    public login(JFrame parent) {
        super(parent);
        initComponents();
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

    private void initComponents() {
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Create gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(25, 25, 35),
                                                         0, getHeight(), new Color(45, 45, 65));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Title Label with icon
        JLabel titleLabel = new JLabel("♔ Chess Login ♛", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(220, 220, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(titleLabel, gbc);

        // Username Label
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(userLabel, gbc);

        // Username Field with styling
        userName = new JTextField(15) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getBackground().equals(Color.WHITE)) {
                    setBackground(new Color(240, 240, 250));
                }
                super.paintComponent(g);
            }
        };
        userName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        userName.setBackground(new Color(240, 240, 250));
        userName.setForeground(new Color(50, 50, 70));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(userName, gbc);

        // Password Label
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(passLabel, gbc);

        // Password Field with styling
        password = new JPasswordField(15);
        password.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        password.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        password.setBackground(new Color(240, 240, 250));
        password.setForeground(new Color(50, 50, 70));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(password, gbc);

        // Login Button with modern styling
        loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(30, 80, 30));
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(60, 120, 60));
                } else {
                    g2d.setColor(new Color(40, 100, 40));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                FontMetrics fm = g2d.getFontMetrics();
                Rectangle stringBounds = fm.getStringBounds(this.getText(), g2d).getBounds();
                int textX = (getWidth() - stringBounds.width) / 2;
                int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), textX, textY);
            }
        };
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(200, 45));
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 15, 15, 15);
        contentPane.add(loginButton, gbc);

        registerLabel = new JLabel("<html><u>New User? Click here to Register</u></html>");
        registerLabel.setForeground(Color.WHITE);
        registerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 15, 15, 15);
        contentPane.add(registerLabel, gbc);

        setPreferredSize(new Dimension(450, 380));
        setMinimumSize(new Dimension(450, 380));
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
