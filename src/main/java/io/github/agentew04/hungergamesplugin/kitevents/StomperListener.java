package io.github.agentew04.hungergamesplugin.kitevents;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class StomperListener implements Listener {
    private final HungerGamesPlugin main;
    public StomperListener(HungerGamesPlugin main){
        this.main=main;
    }
    @EventHandler
    public void onPlayerHit(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            World w = player.getWorld();
            Kits kit = main.game.getPlayerKit(player);

            //check kit
            if(kit.equals(Kits.Stomper)){
                //check if fall damage
                if(e.getCause()== EntityDamageEvent.DamageCause.FALL){
                    double damagetaken = e.getDamage();

                    //cancel the damage
                    e.setCancelled(true);
                    w.spawnParticle(Particle.BLOCK_CRACK,player.getLocation(),5);
                    //apply damage to others
                    for(Entity ps: player.getNearbyEntities(2,2,2)){
                        if(ps instanceof Player){
                            if(((Player) ps).isSneaking()){
                                Bukkit.getLogger().info(((Player)ps).getDisplayName()+" tava no shift, nao levou dano");
                                continue;
                            }
                            ((Player) ps).damage(damagetaken,player);
                            Bukkit.getLogger().info(((Player) ps).getDisplayName()+" tomou dano de stomp de: "+player.getDisplayName());

                        }else{
                            Bukkit.getLogger().info("stomper atingiu um "+ps.getName());
                        }
                        w.spawnParticle(Particle.BLOCK_CRACK,ps.getLocation(),5);
                    }
                }
            }
        }
    }
}
