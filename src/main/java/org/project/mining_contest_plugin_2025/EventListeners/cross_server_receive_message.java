package org.project.mining_contest_plugin_2025.EventListeners;

import fi.iki.elonen.NanoHTTPD;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class cross_server_receive_message extends NanoHTTPD {

    private Mining_contest_plugin_2025 plugin;


    public cross_server_receive_message(Mining_contest_plugin_2025 plugin, int port) throws IOException {
        super(port);
        this.plugin = plugin;
        start(SOCKET_READ_TIMEOUT, false);
        System.out.println("[NanoHTTPD] Server started on port " + port);
    }
    private Map<String, String> decodePostParametersUTF8(IHTTPSession session, Map<String, String> files) {
        Map<String, String> decodedParams = new HashMap<>();
        try {
            // Read the post data as bytes (NanoHTTPD writes it to a temp file internally)
            String tmpFilePath = files.get("postData");
            if (tmpFilePath != null) {
                byte[] bytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(tmpFilePath));
                String postData = new String(bytes, StandardCharsets.UTF_8);

                for (String pair : postData.split("&")) {
                    String[] parts = pair.split("=", 2);
                    String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8.name());
                    String value = parts.length > 1 ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name()) : "";
                    decodedParams.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decodedParams;
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (Method.POST.equals(session.getMethod()) && session.getUri().equals("/message")) {
            try {
                int contentLength = Integer.parseInt(session.getHeaders().getOrDefault("content-length", "0"));
                byte[] buffer = new byte[contentLength];
                session.getInputStream().read(buffer, 0, contentLength);

                String postData = new String(buffer, StandardCharsets.UTF_8);

                Map<String, String> params = new HashMap<>();
                for (String pair : postData.split("&")) {
                    String[] parts = pair.split("=", 2);
                    if (parts.length == 2) {
                        String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8.name());
                        String value = URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
                        params.put(key, value);
                    }
                }

                String server = params.getOrDefault("server", "N/A");
                String message = params.getOrDefault("message", "N/A");
                String sender = params.getOrDefault("sender", "N/A");

                System.out.println("Received: server=" + server + ", sender=" + sender + ", message=" + message);

                OfflinePlayer target = Bukkit.getOfflinePlayer(sender);

                File dataFolder = plugin.getDataFolder();
                String url = "jdbc:sqlite:" + dataFolder.getAbsolutePath() + File.separator + "admin.db";
                String SQL = "SELECT * FROM admin WHERE playername = ?";
                boolean isAdmin = false;
                try (Connection conn = DriverManager.getConnection(url);
                     PreparedStatement stmt = conn.prepareStatement(SQL)) {
                    stmt.setString(1, sender);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            String playername = rs.getString("playername");
                            if (playername.equals(sender)) {
                                isAdmin = true;
                            }
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                if (target.isOp() || isAdmin) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + sender + "[管理員]: " + ChatColor.WHITE + message);
                } else {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + sender + "[分組" + server + "]: " + ChatColor.WHITE + message);
                }

                return newFixedLengthResponse("Message received!");
            } catch (Exception e) {
                e.printStackTrace();
                return newFixedLengthResponse("Failed to parse request");
            }
        }

        return newFixedLengthResponse("Unsupported request");
    }
}
