package io.github.agentew04.hungergamesplugin.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class ReadyCommand implements CommandExecutor {

    private final HungerGamesPlugin main;

    public ReadyCommand(HungerGamesPlugin main){
        this.main=main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Apenas jogadores ingame podem usar este comando!");
            return true;
        }
        Player player =(Player)sender;

        if(!main.game.checkReadyPlayer(player)){
            main.game.addReadyPlayer(player);
        }else{
            main.game.removeReadyPlayer(player);
        }

        Bukkit.broadcast(Component.text("-=-=-=-=-=-=-=-=-=-=-=-",NamedTextColor.DARK_GREEN));
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player onlineplayer:players) {
            if(main.game.checkReadyPlayer(player)){
                Bukkit.broadcast(Component.text("✔-",NamedTextColor.DARK_GREEN)
                        .append(onlineplayer.displayName().color(NamedTextColor.DARK_GREEN)));
            }else{
                Bukkit.broadcast(Component.text("✘-",NamedTextColor.DARK_RED)
                        .append(onlineplayer.displayName().color(NamedTextColor.DARK_RED)));
            }
        }
        Bukkit.broadcast(Component.text("-=-=-=-=-=-=-=-=-=-=-=-",NamedTextColor.DARK_GREEN));
        CheckAllReady();
        return true;
    }

    public void CheckAllReady(){
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player:players) {
            if(!main.game.checkReadyPlayer(player)){
                return;
            }else{
                continue;
            }
        }
        Bukkit.broadcast(Component.text("Todos estão prontos, começando!!",NamedTextColor.GREEN));
        main.game.StartMatch();
    }


}
