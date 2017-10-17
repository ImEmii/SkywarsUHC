package me.emi.swh.game.events;

import me.emi.swh.Main;
import me.emi.swh.game.Arena;
import me.emi.swh.game.scoreboards.WaitingBore;
import me.emi.swh.utils.ConfigMessages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener{

    String prefix = ConfigMessages.getConfig().getString("prefixs.spectator").replace("&", "§");



    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player p = (Player)e.getEntity();

        Player killer = (Player)p.getKiller();

        Arena arena = Main.getInstance().getArenaManager().getArenaByPlayer(p.getName());
        if(arena != null){
            if(killer != null){
                arena.broadcast("§e"+p.getName()+"§a was killed by §e"+killer.getName());
                arena.getKills().put(killer.getName(), arena.getKills().get(killer.getName()) +1);
                WaitingBore.updateScoreboard(p);
                p.setDisplayName(prefix+p.getName());
                arena.addSpectator(p);
            }else {
                arena.addSpectator(p);
                arena.broadcast("§e"+p.getName()+"§a has died");
                p.setDisplayName(prefix+p.getName());
                for(String s : arena.getIngame()){

                }
            }
        }

    }

}
