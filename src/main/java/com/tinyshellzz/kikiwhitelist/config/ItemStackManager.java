package com.tinyshellzz.kikiwhitelist.config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.tinyshellzz.kikiwhitelist.ObjectPool.plugin;

public class ItemStackManager {
    public static final Map<String, ItemStack> itemMap = new HashMap<>();

    public static void save(String itemName, ItemStack item) {
        ConfigWrapper configWrapper = new ConfigWrapper(plugin, "items/" + itemName + ".yml");
        configWrapper.set(itemName, item);
        configWrapper.saveConfig();

        itemMap.put(itemName, item);
    }

    public static ItemStack getItem( String itemName) {
        return itemMap.get(itemName);
    }

    public static void load(String namespace) {
        ConfigWrapper configWrapper = new ConfigWrapper(plugin, "items/" + namespace + ".yml");

        load_0(configWrapper);
    }

    private static void load_0(ConfigWrapper configWrapper) {
        String itemName = configWrapper.getFile().getName().replaceFirst("[.][^.]+$", "");;

        YamlConfiguration config = configWrapper.getConfig();
        Set<String> itemKeySet = config.getKeys(false);
        for (String itemKey : itemKeySet) {
            try {
                ItemStack item = config.getItemStack(itemKey);
                if (item == null) {
                    throw new NullPointerException("Item " + itemKey + " is null");
                }
                itemMap.put(itemName, item);
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + e.getMessage() + "\n" + e.getStackTrace());
            }
        }
    }

    /**
     * reload all items
     */
    public static void reload() {
        File itemFolder = new File(plugin.getDataFolder(), "items");
        new ConfigWrapper(plugin, "items/tcc_server.yml");   // 保存例子

        File[] files = itemFolder.listFiles();
        for(File f: files) {
            ConfigWrapper configWrapper = new ConfigWrapper(f);
            load_0(configWrapper);
        }
    }
}