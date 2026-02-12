package com.LoginFrom;

import java.sql.*;

public class customer {


    static Connection con;


    //    🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿 Making new Connection 🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿
    public void connect() {
        String path = "jdbc:sqlite:chess.db";
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(path);
            System.out.println("Connection Created");
            initializeTables();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }

    }

    private void initializeTables() {
        try {
            Statement s = con.createStatement();

            // Create register table
            String createRegisterTable = "CREATE TABLE IF NOT EXISTS register (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "username TEXT NOT NULL UNIQUE, " +
                    "password TEXT NOT NULL)";
            s.executeUpdate(createRegisterTable);

            // Create player table
            String createPlayerTable = "CREATE TABLE IF NOT EXISTS player (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "username TEXT NOT NULL UNIQUE, " +
                    "password TEXT NOT NULL, " +
                    "elo INTEGER DEFAULT 1200)";
            s.executeUpdate(createPlayerTable);

            // Insert default test user if not exists
            String checkUser = "SELECT COUNT(*) FROM player WHERE username = 'test'";
            ResultSet rs = s.executeQuery(checkUser);
            rs.next();
            if (rs.getInt(1) == 0) {
                String insertUser = "INSERT INTO player(name, username, password) VALUES('Test User', 'test', 'test')";
                s.executeUpdate(insertUser);
                System.out.println("Default test user created (username: test, password: test)");
            }

            System.out.println("Database tables initialized");
        } catch (SQLException e) {
            System.out.println("Error initializing tables: " + e);
        }
    }


//    🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿 Inserting Data in DataBase 🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿

    public void insert(String name, String userName, int password) {

        String query = "INSERT INTO register(name,username,password)VALUES('" + name + "','" + userName + "','" + password + "')";
        try {
            Statement s = con.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

//    🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿 Deleting Data in DataBase 🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿

    public void delete(int id) {
        String query = "DELETE FROM customer WHERE player_id=" + id;

        try {
            Statement s;
            s = con.createStatement();
            s.executeUpdate(query);
            System.out.println("Data Deleted from Row " + id);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//    🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿 UPDATE Data in DataBase 🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿

    public void update() {
        String query = "UPDATE chess SET name='Papu' WHERE player_id=2";

        try {
            Statement s;
            s = con.createStatement();
            s.executeUpdate(query);
            System.out.println("Data Deleted from Row ");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//    🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿 SELECT Data in DataBase 🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿


    //    public int select(String user, String pass){
////        String query = "SELECT * FROM customer ";
//        if(user.equals("")||pass.equals("")) {
//            return -2;
//        }
//        String query = "SELECT * FROM register WHERE userName='"+user+"' AND password='"+pass+"'";
////        System.out.println(query);
//        try {
//            Statement s = con.createStatement();
//            ResultSet rs = s.executeQuery(query);
//            if(!rs.next()) {
//                return -1;
//            }
//            while (rs.next()){
//                System.out.println(rs.getString(1) +"   "+rs.getString(2)+"   " +rs.getString(3)+"   "+rs.getString(4) );
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return 0;
//    }
    public int select(String Username, String Password) {
        if (Username.equals("") || Password.equals("")) {
            return -2;
        }
        String query = "SELECT * FROM player Where username='" + Username + "' AND password='" + Password + "'";
        try {
            Statement s= con.createStatement();
            ResultSet rs = s.executeQuery(query);
            if (!rs.next()) {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}


