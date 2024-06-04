package com.tinyshellzz.kikiwhitelist.database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.sql.*;

public class UserMapper {

    String user_db;

    public UserMapper(File fpath){
        user_db = String.format("jdbc:sqlite:%s", fpath);
    }

    public void update_user_name_by_uuid(String mc_uuid, String user_name, String display_name) {
        user_name = user_name.toLowerCase();

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(user_db);
            User user = get_user_by_uuid(mc_uuid);
            if (!(user == null || user.user_name.equals(user_name))) {

                stmt = conn.prepareStatement("UPDATE users SET user_name=?, display_name=? WHERE mc_uuid=?");
                stmt.setString(1, user_name);
                stmt.setString(2, display_name);
                stmt.setString(3, mc_uuid);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    public User get_user_by_uuid(String mc_uuid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = DriverManager.getConnection(user_db);
            stmt = conn.prepareStatement("SELECT * FROM users WHERE mc_uuid=?");
            stmt.setString(1, mc_uuid);
            rs = stmt.executeQuery();
            if(rs.next()) {
                user =  new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                stmt.close();
                rs.close();
                conn.close();
            } catch (SQLException | NullPointerException e) {
            }
        }

        return user;
    }

    public boolean exists_uuid(String mc_uuid){
        User u = get_user_by_uuid(mc_uuid);
        return u != null;
    }
}
