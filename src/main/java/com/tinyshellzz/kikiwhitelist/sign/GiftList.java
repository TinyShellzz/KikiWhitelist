package com.tinyshellzz.kikiwhitelist.sign;

import com.tinyshellzz.kikiwhitelist.ObjectPool;
import com.tinyshellzz.kikiwhitelist.config.ConfigWrapper;
import com.tinyshellzz.kikiwhitelist.config.ItemStackManager;
import com.tinyshellzz.kikiwhitelist.config.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.plugin;
import static com.tinyshellzz.kikiwhitelist.ObjectPool.pluginConfig;

public class GiftList {
    static  List<List<String>> rewords = new ArrayList<>();
    static {
        for(int i = 0; i < 32; i ++) {
            rewords.add(new ArrayList<>());
        }
    }
    public static ConfigWrapper configWrapper = new ConfigWrapper(plugin, "sign_in/rewords.yml");
    static {
        plugin.saveResource("sign_in/giftlist.txt", false);
    }

    /**
     * 依据日期获取礼物
     * @param day
     * @return
     */
    public static List<ItemStack> getGift(int day) {

        List<ItemStack> gifts = new ArrayList<>();

        List<String> rews = rewords.get(day);
        for(String item: rews) {
            Bukkit.getConsoleSender().sendMessage("==================getGift: " + item);
            int amount = 0;
            Pattern r = Pattern.compile("^(.*)[xX]([0-9]{1,2})$");
            Matcher m = r.matcher(item);
            if(m.find()){
                amount = Integer.parseInt(m.group(2));
                item = m.group(1);
            }

            if(item.equals("random")) {
                ItemStack randomGift = getRandomGift();
                if(amount != 0) randomGift.setAmount(amount);
                gifts.add(randomGift);
                continue;
            }

            Material material = null;
            try {
                Object o = Material.class.getField(item.toUpperCase()).get(null);
                if(o instanceof Material) {
                    material = (Material) o;
                }
            } catch (IllegalAccessException | NoSuchFieldException ignored) {
            }
            if(material != null) {
                ItemStack itemStack = new ItemStack(material);
                if(amount != 0) itemStack.setAmount(amount);
                gifts.add(itemStack);
                continue;
            }

            ItemStack i = ItemStackManager.getItem(item);
            if(i != null) {
                if(amount != 0) i.setAmount(amount);
                gifts.add(i);
            }
        }

        return gifts;
    }

    public static ItemStack getRandomGift(){
        Bukkit.getConsoleSender().sendMessage("=================getRandomGift====================");
        Random rand = new Random();

        Set<String> strings = itemList.keySet();
        // Obtain a number between [0 - keys.size()-1].
        int n = rand.nextInt(itemKeys.size() + customItemKeys.size());

        ItemStack item;
        if(n < itemKeys.size()) {
            Material m = itemList.get(itemKeys.get(n));
            item = new ItemStack(m);
        } else {
            item = ItemStackManager.getItem(customItemKeys.get(n-itemKeys.size()));
        }

        return item;
    }

    public static void reload() {
        configWrapper.reloadConfig(); // 重新加载配置文件
        for(int i = 1; i < 32; i++) {
            rewords.get(i).clear();
        }
        YamlConfiguration config = configWrapper.getConfig();

        Set<String> itemKeySet = config.getKeys(false);
        for (String itemKey : itemKeySet) {
            int i = Integer.parseInt(itemKey);
            if (i < 32) {
                List<String> re = (List) config.get(itemKey);
                if (re != null) {
                    rewords.get(i).addAll(re);
                }
            }
        }

        Set<String> giftlist = new HashSet<>();;

        try (BufferedReader br = new BufferedReader(new FileReader(new File(plugin.getDataFolder(), "sign_in/giftlist.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String strip = line.strip();
                if(strip != "") giftlist.add(strip.toUpperCase());
            }
        } catch (IOException e) {
        }


        itemList = new HashMap<>();
        Field[] declaredFields = Material.class.getDeclaredFields();
        for(Field field: declaredFields) {
            if(Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                try {
                    Object o = field.get(null);     // 获取静态属性
                    String field_name = field.getName();
                    boolean gift_blacklist = ObjectPool.pluginConfig.gift_blacklist;
                    if((gift_blacklist && !giftlist.contains(field_name)) || (!gift_blacklist && giftlist.contains(field_name))) {
                        if (o instanceof Material && !field_name.startsWith("LEGACY_")) {
                            if(pluginConfig.blacklist_pattern != null && gift_blacklist) {
                                Pattern r = Pattern.compile(pluginConfig.blacklist_pattern);
                                Matcher m = r.matcher(field_name);
                                if(!m.find()) {
                                    Material material = (Material) o;
                                    itemList.put(field_name, material);
                                }
                            } else {
                                Material material = (Material) o;
                                itemList.put(field_name, material);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        itemKeys = new ArrayList<>();
        itemKeys.addAll(itemList.keySet());
        customItemKeys = new ArrayList<>();
        Set<String> strings = ItemStackManager.itemMap.keySet();
        customItemKeys = new ArrayList<>();
        // 排除/rewards save 保存的物品
        Pattern r = Pattern.compile("^month_[0-9]{1,2}.*_[0-9]{1,2}$");
        for(String k: strings) {
            Matcher m = r.matcher(k);
            if(!m.find()) customItemKeys.add(k);
        }
    }

    private static HashMap<String, Material> itemList;

    // 用于随机物品
    private static List<String> itemKeys;
    // 用于随机物品
    private static List<String> customItemKeys;
}
