package org.project.mining_contest_plugin_2025.TASK;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;

import java.io.File;
import java.sql.*;

import static org.bukkit.Bukkit.*;

public class CreateGame {


    public static void Create() {
        int sz = Mining_contest_plugin_2025.getMain().getConfig().getInt("border.size");
        World world = getServer().getWorld("world");
        world.getWorldBorder().setSize(sz*2, 1);
        Thread thread = new Thread() {
            public void run() {
                Chunk chunk = null;
                for (int x = -(Math.round(sz/16)); x < (Math.round(sz/16)); x++) // whole chunk
                {
                    for (int z = -(Math.round(sz/16)); z < (Math.round(sz/16)); z++) // whole chunk
                    {
                        Location loc = new Location(world,x*16,0,z*16);
                        chunk = loc.getChunk();
                        System.out.println(chunk);
                    }
                }
            }
        };
       //thread.start();

        // }
        //for (int x = -30; x < 30; x++) // whole chunk
        //{
        //    for (int z = -30; z < 30; z++) // whole chunk
        //    {
        //        for (int y = 250; y < 265; y++) // any level between 1 - 40
        //        {
        //            Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
        //            loc.getBlock().setType(Material.GLASS);
         //       }
         //   }
       // }
       // for (int x = -29; x < 29; x++) // whole chunk
       // {
       //     for (int z = -29; z < 29; z++) // whole chunk
       //     {
        //        for (int y = 251; y < 264; y++) // any level between 1 - 40
         //       {
         //           Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
          //          loc.getBlock().setType(Material.AIR);
          //      }
         //   }
       // }
      //  for (int x = -55; x < 55; x++) // whole chunk
      //  {
      //      for (int z = -55; z < 55; z++) // whole chunk
      //      {

       //             Location loc = new Location(Bukkit.getWorld("world"), x, 120, z);
      //              loc.getBlock().setType(Material.GLASS);
       //     }
      //  }

        //System.out.println("avaliabe to join");
        Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "gamerule keepInventory true");
        //String[] SQLDATA = org.project.mining_contest_plugin_2025.SQL.SQLcollection.SQL();
        try (Connection conn = SQLcollection.getConnection();
             Statement stmt = conn.createStatement();
        ) {
            String creditrecord = "INSERT INTO datafile VALUES (0, 'server', '123456','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "')";
            stmt.executeUpdate(creditrecord);
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}
