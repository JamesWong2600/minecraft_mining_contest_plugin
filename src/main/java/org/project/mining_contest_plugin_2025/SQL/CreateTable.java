package org.project.mining_contest_plugin_2025.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void SQLCreateTable()
    {
       // String[] SQLDATA = SQLcollection.SQL();
        try(Connection connn = SQLcollection.getConnection();
            Statement stmt = connn.createStatement();
            Statement stmt2 = connn.createStatement();
        ) {
            String data = "CREATE TABLE datafile " +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    " player VARCHAR(255), " +
                    " UUID VARCHAR(255), " +
                    " point INTEGER, " +
                    " tp INTEGER, " +
                    " pvppoint INTEGER, " +
                    " server INTEGER, " +
                    " pvpmode INTEGER)";
            String data2 = "CREATE TABLE playercount " +
                      "(id INTEGER PRIMARY KEY, " +
                      " webport INTEGER, " +
                      " count INTEGER)";
            System.out.println("Inserted records into the table...");
            System.out.println("Created table in given database...");
            stmt.executeUpdate(data);
            stmt2.executeUpdate(data2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
}
