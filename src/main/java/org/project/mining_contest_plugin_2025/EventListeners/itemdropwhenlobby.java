package org.project.mining_contest_plugin_2025.EventListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;

import java.sql.*;
import java.util.UUID;

public class itemdropwhenlobby implements Listener {

    private Mining_contest_plugin_2025 plugin;

    public itemdropwhenlobby(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (Mining_contest_plugin_2025.status == 1) {
            event.setCancelled(true);
        }
    }
}