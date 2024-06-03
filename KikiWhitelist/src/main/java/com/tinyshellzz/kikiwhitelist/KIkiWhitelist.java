package com.tinyshellzz.kikiwhitelist;

import com.tinyshellzz.kikiwhitelist.command.KikiCommand;
import com.tinyshellzz.kikiwhitelist.config.Config;
import com.tinyshellzz.kikiwhitelist.database.UserMapper;
import com.tinyshellzz.kikiwhitelist.database.WhitelistCodeMapper;
import com.tinyshellzz.kikiwhitelist.listener.PlayerLoginListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class KIkiWhitelist extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "########################");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "#                      #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "# KikiWhitelist start! #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "#                      #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "########################");

        // 初始化
        init();

        // 注册组件
        register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void init(){
        ObjectPool.plugin = this;
        Config.loadConfig();

        ObjectPool.whitelistCodeMapper = new WhitelistCodeMapper();

        // 加载数据库
        String str = Config.get("user-db");
        if(str != null) {
            File user_db = new File(str);
            if(user_db.exists()) {
                ObjectPool.usermapper = new UserMapper(user_db);
            }
        }
    }

    public void register(){
        // 注册 PlayerLoginListener
        this.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
        // 注册 KikiCommand
        this.getCommand("kiki").setExecutor(new KikiCommand(this));
    }
}
