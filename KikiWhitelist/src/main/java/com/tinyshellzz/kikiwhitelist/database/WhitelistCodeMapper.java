package com.tinyshellzz.kikiwhitelist.database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.sql.*;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.plugin;

public class WhitelistCodeMapper {
    private String code_db;

    public WhitelistCodeMapper(){
        // 连接数据库
        File dir = plugin.getDataFolder();
        File db_file = new File(dir, "code.db");
        code_db = String.format("jdbc:sqlite:%s", db_file);

        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(code_db);

            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE codes (" +
                                "mc_uuid text," +
                                "code text," +
                                "user_name text" +
                                ")");

            stmt.executeUpdate("CREATE UNIQUE INDEX code_index on codes (code);");
            stmt.executeUpdate("CREATE UNIQUE INDEX uuid_index on codes (mc_uuid);");
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

        clear();
    }

    public boolean exists_uuid(String mc_uuid){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        boolean ret = false;
        try {
            conn = DriverManager.getConnection(code_db);
            stmt = conn.prepareStatement("SELECT * FROM codes WHERE mc_uuid=?");
            stmt.setString(1, mc_uuid);
            rs = stmt.executeQuery();
            if(rs.next()) ret = true;
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

        return ret;
    }

    public boolean exists_code(String code){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        boolean ret = false;
        try {
            conn = DriverManager.getConnection(code_db);
            stmt = conn.prepareStatement("SELECT * FROM codes WHERE code=?");
            stmt.setString(1, code);
            rs = stmt.executeQuery();
            if(rs.next()) ret =  true;
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


        return false;
    }

    public void clear(){
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(code_db);
            stmt = conn.createStatement();
            stmt.executeUpdate("Delete from codes");
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

    public void insert_by_uuid(String mc_uuid, String code, String user_name){
        user_name = user_name.toLowerCase();

        // uuid已存在, 就更新
        if(exists_uuid(mc_uuid)) {
            update_by_uuid(mc_uuid, code, user_name);
            return;
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "1");
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(code_db);
            stmt = conn.prepareStatement("INSERT INTO codes VALUES (?, ?, ?)");
            stmt.setString(1, mc_uuid);
            stmt.setString(2, code);
            stmt.setString(3, user_name);
            stmt.executeUpdate();
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

    public void update_by_uuid(String mc_uuid, String code, String user_name){
        user_name = user_name.toLowerCase();

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(code_db);
            stmt = conn.prepareStatement("UPDATE codes SET code=?, user_name=? WHERE mc_uuid=?");
            stmt.setString(1, code);
            stmt.setString(2, user_name);
            stmt.setString(3, mc_uuid);
            stmt.executeUpdate();
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

    public void update_by_code( String mc_uuid, String code, String user_name){
        user_name = user_name.toLowerCase();

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(code_db);
            stmt = conn.prepareStatement("UPDATE codes SET mc_uuid=?, user_name=? WHERE code=?");
            stmt.setString(3, code);
            stmt.setString(2, user_name);
            stmt.setString(1, mc_uuid);
            stmt.executeUpdate();
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
}
