package org.project.mining_contest_plugin_2025.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONObject;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.cache.SimpleGuavaCache;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class remove_admin implements CommandExecutor {
    private final String MOJANG_API_URL = "https://api.mojang.com/users/profiles/minecraft/";

    int pvpcooldown = Mining_contest_plugin_2025.getMain().getConfig().getInt("pvpcooldown");
    SimpleGuavaCache cache = SimpleGuavaCache.getInstance("pvp_cache", pvpcooldown);
    //SimpleGuavaCache cache = SimpleGuavaCache.getInstance(pvpcooldown);
    private Mining_contest_plugin_2025 plugin;
    public remove_admin(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }
    public Connection connection;
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "請輸入 /remove_admin <玩家名稱>");
            return false;
        }
        if (player.isOp()) {
            File dataFolder = plugin.getDataFolder();
            String url = "jdbc:sqlite:" + dataFolder.getAbsolutePath() + File.separator + "admin.db";
            String sql = "DELETE FROM admin WHERE playername = '"+args[0]+"'"; // Correct SQL syntax
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {// Set UUID string
                 stmt.executeUpdate();
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                player.sendMessage(ChatColor.GREEN + "已成功移除管理員 " + args[0] );
            } catch (SQLException e) {
                //System.out.println(e.getMessage());
                player.sendMessage(ChatColor.RED + "該管理員不存在");
            }
    } else {
            player.sendMessage(ChatColor.GREEN + "你沒有使用該指令的權限");
        }
        }return false;}
    }


