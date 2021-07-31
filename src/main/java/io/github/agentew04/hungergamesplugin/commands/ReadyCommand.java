package io.github.agentew04.hungergamesplugin.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
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
    private MultiverseCore core;
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

        Bukkit.broadcastMessage(ChatColor.DARK_GREEN+"-=-=-=-=-=-=-=-=-=-=-=-");
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player onplayer:players) {
            if(main.game.checkReadyPlayer(player)){
                Bukkit.broadcastMessage(ChatColor.DARK_GREEN+ "✔-"+onplayer.getDisplayName());
            }else{
                Bukkit.broadcastMessage(ChatColor.DARK_RED+ "✘-"+onplayer.getDisplayName());
            }
        }
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN+"-=-=-=-=-=-=-=-=-=-=-=-");
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
        Bukkit.broadcastMessage(ChatColor.GREEN+"Todos estão prontos, começando!!");
        StartMatch();
    }
    public void StartMatch(){
        //create scoreboard
        ScoreboardManager manager= Bukkit.getServer().getScoreboardManager();
        Scoreboard scoreBoard = manager.getNewScoreboard();
        Objective objective = scoreBoard.registerNewObjective("killcount","playerKillCount",ChatColor.DARK_RED+"☠"+ChatColor.RED+"KILL COUNTER"+ChatColor.DARK_RED+"☠");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        World targetWorld = Bukkit.getWorld("arena");
        assert targetWorld != null;
        int maxrange=500;
        int minrange=-500;
        for (Player player:Bukkit.getOnlinePlayers()) {

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

            //dar bussola
            player.getInventory().addItem(new ItemStack(Material.COMPASS));

            //dar item do kit se necessário
            Kits kit = main.game.getPlayerKit(player);
            giveKitItem(player,kit);

            //set scoreboard
            player.setScoreboard(scoreBoard);
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
                meta.setDisplayName(ChatColor.DARK_PURPLE+"Machado do lenhador");
                meta.addEnchant(Enchantment.DURABILITY,1,false);
                item.setItemMeta(meta);
            case Gladiator:
                item = new ItemStack(Material.NETHERITE_HOE);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
                meta.setUnbreakable(true);
                meta.setDisplayName(ChatColor.DARK_PURPLE+"A foice do x1");
            case Fisherman:
                item = new ItemStack(Material.FISHING_ROD);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.addEnchant(Enchantment.LURE,1,true);
                meta.setUnbreakable(true);
                meta.setDisplayName(ChatColor.DARK_PURPLE+"Vara do pescador");
                item.setItemMeta(meta);
            case Kangaro:
                item = new ItemStack(Material.FIREWORK_ROCKET);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.setDisplayName(ChatColor.DARK_PURPLE+"Pulo do Canguru");
                item.setItemMeta(meta);
            case Grapler:
                item = new ItemStack(Material.TRIPWIRE_HOOK);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.setDisplayName(ChatColor.DARK_PURPLE+"Grapling Hook");
                item.setItemMeta(meta);
            case WolfTamer:
                item = new ItemStack(Material.WOLF_SPAWN_EGG,10);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.setDisplayName(ChatColor.DARK_PURPLE+"Invocar lobos da floresta");
                item.setItemMeta(meta);
                ItemStack item2 = new ItemStack(Material.BONE,64);
                meta = item2.hasItemMeta()?item2.getItemMeta():Bukkit.getItemFactory().getItemMeta(item2.getType());
                meta.setDisplayName(ChatColor.DARK_PURPLE+"Biscoito Scooby Doo");
                item2.setItemMeta(meta);
                player.getInventory().addItem(item,item2);
                return;
            default:
                item = new ItemStack(Material.AIR);
        }
        player.getInventory().addItem(item);
    }
}
