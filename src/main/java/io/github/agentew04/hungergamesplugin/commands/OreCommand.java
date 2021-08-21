package io.github.agentew04.hungergamesplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class OreCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==0){
            sender.sendMessage(ChatColor.DARK_RED+"-=-Geração de minérios-=-");
            sender.sendMessage(ChatColor.AQUA+"Diamante: ");
            sender.sendMessage(ChatColor.AQUA+" -Geração: 16 até -64");
            sender.sendMessage(ChatColor.AQUA+" -Melhor camada: -48 até -64");

            sender.sendMessage(ChatColor.GRAY +"Ferro: ");
            sender.sendMessage(ChatColor.GRAY +" -Geração: 80 até -16");
            sender.sendMessage(ChatColor.GRAY +" -Melhor camada: 40 até 24");

            sender.sendMessage(ChatColor.GOLD +"Ouro: ");
            sender.sendMessage(ChatColor.GOLD +" -Geração: 24 até -56");
            sender.sendMessage(ChatColor.GOLD +" -Melhor camada: -20 até -28");
            return true;
        }else{
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "diamante":
                case "diamond":
                    sender.sendMessage(ChatColor.AQUA + " -Geração: 16 até -64");
                    sender.sendMessage(ChatColor.AQUA + " -Melhor camada: -48 até -64");
                    return true;
                case "ferro":
                case "iron":
                    sender.sendMessage(ChatColor.GRAY + " -Geração: 80 até -16");
                    sender.sendMessage(ChatColor.GRAY + " -Melhor camada: 40 até 24");
                    return true;
                case "ouro":
                case "gold":
                    sender.sendMessage(ChatColor.GOLD + " -Geração: 24 até -56");
                    sender.sendMessage(ChatColor.GOLD + " -Melhor camada: -20 até -28");
                    return true;
                default:
                    sender.sendMessage(ChatColor.DARK_RED + "Minério inválido!");
                    break;
            }
        }
        return false;
    }
}
