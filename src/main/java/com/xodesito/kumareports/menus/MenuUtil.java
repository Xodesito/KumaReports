package com.xodesito.kumareports.menus;

import com.xodesito.kumareports.KumaReports;
import com.xodesito.kumareports.report.Report;
import com.xodesito.kumareports.report.ReportStatus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {

    static void createItemForEachReport(Report report, KumaReports plugin, Inventory inventory) {
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

        reportMeta.setLore(lore);
        reportItem.setItemMeta(reportMeta);
        inventory.addItem(reportItem);
    }

}
