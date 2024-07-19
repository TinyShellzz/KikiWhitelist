package com.tinyshellzz.kikiwhitelist.database;

public class BanlistUser {
    public String mc_uuid;
    public String user_name;
    public String display_name;
    public String source;
    public String unban_date;
    public String reason;

    public BanlistUser(String mc_uuid, String user_name, String display_name, String source, String unban_date, String reason) {
        this.mc_uuid = mc_uuid;
        this.user_name = user_name;
        this.display_name = display_name;
        this.source = source;
        this.unban_date = unban_date;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "BanlistUser{" +
                "mc_uuid='" + mc_uuid + '\'' +
                ", user_name='" + user_name + '\'' +
                ", display_name='" + display_name + '\'' +
                ", source='" + source + '\'' +
                ", unban_date='" + unban_date + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
