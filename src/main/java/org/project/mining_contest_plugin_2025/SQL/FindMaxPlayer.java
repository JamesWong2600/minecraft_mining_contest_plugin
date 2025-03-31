package org.project.mining_contest_plugin_2025.SQL;

import java.sql.*;

public class FindMaxPlayer {
    public static String[] ranking = new String[16];
    public static String[] MaxPlayer()
    {
        //String[] SQLDATA = SQLcollection.SQL();

        try(Connection connn = SQLcollection.getConnection();
            PreparedStatement stmt = connn.prepareStatement("SELECT player, point FROM datafile ORDER BY point DESC LIMIT 8");
        ) {
            ResultSet rs = stmt.executeQuery();
            int index = 0;
            while (rs.next() && index < 16) {
                ranking[index++] = rs.getString("player"); // 取得玩家名稱
                ranking[index++] = String.valueOf(rs.getInt("point")); // 取得積分
            }
            return ranking;

        } catch (SQLException e) {

        }


        return null;
    }


}
