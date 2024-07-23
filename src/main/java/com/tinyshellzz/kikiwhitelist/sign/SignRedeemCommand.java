package com.tinyshellzz.kikiwhitelist.sign;

import com.tinyshellzz.kikiwhitelist.database.MCUser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.*;

public class SignRedeemCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Bukkit.getConsoleSender().sendMessage("==================sign command===================");

        if(!(sender instanceof Player)) {
            sender.sendMessage("只有玩家才能使用该命令");
            return true;
        }
        Player player = (Player) sender;
        if(args.length < 1) {
            sender.sendMessage("你还未输入验证码");
            return false;
        }
        Bukkit.getConsoleSender().sendMessage("=====================================");

        String mc_uuid = player.getUniqueId().toString().replace("-", "");
        String code = args[0];
        MCUser mc_user = userMCMapper.get_user_by_uuid(mc_uuid);
        SignUser sign_user = signMapper.get_user(mc_user.qq_num, code);
        if(sign_user != null && sign_user.redeemed != 1) {
            Pattern r = Pattern.compile("-([0-9]{2}) ");
            Bukkit.getConsoleSender().sendMessage(sign_user.timestamp);
            Matcher m = r.matcher(sign_user.timestamp);
            m.find();

            Bukkit.getConsoleSender().sendMessage("礼物日期: " + m.group(1));
            int day = Integer.decode(m.group(1));
            List<ItemStack> gifts = GiftList.getGift(day);

            StringBuilder msg = new StringBuilder(ChatColor.GREEN + "领取成功, 获得: ");
            // 依据日期给予玩家礼物
            for(ItemStack gift: gifts) {
                Bukkit.getConsoleSender().sendMessage(gift.toString());
                player.getInventory().addItem(gift);
                String name = gift.getItemMeta().getDisplayName();
                if(name == null || name.equals("")) {
                    name = gift.getItemMeta().getItemName();
                }
                if(name == null || name.equals("")) {
                    name = gift.getType().toString();
                }


                msg.append(name + "X" + gift.getAmount());
                msg.append(", ");
            }
            msg.setLength(msg.length() - 2);

            signMapper.set_redeemed(sign_user.qq_num, code);
            sender.sendMessage(msg.toString());
        } else {
            sender.sendMessage(ChatColor.RED + "验证码错误");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
