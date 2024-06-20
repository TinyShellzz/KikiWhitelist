package com.tinyshellzz.kikiwhitelist.database;

public class MCUser {
    public long id;
    public String qq_num;
    public String user_name;
    public String display_name;
    public String mc_uuid;
    public String last_login_time;
    public String remark;

    public MCUser(long id, String qq_num, String user_name, String display_name, String mc_uuid, String last_login_time, String remark) {
        this.id = id;
        this.qq_num = qq_num;
        this.user_name = user_name;
        this.display_name = display_name;
        this.mc_uuid = mc_uuid;
        this.last_login_time = last_login_time;
        this.remark = remark;
    }
}
