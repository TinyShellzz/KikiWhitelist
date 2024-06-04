package com.tinyshellzz.kikiwhitelist.listener;

import com.tinyshellzz.kikiwhitelist.ObjectPool;
import com.tinyshellzz.kikiwhitelist.config.Config;
import com.tinyshellzz.kikiwhitelist.database.User;
import com.tinyshellzz.kikiwhitelist.database.UserMapper;
import com.tinyshellzz.kikiwhitelist.database.WhitelistCodeMapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class PlayerLoginListener implements Listener {
    private WhitelistCodeMapper codemapper = ObjectPool.whitelistCodeMapper;

    private UserMapper usermapper = ObjectPool.usermapper;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        boolean whitelisted = false;
        String mc_uuid = event.getPlayer().getUniqueId().toString();
        String user_name = event.getPlayer().getDisplayName();

        if(usermapper.exists_uuid(mc_uuid)){
            if(ObjectPool.usermapper != null) ObjectPool.usermapper.update_user_name_by_uuid(mc_uuid, user_name, user_name);

            User user = usermapper.get_user_by_uuid(mc_uuid);
            if(user.whitelisted != null) {
                whitelisted = true;
                // event.allow();   ban 功能会失效
            }
        }

        if(!whitelisted){
            String code = UUID.randomUUID().toString().substring(0, 6);
            // 防止有重复的验证码
            while(codemapper.exists_code(code)){
                code = UUID.randomUUID().toString().substring(0, 6);
            }
            codemapper.insert_by_uuid(mc_uuid, code, user_name, user_name);

            // 如果不在白名单中, 就发送验证码要求
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, String.format("你不在白名单中, 请在群输入: \n%s%s", Config.get("code-prefix"), code));
        }
    }
}
