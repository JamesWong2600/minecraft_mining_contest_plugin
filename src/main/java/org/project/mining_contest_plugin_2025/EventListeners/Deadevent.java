package org.project.mining_contest_plugin_2025.EventListeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;

import java.sql.*;
import java.util.Random;
import java.util.UUID;

public class Deadevent implements Listener {

    private Mining_contest_plugin_2025 plugin;
    public Deadevent(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event) {
        Player p = event.getPlayer();
        UUID UUid = p.getUniqueId();
        String uid = String.valueOf(UUid);
        String[] SQLDATA = org.project.mining_contest_plugin_2025.SQL.SQLcollection.SQL();
        String sqldata = null;
        if(Mining_contest_plugin_2025.status==1) {
            new BukkitRunnable(){
                public void run() {
                    Player d = p.getKiller();
                    if(!(d==null)){
                    UUID DUUid = d.getUniqueId();
                    String duid = String.valueOf(DUUid);
                    try (Connection conn = DriverManager.getConnection(SQLDATA[1], SQLDATA[2], SQLDATA[3]);
                         Statement stmt = conn.createStatement();
                    ) {
                        String sqldata2 = "UPDATE datafile " +
                                "SET pvppoint = pvppoint + 1 WHERE UUID in ('" + duid + "')";
                        stmt.executeUpdate(sqldata2);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    p.spigot().respawn();
                    d.sendMessage(ChatColor.GREEN+"pvp得分+1");
                    d.playSound(d.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                    Random random = new Random();
                    Location[] location = new Location[1];
                    World world = Bukkit.getServer().getWorld("world");
                    location[0] = new Location(world, random.nextInt(-10, 10), 253, random.nextInt(-10, 10));
                    location[0].getChunk();
                    p.teleport(location[0]);
                    PlayerInventory inventory = p.getInventory();
                    inventory.clear();
                    PlayerInventory inventory2 = d.getInventory();
                    inventory2.clear();
                    p.clearActivePotionEffects();
                    d.clearActivePotionEffects();
                    p.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
                    p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                    p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    p.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
                    p.getInventory().setItem(7, new ItemStack(Material.DIAMOND_AXE));
                    p.getInventory().setItem(2, new ItemStack(Material.BOW));
                    p.getInventory().setItem(3, new ItemStack(Material.CROSSBOW));
                    p.getInventory().setItem(4, new ItemStack(Material.ARROW,6));
                    p.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 6));
                    d.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
                    d.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                    d.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    d.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    d.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    d.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
                    d.getInventory().setItem(7, new ItemStack(Material.DIAMOND_AXE));
                    d.getInventory().setItem(2, new ItemStack(Material.BOW));
                    d.getInventory().setItem(3, new ItemStack(Material.CROSSBOW));
                    d.getInventory().setItem(4, new ItemStack(Material.ARROW,6));
                    d.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 6));
                    d.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1, 20));
                    d.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 20));
                    d.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
                    //
                    }
                    else{
                        p.spigot().respawn();
                        PlayerInventory inventory = p.getInventory();
                        inventory.clear();
                        p.clearActivePotionEffects();
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1000000, 10));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
                        Random random = new Random();
                        Location[] location = new Location[1];
                        World world = Bukkit.getServer().getWorld("world");
                        location[0] = new Location(world, random.nextInt(-18, 18), 262, random.nextInt(-18, 18));
                        location[0].getChunk();
                        p.teleport(location[0]);
                        String[] SQLDATA = SQLcollection.SQL();
                        try(Connection conn = DriverManager.getConnection(SQLDATA[1], SQLDATA[2], SQLDATA[3]);
                            Statement stmt = conn.createStatement()
                        ) {
                            String sqldata = "UPDATE datafile " +
                                    "SET pvpmode = 0 WHERE UUID in ('"+uid+"')";
                            stmt.executeUpdate(sqldata);
                        }
                        catch (SQLException ed) {
                            ed.printStackTrace();
                        }

                    }
                    //p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000, 10));
                }}.runTaskLater(plugin, 1L);
        }
        if(Mining_contest_plugin_2025.status==2) {
            try (Connection conn = DriverManager.getConnection(SQLDATA[1], SQLDATA[2], SQLDATA[3]);
                 Statement stmt = conn.createStatement();
                 Statement stmt2 = conn.createStatement();
                 Statement stmt3 = conn.createStatement();
                 ResultSet rs = stmt3.executeQuery("select * from datafile WHERE UUID in ('"+uid+"')");
            ) {
                rs.next();
                int tp = rs.getInt("tp");
                System.out.println(tp);
                if(tp==1){
                int sz = Mining_contest_plugin_2025.getMain().getConfig().getInt("border.size");
                sqldata = "UPDATE datafile " +
                        "SET point = point - 50 WHERE UUID in ('" + uid + "')";
                    String finalSqldata = sqldata;
                    stmt.executeUpdate(finalSqldata);
                    conn.close();
                    new BukkitRunnable(){
                    public void run(){
                        p.sendMessage(ChatColor.RED + "死亡 -50分");
                        p.spigot().respawn();
                        p.setGameMode(GameMode.SURVIVAL);
                        p.clearActivePotionEffects();
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
                        Random random = new Random();
                        int X = random.nextInt(-sz,sz);
                        int Z = random.nextInt(-sz,sz);
                        World world = Bukkit.getServer().getWorld("world");
                        int Y = world.getHighestBlockYAt(X,Z);
                        Location[] location = new Location[1];
                        location[0] = new Location(world,X,Y,Z);
                        location[0].getChunk();
                        do {
                            random = new Random();
                            X = random.nextInt(-sz,sz);
                            Z = random.nextInt(-sz,sz);
                            Y = world.getHighestBlockYAt(X,Z);
                            location[0] = new Location(world,X,Y+1,Z);
                        }
                        while(location[0].getBlock().getBiome().toString().contains("ocean")||
                                location[0].getBlock().getBiome().toString().contains("river"));
                        location[0].getChunk();
                        p.teleport(location[0]);
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                    }
                }.runTaskLater(plugin, 1L);

                }//else{
                   // new BukkitRunnable(){
                      //  public void run(){
                         ///   int sz = Mining_contest_plugin_2025.getMain().getConfig().getInt("border.size");
                         //   p.sendMessage(ChatColor.GREEN + "檢測到由於斷綫無法傳送，已爲你復活，此次死亡不扣分");
                         //   p.spigot().respawn();
                         //   p.setGameMode(GameMode.SURVIVAL);
                         //   p.clearActivePotionEffects();
                         //   p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
                         //   Random random = new Random();
                          //  int X = random.nextInt(-sz,sz);
                          //  int Z = random.nextInt(-sz,sz);
                          //  World world = Bukkit.getServer().getWorld("world");
                          //  int Y = world.getHighestBlockYAt(X,Z);
                          //  Location[] location = new Location[1];
                          //  location[0] = new Location(world,X,Y,Z);
                           // location[0].getChunk();
                          //  do {
                          //      random = new Random();
                          //      X = random.nextInt(-sz,sz);
                          //      Z = random.nextInt(-sz,sz);
                          //      Y = world.getHighestBlockYAt(X,Z);
                          //     location[0] = new Location(world,X,Y+1,Z);
                         //   }
                          //  while(location[0].getBlock().getBiome().toString().contains("ocean")||
                          //          location[0].getBlock().getBiome().toString().contains("river"));
                         //   location[0].getChunk();
                         //   p.teleport(location[0]);
                         //   PlayerInventory inventory2 = p.getInventory();
                         //   inventory2.clear();
                         //   ItemStack item1 = new ItemStack(Material.DIAMOND_PICKAXE);
                           // ItemStack item2 = new ItemStack(Material.BIRCH_BOAT);
                         //   ItemStack item3 = new ItemStack(Material.DARK_OAK_DOOR);
                         //   p.getInventory().setItem(0, item1);
                         //   p.getInventory().setItem(1, item2);
                         //   p.getInventory().setItem(2, item3);
                         //   p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                          //  String[] SQLDATA = org.project.mining_contest_plugin_2025.SQL.SQLcollection.SQL();
                          //  try(Connection connn = DriverManager.getConnection(SQLDATA[1], SQLDATA[2], SQLDATA[3]);
                          //      Statement stmt4 = connn.createStatement()
                         //   ) {
                         //   UUID UUid = p.getUniqueId();
                         //   String sqldata = "UPDATE datafile " +
                          //          "SET tp = 1 WHERE UUID in ('"+UUid+"')";
                       //     stmt4.executeUpdate(sqldata);
                       //     }
                       //     catch (SQLException ed) {
                       //         ed.printStackTrace();
                    //        }

                  //      }
                //    }.runTaskLater(plugin, 1L);

              //  }
            } catch (SQLException ed) {
                ed.printStackTrace();
            }
        }
    }
}