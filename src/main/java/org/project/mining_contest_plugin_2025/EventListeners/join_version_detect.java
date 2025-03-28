package org.project.mining_contest_plugin_2025.EventListeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;

import java.lang.reflect.Method;

import static org.bukkit.Bukkit.getServer;

public class join_version_detect implements Listener {

    private Mining_contest_plugin_2025 plugin;

    public join_version_detect(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        String version = getPlayerClientVersion(event.getPlayer().getUniqueId().toString());
        System.out.println(version);
        if (version != null) {
            event.getPlayer().sendMessage("Your client version is: " + version);
            // Check for mobile version (e.g., Bedrock)
            if (version.startsWith("1.19") || version.startsWith("1.20")) {
                event.getPlayer().sendMessage("You are likely using the Bedrock Edition (Mobile).");
            } else {
                event.getPlayer().sendMessage("You are likely using the Java Edition (Desktop).");
            }
        } else {
            event.getPlayer().sendMessage("Could not determine your client version.");
        }
    }

    public String getPlayerClientVersion(String uuid) {
        try {
            Object player = getServer().getPlayer(uuid).getClass().getMethod("getHandle").invoke(getServer().getPlayer(uuid));
            Method getPlayerConnection = player.getClass().getDeclaredMethod("getPlayerConnection");
            Object connection = getPlayerConnection.invoke(player);
            Method getVersion = connection.getClass().getDeclaredMethod("getVersion");
            return getVersion.invoke(connection).toString(); // Adjust method name as needed
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}