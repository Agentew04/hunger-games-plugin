package io.github.agentew04.hungergamesplugin.events;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ResoupListener implements Listener {
    public final static int HealAmount = 6;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e==null || e.getPlayer()==null || e.getItem()==null || e.getItem().getType()==null){
            return;
        }
        Player player = e.getPlayer();
        int maxHealth = (int)player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
        ItemStack hand = e.getItem();//player.getInventory().getItemInMainHand();
        if(hand.getType() == Material.MUSHROOM_STEW){
            if(player.getHealth()==maxHealth){
                player.sendMessage("Você já está com vida cheia!");
                return;
            }
            if(player.getHealth()>maxHealth-HealAmount){
                player.setHealth(maxHealth);
            }else{
                player.setHealth(player.getHealth() + HealAmount);
            }
            e.getItem().setType(Material.BOWL);
            e.getItem().removeEnchantment(Enchantment.ARROW_INFINITE);
            ItemMeta meta = e.getItem().getItemMeta();
            meta.displayName(Component.text(meta.getLocalizedName()));
            e.getItem().setItemMeta(meta);
        }
    }
}
