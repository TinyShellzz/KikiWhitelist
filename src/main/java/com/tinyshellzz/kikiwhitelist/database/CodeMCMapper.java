package com.tinyshellzz.kikiwhitelist.database;

import com.tinyshellzz.kikiwhitelist.config.DBConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;

public class CodeMCMapper {
    public CodeMCMapper(){
        clear();
    }

    public boolean exists_uuid(String mc_uuid){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        boolean ret = false;
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM codes_mc WHERE mc_uuid=?");
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

    public boolean exists_code(String code){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        boolean ret = false;
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM codes_mc WHERE code=?");
            stmt.setString(1, code);
            rs = stmt.executeQuery();
            if(rs.next()) ret =  true;
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


        return false;
    }

    public void clear(){
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            stmt = conn.createStatement();
            stmt.executeUpdate("Delete from codes_mc");
            conn.commit();
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

    }

    public void insert_by_uuid(String mc_uuid, String code, String user_name, String display_name){
        user_name = user_name.toLowerCase();

        // uuid已存在, 就更新
        if(exists_uuid(mc_uuid)) {
            update_by_uuid(mc_uuid, code, user_name, display_name);
            return;
        }

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            stmt = conn.prepareStatement("INSERT INTO codes_mc VALUES (?, ?, ?, ?)");
            stmt.setString(1, code);
            stmt.setString(2, user_name);
            stmt.setString(3, display_name);
            stmt.setString(4, mc_uuid);
            stmt.executeUpdate();
            conn.commit();
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

    }

    public void update_by_uuid(String mc_uuid, String code, String user_name, String display_name){
        user_name = user_name.toLowerCase();

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            stmt = conn.prepareStatement("UPDATE codes_mc SET code=?, user_name=?, display_name=? WHERE mc_uuid=?");
            stmt.setString(1, code);
            stmt.setString(2, user_name);
            stmt.setString(3, display_name);
            stmt.setString(4, mc_uuid);
            stmt.executeUpdate();
            conn.commit();
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
    }

    public void update_by_code( String mc_uuid, String code, String user_name){
        user_name = user_name.toLowerCase();

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            stmt = conn.prepareStatement("UPDATE codes_mc SET mc_uuid=?, user_name=? WHERE code=?");
            stmt.setString(1, mc_uuid);
            stmt.setString(2, user_name);
            stmt.setString(3, code);
            stmt.executeUpdate();
            conn.commit();
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

    }
}
