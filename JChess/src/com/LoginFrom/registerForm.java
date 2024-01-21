package com.LoginFrom;

import com.chess.JChess;
import com.chess.wp_chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class registerForm extends JDialog {

    ImageIcon icon = new ImageIcon("C:\\Users\\tabis\\OneDrive\\Desktop\\JChess\\src\\Images\\registerIcon.png");
    private JPanel registerPanel;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JPasswordField cPasswordField;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> dateComboBox;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JCheckBox beginnerCB;
    private JCheckBox masterCB;
    private JCheckBox gmCB;
    private JSlider eloSlider;
    private JButton registerButton;
    private JButton cancelButton;
    private JComboBox<String> countryComboBo;
    private JCheckBox intermediateCheckBox;
    private JList countryList;
    private JLabel fName;
    private JLabel elo;
    private JLabel chessLevel;
    private JLabel dateOfBirth;
    private JLabel email;
    private JLabel userName;
    private JLabel gender;
    private JLabel address;
    private JLabel country;
    private JLabel qualification;
    private JLabel experience;
    private JLabel countryLabel;
    private JLabel password;
    private JList countryComboBox;
    private JLabel cPassword;
    private JLabel fullName;
    private JLabel backToLogin;

    public registerForm(JFrame parent) {
        super(parent);
        createUIComponents();
        setTitle("Create a New Account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(950, 700));
        setPreferredSize(new Dimension(950, 700));
        setMaximumSize(new Dimension(950, 700));
        setLocationRelativeTo(parent);
//        setModal(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        this.setIconImage(icon.getImage());

        nameField.setCaretColor(Color.WHITE);
        userNameField.setCaretColor(Color.WHITE);
        emailField.setCaretColor(Color.WHITE);
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

    public static void main(String[] args) {
        new registerForm(null);
    }

    private String getSelectedQualification() {
        if (gmCB.isSelected()) {
            return "Grandmaster";
        } else if (masterCB.isSelected()) {
            return "Master";
        } else if (intermediateCheckBox.isSelected()) {
            return "Intermediate";
        } else if (beginnerCB.isSelected()) {
            return "Beginner";
        } else {
            return "No Qualification";
        }
    }

    public void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String userName = userNameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(cPasswordField.getPassword());
        String date = String.valueOf(dateComboBox.getSelectedItem());
        String month = String.valueOf(monthComboBox.getSelectedItem());
        String year = String.valueOf(yearComboBox.getSelectedItem());
        String myCountry = String.valueOf(countryComboBo.getSelectedItem());
        String elo = String.valueOf(eloSlider.getValue());
        String dob = month + "/" + date + "/" + year;
        String gender = (maleRadioButton.isSelected()) ? "Male" : "Female";
        String chessLevel = getSelectedQualification();

        if (date.isEmpty() || month.isEmpty() || year.isEmpty() || myCountry.isEmpty() ||
                name.isEmpty() || email.isEmpty() || userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please Enter All The Fields!",
                    "Try Again!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password Doesn't Match!",
                    "Try Again!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        int i=addUserToDataBase(name, email, userName, password, dob, gender, myCountry, chessLevel, elo);
//        addUserToDataBasePerformance(userName,elo,0,0,0,0);
        if(i==1){
            JOptionPane.showMessageDialog(this,
                    "Successfully registered new user: " + name,
                    "Welcome!",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Successful registration of: " + name);
            JChess.showLoginDialog();
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Can not Insert User(Open Xampp And Run Server First)!",
                    "Try Again!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

    }

    private int addUserToDataBase(String name, String email, String userName, String password, String dob, String gender, String country, String chessLevel, String elo) {
        String path = "jdbc:mysql://localhost/chess";
        String userPath = "root";
        String Password = "";
        try {
            Connection con = DriverManager.getConnection(path, userPath, Password);
            Statement stmt = con.createStatement();

            String sql = "INSERT INTO player(name, email, username, password, dateOfBirth, gender, country, chessLevel, elo,played,won,lost,draw) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, dob);
            preparedStatement.setString(6, gender);
            preparedStatement.setString(7, country);
            preparedStatement.setString(8, chessLevel);
            preparedStatement.setString(9, elo);
            preparedStatement.setString(10, "0");
            preparedStatement.setString(11, "0");
            preparedStatement.setString(12, "0");
            preparedStatement.setString(13, "0");

            preparedStatement.executeUpdate();

            stmt.close();
            con.close();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception
            JOptionPane.showMessageDialog(this,
                    "Error inserting data into the database. Please try again.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }
    private void createUIComponents() {
        String[] countryList = {
                "Pakistan", "Saudi Arabia", "Bangladesh", "America", "Iran", "Iraq", "Turkey", "Afghanistan", "China",
                "Qatar", "Egypt", "India", "Brazil", "Canada", "France", "Germany", "Italy", "Japan", "Mexico", "Russia",
                "South Korea", "Spain", "United Kingdom", "Australia", "Argentina", "Colombia", "South Africa", "Nigeria",
                "Kenya", "Ghana", "Morocco", "Peru", "Chile", "Singapore", "Malaysia", "Thailand", "Indonesia", "Philippines",
                "Vietnam", "New Zealand", "Netherlands", "Belgium", "Switzerland", "Sweden", "Norway", "Denmark", "Finland",
                "Portugal", "Ireland"
        };
        countryComboBo = new JComboBox<>(countryList);

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = Integer.toString(i + 1);
        }
        String[] years = new String[123];
        for (int i = 0; i < 123; i++) {
            years[i] = Integer.toString(1970 + i);
        }

        monthComboBox = new JComboBox<>(months);
        dateComboBox = new JComboBox<>(days);
        yearComboBox = new JComboBox<>(years);
        ButtonGroup genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);
        ButtonGroup group = new ButtonGroup();
        group.add(maleRadioButton);
        group.add(femaleRadioButton);


        ButtonGroup level = new ButtonGroup();
        level.add(beginnerCB);
        level.add(intermediateCheckBox);
        level.add(masterCB);
        level.add(gmCB);
    }
}
