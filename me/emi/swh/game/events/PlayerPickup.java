package me.emi.swh.game.events;

import me.emi.swh.Main;
import me.emi.swh.game.Arena;
import me.emi.swh.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickup implements Listener{

     @EventHandler
    public static void onPickup(PlayerPickupItemEvent e){
         Player p = e.getPlayer();

         Arena arena = Main.getInstance().getArenaManager().getArenaByPlayer(p.getName());
         if(arena != null){
             if(arena.getState() == GameState.Waiting || arena.getState() == GameState.Corn){
                 e.setCancelled(true);
             }
         }
     }

}
