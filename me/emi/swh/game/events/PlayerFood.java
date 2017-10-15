package me.emi.swh.game.events;

import me.emi.swh.Main;
import me.emi.swh.game.Arena;
import me.emi.swh.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerFood implements Listener {

    @EventHandler
    public void onFood(FoodLevelChangeEvent e){
        Player p = (Player)e.getEntity();

        Arena arena = Main.getInstance().getArenaManager().getArenaByPlayer(p.getName());

        if(arena != null){
            if(arena.getState() == GameState.Waiting){
                e.setCancelled(true);
            }
            else if(arena.getState() == GameState.Starter){
                e.setCancelled(false);
            }
        }
    }
}
