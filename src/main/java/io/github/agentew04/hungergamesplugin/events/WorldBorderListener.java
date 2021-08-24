package io.github.agentew04.hungergamesplugin.events;

import io.papermc.paper.event.world.border.WorldBorderBoundsChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class WorldBorderListener implements Listener {
    public WorldBorderListener(){
    }


    @EventHandler
    public void onBorderFinishChange(WorldBorderBoundsChangeEvent e){
        Component msg=Component.text("A barreira vai fechar para ",NamedTextColor.GREEN)
                .append(Component.text(e.getNewSize(),NamedTextColor.GREEN ,TextDecoration.BOLD))
                .append(Component.text(" blocos em ",NamedTextColor.GREEN))
                .append(Component.text(e.getDuration(),NamedTextColor.GREEN,TextDecoration.ITALIC))
                .append(Component.text(" segundos",NamedTextColor.GREEN));
        Bukkit.broadcast(msg);
    }
}
