package org.project.mining_contest_plugin_2025.command;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.cache.SimpleGuavaCache;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class list_admin implements CommandExecutor {

    int pvpcooldown = Mining_contest_plugin_2025.getMain().getConfig().getInt("pvpcooldown");
    SimpleGuavaCache cache = SimpleGuavaCache.getInstance("pvp_cache", pvpcooldown);
    //SimpleGuavaCache cache = SimpleGuavaCache.getInstance(pvpcooldown);
    private Mining_contest_plugin_2025 plugin;
    public list_admin(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }
    String serverid = Mining_contest_plugin_2025.getMain().getConfig().getString("serverid");
    public Connection connection;
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
        Player player = (Player) sender;
        if (player.isOp()) {
            File dataFolder = plugin.getDataFolder();
            String url = "jdbc:sqlite:" + dataFolder.getAbsolutePath() + File.separator + "admin.db";
            String sql = "SELECT playername FROM admin"; // Correct SQL syntax
            List<String> playerNames = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement(sql);// Set UUID string
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    playerNames.add(rs.getString("playername"));
                }
                if (!playerNames.isEmpty()) {
                    String result = String.join(", ", playerNames);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                    player.sendMessage(ChatColor.GREEN + "伺服器"+serverid+"管理員清單 " + result );
                } else {
                    System.out.println("No players found in server 1.");
                    player.sendMessage(ChatColor.RED + "伺服器"+serverid+"沒有管理員");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    } else {
            player.sendMessage(ChatColor.GREEN + "你沒有使用該指令的權限");
        }
        }return false;}
    }


