package io.github.agentew04.hungergamesplugin.commands;

import io.github.agentew04.hungergamesplugin.HungerGamesPlugin;
import io.github.agentew04.hungergamesplugin.Kits;
import org.bukkit.Bukkit;
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
    public String[] WolfTamerNames = new String[]{"wolf","tamer","animal","dog","wolftamer","dogtamer"};
    public String[] ArcherNames = new String[]{"archer","arch","bow","arqueiro","arco","ranged"};

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
            sender.sendMessage(ChatColor.AQUA+" -None: não faz nada de mais");
            sender.sendMessage(ChatColor.AQUA+" -Stomper: dano de queda transferido para outro player");
            sender.sendMessage(ChatColor.AQUA+" -Lumberjack: machado super rápido");
            sender.sendMessage(ChatColor.AQUA+" -Worm: terra no inventário aumenta sua vida");
            sender.sendMessage(ChatColor.AQUA+" -Gladiator: faça um x1 com os outros");
            sender.sendMessage(ChatColor.AQUA+" -Kangaro: dê um boost para onde você está olhando!");
            sender.sendMessage(ChatColor.AQUA+" -Grapler: lance um grapling hook e se mova rapidamente");
            sender.sendMessage(ChatColor.AQUA+" -Fisherman: uma vara de pesca para puxar jogadores");
            sender.sendMessage(ChatColor.AQUA+" -Viper: dá poison em outros jogadores");
            sender.sendMessage(ChatColor.AQUA+" -Archer: ganha um arco especial");
            sender.sendMessage(ChatColor.AQUA+" -WolfTamer: começa com lobos para te ajudar na jornada");
            return false;
        }
        if(args.length==2 && args[0].equals("get")){
            sender.sendMessage("O kit de "+args[1]+" é: "+main.game.getPlayerKit(Bukkit.getPlayer(args[1])).name());
            return true;
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
        }else if(Arrays.asList(WolfTamerNames).contains(input)){
            return Kits.WolfTamer;
        }else if(Arrays.asList(ArcherNames).contains(input)){
            return Kits.Archer;
        }else{
            return Kits.None;
        }
    }
}
