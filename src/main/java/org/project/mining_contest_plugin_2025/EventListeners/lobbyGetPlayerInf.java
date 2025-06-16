package org.project.mining_contest_plugin_2025.EventListeners;


import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;

import java.sql.*;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;



public class lobbyGetPlayerInf implements Listener{

    private Mining_contest_plugin_2025 plugin;



    public lobbyGetPlayerInf(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }



      @EventHandler
      public void FirstJoinID(PlayerJoinEvent e) {
          Player p = e.getPlayer();
          String name = p.getName();
          UUID UUid = p.getUniqueId();
          String myserverid = "";
         //String[] SQLDATA = SQLcollection.SQL();
          String serverid = Mining_contest_plugin_2025.getMain().getConfig().getString("serverid");
          final BukkitRunnable runnable1 = new BukkitRunnable() {
              public void run() {
                  p.setGameMode(GameMode.ADVENTURE);
                  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
              }};
          final BukkitRunnable runnable2 = new BukkitRunnable() {
              public void run() {
                  p.setGameMode(GameMode.SPECTATOR);
                  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                  p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
                  System.out.println("op");
              }};
      if(Mining_contest_plugin_2025.status==1){
          try (Connection check_conn = SQLcollection.getConnection();
               PreparedStatement stmt = check_conn.prepareStatement("select * from datafile WHERE UUID in ('"+UUid+"')");
          ) {
              ResultSet rs = stmt.executeQuery();
              rs.next();
              myserverid = rs.getString("server");
              //System.out.println("myserverid id = "+myserverid);
              //System.out.println("serverid id = "+serverid);
              if (!(myserverid.equals(serverid))) {
                  System.out.println("myserverid id = "+myserverid);
                  System.out.println("serverid id = "+serverid);
                  p.kickPlayer("這不是你的分流，你已被封鎖進入該分流");
              }
          }
          catch (SQLException ed) {
              if(!p.hasPlayedBefore()){
                  if(!p.isOp()) {
                      try (Connection conn = SQLcollection.getConnection();
                           PreparedStatement stmt = conn.prepareStatement("INSERT INTO datafile (player, UUID, point, tp, pvppoint, server, pvpmode) VALUES ('" + name + "', '" + UUid + "','" + 0 + "','" + 0 + "','" + 0 + "','" + serverid + "','" + 0 + "')")
                      ) {
                          //String creditrecord = "INSERT INTO datafile VALUES ('" + name + "', '" + UUid + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "')";
                          stmt.executeUpdate();

                      } catch (SQLException e2) {
                          e2.printStackTrace();
                      }
                      //   }

                      p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1000000, 10));
                      p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
                      //p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000, 10));
                      Random random = new Random();
                      World world = Bukkit.getServer().getWorld("world");
                      Location location = new Location(world, random.nextInt(-6, 6), 264, random.nextInt(-6, 6));
                      p.teleport(location);
                      //p.sendMessage(ChatColor.GREEN+"現階段處於等待階段，玩家可以使用 /enablepvp 指令進行PVP模式，最高得分者可獲得150mycard，PVP模式所得的分數與挖礦比賽所得的分數獨立分開，不想玩可以無需理會這條訊息");
                      p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                      runnable1.runTaskLater(plugin, 8);
                  }else{
                      runnable2.runTaskLater(plugin, 5);
                  }

              }
          }



      }
        else{
          if(Mining_contest_plugin_2025.status==2){
              try (Connection conn = SQLcollection.getConnection();
                   PreparedStatement stmt = conn.prepareStatement("select * from datafile WHERE UUID in ('"+UUid+"')");
              ) {
                  ResultSet rss = stmt.executeQuery();
                  rss.next();
                  myserverid = rss.getString("server");
                  if (!(myserverid.equals(serverid))) {
                      p.kickPlayer("這不是你的分流，你已被封鎖進入該分流");
                  }
                  else{
                  try (Connection cconn = SQLcollection.getConnection();
                       PreparedStatement stmt3 = conn.prepareStatement("select * from datafile WHERE UUID in ('"+UUid+"')");
                  ) {
                      ResultSet rs = stmt3.executeQuery();
                      rs.next();
                      int tp = rs.getInt("tp");
                      if(tp==0){
                          int sz = Mining_contest_plugin_2025.getMain().getConfig().getInt("border.size");
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
                          p.setGameMode(GameMode.SURVIVAL);
                          p.clearActivePotionEffects();
                          p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
                          PlayerInventory inventory = p.getInventory();
                          p.sendTitle(ChatColor.GREEN + "比賽正式開始", "", 4, 20, 4);
                          inventory.clear();
                          ItemStack item1 = new ItemStack(Material.NETHERITE_PICKAXE);
                          ItemStack item2 = new ItemStack(Material.BIRCH_BOAT);
                          ItemStack item3 = new ItemStack(Material.DARK_OAK_DOOR);
                          ItemStack item4 = new ItemStack(Material.COOKED_BEEF,8);
                          p.getInventory().setItem(0, item1);
                          p.getInventory().setItem(1, item2);
                          p.getInventory().setItem(2, item3);
                          p.getInventory().setItem(3, item4);
                          p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
                          try(Connection connn = SQLcollection.getConnection();
                              Statement stmt5 = connn.createStatement()
                          ) {
                              String sqldata = "UPDATE datafile " +
                                      "SET tp = 1 WHERE UUID in ('"+UUid+"')";
                              stmt5.executeUpdate(sqldata);
                          }
                          catch (SQLException ed) {
                              ed.printStackTrace();
                          }
                      }

                  }catch (SQLException ed) {
                      ed.printStackTrace();
                  }
              }
              }catch (SQLException eed) {
                  if(!p.isOp()) {
                      p.kickPlayer("比賽已經開始");
                      //runnable2.runTaskLater(plugin, 5);
                  }
              }

            }
          if(Mining_contest_plugin_2025.status==3){
              try (Connection conn = SQLcollection.getConnection();
                   PreparedStatement stmt = conn.prepareStatement("select * from datafile WHERE UUID in ('"+UUid+"')");
              ) {
                  ResultSet rss = stmt.executeQuery();
                  rss.next();
                  myserverid = rss.getString("server");
                  if (!(myserverid.equals(serverid))) {
                      p.kickPlayer("這不是你的分流，你已被封鎖進入該分流");
                  }
          }
              catch (SQLException eed) {
                  if(!p.isOp()) {
                      p.kickPlayer("比賽已經結束");
                      //runnable2.runTaskLater(plugin, 5);
                  }
              }
              runnable2.runTaskLater(plugin, 5);
    }}}}

