package com.tinyshellzz.kikiwhitelist.sign;

import com.tinyshellzz.kikiwhitelist.config.ConfigWrapper;
import com.tinyshellzz.kikiwhitelist.config.ItemStackManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.lang.constant.Constable;
import java.util.*;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.plugin;
import static java.util.Map.entry;

public class GiftList {
    static  List<List<String>> rewords = new ArrayList<>();
    static {
        for(int i = 0; i < 32; i ++) {
            rewords.add(new ArrayList<>());
        }
    }
    private static ConfigWrapper configWrapper = new ConfigWrapper(plugin, "rewords.yml");

    /**
     * 依据日期获取礼物
     * @param day
     * @return
     */
    public static List<ItemStack> getGift(int day) {
        Bukkit.getConsoleSender().sendMessage("==================getGift===================");
        List<ItemStack> gifts = new ArrayList<>();

        List<String> rews = rewords.get(day);
        for(String item: rews) {
            Bukkit.getConsoleSender().sendMessage("==================attempt git gift " + item);
            if(item.equals("random")) {
                gifts.add(getRandomGift());
                continue;
            }

            Material m = itemList.get(item);
            if(m != null) {
                Bukkit.getConsoleSender().sendMessage("==================add gift ===================");
                gifts.add(new ItemStack(m));
                continue;
            }

            ItemStack i = ItemStackManager.getItem(item);
            if(i != null) {
                Bukkit.getConsoleSender().sendMessage("==================add gift===================");
                gifts.add(i);
            }
        }

        return gifts;
    }

    public static ItemStack getRandomGift(){
        Bukkit.getConsoleSender().sendMessage("=================getRandomGift====================");
        Random rand = new Random();

        // Obtain a number between [0 - keys.size()-1].
        int n = rand.nextInt(keys.size());

        Material m = itemList.get(keys.get(n));
        return new ItemStack(m);
    }

    public static void reload() {
        configWrapper.reloadConfig(); // 重新加载配置文件
        for(int i = 1; i < 32; i++) {
            rewords.get(i).clear();
        }
        YamlConfiguration config = configWrapper.getConfig();

        MemorySection month = (MemorySection)config.get("month");
        for(Integer i = 1; i < 32 ; i += 1) {

            List<String> re = (List)month.get(i.toString());
            if(re != null) {
                rewords.get(i).addAll(re);
                for(String s: rewords.get(i)) {
                    Bukkit.getConsoleSender().sendMessage(i + ": " + s);
                }
            }
        }
    }

    private static HashMap<String, Material> itemList;
    private static List<String> keys = new ArrayList<>();
    static {
        itemList = new HashMap<>();
        itemList.put("potato", Material.POTATO);
        itemList.put("CAKE", Material.CAKE);
        itemList.put("CHEST", Material.CHEST);
        itemList.put("CLAY", Material.CLAY);

        keys.addAll(itemList.keySet());
    }
}
