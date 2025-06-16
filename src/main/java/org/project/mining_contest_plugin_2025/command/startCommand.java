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
import org.bukkit.scheduler.BukkitRunnable;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;
import org.project.mining_contest_plugin_2025.cache.SimpleGuavaCache;

import java.sql.*;
import java.util.Random;
import java.util.UUID;

import static org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025.status;

public class startCommand implements CommandExecutor {
    private Mining_contest_plugin_2025 plugin;
    public static int[] countdown = {10};
    public startCommand(Mining_contest_plugin_2025 plugin) {
        this.plugin = plugin;
    }
    int sz = Mining_contest_plugin_2025.getMain().getConfig().getInt("border.size");
    int time_cooldown = Mining_contest_plugin_2025.getMain().getConfig().getInt("time");
    SimpleGuavaCache cache = SimpleGuavaCache.getInstance("command_cache", time_cooldown+120);
    //SimpleGuavaCache cache = SimpleGuavaCache.getInstance(time_cooldown+120);
    public static void start()
    {
        for(Player all : Bukkit.getServer().getOnlinePlayers())
        {
            if(all.getGameMode().equals(GameMode.ADVENTURE)){
              if(!all.isOp()) {
                  for (int x = -35; x < 35; x++) // whole chunk
                  {
                      for (int z = -35; z < 35; z++) // whole chunk
                      {
                          for (int y = 249; y < 290; y++) // any level between 1 - 40
                          {
                              Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
                              loc.getBlock().setType(Material.AIR);
                          }
                      }
                  }
                  int sz = Mining_contest_plugin_2025.getMain().getConfig().getInt("border.size");
                  //String[] SQLDATA = SQLcollection.SQL();
                  try (Connection conn = SQLcollection.getConnection();
                       PreparedStatement stmt = conn.prepareStatement( "UPDATE datafile SET tp = 1 WHERE UUID = ?");
                  ) {
                      UUID UUid = all.getUniqueId();
                      stmt.setString(1, UUid.toString());
                      stmt.executeUpdate();
                  } catch (SQLException ed) {
                      ed.printStackTrace();
                  }

                  final int[] countdown = {10};
                  Random random = new Random();
                  int X = random.nextInt(-sz, sz);
                  int Z = random.nextInt(-sz, sz);
                  World world = Bukkit.getServer().getWorld("world");
                  world.setTime(0);
                  int Y = world.getHighestBlockYAt(X, Z);
                  Location[] location = new Location[1];
                  location[0] = new Location(world, X, Y + 1, Z);
                  location[0].getChunk();
                  do {
                      random = new Random();
                      X = random.nextInt(-sz, sz);
                      Z = random.nextInt(-sz, sz);
                      Y = world.getHighestBlockYAt(X, Z);
                      location[0] = new Location(world, X, Y + 1, Z);
                  }
                  while (location[0].getBlock().getBiome().toString().contains("ocean") ||
                          location[0].getBlock().getBiome().toString().contains("river"));
                  location[0].getChunk();
                  all.teleport(location[0]);
                  status = 2;
                  all.setGameMode(GameMode.SURVIVAL);
                  all.clearActivePotionEffects();
                  all.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 3));
                  PlayerInventory inventory = all.getInventory();
                  all.sendTitle(ChatColor.GREEN + "比賽正式開始", "", 4, 20, 4);
                  inventory.clear();
                  ItemStack item1 = new ItemStack(Material.NETHERITE_PICKAXE);
                  ItemStack item2 = new ItemStack(Material.BIRCH_BOAT);
                  ItemStack item3 = new ItemStack(Material.DARK_OAK_DOOR);
                  ItemStack item4 = new ItemStack(Material.COOKED_BEEF,8);
                  all.getInventory().setItem(0, item1);
                  all.getInventory().setItem(1, item2);
                  all.getInventory().setItem(2, item3);
                  all.getInventory().setItem(3, item4);
                  all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
              }}else{
                all.setGameMode(GameMode.SPECTATOR);
                all.sendTitle(ChatColor.GREEN+"比賽正式開始", "", 4, 20, 4);
                all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
        }
    }

    public static void countingdown()
    {
        for(Player all : Bukkit.getServer().getOnlinePlayers())
        {
            all.sendTitle(ChatColor.GREEN+String.valueOf(countdown[0]), "", 4, 20, 4);
            all.playSound(all.getLocation(), Sound.UI_BUTTON_CLICK, 20, 1);
        }
    }

    public static void task()
    {
        for(Player all : Bukkit.getServer().getOnlinePlayers())
        {
            all.sendTitle(ChatColor.GREEN+String.valueOf(countdown[0]), "", 4, 20, 4);
            all.playSound(all.getLocation(), Sound.UI_BUTTON_CLICK, 20, 1);
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(status==1){

        if(sender instanceof Player){
          Player p = (Player) sender;
        if(!(p.isOp())){
            p.sendMessage("你目前還沒有那個權力去執行這條指令");
        }else{
                String start_cooldown = cache.get("start_cooldown");
                if(!start_cooldown.equals("true")){
                cache.put("start_cooldown", "true");
                new BukkitRunnable() {
                    public void run() {
                        countdown[0] -= 1;
                        if (countdown[0] > 0) {
                            countingdown();
                        }
                        if (countdown[0] == 0) {
                            start();
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0, 20);
                }
                else{
                    p.sendMessage("指令已執行，無需再執行");
                }
        }}}return false;}}









