package com.tinyshellzz.kikiwhitelist.config;


import com.tinyshellzz.kikiwhitelist.ObjectPool;
import com.tinyshellzz.kikiwhitelist.sign.GiftList;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.plugin;

public class PluginConfig {
    public String code_prefix;
    public String db_host;
    public int db_port;
    public String db_user;
    public String db_passwd;
    public String db_database;
    public boolean gift_blacklist = false;
    public String blacklist_pattern;

    private static ConfigWrapper configWrapper = new ConfigWrapper(plugin, "config.yml");

    public static void reload() {
        configWrapper.reloadConfig(); // 重新加载配置文件

        YamlConfiguration yamlconfig = configWrapper.getConfig();
        ObjectPool.pluginConfig = new PluginConfig();
        PluginConfig config = ObjectPool.pluginConfig;
        config.code_prefix = yamlconfig.getString("code_prefix");
        config.db_host = yamlconfig.getString("db_host");
        config.db_port = yamlconfig.getInt("db_port");
        config.db_user = yamlconfig.getString("db_user");
        config.db_passwd = yamlconfig.getString("db_passwd");
        config.db_database = yamlconfig.getString("db_database");
        config.gift_blacklist = Boolean.parseBoolean(yamlconfig.getString("gift_blacklist"));
        config.blacklist_pattern = yamlconfig.getString("blacklist_pattern");
        Bukkit.getConsoleSender().sendMessage("blacklist_pattern: " + config.blacklist_pattern);

        ItemStackManager.reload();
        GiftList.reload();
    }

    @Override
    public String toString() {
        return "PluginConfig{" +
                "code_prefix='" + code_prefix + '\'' +
                ", db_host='" + db_host + '\'' +
                ", db_port=" + db_port +
                ", db_user='" + db_user + '\'' +
                ", db_passwd='" + db_passwd + '\'' +
                ", db_database='" + db_database + '\'' +
                '}';
    }

}
