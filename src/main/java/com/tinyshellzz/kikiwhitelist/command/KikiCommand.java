package com.tinyshellzz.kikiwhitelist.command;

import com.tinyshellzz.kikiwhitelist.KIkiWhitelist;
import com.tinyshellzz.kikiwhitelist.services.KikiService;
import com.tinyshellzz.kikiwhitelist.services.KikiTabService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class KikiCommand  implements TabExecutor {
    private KikiService kikiService;
    KikiTabService tabService;
    private Plugin plugin;


    public KikiCommand(Plugin plugin) {
        this.plugin = plugin;
        kikiService = new KikiService(plugin);
        tabService = new KikiTabService(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("错误: kiki命令参数不足");
            return false;
        }
        String args1 = args[0].toLowerCase();

        switch(args1){
            case "reload":
                return kikiService.reload(sender, command, label, args);
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        // 判断命令参数的长度
        if (args.length == 1) {
            // 如果只有一个参数，返回所有子命令的列表
            return Arrays.asList("reload");
        } else if (args.length == 2) {
            // 如果有两个参数，根据第一个参数返回不同的补全列表
        }

        return null;
    }
}
