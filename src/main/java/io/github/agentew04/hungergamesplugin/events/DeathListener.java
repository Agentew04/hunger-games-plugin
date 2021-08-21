package io.github.agentew04.hungergamesplugin.events;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {
    private final HungerGamesPlugin main;
    public DeathListener(HungerGamesPlugin main){
        this.main=main;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Component msg = Component.text("Alguém morreu! Só restam ",NamedTextColor.RED)
                .append(Component.text(main.game.getRemainingPlayers(),NamedTextColor.DARK_RED))
                .append(Component.text(" sobrando.",NamedTextColor.RED));
        e.deathMessage(msg);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        if(main.game.getPlayers().contains(player)){
            main.game.removeAlivePlayer(player);
            main.game.addDeadPlayer(player);
            Bukkit.getLogger().info(player.displayName()+" adicionado na equipe dos mortos");
        }
    }
}
