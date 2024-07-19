package com.tinyshellzz.kikiwhitelist.services;

import com.tinyshellzz.kikiwhitelist.config.DBConfig;
import com.tinyshellzz.kikiwhitelist.config.PluginConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

public class KikiService {
    private Plugin plugin;

    public KikiService(Plugin plugin){
        this.plugin = plugin;
    }

    public boolean reload(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof ConsoleCommandSender)){
            sender.sendMessage("只有控制台才能使用该命令");
            return true;
        }

        PluginConfig.reload();

        return true;
    }

}
