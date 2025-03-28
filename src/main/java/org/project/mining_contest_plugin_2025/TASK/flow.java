package org.project.mining_contest_plugin_2025.TASK;


import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;
import org.project.mining_contest_plugin_2025.SCOREBOARD.setscore;
import org.project.mining_contest_plugin_2025.SQL.FindMaxPlayer;

import java.util.Map;

import static org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025.*;

public class flow {

    public static void flow() {
        if (timer == 0) {
            status = 3;
        }
        if (status == 1) {
            Map<Player, Scoreboard> board = setscore.PrepareBoard();
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.setScoreboard(board.get(all));
            }
        }
        if (status == 2) {
            Map<Player, Scoreboard> board = setscore.ProcessingBoard();
            Scoreboard board3 = setscore.ProcessingBoardSPY();
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                if (all.getGameMode().equals(GameMode.SURVIVAL)&&(!all.isOp())) {
                    all.setScoreboard(board.get(all));
                }
                if (all.getGameMode().equals(GameMode.SPECTATOR)||all.isOp()) {
                    all.setScoreboard(board3);
                }
            }
        }
        if (status == 3) {
            Scoreboard board = setscore.EndBoard();
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.setScoreboard(board);
            }
        }
        if (timer < 10 && timer > 0) {
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.sendTitle(ChatColor.GREEN + String.valueOf(timer), "", 4, 20, 4);
                all.playSound(all.getLocation(), Sound.UI_BUTTON_CLICK, 20, 1);
            }
        }
        if (timer == 0 && end == 0) {
            String[] ranking = FindMaxPlayer.MaxPlayer();
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.setGameMode(GameMode.SPECTATOR);
                all.sendTitle(ChatColor.GREEN + "冠軍", ranking[0] + " 得分= " + ranking[1], 4, 80, 4);
                all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20, 1);
            }
            end = 1;
        }
}}
