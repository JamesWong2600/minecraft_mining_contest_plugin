package org.project.mining_contest_plugin_2025.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.net.URL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.cache.SimpleGuavaCache;
import org.json.JSONObject;

public class add_admin implements CommandExecutor {
    private final String MOJANG_API_URL = "https://api.mojang.com/users/profiles/minecraft/";

    int pvpcooldown = Mining_contest_plugin_2025.getMain().getConfig().getInt("pvpcooldown");
    SimpleGuavaCache cache = SimpleGuavaCache.getInstance("pvp_cache", pvpcooldown);
    //SimpleGuavaCache cache = SimpleGuavaCache.getInstance(pvpcooldown);
    private Mining_contest_plugin_2025 plugin;
    public add_admin(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }
    public Connection connection;
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "請輸入 /add_admin <玩家名稱>");
            return false;
        }
        if (player.isOp()) {
            boolean isOnlineMode = Bukkit.getServer().getOnlineMode();
            File dataFolder = plugin.getDataFolder();
            OfflinePlayer offlinePlayer;
            String string_uuid;
            try {
            if (isOnlineMode) {
            string_uuid = fetchUUIDFromMojang(args[0]);
            if (string_uuid.toString().isEmpty()) {
                player.sendMessage(ChatColor.RED + "無效的玩家名稱或UUID");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                return false;
            }
            } else {
            offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            UUID uuid = offlinePlayer.getUniqueId();
             string_uuid = uuid.toString();
            }
            

            System.out.println("Adding admin: " + string_uuid);
            String url = "jdbc:sqlite:" + dataFolder.getAbsolutePath() + File.separator + "admin.db";
            String sql = "INSERT INTO admin (playername, player_uuid) VALUES (?, ?)"; // Correct SQL syntax
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, args[0]); // Set player name
                stmt.setString(2, string_uuid);         // Set UUID string
                stmt.executeUpdate();
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                player.sendMessage(ChatColor.GREEN + "已成功添加 " + args[0] + " 為管理員");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }catch (Exception e) {
            player.sendMessage(ChatColor.RED + "無效的玩家名稱或UUID");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            e.printStackTrace();
        }
    } else {
            player.sendMessage(ChatColor.GREEN + "你沒有使用該指令的權限");
        }
        }return false;}

private String fetchUUIDFromMojang(String playerName) throws Exception {
    URL url = new URL(MOJANG_API_URL + playerName);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setConnectTimeout(5000);
    connection.setReadTimeout(5000);

    int responseCode = connection.getResponseCode();
    System.out.println("Response code from Mojang API: " + responseCode);

    if (responseCode == 200) {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());
        String rawUuid = json.getString("id"); // e.g., "1234567890abcdef1234567890abcdef"

        // Add dashes to convert to standard UUID format
        return rawUuid.replaceFirst(
            "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
            "$1-$2-$3-$4-$5"
        );
    } else if (responseCode == 204) {
        System.out.println("No content — player not found.");
        return null;
    } else {
        System.out.println("Unexpected response from Mojang API");
        return null;
    }
}
    
    
    }


