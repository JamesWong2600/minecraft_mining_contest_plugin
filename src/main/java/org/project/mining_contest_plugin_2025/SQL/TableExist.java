package org.project.mining_contest_plugin_2025.SQL;

import java.sql.*;

public class TableExist {
    public static void Tableexist()
    {
        String[] SQLDATA = SQLcollection.SQL();
        try(Connection connn = DriverManager.getConnection(SQLDATA[1], SQLDATA[2], SQLDATA[3]);
            Statement stmt = connn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM datafile");
        ) {
             rs.next();
        } catch (SQLException e) {
            CreateTable.SQLCreateTable();
        }
}
}
