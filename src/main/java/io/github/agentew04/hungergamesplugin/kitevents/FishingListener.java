package io.github.agentew04.hungergamesplugin.kitevents;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class FishingListener implements Listener {
    private HungerGamesPlugin main;
    public FishingListener(HungerGamesPlugin main){
        this.main=main;
    }
    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if(main.game.getPlayerKit(player)== Kits.Fisherman){
            if (event.getCaught() instanceof Player) {
                final Player caught = (Player) event.getCaught();
                if (player.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD) {
                    player.sendMessage(ChatColor.RED + "GET OVER HERE!");
                    caught.teleport(player.getLocation());
                }
            }
        }
    }
}
