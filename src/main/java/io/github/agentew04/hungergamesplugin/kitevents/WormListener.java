package io.github.agentew04.hungergamesplugin.kitevents;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WormListener implements Listener {
    private final HungerGamesPlugin main;
    public WormListener(HungerGamesPlugin main){
        this.main=main;
    }

    public void updatePlayerLife(Player player, int dirtamount){
        //convert dirt to amount of half hearts
        int bonushalfhearts= (int) Math.ceil((10*dirtamount)/1152.0);
        if(!assertKit(player)){
            return;
        }
        Collection<AttributeModifier> modifiers = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getModifiers();
        if((modifiers.size()!=0)){
            for (AttributeModifier mod:modifiers) {
                Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).removeModifier(mod);
            }
        }
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).addModifier(new AttributeModifier("GENERIC_MAX_HEALTH",bonushalfhearts, AttributeModifier.Operation.ADD_NUMBER));
    }
    public boolean assertKit(Object o){
        return main.game.getPlayerKit((Player) o) == Kits.Worm;
    }
    public int countDirt(Player player){
        int dirtamount = 0;
        HashMap<Integer, ? extends ItemStack> dirtstacks = player.getInventory().all(Material.DIRT);

        for (Map.Entry<Integer, ? extends ItemStack> slot: dirtstacks.entrySet()) {
            dirtamount += slot.getValue().getAmount();
        }
        return dirtamount;
    }
    @EventHandler
    public void onPickUp(EntityPickupItemEvent e){
        if(e.getEntity() instanceof Player){
            if(assertKit(e.getEntity()) ||
            e.getItem().getItemStack().getType()==Material.DIRT){
                Player player = (Player) e.getEntity();
                updatePlayerLife(player,countDirt(player));
            }
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(e.getItemDrop().getItemStack().getType()==Material.DIRT){
            if(assertKit(e.getPlayer())){
                updatePlayerLife(e.getPlayer(),countDirt(e.getPlayer()));
            }
        }
    }
    @EventHandler
    public void onDrag(InventoryDragEvent e){
        if(e.getWhoClicked() instanceof Player && e.getOldCursor().getType()==Material.DIRT){
            if(assertKit(e.getWhoClicked())){
                updatePlayerLife((Player)e.getWhoClicked(),countDirt((Player)e.getWhoClicked()));
            }
        }
    }

}
