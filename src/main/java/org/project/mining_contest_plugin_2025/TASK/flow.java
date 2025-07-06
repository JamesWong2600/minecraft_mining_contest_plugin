package org.project.mining_contest_plugin_2025.TASK;


import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SCOREBOARD.setscore;
import org.project.mining_contest_plugin_2025.SQL.FindMaxPlayer;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;

import java.sql.*;
import java.util.Map;

import static org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025.*;

public class flow {

    public static void flow() {
        if (timer == 0) {
            status = 3;
        }
        if (status == 1) {
            Map<Player, Scoreboard> board = setscore.PrepareBoard();
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.setScoreboard(board.get(all));
            }
        }
        if (status == 2) {
            Map<Player, Scoreboard> board = setscore.ProcessingBoard();
            Scoreboard board3 = setscore.ProcessingBoardSPY();
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                if (all.getGameMode().equals(GameMode.SURVIVAL)&&(!all.isOp())) {
                    all.setScoreboard(board.get(all));
                }
                if (all.getGameMode().equals(GameMode.SPECTATOR)||all.isOp()) {
                    all.setScoreboard(board3);
                }
            }
        }
        if (status == 3) {
            Scoreboard board = setscore.EndBoard();
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.setScoreboard(board);
            }
        }
        if (timer == 1800) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "時間剩餘30分鐘，請各位繼續加油");
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
        }
        if (timer == 1200) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "時間剩餘20分鐘，請各位繼續加油");
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
        }
        if (timer == 600) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "時間剩餘10分鐘，請各位繼續加油");
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
        }
        if (timer == 300) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "時間剩餘5分鐘，請各位繼續加油");
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
        }
        if (timer == 180) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "時間剩餘3分鐘，請各位繼續加油");
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
        }
        if (timer == 60) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "時間剩餘最後1分鐘，請各位繼續加油");
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
        }
        if (timer < 10 && timer > 0) {
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.sendTitle(ChatColor.GREEN + String.valueOf(timer), "", 4, 20, 4);
                all.playSound(all.getLocation(), Sound.UI_BUTTON_CLICK, 20, 1);
            }
        }
        if (timer == 0 && end == 0) {
            String[] ranking = FindMaxPlayer.MaxPlayer();
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                //String[] SQLDATA = SQLcollection.SQL();
                if(all.isOp()){
                    all.sendTitle(ChatColor.GREEN + "比賽已經結束",ChatColor.YELLOW +"請等待主辦方公佈排名", 4, 150, 4);
                    all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                }
                else if(all.getGameMode().equals(GameMode.SURVIVAL)){
                try(Connection conn = SQLcollection.getConnection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select point from datafile where uuid = '"+all.getUniqueId()+"'");
                ) {
                    rs.next();
                    int point = rs.getInt("point");
                    all.sendTitle(ChatColor.GREEN + "你的最終得分= " + point , ChatColor.YELLOW +"請等待主辦方公佈排名", 5, 300, 5);
                    all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                    all.setGameMode(GameMode.SPECTATOR);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                }
            end = 1;
        }
}}}
