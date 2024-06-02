package com.tinyshellzz.kikiwhitelist.database;

public class User {
    public String qq_num;
    public String user_name;
    public String mc_uuid;
    public String is_banned;
    public String user_info;

    public User(String qq_num, String user_name, String mc_uuid, String is_banned, String user_info) {
        this.qq_num = qq_num;
        this.user_name = user_name;
        this.mc_uuid = mc_uuid;
        this.is_banned = is_banned;
        this.user_info = user_info;
    }
}
