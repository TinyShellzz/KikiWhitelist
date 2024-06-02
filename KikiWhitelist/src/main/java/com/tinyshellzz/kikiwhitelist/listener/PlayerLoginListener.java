package com.tinyshellzz.kikiwhitelist.listener;

import com.tinyshellzz.kikiwhitelist.GlobalObjects;
import com.tinyshellzz.kikiwhitelist.KIkiWhitelist;
import com.tinyshellzz.kikiwhitelist.config.Config;
import com.tinyshellzz.kikiwhitelist.database.UserMapper;
import com.tinyshellzz.kikiwhitelist.database.WhitelistCodeMapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.File;
import java.util.UUID;

public class PlayerLoginListener implements Listener {
    private WhitelistCodeMapper mapper = GlobalObjects.whitelistCodeMapper;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) throws InterruptedException {
        // 在白名单中, 就直接允许
        if(event.getPlayer().isWhitelisted()){
            if(GlobalObjects.usermapper != null) GlobalObjects.usermapper.update_user_name_by_uuid(event.getPlayer().getUniqueId().toString(), event.getPlayer().getDisplayName());

            event.allow();
        } else {
            String code = UUID.randomUUID().toString().substring(0, 6);
            // 防止有重复的验证码
            while(mapper.exists_code(code)){
                code = UUID.randomUUID().toString().substring(0, 6);
            }

            String user_name = event.getPlayer().getDisplayName();
            String mc_uuid = event.getPlayer().getUniqueId().toString();
            mapper.insert_by_uuid(mc_uuid, code, user_name);

            // 如果不在白名单中, 就发送验证码要求
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, String.format("你不在白名单中, 请在群输入: \n%s%s", Config.get("code-prefix"), code));
        }
    }
}
