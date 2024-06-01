package com.tinyshellzz.kikiwhitelist.database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.sql.*;

import static com.tinyshellzz.kikiwhitelist.KIkiWhitelist.plugin;

public class WhitelistCodeMapper {
    private Connection conn = null;

    public WhitelistCodeMapper(){
        // 连接数据库
        File dir = plugin.getDataFolder();
        File db_file = new File(dir, "code.db");
        if(db_file.exists()) db_file.delete();  // 删除旧的验证码

        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", db_file));

            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE codes (" +
                                "mc_uuid text," +
                                "code text," +
                                "user_name text" +
                                ")");

            stmt.executeUpdate("CREATE UNIQUE INDEX code_index on codes (code);");
            stmt.executeUpdate("CREATE UNIQUE INDEX uuid_index on codes (mc_uuid);");
            stmt.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public boolean exists_uuid(String mc_uuid){
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM codes WHERE mc_uuid=?");
            stmt.setString(1, mc_uuid);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return true;

            stmt.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        }

        return false;
    }

    public boolean exists_code(String code){
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM codes WHERE code=?");
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return true;

            stmt.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        }

        return false;
    }

    public void insert_by_uuid(String mc_uuid, String code, String user_name){
        // uuid已存在, 就更新
        if(exists_uuid(mc_uuid)) {
            update_by_uuid(mc_uuid, code, user_name);
            return;
        }

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO codes VALUES (?, ?, ?)");
            stmt.setString(1, mc_uuid);
            stmt.setString(2, code);
            stmt.setString(3, user_name);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void update_by_uuid(String mc_uuid, String code, String user_name){
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE codes SET code=?, user_name=? WHERE mc_uuid=?");
            stmt.setString(1, code);
            stmt.setString(2, user_name);
            stmt.setString(3, mc_uuid);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
