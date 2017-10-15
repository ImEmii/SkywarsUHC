package me.emi.swh.game.events;

import me.emi.swh.Main;
import me.emi.swh.game.Arena;
import me.emi.swh.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDrop implements Listener{

    @EventHandler
    public static void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();

        Arena arena = Main.getInstance().getArenaManager().getArenaByPlayer(p.getName());
        if(arena != null){
            if(arena.getState() == GameState.Waiting || arena.getState() == GameState.Corn){
                e.setCancelled(true);
            }
        }
    }

}
