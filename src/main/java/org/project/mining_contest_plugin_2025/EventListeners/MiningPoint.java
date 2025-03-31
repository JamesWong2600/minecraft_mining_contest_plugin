package org.project.mining_contest_plugin_2025.EventListeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

public class MiningPoint implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        UUID UUid = p.getUniqueId();
        Block block = event.getBlock();
        //String[] SQLDATA = org.project.mining_contest_plugin_2025.SQL.SQLcollection.SQL();
        String sqldata = null;
        Random random = new Random();
        if(Mining_contest_plugin_2025.status==2){
        try(Connection conn = SQLcollection.getConnection();
            Statement stmt = conn.createStatement()
        ) {
            if (block.getType() == Material.DIAMOND_ORE || block.getType() == Material.DEEPSLATE_DIAMOND_ORE) {
                int min = 35; // 最小值
                int max = 50; // 最大值
                int randompoint = random.nextInt(min, max);
                sqldata = "UPDATE datafile " +
                        "SET point = point + "+randompoint+" WHERE UUID in ('"+UUid+"')";
                p.sendMessage(ChatColor.GREEN + "鑽石+"+randompoint+"分");
            }

            if (block.getType() == Material.IRON_ORE || block.getType() == Material.DEEPSLATE_IRON_ORE) {
                int min = 3; // 最小值
                int max = 5; // 最大值
                int randompoint = random.nextInt(min, max);
                sqldata = "UPDATE datafile " +
                        "SET point = point + "+randompoint+" WHERE UUID in ('"+UUid+"')";
                p.sendMessage(ChatColor.GREEN + "鐵礦+"+randompoint+"分");
            }
            if (block.getType() == Material.COAL_ORE || block.getType() == Material.DEEPSLATE_COAL_ORE) {
                int min = 0; // 最小值
                int max = 2; // 最大值
                int randompoint = random.nextInt(min, max);
                sqldata = "UPDATE datafile " +
                        "SET point = point + "+randompoint+" WHERE UUID in ('"+UUid+"')";
                p.sendMessage(ChatColor.GREEN + "煤+"+randompoint+"分");
            }
            if (block.getType() == Material.REDSTONE_ORE || block.getType() == Material.DEEPSLATE_REDSTONE_ORE) {
                int min = 4; // 最小值
                int max = 7; // 最大值
                int randompoint = random.nextInt(min, max);
                sqldata = "UPDATE datafile " +
                        "SET point = point + "+randompoint+" WHERE UUID in ('"+UUid+"')";
                p.sendMessage(ChatColor.GREEN + "紅石+"+randompoint+"分");
            }
            if (block.getType() == Material.GOLD_ORE || block.getType() == Material.DEEPSLATE_GOLD_ORE) {
                int min = 8; // 最小值
                int max = 15; // 最大值
                int randompoint = random.nextInt(min, max);
                sqldata = "UPDATE datafile " +
                        "SET point = point + "+randompoint+" WHERE UUID in ('"+UUid+"')";
                p.sendMessage(ChatColor.GREEN + "黃金+"+randompoint+"分");
            }
            if (block.getType() == Material.EMERALD_ORE || block.getType() == Material.DEEPSLATE_EMERALD_ORE) {
                int min = 150; // 最小值
                int max = 300; // 最大值
                int randompoint = random.nextInt(min, max);
                sqldata = "UPDATE datafile " +
                        "SET point = point + "+randompoint+" WHERE UUID in ('"+UUid+"')";
                p.sendMessage(ChatColor.GREEN + "綠寶石+"+randompoint+"分");
            }
            stmt.executeUpdate(sqldata);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
        }
        catch (SQLException ed) {
            ed.printStackTrace();
        }
        }}


    }
