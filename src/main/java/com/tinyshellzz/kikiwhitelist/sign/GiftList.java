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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            int amount = 0;
            Pattern r = Pattern.compile("(.*?)[xX]([0-9]{1,2})$");
            Matcher m = r.matcher(item);
            if(m.find()){
                amount = Integer.parseInt(m.group(2));
                item = m.group(1);
            }

            if(item.equals("random")) {
                ItemStack randomGift = getRandomGift();
                randomGift.setAmount(amount);
                gifts.add(randomGift);
                continue;
            }

            Material material = itemList.get(item.toUpperCase());
            if(material != null) {
                gifts.add(new ItemStack(material, amount));
                continue;
            }

            ItemStack i = ItemStackManager.getItem(item);
            if(i != null) {
                i.setAmount(amount);
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
        itemList.put("ANVIL", Material.ANVIL);
        itemList.put("APPLE", Material.APPLE);
        itemList.put("ARROW", Material.ARROW);
        itemList.put("BAMBOO", Material.BAMBOO);
        itemList.put("BARREL", Material.BARREL);
        itemList.put("BEACON", Material.BEACON);
        itemList.put("BEEF", Material.BEEF);
        itemList.put("BEE_NEST", Material.BEE_NEST);
        itemList.put("BELL", Material.BELL);
        itemList.put("BIRCH_LOG", Material.BIRCH_LOG);
        itemList.put("BOOK", Material.BOOK);
        itemList.put("BOOKSHELF", Material.BOOKSHELF);
        itemList.put("BOW", Material.BOW);
        itemList.put("BOWL", Material.BOWL);
        itemList.put("BUCKET", Material.BUCKET);
        itemList.put("CAKE", Material.CAKE);
        itemList.put("CHEST", Material.CHEST);
        itemList.put("CLAY", Material.CLAY);
        itemList.put("CLOCK", Material.CLOCK);
        itemList.put("COMPASS", Material.COMPASS);
        itemList.put("POTATO", Material.POTATO);

        // 有价值的物品
        itemList.put("DIAMOND", Material.DIAMOND);
        itemList.put("DIAMOND_ORE", Material.DIAMOND_ORE);
        itemList.put("ELYTRA", Material.ELYTRA);
        // 下届合金
        itemList.put("ANCIENT_DEBRIS", Material.ANCIENT_DEBRIS);
        itemList.put("NETHERITE_BLOCK", Material.NETHERITE_BLOCK);
        itemList.put("NETHERITE_SCRAP", Material.NETHERITE_SCRAP);
        itemList.put("NETHERITE_INGOT", Material.NETHERITE_INGOT);


        keys.addAll(itemList.keySet());
    }
}
