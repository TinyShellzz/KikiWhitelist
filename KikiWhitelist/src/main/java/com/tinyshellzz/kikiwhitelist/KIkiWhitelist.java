package com.tinyshellzz.kikiwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class KIkiWhitelist extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "########################");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "#                      #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "# KikiWhitelist start! #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "#                      #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "########################");



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
