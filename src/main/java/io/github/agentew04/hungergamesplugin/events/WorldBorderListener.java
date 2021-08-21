package io.github.agentew04.hungergamesplugin.events;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.papermc.paper.event.world.border.WorldBorderBoundsChangeFinishEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WorldBorderListener implements Listener {
    private final HungerGamesPlugin main;
    public WorldBorderListener(HungerGamesPlugin main){
        this.main=main;
    }
    @EventHandler
    public void onBorderFinishChange(WorldBorderBoundsChangeFinishEvent e){
        //TODO add border change
    }
}
