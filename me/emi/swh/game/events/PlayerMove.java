package me.emi.swh.game.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener{

    @EventHandler
    public static void onPlayerMove(PlayerMoveEvent e){
        Player p = e.getPlayer();

        Location to = e.getTo();

        if(to.getBlockY()) {

        }



    }


}
