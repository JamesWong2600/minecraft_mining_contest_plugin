package org.project.mining_contest_plugin_2025.command;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;

import java.io.File;
import java.sql.*;

public class Looby_paste_command implements CommandExecutor {

    private Mining_contest_plugin_2025 plugin;
    public Looby_paste_command(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //
        if(!(sender instanceof Player)){
         File dataFolder = plugin.getDataFolder();
             String url = "jdbc:sqlite:" + dataFolder.getAbsolutePath() + File.separator + "database.db";
            String SQL = "SELECT MAX(id) AS ID FROM blockdata";
            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(SQL)) {
                rs.next();
                int maxid = rs.getInt("ID");
                for(int idvalue = 0; idvalue < maxid+1; idvalue++){
               String selectSQL = "SELECT id, blocktype, X, Y, Z, direction FROM blockdata WHERE id = "+idvalue;
               try (Connection connn = DriverManager.getConnection(url);
                    Statement stmt2 = connn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(selectSQL)) {
                   while (rs2.next()) {
                       String blocktype = rs2.getString("blocktype");
                       int X = rs2.getInt("x");
                       int Y = rs2.getInt("y");
                       int Z = rs2.getInt("z");
                       String direction = rs2.getString("direction");
                       Block block;
                       Material material;
                       if (direction.equals("none")){
                           material = Material.valueOf(blocktype.toUpperCase());
                           block = Bukkit.getWorld("world").getBlockAt(X, Y, Z);
                       }else{
                          BlockFace face = BlockFace.valueOf(direction.toUpperCase());
                          material = Material.valueOf(blocktype.toUpperCase());
                          block = Bukkit.getWorld("world").getBlockAt(X, Y, Z).getRelative(face);
                       }
                       //System.out.println(block.toString());
                       block.setType(material);
                   }

               } catch (SQLException e) {
                   System.out.println(e.getMessage());
               }

           }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            //player.sendMessage(ChatColor.GREEN+"所有方塊數據已貼上");
            //player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
        else{
            Player player = (Player) sender;
            player.sendMessage("這不是一個人爲指令");
        }
        return false;
    }
}
