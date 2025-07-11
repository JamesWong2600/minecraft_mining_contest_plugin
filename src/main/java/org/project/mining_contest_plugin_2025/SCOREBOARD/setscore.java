package org.project.mining_contest_plugin_2025.SCOREBOARD;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.FindMaxPlayer;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class setscore {

    public static Map<Player, Scoreboard> PrepareBoard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Map<Player, Scoreboard> scoreboard = new HashMap<Player, Scoreboard>();
        //Scoreboard board = manager.getNewScoreboard();
        int tps = (int) Bukkit.getServer().getAverageTickTime();
        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            scoreboard.put(all, manager.getNewScoreboard());
            Objective objective = scoreboard.get(all).registerNewObjective("test", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.RED + Mining_contest_plugin_2025.getMain().getConfig().getString("title"));
            //String[] SQLDATA = SQLcollection.SQL();
            int playercount = 0;
            for (Player alls : Bukkit.getServer().getOnlinePlayers()) {
                if (alls.getGameMode().equals(GameMode.ADVENTURE)) {
                   playercount += 1;
                }
            }

            String serverid = Mining_contest_plugin_2025.getMain().getConfig().getString("serverid");
            int webport = Mining_contest_plugin_2025.getMain().getConfig().getInt("webport");
            String webip = Mining_contest_plugin_2025.getMain().getConfig().getString("webip");
            String check_sql = """
                INSERT INTO playercount (id, webip, webport, count)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE count = ?
            """;

            try (Connection check_conn = SQLcollection.getConnection();
                 PreparedStatement check_stmt = check_conn.prepareStatement(check_sql)) {

                check_stmt.setString(1, serverid);
                check_stmt.setString(2, webip);
                check_stmt.setInt(3, webport);
                check_stmt.setInt(4, playercount); // for the update part
                check_stmt.setInt(5, playercount);

                int affectedRows = check_stmt.executeUpdate();
                //System.out.println("Upsert complete. Rows affected: " + affectedRows);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            String sql = "SELECT SUM(count) AS total_count FROM playercount";

            try (Connection count_conn = SQLcollection.getConnection();
                 PreparedStatement count_stmt = count_conn.prepareStatement(sql)) {

                ResultSet count_rs = count_stmt.executeQuery();

                if (count_rs.next()) {
                    playercount = count_rs.getInt(1);
                    //System.out.println("Count of rows where column != 'server': " + playercount);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            int ping = all.getPing();
            UUID uuid = all.getUniqueId();
            String uid = String.valueOf(uuid);
            int[] pvpmode = new int[1];
            int[] pvpoint = new int[1];
            String[] maxpointplayer = new String[1];
            int[] maxpoint = new int[1];;
            if(!all.isOp()) {
                if (all.getGameMode().equals(GameMode.ADVENTURE)){
                try (Connection conn = SQLcollection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("select * from datafile WHERE UUID in ('" + uid + "')");
                ) {
                    ResultSet rs = stmt.executeQuery();
                    rs.next();
                    pvpmode[0] = rs.getInt("pvpmode");
                    pvpoint[0] = rs.getInt("pvppoint");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }}
            try (Connection conn = SQLcollection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("select * from datafile where pvppoint = (select max(pvppoint) from datafile)");
            ) {
                ResultSet rss = stmt.executeQuery();
                rss.next();
                maxpointplayer[0] = rss.getString("player");
                maxpoint[0] = rss.getInt("pvppoint");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Score score = objective.getScore("§" + "a" + ChatColor.WHITE + "目前狀態: lobby");
            score.setScore(8);
            //test
            Score score3 = objective.getScore("§" + "b" + ChatColor.WHITE + "玩家總數量: " + ChatColor.GREEN + playercount);
            score3.setScore(8);
            Score score6 = objective.getScore("§" + "c" + ChatColor.WHITE + "請等待主辦方開始比賽");
            score6.setScore(8);
            Score score000 = objective.getScore("§" + "d" + ChatColor.WHITE + "mspt: " + ChatColor.GREEN + tps+ ChatColor.GREEN + " 毫秒/刻(tick)");
            score000.setScore(8);
            Score scoref = objective.getScore("§" + "e" + ChatColor.WHITE + "ping: " + ChatColor.GREEN + ping + ChatColor.GREEN + " 毫秒(ms)");
            scoref.setScore(8);
            Score scoreaa = objective.getScore("§" + "f" + ChatColor.WHITE + "PVP: ");
            scoreaa.setScore(8);
            if(!all.isOp()){
                if (all.getGameMode().equals(GameMode.ADVENTURE)){
            if(pvpmode[0]==1){
            Score scoreb = objective.getScore("§" + "g" + ChatColor.WHITE + " 狀態: "+ ChatColor.GREEN +"已打開");
            scoreb.setScore(8);
            }else{
            Score scoreb = objective.getScore("§" + "h" + ChatColor.WHITE + " 狀態: "+ ChatColor.GREEN +"已關閉");
            scoreb.setScore(8);
            }
            Score scorec = objective.getScore("§" + "i" + ChatColor.WHITE + " 得分: " + ChatColor.GREEN + pvpoint[0]+ " " +ChatColor.GREEN + "分");
            scorec.setScore(8);
            }}
            Score scored = objective.getScore("§" + "j" + ChatColor.WHITE + " 最高分: "+ChatColor.GREEN + maxpointplayer[0]);
            scored.setScore(8);
        }

    return scoreboard;
    }




    public static Map<Player, Scoreboard> ProcessingBoard()
    {
    //String[] SQLDATA = SQLcollection.SQL();
    ScoreboardManager manager = Bukkit.getScoreboardManager();
    //Scoreboard Scoreboard = manager.getNewScoreboard();
    Map<Player, Scoreboard> Scoreboard = new HashMap<Player, Scoreboard>();  ;
    Mining_contest_plugin_2025.timer-=1;
    int tps = (int) Bukkit.getServer().getAverageTickTime();
    String mark;
    for(Player all : Bukkit.getServer().getOnlinePlayers())
    {
        int ping = all.getPing();
        String[] ranking = FindMaxPlayer.MaxPlayer();
        if(all.getGameMode().equals(GameMode.SURVIVAL)){
        try(Connection connn = SQLcollection.getConnection();
         PreparedStatement stmt = connn.prepareStatement("SELECT * FROM datafile where UUID in ('"+all.getUniqueId().toString()+"')");
    ) {
         ResultSet rs = stmt.executeQuery();
         rs.next();
         mark = rs.getString("point");
         Scoreboard.put(all, manager.getNewScoreboard());
         Objective objective = Scoreboard.get(all).registerNewObjective("test", "dummy");
         objective.setDisplaySlot(DisplaySlot.SIDEBAR);
         objective.setDisplayName(ChatColor.RED + Mining_contest_plugin_2025.getMain().getConfig().getString("title"));
            int playercount = 0;
            for (Player alls : Bukkit.getServer().getOnlinePlayers()) {
                if (alls.getGameMode().equals(GameMode.SURVIVAL)) {
                    playercount += 1;
                }
            }

            String serverid = Mining_contest_plugin_2025.getMain().getConfig().getString("serverid");
            int webport = Mining_contest_plugin_2025.getMain().getConfig().getInt("webport");
            String webip = Mining_contest_plugin_2025.getMain().getConfig().getString("webip");
            String check_sql = """
                INSERT INTO playercount (id, webip, webport, count)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE count = ?
            """;

            try (Connection check_conn = SQLcollection.getConnection();
                 PreparedStatement check_stmt = check_conn.prepareStatement(check_sql)) {

                check_stmt.setString(1, serverid);
                check_stmt.setString(2, webip);
                check_stmt.setInt(3, webport);
                check_stmt.setInt(4, playercount); // for the update part
                check_stmt.setInt(5, playercount);

                int affectedRows = check_stmt.executeUpdate();
                //System.out.println("Upsert complete. Rows affected: " + affectedRows);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            String sql = "SELECT SUM(count) AS total_count FROM playercount";

            try (Connection count_conn = SQLcollection.getConnection();
                 PreparedStatement count_stmt = count_conn.prepareStatement(sql)) {

                ResultSet count_rs = count_stmt.executeQuery();

                if (count_rs.next()) {
                    playercount = count_rs.getInt(1);
                    //System.out.println("Count of rows where column != 'server': " + playercount);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        //
         //for(Player alls : Bukkit.getServer().getOnlinePlayers())
         //{
         //    if(alls.getGameMode().equals(GameMode.SURVIVAL)){
         //    playercount+=1;
        //     }
        // }
       connn.close();
      Score score = objective.getScore("§" + "a" +ChatColor.WHITE+"目前狀態: 正在比賽");
      score.setScore(8);
      Score score6 = objective.getScore("§" + "b" +ChatColor.WHITE+"比賽剩餘時間: " + ChatColor.GREEN + Mining_contest_plugin_2025.timer+ ChatColor.GREEN + " 秒");
      score6.setScore(8);
      int sz = Mining_contest_plugin_2025.getMain().getConfig().getInt("border.size");
      Score score9 = objective.getScore("§" + "c" +ChatColor.WHITE+"邊界: "+ChatColor.GREEN+sz+ ChatColor.GREEN + " 格");
      score9.setScore(8);
      //Score score0 = objective.getScore("§" + "d" +ChatColor.WHITE + "排行榜");
      //score0.setScore(8);
        //Score r1 = objective.getScore("§" + "e" +ChatColor.WHITE+ " "+"1st: " + ChatColor.GREEN + ranking[1] + ChatColor.GREEN + " 分");
        //r1.setScore(8);
        //Score r2 = objective.getScore("§" + "f" +ChatColor.WHITE+ " "+"2nd: " +ChatColor.GREEN + ranking[3]+ ChatColor.GREEN + " 分");
        //r2.setScore(8);
        //Score r3 = objective.getScore("§" + "g" +ChatColor.WHITE+ " "+"3rd: " + ChatColor.GREEN + ranking[5]+ ChatColor.GREEN + " 分");
        //r3.setScore(8);
        //Score r4 = objective.getScore("§" + "h" +ChatColor.WHITE+ " "+"4th: " + ChatColor.GREEN + ranking[7]+ ChatColor.GREEN + " 分");
        //r4.setScore(8);
        //Score r5 = objective.getScore("§" + "i" +ChatColor.WHITE + " "+"5th: " + ChatColor.GREEN + ranking[9]+ ChatColor.GREEN + " 分");
        //r5.setScore(8);
        Score score7 = objective.getScore("§" + "d" +ChatColor.WHITE+"你的分數: " + ChatColor.GREEN + mark + ChatColor.GREEN + " 分");
        score7.setScore(8);
        Score score3 = objective.getScore("§" + "e" +ChatColor.WHITE+"玩家總數量: " + ChatColor.GREEN + playercount+ ChatColor.GREEN + " 條");
        score3.setScore(8);
        Score score000 = objective.getScore("§" + "f" +ChatColor.WHITE+"mspt: " + ChatColor.GREEN + tps + ChatColor.GREEN + " 毫秒/刻(tick)");
        score000.setScore(8);
        Score scorem = objective.getScore("§" + "g" +ChatColor.WHITE+"ping: " + ChatColor.GREEN + ping + ChatColor.GREEN + " 毫秒(ms)");
        scorem.setScore(8);
        Score score00 = objective.getScore("§" + "h" +ChatColor.WHITE+"祝你游玩愉快");
        score00.setScore(8);

         } catch (SQLException e) {
        }
    }} return Scoreboard;
    }

    public static Scoreboard ProcessingBoardSPY()
    {
        String[] ranking = FindMaxPlayer.MaxPlayer();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.RED + Mining_contest_plugin_2025.getMain().getConfig().getString("title"));
        int playercount = 0;
        int tps = (int) Bukkit.getServer().getAverageTickTime();
        //for(Player alls : Bukkit.getServer().getOnlinePlayers())
        //{
        //    if(alls.getGameMode().equals(GameMode.SURVIVAL)){
       //         playercount+=1;
       //     }
       // }
        for (Player alls : Bukkit.getServer().getOnlinePlayers()) {
            if (alls.getGameMode().equals(GameMode.SURVIVAL)) {
                playercount += 1;
            }
        }

        String serverid = Mining_contest_plugin_2025.getMain().getConfig().getString("serverid");
        int webport = Mining_contest_plugin_2025.getMain().getConfig().getInt("webport");
        String webip = Mining_contest_plugin_2025.getMain().getConfig().getString("webip");
        String check_sql = """
                INSERT INTO playercount (id, webip, webport, count)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE count = ?
            """;

        try (Connection check_conn = SQLcollection.getConnection();
             PreparedStatement check_stmt = check_conn.prepareStatement(check_sql)) {

            check_stmt.setString(1, serverid);
            check_stmt.setString(2, webip);
            check_stmt.setInt(3, webport);
            check_stmt.setInt(4, playercount); // for the update part
            check_stmt.setInt(5, playercount);

            int affectedRows = check_stmt.executeUpdate();
            //System.out.println("Upsert complete. Rows affected: " + affectedRows);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "SELECT SUM(count) AS total_count FROM playercount";

        try (Connection count_conn = SQLcollection.getConnection();
             PreparedStatement count_stmt = count_conn.prepareStatement(sql)) {

            ResultSet count_rs = count_stmt.executeQuery();

            if (count_rs.next()) {
                playercount = count_rs.getInt(1);
                //System.out.println("Count of rows where column != 'server': " + playercount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Score score = objective.getScore("§" + "a" + ChatColor.WHITE +"目前狀態: 正在比賽");
        score.setScore(8);
        Score score6 = objective.getScore("§" + "b" +ChatColor.WHITE +"比賽剩餘時間: " + ChatColor.GREEN + Mining_contest_plugin_2025.timer+ ChatColor.GREEN + " 秒");
        score6.setScore(8);
        int sz = Mining_contest_plugin_2025.getMain().getConfig().getInt("border.size");
        Score score9 = objective.getScore("§" + "c" + ChatColor.WHITE + "邊界: "+ChatColor.GREEN+sz+ ChatColor.GREEN + " 格(block)");
        score9.setScore(8);
        Score score0 = objective.getScore("§" + "d" +ChatColor.WHITE+ "排行榜");
        score0.setScore(8);
        Score r1 = objective.getScore("§" + "e" +ChatColor.WHITE+ " "+"1st: " + ChatColor.GREEN +ranking[0] +" "+ ChatColor.GREEN +ranking[1]+ ChatColor.GREEN + " 分");
        r1.setScore(8);
        Score r2 = objective.getScore("§" + "f" +ChatColor.WHITE+ " "+"2nd: " + ChatColor.GREEN +ranking[2] +" "+ChatColor.GREEN +ranking[3]+ ChatColor.GREEN + " 分");
        r2.setScore(8);
        Score r3 = objective.getScore("§" + "g" +ChatColor.WHITE+ " "+"3rd: " + ChatColor.GREEN +ranking[4] +" "+ ChatColor.GREEN + ranking[5]+ ChatColor.GREEN + " 分");
        r3.setScore(8);
        Score r4 = objective.getScore("§" + "h" +ChatColor.WHITE+ " "+"4th: " + ChatColor.GREEN +ranking[6] +" "+ ChatColor.GREEN + ranking[7]+ ChatColor.GREEN + " 分");
        r4.setScore(8);
        Score r5 = objective.getScore("§" + "i" +ChatColor.WHITE+ " "+"5th: " + ChatColor.GREEN +ranking[8] +" "+ ChatColor.GREEN + ranking[9]+ ChatColor.GREEN + " 分");
        r5.setScore(8);
        Score r6 = objective.getScore("§" + "j" +ChatColor.WHITE+ " "+"6th: " + ChatColor.GREEN +ranking[10] +" "+ ChatColor.GREEN + ranking[11]+ ChatColor.GREEN + " 分");
        r6.setScore(8);
        Score r7 = objective.getScore("§" + "k" +ChatColor.WHITE+ " "+"7th: " + ChatColor.GREEN +ranking[12] +" "+ ChatColor.GREEN + ranking[13]+ ChatColor.GREEN + " 分");
        r7.setScore(8);
        Score r8 = objective.getScore("§" + "l" +ChatColor.WHITE+ " "+"8th: " + ChatColor.GREEN +ranking[14] +" "+ ChatColor.GREEN + ranking[15]+ ChatColor.GREEN + " 分");
        r8.setScore(8);
        Score score3 = objective.getScore("§" + "m" +ChatColor.WHITE +"玩家總數量: " + ChatColor.GREEN + playercount+ ChatColor.GREEN + " 條");
        score3.setScore(8);
        Score score000 = objective.getScore("§" + "n" +ChatColor.WHITE+"mspt: " + ChatColor.GREEN + tps+ ChatColor.GREEN + " 毫秒/刻(tick)");
        score000.setScore(8);
        Score score2 = objective.getScore("§" + "o" +ChatColor.WHITE +"管理員視角");
        score2.setScore(8);
        return board;
    }


    public static Scoreboard EndBoard()
    {
        String[] ranking = FindMaxPlayer.MaxPlayer();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.RED + Mining_contest_plugin_2025.getMain().getConfig().getString("title"));
        Score score = objective.getScore("§" + "a" +ChatColor.WHITE +"目前狀態: 比賽結束");
        score.setScore(8);
        Score score0 = objective.getScore("§" + "b" +ChatColor.WHITE + "請等待主辦方公佈成績");
        score0.setScore(8);
        Score score9 = objective.getScore("§" + "c" +ChatColor.WHITE+ "感謝參與");
        score9.setScore(8);

        return board;
    }

}
