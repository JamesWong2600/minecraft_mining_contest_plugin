package org.project.mining_contest_plugin_2025.EventListeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SQL.SQLcollection;

import java.sql.*;
import java.util.UUID;

public class NoDamage implements Listener {


    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
       // if(!(Mine2024.status==2)){
       //     if (event.getDamager() instanceof Player) {
       //         Player victim = (Player) event.getEntity();
        //        victim.setHealth(20);
      //      }
      //  }
      //  else{
        if(Mining_contest_plugin_2025.status==1) {
            //String[] SQLDATA = SQLcollection.SQL();
            if(event.getDamager() instanceof Player){
            Player p = (Player) event.getEntity();
            Player d = (Player) event.getDamager();
            UUID UUid = p.getUniqueId();
            String uid = String.valueOf(UUid);
            UUID dUUid = d.getUniqueId();
            String duid = String.valueOf(dUUid);
            try (Connection conn = SQLcollection.getConnection();
                 Statement stmt = conn.createStatement();
                 Statement stmt2 = conn.createStatement();
                 Statement stmt3 = conn.createStatement();
                 ResultSet rs = stmt2.executeQuery("select * from datafile WHERE UUID in ('"+uid+"')");
                 ResultSet rss = stmt3.executeQuery("select * from datafile WHERE UUID in ('"+duid+"')");
            ) {
                rs.next();
                rss.next();
                int pvpmode = rs.getInt("pvpmode");
                int dpvpmode = rss.getInt("pvpmode");
                conn.close();
                if((pvpmode==0)&&((dpvpmode==0))){
                    event.setCancelled(true);
                    d.sendMessage("你們兩個沒有開啓PVP模式");
                }
                if((pvpmode==0)&&(dpvpmode==1)){
                    event.setCancelled(true);
                    d.sendMessage("該玩家沒有開啓PVP模式");
                }
                if((dpvpmode==0)&&(pvpmode==1)){
                    event.setCancelled(true);
                    d.sendMessage("你沒有開啓PVP模式");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            }
            else if(event.getDamager() instanceof Arrow){
                Player p = (Player) event.getEntity();
                UUID UUid = p.getUniqueId();
                String uid = String.valueOf(UUid);
                Arrow arrow = (Arrow) event.getDamager();
                Player aa = (Player) arrow.getShooter();
                UUID aaUUid = aa.getUniqueId();
                String aauid = String.valueOf(aaUUid);
            try (Connection conn = SQLcollection.getConnection();
                 Statement stmt = conn.createStatement();
                 Statement stmt2 = conn.createStatement();
                 Statement stmt3 = conn.createStatement();
                 ResultSet rss = stmt.executeQuery("select * from datafile WHERE UUID in ('"+aauid+"')");
                 ResultSet rs = stmt2.executeQuery("select * from datafile WHERE UUID in ('"+uid+"')");
            ) {
                rs.next();
                rss.next();
                int pvpmode = rs.getInt("pvpmode");
                int dpvpmode = rss.getInt("pvpmode");
                conn.close();
                if((pvpmode==0)&&((dpvpmode==0))){
                    event.setCancelled(true);
                    aa.sendMessage("你們兩個沒有開啓PVP模式");
                }
                if((pvpmode==0)&&(dpvpmode==1)){
                    event.setCancelled(true);
                    aa.sendMessage("該玩家沒有開啓PVP模式");
                }
                if((dpvpmode==0)&&(pvpmode==1)){
                    event.setCancelled(true);
                    aa.sendMessage("你沒有開啓PVP模式");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            }

        }
        if(Mining_contest_plugin_2025.status==2) {
        if ((event.getDamager() instanceof Player && event.getEntity() instanceof Player)) {
            Player damager = (Player) event.getDamager();
            event.setCancelled(true);
            damager.sendMessage("比賽期間不允許傷害其他玩家");
          }
            else if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
                Arrow arrow = (Arrow) event.getDamager();
                if(arrow.getShooter() instanceof Player){
                Player aa = (Player) arrow.getShooter();
                event.setCancelled(true);
                aa.sendMessage("比賽期間不允許傷害其他玩家");
                }
            }
            else if (event.getDamager() instanceof SpectralArrow && event.getEntity() instanceof Player) {
                SpectralArrow arrow = (SpectralArrow) event.getDamager();
                if(arrow.getShooter() instanceof Player){
                Player aa = (Player) arrow.getShooter();
                event.setCancelled(true);
                aa.sendMessage("比賽期間不允許傷害其他玩家");
            }
        }

        }
    }
}