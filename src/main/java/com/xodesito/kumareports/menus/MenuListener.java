package com.xodesito.kumareports.menus;

import com.xodesito.kumareports.KumaReports;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MenuListener implements Listener {

    private final CheckReportsToMenu checkReportsToMenu;
    private final CheckReportsFromMenu checkReportsFromMenu;
    private final KumaReports plugin;

    public MenuListener(KumaReports plugin, CheckReportsToMenu checkReportsToMenu, CheckReportsFromMenu checkReportsFromMenu) {
        this.checkReportsToMenu = checkReportsToMenu;
        this.checkReportsFromMenu = checkReportsFromMenu;
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory inventory = event.getClickedInventory();
        event.getInventory();
        if (!(inventory.getHolder() instanceof CheckReportsToMenu toInventory)) return;
        event.setCancelled(true);

    }

}
