package com.tinyshellzz.kikiwhitelist.database;

import com.tinyshellzz.kikiwhitelist.config.DBConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BanlistMapper {
    public boolean exists_uuid(String mc_uuid){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        boolean ret = false;
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM banlist WHERE mc_uuid=?");
            stmt.setString(1, mc_uuid);
            rs = stmt.executeQuery();
            if(rs.next()) ret = true;
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
            }
        }

        return ret;
    }

    public BanlistUser get_user_by_uuid(String mc_uuid){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        BanlistUser ret = null;
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM banlist WHERE mc_uuid=?");
            stmt.setString(1, mc_uuid);
            rs = stmt.executeQuery();
            if(rs.next()) {
                ret = new BanlistUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6));
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
            }
        }

        return ret;
    }

    public void insert(BanlistUser user){
        user.user_name = user.user_name.toLowerCase();

        // uuid已存在, 就更新
        if(exists_uuid(user.mc_uuid)) {
            update_by_uuid(user);
            return;
        }

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            stmt = conn.prepareStatement("INSERT INTO banlist VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, user.mc_uuid);
            stmt.setString(2, user.user_name);
            stmt.setString(3, user.display_name);
            stmt.setString(4, user.source);
            stmt.setString(5, user.unban_date);
            stmt.setString(6, user.reason);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Banlist insert" + ": " + e.getMessage());
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public void update_by_uuid(BanlistUser user){
        user.user_name = user.user_name.toLowerCase();

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            stmt = conn.prepareStatement("UPDATE banlist SET user_name=?, display_name=?, source=?, unban_date=?, reason=? WHERE mc_uuid=?");
            stmt.setString(1, user.user_name);
            stmt.setString(2, user.display_name);
            stmt.setString(3, user.source);
            stmt.setString(4, user.unban_date);
            stmt.setString(5, user.reason);
            stmt.setString(6, user.mc_uuid);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Banlist update" + ": " + e.getMessage());
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public void delete_by_name(String user_name){
        user_name = user_name.toLowerCase();

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            stmt = conn.prepareStatement("DELETE FROM banlist WHERE user_name=?");
            stmt.setString(1, user_name);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Banlist delete_by_name" + ": " + e.getMessage());
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
            }
        }
    }
}