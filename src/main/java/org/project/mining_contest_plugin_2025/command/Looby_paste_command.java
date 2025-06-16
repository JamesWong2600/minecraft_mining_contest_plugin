package org.project.mining_contest_plugin_2025.command;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
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
               String selectSQL = "SELECT * FROM blockdata WHERE id = "+idvalue;
               try (Connection connn = DriverManager.getConnection(url);
                    Statement stmt2 = connn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(selectSQL)) {
                   while (rs2.next()) {
                       String blocktype = rs2.getString("blocktype").toUpperCase();
                       int X = rs2.getInt("x");
                       int Y = rs2.getInt("y");
                       int Z = rs2.getInt("z");
                       String slab_y = rs2.getString("slab_y").toUpperCase();
                       String shapeStr = rs2.getString("stair_shape").toUpperCase();     // e.g. "INNER_LEFT"
                       String halfStr = rs2.getString("stair_half").toUpperCase();
                       String facingStr = rs2.getString("stair_facing").toUpperCase();   // e.g. "NORTH"
                       String direction = rs2.getString("direction").toUpperCase();
                       Block block;
                       block = Bukkit.getWorld("world").getBlockAt(X, Y, Z);
                       Material material;
                       material = Material.valueOf(blocktype);
                       BlockData data = block.getBlockData();
                       //if (direction.equals("none")) {
                           //block = Bukkit.getWorld("world").getBlockAt(X, Y, Z);
                      //}
                       if (!direction.equals("NONE")) {
                           data = block.getBlockData();
                           if (data instanceof Directional) {
                           Directional directional = (Directional) data;

                           try {
                               System.out.println(direction);
                               BlockFace face = BlockFace.valueOf(direction);
                               block = block.getRelative(face);
                               block.setType(material);
                               block.setBlockData(directional);
                           } catch (IllegalArgumentException e) {
                               System.out.println("Invalid direction: " + direction);
                           }
                           }
                       } else if (!slab_y.equals("NONE")) {
                           System.out.println("slab");

                           block.setType(material); // Set type first
                           data = block.getBlockData();

                           if (data instanceof Slab) {
                               Slab slab = (Slab) data;

                               try {
                                   Slab.Type slabType = Slab.Type.valueOf(slab_y);
                                   slab.setType(slabType);
                                   block.setBlockData(slab); // Apply modified data
                               } catch (IllegalArgumentException e) {
                                   System.out.println("Invalid slab type string: " + slab_y);
                               }
                           } else {
                               System.out.println(material + " is not a slab.");
                           }

                       } else if (!halfStr.equals("NONE")) {
                           System.out.println("stair");

                           block.setType(material); // Set type first
                           data = block.getBlockData();

                           if (data instanceof Stairs) {
                               Stairs stairs = (Stairs) data;

                               try {
                                   BlockFace facing = BlockFace.valueOf(facingStr);
                                   Stairs.Shape shape = Stairs.Shape.valueOf(shapeStr);
                                   Bisected.Half half = Bisected.Half.valueOf(halfStr);

                                   stairs.setFacing(facing);
                                   stairs.setShape(shape);
                                   stairs.setHalf(half);

                                   block.setBlockData(stairs); // Apply modified data
                               } catch (IllegalArgumentException e) {
                                   System.out.println("Invalid stair data: " + facingStr + ", " + shapeStr + ", " + halfStr);
                               }
                           } else {
                               System.out.println(material + " is not a stair.");
                           }
                       }
                   //System.out.println(block.toString());

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
