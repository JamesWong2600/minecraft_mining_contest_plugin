package org.project.mining_contest_plugin_2025.EventListeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;

public class sheildblocksound implements Listener {

    private Mining_contest_plugin_2025 plugin;

    public sheildblocksound(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (Mining_contest_plugin_2025.status == 1) {
            Player player = (Player) event.getEntity();
            if(player.isBlocking()) {
                if(event.getDamager() instanceof Arrow) {
                    Arrow arrow = (Arrow) event.getDamager();
                    if (arrow.getShooter() instanceof Player) {
                        Player d = (Player) arrow.getShooter();
                        d.playSound(d.getLocation(), Sound.ENTITY_ITEM_BREAK, 20, 1);
                        player.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_SWORD));
                        new BukkitRunnable(){
                            public void run() {
                                player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
                                player.setCooldown(Material.SHIELD, 100);
                            }}.runTaskLater(plugin, 5L);

                    }
                }
            }
        }
    }
}
