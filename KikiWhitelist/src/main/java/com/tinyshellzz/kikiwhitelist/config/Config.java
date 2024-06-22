package com.tinyshellzz.kikiwhitelist.config;

import com.tinyshellzz.kikiwhitelist.ObjectPool;
import com.tinyshellzz.kikiwhitelist.KIkiWhitelist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

            String str = "code-prefix: \"我是爱坤我不是小黑子\"\ndb_host: \"localhost\"      # mysql地址\n" +
                    "db_port: 3306               # mysql端口\n" +
                    "db_user: \"root\"           # mysql用户名\n" +
                    "db_passwd: \"anyingis21\"   # mysql密码\n" +
                    "db_database: \"TCC\"        # 所用数据库";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir, "config.yml"), StandardCharsets.UTF_8));
                writer.append(str);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            config = new HashMap<>();
            config.put((Object) "code-prefix", (Object) "我是爱坤我不是小黑子");
            config.put((Object) "db_host", (Object) "localhost");
            config.put((Object) "db_port", (Object) "3306");
            config.put((Object) "db_user", (Object) "root");
            config.put((Object) "db_passwd", (Object) "anyingis21");
            config.put((Object) "db_database", (Object) "TCC");
        }

        ObjectPool.config = config;
        return config;
    }

    public static String get(String key) {
        return config.get((Object) key).toString();
    }

    public static Connection connect() throws SQLException {
        String database = String.format("jdbc:mysql://%s:%s/%s", config.get("db_host"), config.get("db_port"), config.get("db_database"));
        Connection conn = DriverManager.getConnection(database, config.get("db_user").toString(), config.get("db_passwd").toString());
        conn.setAutoCommit(false);
        return conn;
    }
}
