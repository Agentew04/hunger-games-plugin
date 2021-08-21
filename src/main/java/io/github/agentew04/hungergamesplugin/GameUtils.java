package io.github.agentew04.hungergamesplugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GameUtils {
    public GameUtils(){
    }
    //region Game variables
    private final List<UUID> PlayersReady = new ArrayList<>();
    private final Map<UUID,Kits> PlayerKit = new HashMap<>();
    private final List<UUID> Players = new ArrayList<>();
    private final List<UUID> Spectators = new ArrayList<>();
    private boolean IsGameStarted = false;

    private UUID Gladiator =null;
    private UUID Gladiated =null;
    private Location GladiatorLastPos=null;
    private Location GladiatedLastPos=null;
    private boolean IsGladiatorStarted=false;

    public void addReadyPlayer(Player player){
        UUID id = player.getUniqueId();
        if(!PlayersReady.contains(id)){
            PlayersReady.add(id);
        }
    }
    public void removeReadyPlayer(Player player){
        UUID id = player.getUniqueId();
        PlayersReady.remove(id);
    }
    public List<Player> getReadyPlayers(){List<Player> players = new ArrayList<>();
        for (UUID id:PlayersReady) {
            players.add(Bukkit.getPlayer(id));
        }
        return players;
    }
    public boolean playersReadyHasPlayer(Player player){
        return PlayersReady.contains(player.getUniqueId());
    }
    public boolean checkReadyPlayer(Player player){
        return PlayersReady.contains(player.getUniqueId());
    }
    public void setPlayerKit(Player player, Kits kit){
        UUID id = player.getUniqueId();
        if(!PlayerKit.containsKey(id)){
            PlayerKit.put(id,kit);
        }else{
            PlayerKit.replace(id, kit);
        }

    }
    public Kits getPlayerKit(Player player){
        UUID id = player.getUniqueId();
        return PlayerKit.getOrDefault(id, Kits.None);
    }
    public void addAlivePlayer(Player player){
        UUID id = player.getUniqueId();
        if(!Players.contains(id)){
            Players.add(id);
        }
    }
    public void removeAlivePlayer(Player player){
        UUID id = player.getUniqueId();
        Players.remove(id);
    }
    public List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        for (UUID id: Players) {
            players.add(Bukkit.getPlayer(id));
        }
        return players;
    }
    public void addDeadPlayer(Player player){
        UUID id = player.getUniqueId();
        if(!Spectators.contains(id)){
            Spectators.add(id);
        }
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(new Location(Bukkit.getWorld("arena"),0,62,0));
    }
    public void removeDeadPlayer(Player player){
        UUID id = player.getUniqueId();
        Spectators.remove(id);
    }
    public List<Player> getSpectators(){
        List<Player> players = new ArrayList<>();
        for (UUID id: Spectators) {
            players.add(Bukkit.getPlayer(id));
        }
        return players;
    }
    public int getRemainingPlayers(){
        return Players.size();
    }
    public boolean getStartStatus(){
        return  IsGameStarted;
    }
    public void setStartStatus(boolean started){
        IsGameStarted=started;
    }
    public void setGladiator(@Nullable Player player){
        if(player==null){
            Gladiator=null;
        }else{
            Gladiator =player.getUniqueId();
        }
    }
    public Player getGladiator(){
        return Bukkit.getPlayer(Gladiator);
    }
    public void setGladiatorLastPos(Location pos){
        GladiatorLastPos =pos;
    }
    public Location getGladiatorLastPos(){
        return GladiatorLastPos;
    }
    public void setGladiatedLastPos(Location pos){
        GladiatedLastPos = pos;
    }
    public Location getGladiatedLastPos(){
        return GladiatedLastPos;
    }
    public void setGladiated(Player player){
        Gladiated =player.getUniqueId();
    }
    public Player getGladiated(){
        return Bukkit.getPlayer(Gladiated);
    }
    public boolean getGladiatorStatus(){
        return IsGladiatorStarted;
    }
    public void setGladiatorStatus(boolean gladiatorStatus){
        IsGladiatorStarted=gladiatorStatus;
    }

    public void StartBorder(){
        WorldBorder wb = Bukkit.getWorld("arena").getWorldBorder();
        BukkitRunnable b1 = new BukkitRunnable() {
            @Override
            public void run() {
                
            }
        };
        BukkitRunnable b2 = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
        BukkitRunnable b3 = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
        BukkitRunnable b4 = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
        BukkitRunnable b5 = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
        BukkitRunnable b6 = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
        BukkitRunnable b7 = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
        b1.run();
    }

}
