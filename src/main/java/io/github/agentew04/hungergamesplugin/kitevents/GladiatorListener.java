package io.github.agentew04.hungergamesplugin.kitevents;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class GladiatorListener implements Listener {
    private final HungerGamesPlugin main;

    public GladiatorListener(HungerGamesPlugin main){
        this.main=main;
    }

    @EventHandler
    public void onNetheriteHoe(PlayerInteractEntityEvent e){
        if(e.getPlayer().getInventory().getItemInMainHand().getType()==Material.AIR ||
        !(e.getRightClicked() instanceof Player)){
            return;
        }
        if(e.getPlayer().getInventory().getItemInMainHand().getType()== Material.NETHERITE_HOE && main.game.getPlayerKit(e.getPlayer())== Kits.Gladiator){
            if(main.game.getGladiatorStatus()){
                e.getPlayer().sendMessage(ChatColor.RED+"Um x1 já está em progesso!");
                return;
            }

            Player clicker = e.getPlayer();
            Player clicked = (Player) e.getRightClicked();
            main.game.setGladiator(clicker);
            main.game.setGladiatorLastPos(clicker.getLocation());
            main.game.setGladiated(clicked);
            main.game.setGladiatedLastPos(clicked.getLocation());
            main.game.setGladiatorStatus(true);
            World world = Bukkit.getWorld("glad");

            clicked.teleport(new Location(world,238.5,-59,67.5,0,0));
            clicker.teleport(new Location(world,238.5,-59,95.5,180,0));
            Bukkit.broadcastMessage(ChatColor.YELLOW+clicker.getDisplayName()+ChatColor.GREEN+" desafiou "+ChatColor.YELLOW+clicked.getDisplayName()+ChatColor.GREEN+" para um x1!");
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player player = e.getEntity();
        if(player.equals(main.game.getGladiator())){
            //gladiator died
            Bukkit.broadcastMessage(ChatColor.YELLOW+main.game.getGladiated().getDisplayName() + ChatColor.GREEN+
                    " se sobresaiu e venceu "
                    + ChatColor.YELLOW+player.getDisplayName()
                    + ChatColor.GREEN+"!!!");

            //tp alive back
            main.game.getGladiated().teleport(main.game.getGladiatedLastPos());

            //reset for next
            main.game.setGladiator(null);
            main.game.setGladiated(null);
            main.game.setGladiatorStatus(false);

        }else if(player.equals(main.game.getGladiated())) {
            //gladiated died
            Bukkit.broadcastMessage(ChatColor.YELLOW+main.game.getGladiator().getDisplayName() + ChatColor.GREEN+
                    " foi mais forte e derrotou "
                    + ChatColor.YELLOW+player.getDisplayName()
                    + ChatColor.GREEN+"!!!");

            //tp alive back
            main.game.getGladiator().teleport(main.game.getGladiatorLastPos());

            //reset for next
            main.game.setGladiator(null);
            main.game.setGladiated(null);
            main.game.setGladiatorStatus(false);
        }
    }
}
