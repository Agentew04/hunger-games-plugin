package io.github.agentew04.hungergamesplugin.events;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {
    private final HungerGamesPlugin main;
    public DeathListener(HungerGamesPlugin main){
        this.main=main;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        if(main.game.getAlivePlayers().contains(player)){
            main.game.removeAlivePlayer(player);
            main.game.addDeadPlayer(player);
            Bukkit.getLogger().info(player.displayName()+" adicionado na equipe dos mortos");
        }
    }
}
