package org.project.mining_contest_plugin_2025.SQL;


import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLcollection {

    public static String[] SQL()
    {
        String ip = Mining_contest_plugin_2025.getMain().getConfig().getString("SQL.ip");
        String table = Mining_contest_plugin_2025.getMain().getConfig().getString("SQL.database");
        String user = Mining_contest_plugin_2025.getMain().getConfig().getString("SQL.user");
        String password = Mining_contest_plugin_2025.getMain().getConfig().getString("SQL.password");
        String[] SQLDATA = new String[4];
        SQLDATA[0] = "com.mysql.jdbc.Driver";
        SQLDATA[1] = "jdbc:mysql://" + ip + "/" + table;
        SQLDATA[2] = user;
        SQLDATA[3] = password;
        return SQLDATA;
    }

    public static String time()
    {
        String times = Mining_contest_plugin_2025.getMain().getConfig().getString("time");
        return times;
    }
    public static void SQLCONNECT()
    {
        Mining_contest_plugin_2025.getMain().saveDefaultConfig();
        String[] SQLDATA = SQL();
        Connection conn = null;
        try {
            //Register the JDBC driver
            Class.forName(SQLDATA[0]);
            //Open the connection
            conn = DriverManager.
                    getConnection(SQLDATA[1], SQLDATA[2], SQLDATA[3]);
            if (conn != null) {
                System.out.println("Successfully connected.");
            } else {
                System.out.println("Failed to connect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
