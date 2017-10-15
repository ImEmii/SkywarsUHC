package me.emi.swh.game.events;

import me.emi.swh.Main;
import me.emi.swh.game.Arena;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class SignChange implements Listener{

    @EventHandler
    public void onSignChange(SignChangeEvent e){
        if(e.getLine(0).equals("[swh]")){
            Arena arena = Main.getInstance().getArenaManager().getArenaByName(e.getLine(1));

            if(arena != null){

                e.setLine(0, "§b[SkywarsUHC]");

                e.setLine(1, "§b"+arena.getName());

                e.setLine(2, "§a"+arena.getState().toString());

                e.setLine(3, "§e"+arena.getIngame().size()+"/"+arena.getMaxPlayers());

                Main.getInstance().getConfig().set("arenas."+arena.getName()+".sign",
                        Main.getInstance().serializeLocation(e.getBlock().getLocation()));

                arena.setSign((Sign)e.getBlock().getState());
                Main.getInstance().saveConfig();

            }
        }
    }


}
