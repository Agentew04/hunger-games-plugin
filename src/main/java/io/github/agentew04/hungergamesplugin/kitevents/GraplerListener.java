package io.github.agentew04.hungergamesplugin.kitevents;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import io.github.agentew04.hungergamesplugin.RayTrace;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class GraplerListener implements Listener {
    private HungerGamesPlugin main;
    public GraplerListener(HungerGamesPlugin main){
        this.main=main;
    }

    @EventHandler
    public void onGrapple(PlayerInteractEvent e){
        if(!e.hasItem()){
            return;
        }
        if(e.getItem().getType()== Material.TRIPWIRE_HOOK && main.game.getPlayerKit(e.getPlayer())== Kits.Grapler){
            Player player =e.getPlayer();
            Location eye = player.getEyeLocation();
            RayTrace rt = new RayTrace(eye.toVector(),eye.getDirection());
            Location finalpos = rt.getPostion(30).toLocation(player.getWorld(),eye.getYaw(),eye.getPitch());
            player.teleport(finalpos, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }
}
