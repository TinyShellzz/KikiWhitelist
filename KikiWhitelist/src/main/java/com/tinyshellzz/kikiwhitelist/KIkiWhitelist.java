package com.tinyshellzz.kikiwhitelist;

import com.tinyshellzz.kikiwhitelist.command.IbanCommand;
import com.tinyshellzz.kikiwhitelist.command.IunbanCommand;
import com.tinyshellzz.kikiwhitelist.command.KikiCommand;
import com.tinyshellzz.kikiwhitelist.config.Config;
import com.tinyshellzz.kikiwhitelist.database.BanlistMapper;
import com.tinyshellzz.kikiwhitelist.database.CodeMCMapper;
import com.tinyshellzz.kikiwhitelist.database.UserMCMapper;
import com.tinyshellzz.kikiwhitelist.listener.PlayerLoginListener;
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

        ObjectPool.codeMCMapper = new CodeMCMapper();
        ObjectPool.userMCMapper = new UserMCMapper();
        ObjectPool.banlistMapper = new BanlistMapper();
    }

    public void register(){
        // 注册 PlayerLoginListener
        this.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
        // 注册 kiki 命令
        this.getCommand("kiki").setExecutor(new KikiCommand(this));
        // 注册 iban 命令
        this.getCommand("iban").setExecutor(new IbanCommand(this));
        // 注册 iunban 命令
        this.getCommand("iunban").setExecutor(new IunbanCommand(this));
    }
}
