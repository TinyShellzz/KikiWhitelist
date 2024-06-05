package com.tinyshellzz.kikiwhitelist.database;

public class User {
    public String qq_num;
    public String user_name;
    public String display_name;
    public String mc_uuid;
    public String whitelisted;
    public String last_login_time;
    public String user_info;

    public User(String qq_num, String user_name, String display_name, String mc_uuid, String whitelisted, String last_login_time, String user_info) {
        this.qq_num = qq_num;
        this.user_name = user_name;
        this.display_name = display_name;
        this.mc_uuid = mc_uuid;
        this.whitelisted = whitelisted;
        this.last_login_time = last_login_time;
        this.user_info = user_info;
    }
}
