package me.emi.swh.game.events;

import me.emi.swh.Main;
import me.emi.swh.game.Arena;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener{

    private Main main;

    public PlayerInteract(Main main){
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        Player p = e.getPlayer();

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST){

                FileConfiguration config = Main.getInstance().getConfig();

                String block = Main.getInstance().serializeLocation(e.getClickedBlock().getLocation());

                for(String arena : config.getConfigurationSection("arenas").getKeys(true)){

                    Arena arenaObj = Main.getInstance().getArenaManager().getArenaByName(arena);

                    if(arenaObj != null){
                        if(config.getString("arenas."+arena+".sign").equals(block)){

                            if(arenaObj.getSign() != null){

                                arenaObj.joinPlayer(p);


                                arenaObj.getSign().setLine(3, "§e"+arenaObj.getIngame().size()+"/"+arenaObj.getMaxPlayers());

                                arenaObj.getSign().update(true);

                            }
                        }
                    }
                }
            }
        }



    }

}
