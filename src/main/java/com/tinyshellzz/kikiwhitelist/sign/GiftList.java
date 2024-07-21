package com.tinyshellzz.kikiwhitelist.sign;

import com.tinyshellzz.kikiwhitelist.ObjectPool;
import com.tinyshellzz.kikiwhitelist.config.ConfigWrapper;
import com.tinyshellzz.kikiwhitelist.config.ItemStackManager;
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

public class GiftList {
    static  List<List<String>> rewords = new ArrayList<>();
    static {
        for(int i = 0; i < 32; i ++) {
            rewords.add(new ArrayList<>());
        }
    }
    private static ConfigWrapper configWrapper = new ConfigWrapper(plugin, "sign_in/rewords.yml");
    static {
        plugin.saveResource("sign_in/giftlist.txt", false);
    }

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
            int amount = 1;
            Pattern r = Pattern.compile("^(.*)[xX]([0-9]{1,2})$");
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

            Material material = null;
            try {
                Object o = Material.class.getField(item.toUpperCase()).get(null);
                if(o instanceof Material) {
                    material = (Material) o;
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
            }
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


        Set<String> giftlist = new HashSet<>();;

        try (BufferedReader br = new BufferedReader(new FileReader(new File(plugin.getDataFolder(), "sign_in/giftlist.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                giftlist.add(line.strip().toUpperCase());
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
                            Material material = (Material) o;
                            itemList.put(field_name, material);
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
        customItemKeys.addAll(ItemStackManager.itemMap.keySet());
    }

    private static HashMap<String, Material> itemList;
    private static List<String> itemKeys;

    private static List<String> customItemKeys;
}
