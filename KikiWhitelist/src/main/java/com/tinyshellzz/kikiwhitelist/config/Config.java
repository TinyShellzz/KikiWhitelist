package com.tinyshellzz.kikiwhitelist.config;

import com.tinyshellzz.kikiwhitelist.ObjectPool;
import com.tinyshellzz.kikiwhitelist.KIkiWhitelist;
import com.tinyshellzz.kikiwhitelist.database.UserMapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Config {
    private static KIkiWhitelist plugin = ObjectPool.plugin;

    public static HashMap<Object, Object> config;

    public static HashMap<Object, Object> loadConfig(){
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

            String str = "code-prefix: '我是爱坤我不是小黑子'\nuser-db: 'D:/Learning/Python/Python/03_高级/QQ_nonebot/kiki_projects_for_TCC/user.db'";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir, "config.yml"), StandardCharsets.UTF_8));
                writer.append(str);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            config = new HashMap<>();
            config.put((Object) "code-prefix", (Object) "我是爱坤我不是小黑子");
            config.put((Object) "user-db", (Object) "D:/Learning/Python/Python/03_高级/QQ_nonebot/kiki_projects_for_TCC/user.db");
        }

        ObjectPool.config = config;
        connect_db();
        return config;
    }

    public static String get(String key) {
        return (String) config.get((Object) key);
    }

    public static void connect_db(){
        // 加载数据库
        try {
            String str = Config.get("user-db");
            File user_db = new File(str);
            if (user_db.exists()) {
                ObjectPool.usermapper = new UserMapper(user_db);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "user.db 配置错误");
            throw new NullPointerException();
        }
    }
}
