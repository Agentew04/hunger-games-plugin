package io.github.agentew04.hungergamesplugin;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameUtils {
    private HungerGamesPlugin main;
    public GameUtils(HungerGamesPlugin main){
        this.main=main;
    }
    //region Game variables
    private List<Player> PlayersIngame = new ArrayList<>();
    private List<Player> PlayersReady = new ArrayList<>();
    private Map<Player,Kits> PlayerKit = new HashMap<>();
    private List<Player> AlivePlayers = new ArrayList<>();
    private List<Player> DeadPlayers = new ArrayList<>();
    private boolean IsGameStarted = false;
    private boolean IsGameFinished = false;

    public void addPlayerInGame(Player player){
        if(!PlayersIngame.contains(player)){
            PlayersIngame.add(player);
        }
    }
    public void removePlayerInGame(Player player){
        if(PlayersIngame.contains(player)){
            PlayersIngame.remove(player);
        }
    }
    public List<Player> getPlayersIngame(){
        return PlayersIngame;
    }
    public boolean playersInGameHasPlayer(Player player){
        return PlayersIngame.contains(player);
    }
    public void addReadyPlayer(Player player){
        if(!PlayersReady.contains(player)){
            PlayersReady.add(player);
        }
    }
    public void removeReadyPlayer(Player player){
        if(PlayersReady.contains(player)){
            PlayersReady.remove(player);
        }
    }
    public List<Player> getReadyPlayers(){
        return PlayersReady;
    }
    public boolean playersReadyHasPlayer(Player player){
        return PlayersReady.contains(player);
    }
    public boolean checkReadyPlayer(Player player){
        if(PlayersReady.contains(player)){
            return true;
        }else {
            return false;
        }
    }
    public void addPlayerKit(Player player, Kits kit){
        if(!PlayerKit.containsKey(player)){
            PlayerKit.put(player,kit);
        }else{
            PlayerKit.replace(player, kit);
        }

    }
    public Kits getPlayerKit(Player player){
        if(PlayerKit.containsKey(player)){
            return PlayerKit.get(player);
        }else{
            return Kits.None;
        }
    }
    public void addAlivePlayer(Player player){
        if(!AlivePlayers.contains(player)){
            AlivePlayers.add(player);
        }
    }
    public void removeAlivePlayer(Player player){
        if(AlivePlayers.contains(player)){
            AlivePlayers.remove(player);
        }
    }
    public List<Player> getAlivePlayers(){
        return AlivePlayers;
    }
    public void addDeadPlayer(Player player){
        if(!DeadPlayers.contains(player)){
            DeadPlayers.add(player);
        }
    }
    public void removeDeadPlayer(Player player){
        if(DeadPlayers.contains(player)){
            DeadPlayers.remove(player);
        }
    }
    public List<Player> getDeadPlayers(){
        return DeadPlayers;
    }
    public int getRemainingPlayers(){
        return AlivePlayers.size();
    }
    public boolean getStartStatus(){
        return  IsGameStarted;
    }
    public void setStartStatus(boolean started){
        IsGameStarted=started;
    }
    public boolean getFinishedStatus(){
        return IsGameFinished;
    }
    public void setFinishedStatus(boolean finished){
        IsGameFinished = finished;
    }
}
