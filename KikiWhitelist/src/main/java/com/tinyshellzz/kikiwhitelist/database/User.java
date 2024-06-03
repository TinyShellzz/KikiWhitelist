package com.tinyshellzz.kikiwhitelist.database;

public class User {
    public String qq_num;
    public String user_name;
    public String mc_uuid;
    public String whitelisted;
    public String user_info;

    public User(String qq_num, String user_name, String mc_uuid, String whitelisted, String user_info) {
        this.qq_num = qq_num;
        this.user_name = user_name;
        this.mc_uuid = mc_uuid;
        this.whitelisted = whitelisted;
        this.user_info = user_info;
    }
}
