package org.project.mining_contest_plugin_2025.command;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;
import org.project.mining_contest_plugin_2025.cache.SimpleGuavaCache;

import java.sql.*;
import java.util.Random;
import java.util.UUID;

import static org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025.status;

public class enablepvp implements CommandExecutor {
    int pvpcooldown = Mining_contest_plugin_2025.getMain().getConfig().getInt("pvpcooldown");
    SimpleGuavaCache cache = SimpleGuavaCache.getInstance("pvp_cache", pvpcooldown);
    //SimpleGuavaCache cache = SimpleGuavaCache.getInstance(pvpcooldown);
    private Mining_contest_plugin_2025 plugin;
    public enablepvp(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
        if(status==1){
                Player p = (Player) sender;
                UUID UUid = p.getUniqueId();
                String uid = String.valueOf(UUid);

                //String enabled_value = cache.get(uid+"_enabled");
                //String disabled_value = cache.get(uid+"_disabled");


            try(Connection mode_conn = SQLcollection.getConnection();
                PreparedStatement mode_stmt = mode_conn.prepareStatement("select pvpmode from datafile WHERE UUID in ('"+UUid+"')");
            ) {
                ResultSet rs = mode_stmt.executeQuery();
                rs.next();
                int pvpmode = rs.getInt("pvpmode");
                if (pvpmode == 1){
                    p.sendMessage(ChatColor.RED+"你已經打開PVP模式");
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                }
                else{
                    String pvp_value = cache.get(uid+"_pvp");
                    System.out.print("enable"+pvp_value);
                    if(pvp_value.equals("true")) {
                        System.out.println("enable"+pvp_value);
                        p.sendMessage(ChatColor.RED + "指令冷卻時間剩餘" + cache.getTimeToExpire(uid+"_pvp")+"秒");
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                    }
                    else {
                        cache.put(uid + "_pvp", "true");
                        //cache.put(uid + "_enabled", "true");
                        //cache.put(uid+"_disabled", "true");
                        //System.out.println(cache.get(uid + "_enabled"));
                        //String[] SQLDATA = SQLcollection.SQL();
                        try (Connection conn = SQLcollection.getConnection();
                             Statement stmt = conn.createStatement()
                        ) {
                            String sqldata = "UPDATE datafile " +
                                    "SET pvpmode = 1 WHERE UUID in ('" + uid + "')";
                            stmt.executeUpdate(sqldata);
                        } catch (SQLException ed) {
                            ed.printStackTrace();
                        }
                        PlayerInventory inventory = p.getInventory();
                        inventory.clear();
                        p.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
                        p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                        p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        p.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
                        p.getInventory().setItem(7, new ItemStack(Material.DIAMOND_AXE));
                        p.getInventory().setItem(2, new ItemStack(Material.BOW));
                        p.getInventory().setItem(3, new ItemStack(Material.CROSSBOW));
                        p.getInventory().setItem(4, new ItemStack(Material.ARROW, 6));
                        p.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 6));
                        p.clearActivePotionEffects();
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
                        p.sendMessage(ChatColor.GREEN + "pvp已開啓，可以透過輸入指令 /disablepvp 來關閉pvp模式");
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                        Random random = new Random();
                        World world = Bukkit.getServer().getWorld("world");
                        Location location = new Location(world, random.nextInt(-6, 6), 255, random.nextInt(-6, 6));
                        p.teleport(location);
                    }
                }
                }
                catch (SQLException ed) {
                    ed.printStackTrace();
                }

        }
        else{
            Player p = (Player) sender;
            p.sendMessage(ChatColor.GREEN+"比賽已開始，無法使用pvp");

        }
        }return false;}}


