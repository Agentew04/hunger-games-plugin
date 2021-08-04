package io.github.agentew04.hungergamesplugin.events;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GoldenAppleListener implements Listener {

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e){
        if(e.getItem().getType()!=Material.GOLDEN_APPLE){
            return;
        }
        Player player = e.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,120*20,1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,7*20,1));
        e.setCancelled(true);
        ItemStack item = e.getItem();
        if(item.getAmount()==1){
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }else{
            item.setAmount(item.getAmount()-1);
        }
        //player.setItemInHand(item);
        player.setFoodLevel(player.getFoodLevel()+4);
    }
}
