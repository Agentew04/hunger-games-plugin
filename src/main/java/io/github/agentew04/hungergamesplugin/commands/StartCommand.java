package io.github.agentew04.hungergamesplugin.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class StartCommand implements CommandExecutor {

    private final HungerGamesPlugin main;
    private final MultiverseCore core;

    public StartCommand(HungerGamesPlugin main){
        this.main = main;
        core = main.getMV();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //cadatrade all players in config
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();

        Bukkit.broadcast(Component.text("Gerando o mundo, aguarde...", NamedTextColor.GREEN));

        core.deleteWorld("arena");
        CreateWorld();
        //core.getMVWorldManager().addWorld("arena", World.Environment.NORMAL,null,WorldType.NORMAL,true,null);
        Bukkit.broadcast(Component.text("O novo mundo foi gerado!", NamedTextColor.GREEN));
        Component msg = Component.text("Selecione o seu kit com ",NamedTextColor.GREEN)
                .append(Component.text("/kit",NamedTextColor.YELLOW))
                .append(Component.text(" e digite ",NamedTextColor.GREEN))
                .append(Component.text("/ready",NamedTextColor.YELLOW));
        Bukkit.broadcast(msg);
        return true;
    }
    public void CreateWorld(){
        core.getMVWorldManager().addWorld("arena", World.Environment.NORMAL,null,WorldType.NORMAL,true,null);
        World arena = Bukkit.getWorld("arena");
        while(isOcean(arena.getBiome(0,62,0))){
            core.getMVWorldManager().deleteWorld("arena");
            core.getMVWorldManager().addWorld("arena", World.Environment.NORMAL,null,WorldType.NORMAL,true,null);
            arena = Bukkit.getWorld("arena");
        }
    }
    public boolean isOcean(Biome biome){
        return biome == Biome.OCEAN || biome == Biome.COLD_OCEAN || biome == Biome.DEEP_COLD_OCEAN ||
                biome == Biome.DEEP_FROZEN_OCEAN || biome == Biome.DEEP_OCEAN || biome == Biome.DEEP_LUKEWARM_OCEAN ||
                biome == Biome.DEEP_WARM_OCEAN || biome == Biome.FROZEN_OCEAN || biome == Biome.LUKEWARM_OCEAN ||
                biome == Biome.WARM_OCEAN;
    }
}
