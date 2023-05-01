package com.xodesito.kumareports.menus;

import com.xodesito.kumareports.KumaReports;
import com.xodesito.kumareports.report.Report;
import com.xodesito.kumareports.report.ReportStatus;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.xodesito.api.head.HeadHandler.getHead;
import static com.xodesito.api.text.ChatUtil.translate;

@Getter
public class CheckReportsFromMenu {

    private final KumaReports plugin;
    private Inventory inventory;

    public CheckReportsFromMenu(KumaReports plugin) {
        this.plugin = plugin;
    }

    public void openMenu(String name, Player player) {

        inventory = plugin.getServer().createInventory(null, 54,
                plugin.getLangFile().getString("menus.checkFrom.title").replace("$player$", name));

        // Main item
        ItemStack itemStack = new ItemStack(Material.DIRT);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Test");
        itemMeta.setLore(List.of("Bug! Contact the developer!"));
        itemStack.setItemMeta(itemMeta);

        // Back item
        ItemStack back = getHead("f84f597131bbe25dc058af888cb29831f79599bc67c95c802925ce4afba332fc");
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(plugin.getLangFile().getString("menus.checkFrom.previousPage"));
        back.setItemMeta(backMeta);

        // Next item
        ItemStack next = getHead("18660691d1ca029f120a3ff0eabab93a2306b37a7d61119fcd141ff2f6fcd798");
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.setDisplayName(plugin.getLangFile().getString("menus.checkFrom.nextPage"));
        next.setItemMeta(nextMeta);

        // Check if the player has reports

        if (plugin.getReportManager().getReportsFromUuid(Bukkit.getOfflinePlayer(name).getUniqueId()).isEmpty()
                || plugin.getReportManager().getReportsFromUuid(Bukkit.getOfflinePlayer(name).getUniqueId()) == null) {
            ItemStack noReports = new ItemStack(Material.BARRIER);
            ItemMeta noReportsMeta = noReports.getItemMeta();
            noReportsMeta.setDisplayName(plugin.getLangFile().getString("menus.checkTo.noReports"));
            noReports.setItemMeta(noReportsMeta);
            inventory.setItem(22, noReports);
        }

        // Fill the inventory with Reports
        for (Report report : plugin.getReportManager().getReportsFromUuid(Bukkit.getOfflinePlayer(name).getUniqueId())) {
            ItemStack reportItem = new ItemStack(Material.PAPER);
            ItemMeta reportMeta = reportItem.getItemMeta();
            reportMeta.setDisplayName(plugin.getLangFile().getString("menus.report.title").replace("$reportId$", String.valueOf(report.getId())));
            List<String> lore = new ArrayList();
            lore.add(plugin.getLangFile().getString("menus.report.reportedBy").replace("$reporter$", report.getReporterName()));
            lore.add(plugin.getLangFile().getString("menus.report.reported").replace("$reported$", Bukkit.getPlayer(report.getReportedUuid()).getName()));
            lore.add(plugin.getLangFile().getString("menus.report.reason").replace("$reason$", report.getReason()));
            lore.add(plugin.getLangFile().getString("menus.report.date").replace("$date$", report.getDateString()));
            if (report.getStatus() == ReportStatus.OPEN) {
                lore.add(plugin.getLangFile().getString("menus.report.status.text").replace("$status$", plugin.getLangFile().getString("menus.report.status.open")));
            } else if (report.getStatus() == ReportStatus.CLOSED) {
                lore.add(plugin.getLangFile().getString("menus.report.status.text").replace("$status$", plugin.getLangFile().getString("menus.report.status.closed")));
            } else {
                lore.add(plugin.getLangFile().getString("menus.report.status.text").replace("$status$", "Bug! Contact the developer!"));
            }
            lore.add("&f");
            lore.add(translate("&7Click to see more details!"));

            reportMeta.setLore(lore);
            reportItem.setItemMeta(reportMeta);
            inventory.addItem(reportItem);
        }

        // Fill the inventory with the back and next items
        inventory.setItem(45, back);
        inventory.setItem(53, next);

        // Open the inventory
        player.openInventory(inventory);

    }

}
