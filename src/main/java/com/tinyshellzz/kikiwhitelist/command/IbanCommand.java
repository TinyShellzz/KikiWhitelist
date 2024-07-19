package com.tinyshellzz.kikiwhitelist.command;

import com.tinyshellzz.kikiwhitelist.database.BanlistUser;
import com.tinyshellzz.kikiwhitelist.database.MCUser;
import com.tinyshellzz.kikiwhitelist.database.UserMCMapper;
import com.tinyshellzz.kikiwhitelist.tools.Tools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.plugin.Plugin;
import org.slf4j.helpers.NOPLogger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.*;

public class IbanCommand  implements TabExecutor {
    private Plugin plugin;


    public IbanCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getConsoleSender().sendMessage("sender: " + sender.getName());
        Matcher _m = Pattern.compile("^.*CraftRemoteConsoleCommandSender.*$").matcher(sender.toString());
        if(!(sender instanceof ConsoleCommandSender || _m.find() || sender.isOp())){
            sender.sendMessage("只有控制台和op才能使用该命令");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("iban命令参数不足, 使用方式 /iban tinyshellzzz 6d 有问题找管理");
            return false;
        }
        String player = args[0].toLowerCase();

        // 读取封禁时间，单位s
        long duration = -1;
        int index = 1;
        if(index != args.length) {
            Pattern p = Pattern.compile("^[0-9]+[smhdMyY]$");
            Matcher m = p.matcher(args[1]);

            if (m.find()) {
                char unit = args[1].charAt(args[1].length() - 1);
                long number = Long.decode(args[1].substring(0, args[1].length() - 1));
                if (unit == 's') {
                    duration = number;
                } else if (unit == 'm') {
                    duration = number * 60;
                } else if (unit == 'h') {
                    duration = number * 3600;
                } else if (unit == 'd') {
                    duration = number * 86400;
                } else if (unit == 'M') {
                    duration = number * 86400 * 30;
                } else if (unit == 'y' || unit == 'Y') {
                    duration = number * 86400 * 365;
                }

                index++;
            }
        }
        String unban_date = null;
        if(duration == -1) {
            unban_date = "9999-12-31 23:59:59";
        } else {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            unban_date = dateTimeFormatter.format(now.plusSeconds(duration));
        }



        // 读取备注
        StringBuilder reason = new StringBuilder("");
        if(index != args.length) {
            for (; index < args.length - 1; index++) {
                reason.append(args[index] + " ");
            }
            reason.append(args[index]);
        }

        String mc_uuid = null;
        String user_name = null;
        try {
            String[] ret = Tools.get_player(player);
            mc_uuid = ret[0];
            user_name = ret[1];
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "IbanCommand" + ": " + e.getMessage());
        }

        if(mc_uuid == null) {
            OfflinePlayer[] offlinePlayers = Bukkit.getServer().getOfflinePlayers();
            for(OfflinePlayer _p : offlinePlayers) {
                Player p = _p.getPlayer();
                if(p.getDisplayName().toLowerCase().equals(player)) {
                    user_name = p.getDisplayName();
                    mc_uuid = p.getUniqueId().toString().replace("-", "");
                }
            }
        }

        if(mc_uuid == null) {
            sender.sendMessage(ChatColor.RED + "IbanCommand" + ": " + "该玩家不存在");
        }

        BanlistUser user = new BanlistUser(mc_uuid, user_name.toLowerCase(), user_name, sender.getName(), unban_date, reason.toString());

        banlistMapper.insert(user);
        Player p = Bukkit.getPlayer(user_name);
        if(p != null) p.kickPlayer(reason.toString());
        StringBuilder msg = new StringBuilder(user.display_name + "因" + reason + "被封禁" + " " + user.display_name + "已被封禁");
        if(userMCMapper.exists_uuid(user.mc_uuid)) {
            MCUser mc_user = userMCMapper.get_user_by_uuid(user.mc_uuid);
            ArrayList<MCUser> res = invitationMapper.get_relations(mc_user.id);

            // 连 ban 相关
            if(res != null && res.size() > 1) {
                msg.append("连坐：");
                for(MCUser r: res){
                    BanlistUser u = new BanlistUser(r.mc_uuid, r.user_name, r.display_name, sender.getName(), unban_date, "连坐" + user.display_name + ":" + reason);
                    if(!Objects.equals(user.mc_uuid, u.mc_uuid)) {
                        banlistMapper.insert(u);
                        p = Bukkit.getPlayer(r.user_name);
                        if (p != null) p.kickPlayer("连坐" + user.display_name + ":" + reason);
                        msg.append(u.display_name);
                        msg.append(",");
                    }
                }

                if(msg.charAt(msg.length()-1) == ','){
                    msg = new StringBuilder(msg.substring(0, msg.length() - 1));
                }
            }
        }

        msg.append(" 封禁理由：").append(reason);
        sender.sendMessage(msg.toString());
        if(!(sender instanceof ConsoleCommandSender)) {
            Bukkit.getConsoleSender().sendMessage(msg.toString());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
