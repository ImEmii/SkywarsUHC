package me.emi.swh.game;

import me.emi.swh.Main;
import me.emi.swh.game.events.PlayerPlace;
import me.emi.swh.game.scoreboards.WaitingBore;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable implements Listener{

    private Arena arena;

    private int lobbyTimeLeft;
    private int gameTimeLeft;
    private int endTimeLeft;
    private int restartingTimeLeft;

    private boolean pause;

    public Timer(Arena arena) {

        this.arena = arena;

        this.lobbyTimeLeft = 10;
        this.gameTimeLeft = 300;
        this.endTimeLeft = 5;
        this.restartingTimeLeft = 5;

        this.pause = true;

        this.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    @Override
    public void run() {
        if(!pause){
            switch (arena.getState()){
                case Waiting:
                    if(lobbyTimeLeft != 0){
                        lobbyTimeLeft--;
                    }else{

                        for(int i=0; i<arena.getIngame().size(); i++){
                            Bukkit.getPlayer(arena.getIngame().get(i)).teleport(arena.getSpawns().get(i));
                        }


                        arena.setState(GameState.Starter);
                        arena.updateSign();
                        //TODO: Send players to spawn
                    }
                    for(String s : arena.getIngame()){
                        Player p = Bukkit.getPlayer(s);
                        if (p != null){
                            WaitingBore.updateScoreboard(p);
                        }
                    }
                    break;
                case Starter:
                    if(gameTimeLeft != 0){
                        gameTimeLeft--;
                    }else{
                        arena.setState(GameState.End);
                        arena.updateSign();
                       if(arena.getIngame().size() != 0){
                           for(String s : arena.getIngame()){
                               Player p = Bukkit.getPlayer(s);
                               if (p != null){

                               }
                           }
                       }
                        //TODO: Send players to spawn
                    }
                    for(String s : arena.getIngame()){
                        Player p = Bukkit.getPlayer(s);
                        if (p != null){
                            WaitingBore.updateScoreboard(p);
                        }
                    }
                    break;
                case End:
                    if(endTimeLeft != 0){
                        endTimeLeft--;
                    }else{
                        arena.setState(GameState.Restarting);
                        arena.updateSign();

                        //TODO: Send players to spawn
                    }
                    for(String s : arena.getIngame()){
                        Player p = Bukkit.getPlayer(s);
                        if (p != null){
                            WaitingBore.updateScoreboard(p);
                            arena.endArena(p);
                        }
                    }
                    break;
                case Restarting:
                    if(restartingTimeLeft != 0){
                        restartingTimeLeft--;
                    }else{
                        arena.setState(GameState.Waiting);
                        arena.updateSign();
                        setPause(true);

                    }
                    for(String s : arena.getIngame()){
                        Player p = Bukkit.getPlayer(s);
                        if (p != null){
                            WaitingBore.updateScoreboard(p);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public int getMinutes(){
        switch (arena.getState()){
            case Waiting:
                return lobbyTimeLeft / 60;

            case Starter:
                return gameTimeLeft / 60;

            case End:
                return endTimeLeft / 60;

            case Restarting:
                return restartingTimeLeft / 60;

            default:
                return 0;

        }
    }
    public int getSeconds(){
        switch (arena.getState()){
            case Waiting:
                return lobbyTimeLeft % 60;

            case Starter:
                return gameTimeLeft % 60;

            case End:
                return endTimeLeft % 60;
            case Restarting:
                return restartingTimeLeft % 60;

            default:
                return 0;

        }
    }
    public String formatTime(){
        int iminutes = getMinutes(), iseconds = getSeconds();
        String minutes = String.valueOf(getMinutes()), seconds = String.valueOf(getSeconds());
        if(iminutes < 10){
            minutes = "0".concat(minutes);
        }

        if(iseconds < 10){
            seconds = "0".concat(seconds);
        }

        return minutes + ":" + seconds;
    }
}
