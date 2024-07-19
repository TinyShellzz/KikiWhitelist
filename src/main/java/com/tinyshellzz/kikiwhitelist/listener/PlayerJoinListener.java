package com.tinyshellzz.kikiwhitelist.listener;

import com.tinyshellzz.kikiwhitelist.config.ItemStackManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void handle(PlayerJoinEvent event) {
        ItemStack tcc_server = ItemStackManager.getItem("tcc_server");
//        Bukkit.getConsoleSender().sendMessage("item: " + tcc_server.toString());
        event.getPlayer().getInventory().addItem(tcc_server);
    }
}
