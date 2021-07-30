package io.github.agentew04.hungergamesplugin.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;

public class StartCommand implements CommandExecutor {

    private final HungerGamesPlugin main;
    private MultiverseCore core;

    public StartCommand(HungerGamesPlugin main){
        this.main = main;
        core = main.getMV();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //cadatrade all players in config
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();

        for(Player playeringame: players){
            main.game.addPlayerInGame(playeringame);
        }

        Bukkit.broadcastMessage(ChatColor.GREEN+"Gerando o mundo, aguarde...");
        core.deleteWorld("arena");
        CreateWorld();
        //core.getMVWorldManager().addWorld("arena", World.Environment.NORMAL,null,WorldType.NORMAL,true,null);
        Bukkit.broadcastMessage(ChatColor.GREEN+"O novo mundo foi gerado!");
        Bukkit.broadcastMessage(ChatColor.GREEN +"Selecione o seu kit com "+ChatColor.YELLOW+"/kit"+ChatColor.GREEN+" e digite "+ ChatColor.YELLOW +"/ready");
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
        if(biome == Biome.OCEAN ||biome == Biome.COLD_OCEAN ||biome == Biome.DEEP_COLD_OCEAN ||
                biome == Biome.DEEP_FROZEN_OCEAN ||biome == Biome.DEEP_OCEAN ||biome == Biome.DEEP_LUKEWARM_OCEAN ||
                biome == Biome.DEEP_WARM_OCEAN ||biome == Biome.FROZEN_OCEAN ||biome == Biome.LUKEWARM_OCEAN ||
                biome == Biome.WARM_OCEAN){
            return true;
        }else{
            return false;
        }
    }
}
