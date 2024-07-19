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

import static com.tinyshellzz.kikiwhitelist.ObjectPool.pluginConfig;


public class DBConfig {
    public static Connection connect() throws SQLException {
        String database = String.format("jdbc:mysql://%s:%s/%s", pluginConfig.db_host, pluginConfig.db_port, pluginConfig.db_database);
        Connection conn = DriverManager.getConnection(database, pluginConfig.db_user, pluginConfig.db_passwd);
        conn.setAutoCommit(false);
        return conn;
    }
}
