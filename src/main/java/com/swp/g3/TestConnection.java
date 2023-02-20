package com.swp.g3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    private static Connection conn;
    public static void getConnection() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String user = "g3";
        String pass = "123456";

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=SWP_G3;encrypt=true;trustServerCertificate=true", user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (conn == null) {
            System.out.println("ket noi that bai");
        } else {
            System.out.println("ket noi thanh cong");
        }
    }

    public static void main(String[] args) {
        getConnection();
    }
        }


