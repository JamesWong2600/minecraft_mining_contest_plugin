package org.project.mining_contest_plugin_2025.EventListeners;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;
import java.net.InetAddress;
import java.io.IOException;
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
        String sql = "SELECT webport FROM playercount";
        try (Connection conn = SQLcollection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int server_amount = rs.getInt("webport");
                    String urlString = "http://"+ip+":"+server_amount+"/message";
                    MultipartBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("server", serverid)
                            .addFormDataPart("message", message)
                            .addFormDataPart("sender", playerName)
                            .build();

                    Request req = new Request.Builder()
                            .url(urlString)
                            .post(requestBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    try (Response res = client.newCall(req).execute()) {
                        System.out.println(res.body().string());
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
