package io.github.agentew04.hungergamesplugin.kitevents;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class KangaroListener implements Listener {
    private HungerGamesPlugin main;
    public KangaroListener(HungerGamesPlugin main){
        this.main=main;
    }

    @EventHandler
    public void onJump(PlayerInteractEvent e){
        if(!e.hasItem()){
            return;
        }
        if(e.getItem().getType()== Material.FIREWORK_ROCKET && main.game.getPlayerKit(e.getPlayer())== Kits.Kangaro) {
            Player player = e.getPlayer();
            Vector v = player.getLocation().getDirection();
            v.multiply(5);
            player.setVelocity(v);
            e.setCancelled(true);
        }
    }
}
