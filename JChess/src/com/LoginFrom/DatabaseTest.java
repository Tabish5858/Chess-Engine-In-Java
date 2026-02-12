package com.LoginFrom;

public class DatabaseTest {
    public static void main(String[] args) {
        customer c = new customer();
        c.connect();
        System.out.println("Database connection test completed!");
    }
}
