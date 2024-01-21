package com.LoginFrom;

import java.sql.*;

public class customer {


    static Connection con;


    //    🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿 Making new Connection 🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿🧿
    public void connect() {
        String path = "jdbc:mysql://localhost/chess";
        String user = "root";
        String password = "";
        try {
            con = DriverManager.getConnection(path, user, password);
            System.out.println("Connection Created");

        } catch (SQLException e) {
            System.out.println(e);
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


