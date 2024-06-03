package com.tinyshellzz.kikiwhitelist;

import com.tinyshellzz.kikiwhitelist.database.UserMapper;
import com.tinyshellzz.kikiwhitelist.database.WhitelistCodeMapper;

import java.util.Map;

public class ObjectPool {
    public static WhitelistCodeMapper whitelistCodeMapper;
    public static UserMapper usermapper;
    public static Map<Object, Object> config;
    public static KIkiWhitelist plugin;
}
