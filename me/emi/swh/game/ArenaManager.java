package me.emi.swh.game;


import java.util.HashSet;
import java.util.Set;

public class ArenaManager {

    private Set<Arena> arenas;

    public ArenaManager() {
        arenas = new HashSet<>();
    }

    public void addArena(Arena arena){

        arenas.add(arena);

    }

    public void removeArena(Arena arena){
        arenas.remove(arena);
    }

    public Arena getArenaByName(String name){
        for(Arena arena : arenas){
            if(arena.getName().equals(name)){
                return arena;
            }
        }

        return null;
    }
    public Arena getArenaByPlayer(String name){
        for(Arena arena : arenas){
            if(arena.getIngame().contains(name)){
                return arena;
            }
        }

        return null;
    }

}
