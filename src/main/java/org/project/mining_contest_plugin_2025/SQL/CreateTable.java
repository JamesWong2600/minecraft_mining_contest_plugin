package org.project.mining_contest_plugin_2025.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void SQLCreateTable()
    {
        String[] SQLDATA = SQLcollection.SQL();
        try(Connection connn = DriverManager.getConnection(SQLDATA[1], SQLDATA[2], SQLDATA[3]);
            Statement stmt = connn.createStatement();
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
            //String data = "CREATE TABLE datafile " +
               //     "(id INTEGER not NULL, " +
               //     " player VARCHAR(255), " +
               //     " UUID VARCHAR(255), " +
               //     " point FLOAT, " +
               //     " tp INTEGER, " +
                //    " pvppoint INTEGER, " +
                //    " pvpmode INTEGER, " +
                 //   " PRIMARY KEY ( id ))";
            System.out.println("Inserted records into the table...");
            System.out.println("Created table in given database...");
            stmt.executeUpdate(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
}
