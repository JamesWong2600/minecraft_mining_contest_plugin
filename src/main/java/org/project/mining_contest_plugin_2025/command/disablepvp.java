package org.project.mining_contest_plugin_2025.command;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;

import static org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025.status;

public class disablepvp implements CommandExecutor {
    private Mining_contest_plugin_2025 plugin;
    public disablepvp(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
        if(status==1){
                Player p = (Player) sender;
                UUID UUid = p.getUniqueId();
              String uid = String.valueOf(UUid);
                //String[] SQLDATA = SQLcollection.SQL();
                try(Connection conn = SQLcollection.getConnection();
                    Statement stmt = conn.createStatement()
                ) {
                    String sqldata = "UPDATE datafile " +
                            "SET pvpmode = 0 WHERE UUID in ('"+uid+"')";
                    stmt.executeUpdate(sqldata);
                }
                catch (SQLException ed) {
                    ed.printStackTrace();
                }
            PlayerInventory inventory = p.getInventory();
            inventory.clear();
            p.clearActivePotionEffects();
            p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1000000, 10));
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
            p.sendMessage(ChatColor.RED+"pvp已關閉，可以透過輸入指令 /enablepvp 來開啓pvp模式");
            Random random = new Random();
            World world = Bukkit.getServer().getWorld("world");
            Location location = new Location(world, random.nextInt(-6, 6), 262, random.nextInt(-6, 6));
            p.teleport(location);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
        }

        else{
            Player p = (Player) sender;
            p.sendMessage(ChatColor.GREEN+"比賽已開始，無法使用pvp");

        }
        }return false;}}


