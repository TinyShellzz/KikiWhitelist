package com.tinyshellzz.kikiwhitelist.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.userMCMapper;

public class PlayerQuitListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        String mc_uuid = event.getPlayer().getUniqueId().toString().replace("-", "");
        String user_name = event.getPlayer().getDisplayName();

        if(userMCMapper.exists_uuid(mc_uuid)) {
            userMCMapper.update_user_name_by_uuid(mc_uuid, user_name, user_name);
            userMCMapper.update_login_time_by_uuid(mc_uuid);
        }
    }
}
