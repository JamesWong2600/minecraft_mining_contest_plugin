package org.project.mining_contest_plugin_2025.SCOREBOARD;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025;

import static org.project.mining_contest_plugin_2025.Mining_contest_plugin_2025.status;

public class queue {

    public static void QueueScoreBoardCONTENT(){

    }
    public static void QueueScoreBoard()
    {
    Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Mining_contest_plugin_2025.getMain(), new Runnable() {
        public void run() {
            status = 1;
            int timer = 3600;
            int playercount = 0;
            for(Player all : Bukkit.getServer().getOnlinePlayers())
            {
                playercount+=1;
            }
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            final Scoreboard board = manager.getNewScoreboard();
            if(status==1){
                final Objective objective = board.registerNewObjective("test", "dummy");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(ChatColor.RED + "挖礦大賽2023");
                Score score = objective.getScore("目前狀態:        lobby");
                score.setScore(10);
                Score score1 = objective.getScore("- ");
                score1.setScore(9);
                Score score2 = objective.getScore("- ");
                score2.setScore(8);
                Score score3 = objective.getScore("玩家:           " + ChatColor.GREEN + playercount);
                score3.setScore(7);
                Score score4 = objective.getScore("- ");
                score4.setScore(9);
                Score score5 = objective.getScore("- ");
                score5.setScore(8);
                Score score6 = objective.getScore("請等待主辦方開始比賽");
                score6.setScore(8);
            }
            if(status==2){
                timer-=1;
                final Objective objective = board.registerNewObjective("test", "dummy");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(ChatColor.RED + "挖礦大賽2023");
                Score score = objective.getScore("目前狀態:        正在比賽");
                score.setScore(10);
                Score score1 = objective.getScore("- ");
                score1.setScore(9);
                Score score2 = objective.getScore("- ");
                score2.setScore(8);
                Score score3 = objective.getScore("玩家:           " + ChatColor.GREEN + playercount);
                score3.setScore(7);
                Score score4 = objective.getScore("- ");
                score4.setScore(9);
                Score score5 = objective.getScore("- ");
                score5.setScore(8);
                Score score6 = objective.getScore("比賽剩餘時間:  "+timer);
                score6.setScore(8);
            }
            for(Player all : Bukkit.getServer().getOnlinePlayers())
            {
                all.setScoreboard(board);
            }
        }
    }, 0, 20);
}}
