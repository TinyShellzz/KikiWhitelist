package com.tinyshellzz.kikiwhitelist.listener;

import com.tinyshellzz.kikiwhitelist.ObjectPool;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.userMCMapper;

public class PlayerQuitListener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        String mc_uuid = event.getPlayer().getUniqueId().toString().replace("-", "");
        String user_name = event.getPlayer().getDisplayName();

        if(userMCMapper.exists_uuid(mc_uuid)) {
            userMCMapper.update_user_name_by_uuid(mc_uuid, user_name, user_name);
            userMCMapper.update_login_time_by_uuid(mc_uuid);
        }
    }
}
