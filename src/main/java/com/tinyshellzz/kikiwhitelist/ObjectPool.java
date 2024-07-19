package com.tinyshellzz.kikiwhitelist;

import com.tinyshellzz.kikiwhitelist.config.PluginConfig;
import com.tinyshellzz.kikiwhitelist.database.BanlistMapper;
import com.tinyshellzz.kikiwhitelist.database.InvitationMapper;
import com.tinyshellzz.kikiwhitelist.database.UserMCMapper;
import com.tinyshellzz.kikiwhitelist.database.CodeMCMapper;

import java.util.Map;

public class ObjectPool {
    public static CodeMCMapper codeMCMapper;
    public static UserMCMapper userMCMapper;
    public static BanlistMapper banlistMapper;

    public static InvitationMapper invitationMapper;
    public static Map<Object, Object> config;
    public static KIkiWhitelist plugin;
    public static PluginConfig pluginConfig;
}
