package com.tinyshellzz.kikiwhitelist.database;

import com.tinyshellzz.kikiwhitelist.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.sql.*;

import static com.tinyshellzz.kikiwhitelist.GlobalObjects.config;
import static com.tinyshellzz.kikiwhitelist.GlobalObjects.plugin;

public class UserMapper {
    private Connection conn = null;

    private File user_db;

    public UserMapper(File user_db){
        this.user_db = user_db;

        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", user_db));
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void update_user_name_by_uuid(String mc_uuid, String user_name) {
        PreparedStatement stmt = null;
        try {
            User user = get_user_by_uuid(mc_uuid);
            if(user == null || user.user_name == user_name) return;

            stmt = conn.prepareStatement("UPDATE users SET user_name=? WHERE mc_uuid=?");
            stmt.setString(1, user_name);
            stmt.setString(2, mc_uuid);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public User get_user_by_uuid(String mc_uuid) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM users WHERE mc_uuid=?");
            stmt.setString(1, mc_uuid);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }

            stmt.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        }

        return null;
    }
}
