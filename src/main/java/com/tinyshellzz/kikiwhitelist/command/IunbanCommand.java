package com.tinyshellzz.kikiwhitelist.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.banlistMapper;

public class IunbanCommand  implements TabExecutor {
    private Plugin plugin;


    public IunbanCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Matcher _m = Pattern.compile("^.*CraftRemoteConsoleCommandSender.*$").matcher(sender.toString());
        if(!(sender instanceof ConsoleCommandSender || _m.find() || sender.isOp())){
            sender.sendMessage("只有控制台和op才能使用该命令");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("iban命令参数不足, 使用方式 /iunban tinyshellzzz");
            return false;
        }
        String player = args[0].toLowerCase();

        banlistMapper.delete_by_name(player);
        sender.sendMessage(ChatColor.GREEN + "成功解封玩家" + player);
        if(!(sender instanceof ConsoleCommandSender)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "成功解封玩家" + player);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
