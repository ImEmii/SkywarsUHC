package me.emi.swh.game.scoreboards;


import me.emi.swh.Main;
import me.emi.swh.game.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class WaitingBore {

    public static void onWaiting(Player p){
        Arena arena = Main.getInstance().getArenaManager().getArenaByPlayer(p.getName());
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("Stats", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.AQUA + ChatColor.BOLD.toString() + "SkywarsUHC");


        Team ip = board.registerNewTeam("ip");
        ip.addEntry(ChatColor.DARK_AQUA.toString());
        ip.setPrefix("youserver.com");
        obj.getScore(ChatColor.DARK_AQUA.toString()).setScore(0);

        Team line1 = board.registerNewTeam("line1");
        line1.addEntry("§m------------");
        line1.setPrefix("");
        line1.setSuffix("§m------------");
        obj.getScore("§m------------").setScore(1);

        Team spec = board.registerNewTeam("spec");
        spec.addEntry(ChatColor.DARK_GRAY.toString());
        spec.setPrefix("§aSpectators >");
        spec.setSuffix(ChatColor.RED+String.valueOf(arena.getSpectators()).replace("null", "0"));
        obj.getScore(ChatColor.DARK_GRAY.toString()).setScore(2);

        Team kills = board.registerNewTeam("kills");
        kills.addEntry(ChatColor.DARK_RED.toString());
        kills.setPrefix("§aKills >");
        kills.setSuffix(ChatColor.RED+String.valueOf(arena.getKills().get(p.getName())));
        obj.getScore(ChatColor.DARK_RED.toString()).setScore(3);

        Team blank = board.registerNewTeam("blank");
        blank.addEntry("§7");
        obj.getScore("§7").setScore(4);

        Team players = board.registerNewTeam("players");
        players.addEntry(ChatColor.AQUA.toString());
        players.setPrefix("§a"+arena.getIngame().size());
        players.setSuffix("§a/"+arena.getMaxPlayers());
        obj.getScore(ChatColor.AQUA.toString()).setScore(5);

        Team state = board.registerNewTeam("state");
        state.addEntry(ChatColor.BLUE.toString());
        state.setPrefix("§aState >");
        state.setSuffix(ChatColor.RED+Character.toString(arena.getState().toString().charAt(0)) + arena.getState().toString().toLowerCase().substring(1));
        obj.getScore(ChatColor.BLUE.toString()).setScore(6);

        Team name = board.registerNewTeam("name");
        name.addEntry(ChatColor.RED.toString());
        name.setPrefix("§aArena >");
        name.setSuffix(ChatColor.RED+arena.getName());
        obj.getScore(ChatColor.RED.toString()).setScore(7);

        Team time = board.registerNewTeam("time");
        time.addEntry(ChatColor.DARK_BLUE.toString());
        time.setPrefix("§aTime >");
        time.setSuffix(ChatColor.RED+arena.getTimer().formatTime());
        obj.getScore(ChatColor.DARK_BLUE.toString()).setScore(8);

        Team line = board.registerNewTeam("line");
        line.addEntry("§m");
        line.setPrefix("§m------------");
        line.setSuffix("§m------------");
        obj.getScore("§m").setScore(9);

        p.setScoreboard(board);

    }

    public static void updateScoreboard(Player p){

        Arena arena = Main.getInstance().getArenaManager().getArenaByPlayer(p.getName());

          if(arena != null){

            for(String s : arena.getIngame()){
                Player se = Bukkit.getPlayer(s);
                if(se != null){

                    Scoreboard board = p.getScoreboard();

                    board.getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.AQUA + ChatColor.BOLD.toString() + "SkywarsUHC");

                    board.getTeam("time").setSuffix(ChatColor.RED+arena.getTimer().formatTime());
                    board.getTeam("state").setSuffix(ChatColor.RED+Character.toString(arena.getState().toString().charAt(0)) + arena.getState().toString().toLowerCase().substring(1));
                    board.getTeam("players").setPrefix("§a"+arena.getIngame().size());
                    board.getTeam("kills").setSuffix(ChatColor.RED+String.valueOf(arena.getKills().get(p.getName())));
                    board.getTeam("spec").setSuffix(ChatColor.RED+String.valueOf(arena.getSpectators()).replace("null", "0"));

                }

            }


          }

    }

    public static void ScoreNull(Player p){

        Arena arena = Main.getInstance().getArenaManager().getArenaByPlayer(p.getName());
        if(arena != null) {
            Scoreboard boards = p.getScoreboard();

            Objective obj = boards.registerNewObjective("null", "dummy");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName("§a");



            p.setScoreboard(boards);
        }

    }
}
