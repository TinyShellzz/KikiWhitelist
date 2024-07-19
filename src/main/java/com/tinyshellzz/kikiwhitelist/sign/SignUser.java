package com.tinyshellzz.kikiwhitelist.sign;

public class SignUser {
    public String qq_num;
    public String code;
    public String timestamp;
    public int redeemed;

    public SignUser(String qq_num, String code, String timestamp, int redeemed) {
        this.qq_num = qq_num;
        this.code = code;
        this.timestamp = timestamp;
        this.redeemed = redeemed;
    }
}
