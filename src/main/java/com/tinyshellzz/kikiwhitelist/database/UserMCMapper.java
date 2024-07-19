package com.tinyshellzz.kikiwhitelist.database;

import com.tinyshellzz.kikiwhitelist.config.DBConfig;
import com.tinyshellzz.kikiwhitelist.utils.Tools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;

public class UserMCMapper {
    public void update_user_name_by_uuid(String mc_uuid, String user_name, String display_name) {
        user_name = user_name.toLowerCase();

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            MCUser user = get_user_by_uuid(mc_uuid);
            if (!(user == null || user.user_name.equals(user_name))) {

                stmt = conn.prepareStatement("UPDATE users_mc SET user_name=?, display_name=? WHERE mc_uuid=?");
                stmt.setString(1, user_name);
                stmt.setString(2, display_name);
                stmt.setString(3, mc_uuid);
                stmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "UserMCMapper.update_user_name_by_uuid: " + e.getMessage());
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public void update_login_time_by_uuid(String mc_uuid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConfig.connect();
            MCUser user = get_user_by_uuid(mc_uuid);
            if (user != null) {
                stmt = conn.prepareStatement("UPDATE users_mc SET last_login_time=? WHERE mc_uuid=?");
                stmt.setString(1, Tools.get_current_time());
                stmt.setString(2, mc_uuid);
                stmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "UserMCMapper.update_login_time_by_uuid:"  + e.getMessage());
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public MCUser get_user_by_uuid(String mc_uuid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        MCUser user = null;
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM users_mc WHERE mc_uuid=?");
            stmt.setString(1, mc_uuid);
            rs = stmt.executeQuery();
            if(rs.next()) {
                user =  new MCUser(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "UserMCMapper.get_user_by_uuid:" + e.getMessage());
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

    public MCUser get_user_by_id(long id) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        MCUser user = null;
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM users_mc WHERE id=?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if(rs.next()) {
                user =  new MCUser(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "UserMCMapper.get_user_by_uuid:" + e.getMessage());
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

    public boolean exists_uuid(String mc_uuid){
        MCUser u = get_user_by_uuid(mc_uuid);
        return u != null;
    }

    public boolean exists_whitelist(long id){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        MCUser user = null;
        boolean ret = false;
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM whitelist WHERE id=?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if(rs.next()) {
                ret = true;
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "UserMCMapper.exists_whitelist:" + e.getMessage());
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
}
