package me.emi.swh.game.events;

import me.emi.swh.Main;
import me.emi.swh.game.Arena;
import me.emi.swh.game.GameState;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener{

    @EventHandler
    public void onMove(PlayerMoveEvent e){

        Location to = e.getTo();
        Location from = e.getFrom();

        Arena arena = Main.getInstance().getArenaManager().getArenaByPlayer(e.getPlayer().getName());

        if(arena != null){
            if(arena.getState()  == GameState.Corn){
                if(to.getX() != from.getX() ||to.getZ() != from.getZ()||to.getY() != from.getY()){
                    e.setCancelled(true);
                }
            }
        }

    }

}
