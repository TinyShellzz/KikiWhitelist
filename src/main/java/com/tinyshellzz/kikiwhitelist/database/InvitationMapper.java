package com.tinyshellzz.kikiwhitelist.database;


import com.tinyshellzz.kikiwhitelist.config.DBConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.userMCMapper;

//    def get_relations(id: int):
//            inviter = None
//            inviter = InvitationMapper.get_inviter(id)
//            if inviter == None:
//            if not InvitationMapper.exists_inviter(id):
//            return None
//            inviter = id
//
//            res = None
//            with connect() as db:
//            with db.cursor() as c:
//            db.commit()
//            c.execute("SELECT * FROM invitaions WHERE inviter=%s", id)
//            res = c.fetchall()
//
//            ret = [int(inviter)]
//            for r in res:
//            ret.append(int(r[0]))
//
//            return ret
public class InvitationMapper {

    public ArrayList<MCUser> get_relations(long id){
        Long inviter = null;
        inviter = get_inviter(id);
        if(inviter == null){
            if(!exists_inviter(id)) return null;
            inviter = id;
        }

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        ArrayList<MCUser> ret = new ArrayList<>();
        ret.add(userMCMapper.get_user_by_id(inviter));
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM invitations WHERE inviter=?");
            stmt.setLong(1, inviter);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ret.add(userMCMapper.get_user_by_id(rs.getLong(1)));
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

    public Long get_inviter(long id){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        Long ret = null;
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM invitations WHERE id=?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if(rs.next()) {
                ret =  rs.getLong(2);
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

    public boolean exists_inviter(long id){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        boolean ret = false;
        try {
            conn = DBConfig.connect();
            conn.commit();
            stmt = conn.prepareStatement("SELECT * FROM invitations WHERE inviter=?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if(rs.next()) {
                ret =  true;
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
}
