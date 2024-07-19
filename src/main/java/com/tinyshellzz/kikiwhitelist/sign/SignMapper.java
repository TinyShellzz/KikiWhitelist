package com.tinyshellzz.kikiwhitelist.sign;

import com.tinyshellzz.kikiwhitelist.config.DBConfig;
import com.tinyshellzz.kikiwhitelist.database.MCUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;

public class SignMapper {
    public SignUser get_user(String qq_num, String code) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        SignUser user = null;
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM signdata WHERE qq_num=? and code=?");
            stmt.setString(1, qq_num);
            stmt.setString(2, code);
            rs = stmt.executeQuery();
            if(rs.next()) {
                user =  new SignUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SignMapper.get_user:" + e.getMessage());
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
            }
        }

        return user;
    }

    public boolean exists_code(String qq_num, String code) {
        SignUser u = get_user(qq_num, code);
        return u != null;
    }

    public void delete_code(String qq_num, String code) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            stmt = conn.prepareStatement("DELETE from signdata WHERE qq_num=? and code=?");
            stmt.setString(1, qq_num);
            stmt.setString(2, code);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SignMapper.delete_code:" + ": " + e.getMessage());
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public void set_redeemed(String qq_num, String code){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            stmt = conn.prepareStatement("UPDATE signdata SET redeemed=1 WHERE qq_num=? and code=?");
            stmt.setString(1, qq_num);
            stmt.setString(2, code);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SignMapper.set_redeemed:" + ": " + e.getMessage());
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
