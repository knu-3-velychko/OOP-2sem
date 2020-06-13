package com.knu.exam.dao;

import com.knu.exam.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpaceDao {
    public static void getAlive(String galaxy) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.initDB();
        PreparedStatement statement = connection.prepareStatement(DBConnection.load("query1.sql"));
        statement.setString(1, galaxy);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            System.out.println("Planet " + results.getString("pname") + " has live!");
            if (results.getString("sname") != null) {
                System.out.println("\t\twith satelite " + results.getString("sname"));
            }
        }
    }

    public static void getMinRadMaxSat() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.initDB();
        PreparedStatement statement = connection.prepareStatement(DBConnection.load("query2.sql"));
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            System.out.println("Planet " + results.getString("name") + " is smallest with the biggest amount of satelites");
        }
    }

    public static void getThirdQuery() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.initDB();
        PreparedStatement statement = connection.prepareStatement(DBConnection.load("query3.sql"));
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            System.out.println("Planet " + results.getString("name") + " has max amount of satelites");
        }
    }

    public static void getHottestGalaxy() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.initDB();
        PreparedStatement statement = connection.prepareStatement(DBConnection.load("query4.sql"));
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            System.out.println("Galaxy " + results.getString("name") + " has the biggest sum of temperatures. HOT!!!");
        }
    }
}
