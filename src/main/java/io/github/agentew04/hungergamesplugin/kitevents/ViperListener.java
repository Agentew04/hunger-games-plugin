package io.github.agentew04.hungergamesplugin.kitevents;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class ViperListener implements Listener {

    private HungerGamesPlugin main;
    private Random rng;
    public ViperListener(HungerGamesPlugin main){
        this.main=main;
        rng =new Random();
    }
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            Kits kit = main.game.getPlayerKit(player);
            if(kit.equals(Kits.Viper)){
                if(rng.nextFloat()<=33){
                    LivingEntity damaged =  (LivingEntity) e.getEntity();
                    damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON,3*20,0));
                }
            }
        }
    }

}
