package io.github.agentew04.hungergamesplugin.events;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
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
        e.quitMessage(main.getQuitMessage(player));

        if(main.game.getStartStatus()){
            main.game.removeAlivePlayer(player);
        }else{
            main.game.removePlayerInGame(player);
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        e.joinMessage(main.getJoinMessage(player));
        if(main.game.getStartStatus()){
            main.game.addAlivePlayer(player);
        }else{
            main.game.addPlayerInGame(player);
        }
    }
}
