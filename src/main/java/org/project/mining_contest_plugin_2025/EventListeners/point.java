package org.project.mining_contest_plugin_2025.EventListeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

public class point implements Listener {
    int x= 0;
    static final String mineral = "SELECT id, iron, coal, diamond FROM mineral";
    //String sql = "UPDATE datafile SET point = point + ? WHERE UUID = '?'";
    Random random = new Random();
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        UUID UUid = p.getUniqueId();
        String uid = String.valueOf(UUid);
        Random random = new Random();
        Block block = event.getBlock();
        String q3 = p.getName();
        int possibilty = random.nextInt(5,16);
        //String[] SQLDATA = org.project.mining_contest_plugin_2025.SQL.SQLcollection.SQL();
        String sqldata = null;
        if(p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)){
            p.sendMessage(ChatColor.RED + "此比賽不允許使用絲綢之觸的工具");
            event.setCancelled(true);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
        }
        else{
        if(Mining_contest_plugin_2025.status==2){
        try(Connection conn = SQLcollection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE datafile SET point = point + ? WHERE UUID = ?");
            //Statement stmt = conn.createStatement()
        ) {
            if (block.getType() == Material.DIAMOND_ORE || block.getType() == Material.DEEPSLATE_DIAMOND_ORE) {
                if(possibilty == 15){
                int min = 35; // 最小值
                int max = 51; // 最大值
                int randompoint = random.nextInt(min, max);
                stmt.setInt(1, 2*randompoint);
                stmt.setString(2, uid);
                p.sendMessage(ChatColor.YELLOW + "[暴擊]鑽石+"+2*randompoint+"分");
                stmt.executeUpdate();
                }
                else{
                int min = 35; // 最小值
                int max = 51; // 最大值
                int randompoint = random.nextInt(min, max);
                stmt.setInt(1, randompoint);
                stmt.setString(2, uid);
                p.sendMessage(ChatColor.GREEN + "鑽石+"+randompoint+"分");
                    stmt.executeUpdate();
                }
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }

            if (block.getType() == Material.IRON_ORE || block.getType() == Material.DEEPSLATE_IRON_ORE) {
                if(possibilty == 15){
                int min = 3; // 最小值
                int max = 6; // 最大值
                int randompoint = random.nextInt(min, max);
                stmt.setInt(1, 2*randompoint);
                stmt.setString(2, uid);
                p.sendMessage(ChatColor.YELLOW + "[暴擊]鐵礦+"+2*randompoint+"分");
                stmt.executeUpdate();
                }else{
                int min = 3; // 最小值
                int max = 6; // 最大值
                int randompoint = random.nextInt(min, max);
                stmt.setInt(1, randompoint);
                stmt.setString(2, uid);
                p.sendMessage(ChatColor.GREEN + "鐵礦+"+randompoint+"分");
                stmt.executeUpdate();
                }
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
            if (block.getType() == Material.COAL_ORE || block.getType() == Material.DEEPSLATE_COAL_ORE) {
                if(possibilty == 15){
                int min = 0; // 最小值
                int max = 3; // 最大值
                int randompoint = random.nextInt(min, max);
                stmt.setInt(1, 2*randompoint);
                stmt.setString(2, uid);
                p.sendMessage(ChatColor.YELLOW + "[暴擊]煤礦+"+2*randompoint+"分");
                stmt.executeUpdate();
                }else{
                int min = 0; // 最小值
                int max = 3; // 最大值
                int randompoint = random.nextInt(min, max);
                stmt.setInt(1, randompoint);
                stmt.setString(2, uid);
                p.sendMessage(ChatColor.GREEN + "煤礦+"+randompoint+"分");
                stmt.executeUpdate();
                }
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
            if (block.getType() == Material.REDSTONE_ORE || block.getType() == Material.DEEPSLATE_REDSTONE_ORE) {
                if(possibilty == 15){
                    int min = 4; // 最小值
                    int max = 8; // 最大值
                    int randompoint = random.nextInt(min, max);
                    stmt.setInt(1, 2*randompoint);
                    stmt.setString(2, uid);
                    p.sendMessage(ChatColor.YELLOW + "[暴擊]紅石礦+"+2*randompoint+"分");
                    stmt.executeUpdate();
                }else{
                    int min = 4; // 最小值
                    int max = 8; // 最大值
                    int randompoint = random.nextInt(min, max);
                    stmt.setInt(1, randompoint);
                    stmt.setString(2, uid);
                    p.sendMessage(ChatColor.GREEN + "紅石礦+"+randompoint+"分");
                    stmt.executeUpdate();
                }
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
            if (block.getType() == Material.GOLD_ORE || block.getType() == Material.DEEPSLATE_GOLD_ORE) {
                if(possibilty == 15){
                    int min = 8; // 最小值
                    int max = 16; // 最大值
                    int randompoint = random.nextInt(min, max);
                    stmt.setInt(1, 2*randompoint);
                    stmt.setString(2, uid);
                    p.sendMessage(ChatColor.YELLOW + "[暴擊]黃金礦+"+2*randompoint+"分");
                    stmt.executeUpdate();
                }else{
                    int min = 8; // 最小值
                    int max = 16; // 最大值
                    int randompoint = random.nextInt(min, max);
                    stmt.setInt(1, randompoint);
                    stmt.setString(2, uid);
                    p.sendMessage(ChatColor.GREEN + "黃金礦+"+randompoint+"分");
                    stmt.executeUpdate();
                }
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
            if (block.getType() == Material.EMERALD_ORE || block.getType() == Material.DEEPSLATE_EMERALD_ORE) {

                if(possibilty == 15){
                    int min = 150; // 最小值
                    int max = 301; // 最大值
                    int randompoint = random.nextInt(min, max);
                    stmt.setInt(1, 2*randompoint);
                    stmt.setString(2, uid);
                    p.sendMessage(ChatColor.YELLOW + "[暴擊]綠寶石礦+"+2*randompoint+"分");
                    stmt.executeUpdate();
                }else{
                    int min = 150; // 最小值
                    int max = 301; // 最大值
                    int randompoint = random.nextInt(min, max);
                    stmt.setInt(1, randompoint);
                    stmt.setString(2, uid);
                    p.sendMessage(ChatColor.GREEN + "綠寶石礦+"+randompoint+"分");
                    stmt.executeUpdate();

                }

                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
            if (block.getType() == Material.COPPER_ORE || block.getType() == Material.DEEPSLATE_COPPER_ORE) {

                if(possibilty == 15){
                    int min = 0; // 最小值
                    int max = 3; // 最大值
                    int randompoint = random.nextInt(min, max);
                    stmt.setInt(1, 2*randompoint);
                    stmt.setString(2, uid);
                    p.sendMessage(ChatColor.YELLOW + "[暴擊]銅礦+"+2*randompoint+"分");
                    stmt.executeUpdate();
                }else{
                    int min = 0; // 最小值
                    int max = 3; // 最大值
                    int randompoint = random.nextInt(min, max);
                    stmt.setInt(1, randompoint);
                    stmt.setString(2, uid);
                    p.sendMessage(ChatColor.GREEN + "銅礦+"+randompoint+"分");
                    stmt.executeUpdate();

                }

                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
            if (block.getType() == Material.LAPIS_ORE || block.getType() == Material.DEEPSLATE_LAPIS_ORE) {

                if(possibilty == 15){
                    int min = 6; // 最小值
                    int max = 11; // 最大值
                    int randompoint = random.nextInt(min, max);
                    stmt.setInt(1, 2*randompoint);
                    stmt.setString(2, uid);
                    p.sendMessage(ChatColor.YELLOW + "[暴擊]青金石礦+"+2*randompoint+"分");
                    stmt.executeUpdate();
                }else{
                    int min = 6; // 最小值
                    int max = 11; // 最大值
                    int randompoint = random.nextInt(min, max);
                    stmt.setInt(1, randompoint);
                    stmt.setString(2, uid);
                    p.sendMessage(ChatColor.GREEN + "青金石礦+"+randompoint+"分");
                    stmt.executeUpdate();

                }

                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
          //  if(!(sqldata == null)){
            //stmt.executeUpdate(sqldata);
           // }
            conn.close();
        }
        catch (SQLException ed) {
            ed.printStackTrace();
        }
        }
        }
    }
}