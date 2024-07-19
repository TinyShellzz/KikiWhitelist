package com.tinyshellzz.kikiwhitelist.config;


import com.tinyshellzz.kikiwhitelist.ObjectPool;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.plugin;

public class PluginConfig {
    public String code_prefix;
    public String db_host;
    public int db_port;
    public String db_user;
    public String db_passwd;
    public String db_database;

    private static ConfigWrapper configWrapper = new ConfigWrapper(plugin, "config.yml");
    public static void reload() {
        configWrapper.reloadConfig(); // 重新加载配置文件

        YamlConfiguration config = configWrapper.getConfig();
        ObjectPool.pluginConfig = new PluginConfig();
        ObjectPool.pluginConfig.code_prefix = config.getString("code_prefix");
        ObjectPool.pluginConfig.db_host = config.getString("db_host");
        ObjectPool.pluginConfig.db_port = config.getInt("db_port");
        ObjectPool.pluginConfig.db_user = config.getString("db_user");
        ObjectPool.pluginConfig.db_passwd = config.getString("db_passwd");
        ObjectPool.pluginConfig.db_database = config.getString("db_database");
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
