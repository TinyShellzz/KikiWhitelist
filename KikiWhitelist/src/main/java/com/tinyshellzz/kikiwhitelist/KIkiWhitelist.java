package com.tinyshellzz.kikiwhitelist;

import com.tinyshellzz.kikiwhitelist.listener.PlayerLoginListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class KIkiWhitelist extends JavaPlugin {
    public static Map<Object, Object> config;
    public static KIkiWhitelist plugin;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "########################");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "#                      #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "# KikiWhitelist start! #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "#                      #");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "########################");

        plugin = this;

        // 加载配置文件
        File config_file = new File(plugin.getDataFolder(), "config.yml");
        if(config_file.exists()) {
            Yaml yaml = new Yaml();
            InputStream in = null;
            try {
                in = new FileInputStream(config_file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            config = yaml.load(in);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[Team]" + ChatColor.GREEN + "# KikiWhitelist 配置文件加载成功 #");
        } else {
            File dir = plugin.getDataFolder();
            if(!dir.exists()) dir.mkdirs();

            String str = "code-prefix='我是爱坤我不是小黑子'";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir, "code.db")));
                writer.append(str);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            config = new HashMap<>();
            config.put((Object) "code-prefix", (Object) "我是爱坤我不是小黑子");
        }

        // 注册 PlayerLoginListener
        this.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
