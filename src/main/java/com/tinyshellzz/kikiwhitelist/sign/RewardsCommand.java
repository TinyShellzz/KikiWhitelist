package com.tinyshellzz.kikiwhitelist.sign;

import com.tinyshellzz.kikiwhitelist.ObjectPool;
import com.tinyshellzz.kikiwhitelist.config.ItemStackManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RewardsCommand  implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender.hasPermission("kiki.use"))){
            sender.sendMessage("你没有足够的权限");
            return true;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage("只有玩家才能使用该命令");
            return true;
        }
        Player player = (Player) sender;

        if (args.length < 2) {
            sender.sendMessage("rewards命令参数不足, 使用方式 /rewards save <day>");
            return false;
        }
        String args1 = args[0].toLowerCase();

        switch(args1){
            case "save":
                return save(player, command, label, args);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // 判断命令参数的长度
        if (args.length == 1) {
            // 如果只有一个参数，返回所有子命令的列表
            return Arrays.asList("save");
        } else if (args.length == 2) {
            List<String> days = new ArrayList<>();
            for(int i = 1; i < 32; i++) {
                days.add(i+"");
            }
            return days;
        }

        return null;
    }

    public boolean save(Player player, Command command, String label, String[] args) {
        String day = args[1];
        @Nullable ItemStack[] contents = player.getInventory().getContents();

        // 清除旧的item
        File folder = new File(ObjectPool.plugin.getDataFolder(), "items");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if(listOfFiles[i].getName().startsWith(day + "_")) {
                        listOfFiles[i].delete();
                    }
                }
            }
        }

        List<String> items = new ArrayList<>();
        for(ItemStack itemStack: contents) {
            if(itemStack != null) {
                String s = itemStack.getType().toString();
                String item_name = day + "_" + s;

                ItemStackManager.save(item_name, itemStack);
                items.add(item_name);
            }
        }
        GiftList.configWrapper.set(day, items);
        GiftList.configWrapper.saveConfig();

        return true;
    }
}
