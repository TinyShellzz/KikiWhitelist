package com.tinyshellzz.kikiwhitelist.listener;

import com.tinyshellzz.kikiwhitelist.ObjectPool;
import com.tinyshellzz.kikiwhitelist.config.DBConfig;
import com.tinyshellzz.kikiwhitelist.config.ItemStackManager;
import com.tinyshellzz.kikiwhitelist.database.BanlistUser;
import com.tinyshellzz.kikiwhitelist.database.MCUser;
import com.tinyshellzz.kikiwhitelist.database.UserMCMapper;
import com.tinyshellzz.kikiwhitelist.database.CodeMCMapper;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.banlistMapper;
import static com.tinyshellzz.kikiwhitelist.ObjectPool.pluginConfig;

public class PlayerLoginListener implements Listener {
    private CodeMCMapper codemapper = ObjectPool.codeMCMapper;

    private UserMCMapper userMCMapper = ObjectPool.userMCMapper;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        boolean whitelisted = false;
        String mc_uuid = event.getPlayer().getUniqueId().toString().replace("-", "");
        String user_name = event.getPlayer().getDisplayName();
        if(banlistMapper.exists_uuid(mc_uuid)) {
            BanlistUser b_user = banlistMapper.get_user_by_uuid(mc_uuid);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now_str = dateTimeFormatter.format(now);
            if (b_user != null && now_str.compareTo(b_user.unban_date) < 0) {
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "你已被封禁: " + b_user.reason + "\n 解封时间: " + b_user.unban_date);
            }
        }

        if(userMCMapper.exists_uuid(mc_uuid)){
            userMCMapper.update_user_name_by_uuid(mc_uuid, user_name, user_name);

            MCUser user = userMCMapper.get_user_by_uuid(mc_uuid);
            Bukkit.getConsoleSender().sendMessage(" " + user.id);
            if(userMCMapper.exists_whitelist(user.id)) {
                whitelisted = true;
                userMCMapper.update_login_time_by_uuid(mc_uuid);
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
            String kickMessage = ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() +
                    "============================Tcc============================\n      \n" +
                    ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "你的游戏账号还没有绑定QQ哦\n" +
                    ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "请在Tcc大群内输入以下内容进行绑定!\n" +
                    ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "请在群内输入:   " +
                    ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + pluginConfig.code_prefix + code + "\n      \n" +
                    ChatColor.RED.toString() + ChatColor.BOLD.toString() + "在群内绑定完毕后返回游戏重新连接服务器即可\n      \n" +
                    ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "============================Tcc============================";

            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, kickMessage);
        }
    }
}
