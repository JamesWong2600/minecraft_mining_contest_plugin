package org.project.mining_contest_plugin_2025.EventListeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;

public class axedistance implements Listener {

    private Mining_contest_plugin_2025 plugin;

    public axedistance(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (Mining_contest_plugin_2025.status == 1) {
            Player player = (Player) event.getEntity();
            Player dd = (Player) event.getDamager();
            if (dd.getItemInHand().getType().equals(Material.DIAMOND_AXE)) {
                if(player.isBlocking()) {
                dd.playSound(dd.getLocation(), Sound.ENTITY_ITEM_BREAK, 20, 1);
                }
            }
        }
    }
}
