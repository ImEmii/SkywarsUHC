package me.emi.swh.game;

import me.emi.swh.Main;
import me.emi.swh.game.scoreboards.WaitingBore;
import me.emi.swh.utils.ConfigMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arena {

    private Timer timer;

    private String name;

    private GameState state;

    private List<String> ingame;

    private int maxPlayers;

    private Sign sign;

    private Location lobbySpawn;
    private Location mainLobbySpawn;

    private List<Location> spawns;

    public Map<String, Integer>Scenarios;

   public ArrayList<String> spectators;

    public Map<String, Integer> kills;

    public Arena(String name, Location lobbySpawn,Location mainLobbySpawn,List<Location> spawns,int maxPlayers, Sign sign) {
        this.name = name;
        this.maxPlayers = maxPlayers;

        this.mainLobbySpawn = mainLobbySpawn;
        this.lobbySpawn = lobbySpawn;
        this.spawns = spawns;

        this.state = GameState.Waiting;

        this.kills = new HashMap<>();

        this.Scenarios = new HashMap<>();

        this.sign = sign;

        this.timer = new Timer(this);
        this.ingame = new ArrayList<>();

        Main.getInstance().getArenaManager().addArena(this);

    }

    public Timer getTimer() {
        return timer;
    }


    public String getName() {
        return name;
    }


    public List<String> getIngame() {
        return ingame;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }


    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Sign getSign() {
        return sign;
    }

    public Location getLobbySpawn() {
        return lobbySpawn;
    }

    public void setLobbySpawn(Location lobbySpawn) {
        this.lobbySpawn = lobbySpawn;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public void broadcast(String message){

        for(String players : ingame){
            Player jugadores = Bukkit.getPlayer(players);
            jugadores.sendMessage(message);
        }

    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public void setSpawns(List<Location> spawns) {
        this.spawns = spawns;
    }

    public void addSpawn(Location location){
        spawns.add(location);
    }

    public ArrayList<String> getSpectators() {
        return spectators;
    }


    public Location getMainLobbySpawn() {
        return Main.getInstance().unserializeLocation(Main.getInstance().getConfig().getString("arenas."+getName()+".mainlobby"));
    }

    public void addSpectator(Player p){
        if(!spectators.contains(p.getName())){
            spectators.add(p.getName());
        }
    }

    public void setMainLobbySpawn(Location mainLobbySpawn) {
        this.mainLobbySpawn = mainLobbySpawn;
    }

    public void joinPlayer(Player p) {
        if (getIngame().size() < maxPlayers){
            if(!getIngame().contains(p.getName())){
                if(getState() == GameState.Waiting){

                    getIngame().add(p.getName());
                    broadcast("§a"+p.getName()+"entro al juego"+getIngame().size()+"/"+getMaxPlayers());
                    kills.put(p.getName(), 0);

                    WaitingBore.onWaiting(p);

                    p.teleport(getLobbySpawn());
                    if(getIngame().size() == Main.getInstance().getConfig().getInt("arenas." + getName() + ".minplayers")){
                        getTimer().setPause(false);
                    }



                    updateSign();
                    for(String s : getIngame()){
                        WaitingBore.updateScoreboard(Bukkit.getPlayer(s));
                    }


                }else{
                    p.sendMessage("The game is already started");
                }

            }else{
                p.sendMessage("You are ready in the match");
            }
        }else{
            p.sendMessage("The game has max players");
        }
    }
    public void leavePlayer(Player p){
        if(getIngame().contains(p.getName())){
            WaitingBore.ScoreNull(p);
            getIngame().remove(p.getName());

            updateSign();
            p.teleport(getMainLobbySpawn());

            for(String s : getIngame()){
               if(this != null){
                   WaitingBore.updateScoreboard(Bukkit.getPlayer(s));
                   Bukkit.getPlayer(s).sendMessage(ConfigMessages.getConfig().getString("messages.playerleave").replace("&", "§").replace("{player}", p.getName())
                           .replace("{players}", String.valueOf(getIngame())).replace("{maxplayers}", String.valueOf(getMaxPlayers())));
               }
            }

        }
    }
    public void endArena(Player p){
        if(getState() == GameState.End){
            if(this != null){
                if(getIngame().contains(p.getName())){
                    WaitingBore.ScoreNull(p);
                        List<String> msg = Main.getInstance().getConfig().getStringList("messages.winnermessage");

                            getIngame().remove(p.getName());
                           if(p != null){
                               for(int i=0; i<msg.size();i++){
                                   String text = msg.get(i);
                                   p.sendMessage(ChatColor.translateAlternateColorCodes('&', text.replaceAll("%player%", p.getName()).replaceAll("%kills", String.valueOf(getKills().get(p.getName())))));
                               }
                           }

                    updateSign();
                    p.teleport(getMainLobbySpawn());

                }
            }
        }
    }

    public void updateSign() {
        getSign().setLine(0, "§b[SkywarsUHC]");

        getSign().setLine(1, "§b"+getName());

        getSign().setLine(2, "§a"+getState().toString());

        getSign().setLine(3, "§e"+getIngame().size()+"/"+getMaxPlayers());

        getSign().update(true);


    }

    public Map<String, Integer> getKills() {
        return kills;
    }


    public static StringBuilder getWin(Player p){

        StringBuilder winner = new StringBuilder("§6SkywarsUHC stats");


        return getWin(p);
    }
}
