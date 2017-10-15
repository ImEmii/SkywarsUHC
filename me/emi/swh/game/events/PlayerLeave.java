package me.emi.swh.game.events;

import me.emi.swh.Main;
import me.emi.swh.game.Arena;
import me.emi.swh.game.scoreboards.WaitingBore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener{


    @EventHandler
    public static void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        Arena arena = Main.getInstance().getArenaManager().getArenaByPlayer(p.getName());

        if(arena != null){
            arena.leavePlayer(p);
            arena.broadcast("§e"+p.getName()+" Se desconecto");
            for(String s : arena.getIngame()){
                Player pl = Bukkit.getPlayer(s);
                WaitingBore.updateScoreboard(pl);

            }

        }
    }

}
