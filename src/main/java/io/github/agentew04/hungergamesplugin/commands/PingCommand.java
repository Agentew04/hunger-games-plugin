package io.github.agentew04.hungergamesplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("Ping: "+String.valueOf(player.getPing())+" ms");
            return true;
        }
        return false;
    }
}
