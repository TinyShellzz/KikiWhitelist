package com.tinyshellzz.kikiwhitelist;

import com.tinyshellzz.kikiwhitelist.config.Config;
import com.tinyshellzz.kikiwhitelist.database.User;
import com.tinyshellzz.kikiwhitelist.database.UserMapper;
import com.tinyshellzz.kikiwhitelist.database.WhitelistCodeMapper;
import com.tinyshellzz.kikiwhitelist.listener.PlayerLoginListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class KIkiWhitelist extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "########################");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "#                      #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "# KikiWhitelist start! #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "#                      #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "########################");

        GlobalObjects.plugin = this;
        Config.loadConfig();

        GlobalObjects.whitelistCodeMapper = new WhitelistCodeMapper();

        String str = Config.get("user-db");
        if(str != null) {
            File user_db = new File(str);
            if(user_db.exists()) {
                GlobalObjects.usermapper = new UserMapper(user_db);
            }
        }

        // 注册 PlayerLoginListener
        this.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
