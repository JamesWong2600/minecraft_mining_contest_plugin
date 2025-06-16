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
import org.project.mining_contest_plugin_2025.cache.SimpleGuavaCache;

import java.sql.*;
import java.util.Random;
import java.util.UUID;

import static org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025.status;

public class disablepvp implements CommandExecutor {
    int pvpcooldown = Mining_contest_plugin_2025.getMain().getConfig().getInt("pvpcooldown");
    SimpleGuavaCache cache = SimpleGuavaCache.getInstance("pvp_cache", pvpcooldown);
    //SimpleGuavaCache cache = SimpleGuavaCache.getInstance(pvpcooldown);
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

                //String disabled_value = cache.get(uid+"_disabled");



                try(Connection mode_conn = SQLcollection.getConnection();
                    PreparedStatement mode_stmt = mode_conn.prepareStatement("select pvpmode from datafile WHERE UUID in ('"+UUid+"')");
                ) {
                    ResultSet rs = mode_stmt.executeQuery();
                    rs.next();
                    int pvpmode = rs.getInt("pvpmode");
                    {
                        if (pvpmode == 0){
                            p.sendMessage(ChatColor.RED+"你沒有打開PVP模式");
                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                        }
                        else{
                            String pvp_value = cache.get(uid+"_pvp");
                            System.out.print("disable"+pvp_value);
                            if(pvp_value.equals("true")) {
                                System.out.println("disable"+pvp_value);
                                p.sendMessage(ChatColor.RED+"指令冷卻時間剩餘"+cache.getTimeToExpire(uid+"_pvp")+"秒");
                                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                            }
                            else{
                                cache.put(uid + "_pvp", "true");
                                //cache.put(uid+"_disabled", "true");
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
                                Location location = new Location(world, random.nextInt(-6, 6), 263, random.nextInt(-6, 6));
                                p.teleport(location);
                                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                            }
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


