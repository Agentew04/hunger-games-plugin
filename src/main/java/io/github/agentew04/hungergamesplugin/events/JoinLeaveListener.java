package io.github.agentew04.hungergamesplugin.events;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    private final HungerGamesPlugin main;
    public JoinLeaveListener(HungerGamesPlugin main){
        this.main=main;
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if(!main.game.getStartStatus()){
            main.game.removePlayerInGame(player);
            e.setQuitMessage(ChatColor.RED+player.getDisplayName()+" saiu do jogo");
        }else{
            e.setQuitMessage(ChatColor.DARK_RED+player.getDisplayName()+" se desconectou");
            main.game.removeAlivePlayer(player);
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if(main.game.getStartStatus()){
            main.game.addAlivePlayer(player);
            e.setJoinMessage(ChatColor.DARK_GREEN +player.getDisplayName()+" se reconectou");
        }else{
            main.game.addPlayerInGame(player);
            e.setJoinMessage(ChatColor.GREEN +player.getDisplayName()+" entrou no jogo");
        }
    }
}
