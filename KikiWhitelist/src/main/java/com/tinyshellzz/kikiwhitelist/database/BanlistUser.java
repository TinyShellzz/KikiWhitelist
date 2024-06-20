package com.tinyshellzz.kikiwhitelist.database;

public class BanlistPlayer {
    public String uuid;
    public String user_name;
    public String display_name;
    public String unban_date;
    public String reason;

    public BanlistPlayer(String uuid, String user_name, String display_name, String unban_date, String reason) {
        this.uuid = uuid;
        this.user_name = user_name;
        this.display_name = display_name;
        this.unban_date = unban_date;
        this.reason = reason;
    }
}
