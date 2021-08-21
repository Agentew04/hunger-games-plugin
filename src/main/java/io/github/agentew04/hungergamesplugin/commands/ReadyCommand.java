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
    private final MultiverseCore core;
    public ReadyCommand(HungerGamesPlugin main){
        this.main=main;
        core = main.getMV();
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
        StartMatch();
    }
    public void StartMatch(){
        //create scoreboard
        ScoreboardManager manager= Bukkit.getServer().getScoreboardManager();
        Scoreboard scoreBoard = manager.getNewScoreboard();
        Component title = Component.text("☠",NamedTextColor.DARK_RED)
                .append(Component.text("KILL COUNTER",NamedTextColor.RED))
                .append(Component.text("☠",NamedTextColor.DARK_RED));
        Objective objective = scoreBoard.registerNewObjective("killcount","playerKillCount",title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        World targetWorld = Bukkit.getWorld("arena");
        assert targetWorld != null;
        int maxrange=500;
        int minrange=-500;
        for (Player player:main.game.getReadyPlayers()) {

            //pegar spawn coords
            double x = ThreadLocalRandom.current().nextInt(minrange, maxrange + 1);
            double z = ThreadLocalRandom.current().nextInt(minrange, maxrange + 1);
            double y = targetWorld.getHighestBlockYAt((int)x,(int)z);

            //check spawn
            Biome spawnBiome = targetWorld.getBiome((int)x,(int)y,(int)z);
            while (spawnBiome == Biome.OCEAN ||spawnBiome == Biome.COLD_OCEAN ||spawnBiome == Biome.DEEP_COLD_OCEAN ||
            spawnBiome == Biome.DEEP_FROZEN_OCEAN ||spawnBiome == Biome.DEEP_OCEAN ||spawnBiome == Biome.DEEP_LUKEWARM_OCEAN ||
            spawnBiome == Biome.DEEP_WARM_OCEAN ||spawnBiome == Biome.FROZEN_OCEAN ||spawnBiome == Biome.LUKEWARM_OCEAN ||
            spawnBiome == Biome.WARM_OCEAN){
                x=ThreadLocalRandom.current().nextInt(minrange, maxrange + 1);
                z = ThreadLocalRandom.current().nextInt(minrange, maxrange + 1);
                y = targetWorld.getHighestBlockYAt((int)x,(int)z);
                spawnBiome = targetWorld.getBiome((int)x,(int)y,(int)z);
            }

            //tp player
            core.teleportPlayer(Bukkit.getServer().getConsoleSender(), player, new Location(targetWorld,
                    x,
                    y,
                    z));

            //clear inventory
            player.getInventory().clear();

            //reset stats
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            player.setFoodLevel(20);
            player.setSaturation(20.0f);
            player.setExp(0.0f);
            player.setLevel(0);
            player.setGameMode(GameMode.SURVIVAL);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,6000,0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,6000,4));

            //dar tracker compass
            player.getInventory().addItem(new ItemStack(Material.COMPASS));

            //dar item do kit se necessário
            Kits kit = main.game.getPlayerKit(player);
            giveKitItem(player,kit);

            //set scoreboard
            player.setScoreboard(scoreBoard);

            main.game.addAlivePlayer(player);
        }
    }
    public void giveKitItem(Player player, Kits kit){
        ItemStack item;
        ItemMeta meta;
        switch (kit){
            case LumberJack:
                item=new ItemStack(Material.WOODEN_AXE);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.addEnchant(Enchantment.DIG_SPEED,7,true);
                meta.displayName(Component.text("Machado do lenhador",NamedTextColor.DARK_PURPLE));
                meta.addEnchant(Enchantment.DURABILITY,1,false);
                item.setItemMeta(meta);
                break;
            case Gladiator:
                item = new ItemStack(Material.NETHERITE_HOE);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
                meta.setUnbreakable(true);
                meta.displayName(Component.text("A foice do x1",NamedTextColor.DARK_PURPLE));
                break;
            case Fisherman:
                item = new ItemStack(Material.FISHING_ROD);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.addEnchant(Enchantment.LURE,1,true);
                meta.setUnbreakable(true);
                meta.displayName(Component.text("Vara do pescador",NamedTextColor.DARK_PURPLE));
                item.setItemMeta(meta);
                break;
            case Kangaro:
                item = new ItemStack(Material.FIREWORK_ROCKET);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.displayName(Component.text("Pulo do Canguru",NamedTextColor.DARK_PURPLE));
                item.setItemMeta(meta);
                break;
            case Grapler:
                item = new ItemStack(Material.TRIPWIRE_HOOK);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.displayName(Component.text("Grapling Hook",NamedTextColor.DARK_PURPLE));
                item.setItemMeta(meta);
                break;
            case WolfTamer:
                item = new ItemStack(Material.WOLF_SPAWN_EGG,10);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.displayName(Component.text("Lobos da floresta",NamedTextColor.DARK_PURPLE));
                item.setItemMeta(meta);
                ItemStack item2 = new ItemStack(Material.BONE,64);
                meta = item2.hasItemMeta()?item2.getItemMeta():Bukkit.getItemFactory().getItemMeta(item2.getType());
                meta.displayName(Component.text("Biscoito Scooby Doo",NamedTextColor.DARK_PURPLE));

                item2.setItemMeta(meta);
                player.getInventory().addItem(item,item2);
                return;
            case Archer:
                item = new ItemStack(Material.BOW);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.displayName(Component.text("Arco-Escudo Imortal",NamedTextColor.DARK_PURPLE));
                meta.setUnbreakable(true);
                meta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
                item.setItemMeta(meta);
                break;
            default:
                item = new ItemStack(Material.AIR);
                break;
        }
        player.getInventory().addItem(item);
    }
}
