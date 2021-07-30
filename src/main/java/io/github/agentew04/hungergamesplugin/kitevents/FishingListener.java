package io.github.agentew04.hungergamesplugin.kitevents;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class FishingListener implements Listener {
    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        Bukkit.getLogger().info("pescador:"+player.getDisplayName());
        if (event.getCaught() instanceof Player) {
            Bukkit.getLogger().info("é instancia");
            final Player caught = (Player) event.getCaught();
            Bukkit.getLogger().info("player caugth:"+caught.getDisplayName());
            if (player.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD) {
                player.sendMessage(ChatColor.RED + "GET OVER HERE!");
                caught.teleport(player.getLocation());
                Bukkit.getLogger().info("deu tp");
            }
        }
    }
}
