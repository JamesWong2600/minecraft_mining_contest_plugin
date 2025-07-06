package org.project.mining_contest_plugin_2025.EventListeners;

import okhttp3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;
import java.net.InetAddress;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cross_server_send_message implements Listener {

    private Mining_contest_plugin_2025 plugin;

    public cross_server_send_message(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }




    @EventHandler
    public void onMessageEvent(AsyncPlayerChatEvent event){
        String message = event.getMessage();
        String playerName = event.getPlayer().getName();
        String serverid = Mining_contest_plugin_2025.getMain().getConfig().getString("serverid");
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "SELECT webip, webport FROM playercount";
        try (Connection conn = SQLcollection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int server_amount = rs.getInt("webport");
                    String server_ip = rs.getString("webip");
                    String urlString = "http://"+server_ip+":"+server_amount+"/message";
                    RequestBody requestBody = new FormBody.Builder(StandardCharsets.UTF_8)
                            .add("server", serverid)
                            .add("message", message)
                            .add("sender", playerName)
                            .build();

                    Request req = new Request.Builder()
                            .url(urlString)
                            .post(requestBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    try (Response res = client.newCall(req).execute()) {
                        // Ensure response is interpreted as UTF-8
                        String responseBody = new String(res.body().bytes(), StandardCharsets.UTF_8);
                        System.out.println(responseBody);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            event.setCancelled(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
