package io.github.agentew04.hungergamesplugin.commands;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Locale;

public class KitCommand implements CommandExecutor {

    private final HungerGamesPlugin main;
    public KitCommand(HungerGamesPlugin main){
        this.main=main;
    }

    public String[] StomperNames = new String[]{"stomper","stp","stomp"};
    public String[] LumberJackNames = new String[]{"lumberjack","lumber","jack","lj","l","j","wood","axe"};
    public String[] WormNames = new String[]{"worm","w","dirt","grass"};
    public String[] GladiatorNames = new String[]{"gladiator","glad","g","1v1","1x1"};
    public String[] KangaroNames = new String[]{"kangaro","canguru","c","kang","kangaroo","cangaroo","jump"};
    public String[] GraplerNames = new String[]{"grapler","graplinghook","grapling","hook","gh","grap"};
    public String[] FishermanNames = new String[]{"fish","grap","fisherman","fishingrod","rod","fishing"};
    public String[] ViperNames = new String[]{"viper","vipe","v","vip","poison","veneno"};

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //ver se é um player
        if(!(sender instanceof Player)){
            sender.sendMessage("Apenas Jogadores ingame podem usar este comando!");
            return false;
        }
        if(args.length==0){
            sender.sendMessage(ChatColor.RED+"Você deve selecionar um kit!");
            sender.sendMessage(ChatColor.AQUA+"Kits disponíveis:");
            sender.sendMessage(ChatColor.AQUA+" -None");
            sender.sendMessage(ChatColor.AQUA+" -Stomper");
            sender.sendMessage(ChatColor.AQUA+" -Lumberjack");
            sender.sendMessage(ChatColor.AQUA+" -Worm");
            sender.sendMessage(ChatColor.AQUA+" -Gladiator");
            sender.sendMessage(ChatColor.AQUA+" -Kangaro");
            sender.sendMessage(ChatColor.AQUA+" -Grapler");
            sender.sendMessage(ChatColor.AQUA+" -Fisherman");
            sender.sendMessage(ChatColor.AQUA+" -Viper");
            return false;
        }
        Kits selected = ParseKit(args[0]);
        Player player = (Player)sender;
        player.sendMessage(ChatColor.GOLD+"Você selecionou "+selected.name());
        main.game.setPlayerKit(player,selected);
        return true;
    }

    public Kits ParseKit(String input){
        input = input.toLowerCase(Locale.ROOT);
        if(Arrays.asList(StomperNames).contains(input)){
            return Kits.Stomper;
        }else if(Arrays.asList(LumberJackNames).contains(input)){
            return Kits.LumberJack;
        }else if(Arrays.asList(WormNames).contains(input)){
            return Kits.Worm;
        }else if(Arrays.asList(GladiatorNames).contains(input)){
            return Kits.Gladiator;
        }else if(Arrays.asList(KangaroNames).contains(input)){
            return Kits.Kangaro;
        }else if(Arrays.asList(GraplerNames).contains(input)){
            return Kits.Grapler;
        }else if(Arrays.asList(FishermanNames).contains(input)){
            return Kits.Fisherman;
        }else if(Arrays.asList(ViperNames).contains(input)){
            return Kits.Viper;
        }else{
            return Kits.None;
        }
    }
}
