package io.github.agentew04.hungergamesplugin.kitevents;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import io.github.agentew04.hungergamesplugin.raycast.Raycast;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.Objects;

public class GraplerListener implements Listener {
    private final HungerGamesPlugin main;
    public GraplerListener(HungerGamesPlugin main){
        this.main=main;
    }

    @EventHandler
    public void onGrapple(PlayerInteractEvent e){
        if(!e.hasItem()){
            return;
        }
        if(Objects.requireNonNull(e.getItem()).getType()== Material.TRIPWIRE_HOOK && main.game.getPlayerKit(e.getPlayer())== Kits.Grapler){
            Player player =e.getPlayer();
            Location eye = player.getEyeLocation();
            float oldyaw = eye.getYaw();
            float oldpitch = eye.getPitch();
            Raycast rc = new Raycast(eye,40);
            if(rc.compute(Raycast.RaycastType.ENTITY)){
                Location newloc = rc.getHurtLocation();
                if(rc.getHurtEntity() instanceof Player) {
                    Player playerattacked = (Player) rc.getHurtEntity();
                    if (playerattacked == player) {
                        return;
                    }
                    World w = Bukkit.getWorld("arena");
                    ArrayList<Location> raypos = rc.getTestedLocations();
                    for(Location loc:raypos){
                        w.spawnParticle(Particle.CRIT,loc,5);
                    }
                    newloc.setYaw(oldyaw);
                    newloc.setPitch(oldpitch);
                    player.teleport(newloc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    player.sendMessage(ChatColor.GREEN + "Ziip");
                    playerattacked.sendMessage(ChatColor.GREEN+"Ziip");
                }else{
                    //on entity
                    World w = Bukkit.getWorld("arena");
                    ArrayList<Location> raypos = rc.getTestedLocations();
                    for(Location loc:raypos){
                        w.spawnParticle(Particle.CRIT,loc,5);
                    }
                    newloc.setYaw(oldyaw);
                    newloc.setPitch(oldpitch);
                    player.teleport(newloc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    player.sendMessage(ChatColor.GREEN + "Ziip");
                }
            }else{
                player.sendMessage(ChatColor.RED+"N??o consegui acertar!");
            }
            e.setCancelled(true);
        }
    }
}
