package com.xodesito.kumareports.menus;

import com.xodesito.kumareports.KumaReports;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {

    private final KumaReports plugin;

    public MenuListener(KumaReports plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory() != player.getInventory()) return;
        event.setCancelled(true);
    }

}
