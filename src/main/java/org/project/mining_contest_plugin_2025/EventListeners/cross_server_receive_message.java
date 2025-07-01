package org.project.mining_contest_plugin_2025.EventListeners;

import fi.iki.elonen.NanoHTTPD;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;

import java.io.IOException;
import java.util.Map;

public class cross_server_receive_message extends NanoHTTPD {

    public cross_server_receive_message(int port) throws IOException {
        super(port);
        start(SOCKET_READ_TIMEOUT, false);
        System.out.println("[NanoHTTPD] Server started on port " + port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        if (uri.equals("/message")) {
            Map<String, String> files = new java.util.HashMap<>();
            if (Method.POST.equals(session.getMethod())) {
                try {
                    session.parseBody(files);
                } catch (Exception e) {
                    return newFixedLengthResponse("Error parsing request");
                }
            }

            Map<String, String> params = session.getParms();
            String server = params.getOrDefault("server", "N/A");
            String message = params.getOrDefault("message", "N/A");
            String sender = params.getOrDefault("sender", "N/A");
            Bukkit.broadcastMessage(ChatColor.YELLOW + sender + "(分組" + server + "): " + ChatColor.WHITE + message);

            // Do something with the data, e.g., broadcast message
            System.out.println("[" + server + "] <" + sender + "> " + message);
        }
        return newFixedLengthResponse("Message received!");
    }
}
