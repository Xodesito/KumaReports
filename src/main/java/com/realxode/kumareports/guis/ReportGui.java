package com.realxode.kumareports.guis;

import com.realxode.kumareports.KumaReports;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.realxode.kumareports.utils.TextHandler.color;

public class ReportGui implements Listener {

    private final KumaReports plugin;
    private static Player reporter;
    private static Player target;
    private static Inventory inventory = null;

    public ReportGui(KumaReports plugin, Player reporter, Player target) {
        this.plugin = plugin;
        ReportGui.reporter = reporter;
        ReportGui.target = target;
        inventory = Bukkit.createInventory(null, plugin.guiConfig.getInt("gui.rows"), PlaceholderAPI.setPlaceholders
                (target, plugin.guiConfig.getString("gui.title").replace("{player}", reporter.getName()).replace("{target}",
                        target.getName())));
    }

    public Inventory reportGui() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();

        for (String key : plugin.guiConfig.getConfigurationSection("gui.items").getKeys(false)) {
            ConfigurationSection items = plugin.guiConfig.getConfigurationSection("gui.items." + key);
            itemStack = new ItemStack(Material.valueOf(items.getString("material")));
            itemMeta.setDisplayName(PlaceholderAPI.setPlaceholders(target, color(items.getString("display-name"))));

            List<String> loreWithoutColor = items.getStringList("lore");

            List<String> lore = new ArrayList<>();

            for (String lines : loreWithoutColor) {
                lore.add(PlaceholderAPI.setPlaceholders(target, color(lines)));
            }

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(items.getInt("slot"), itemStack);
        }

        return inventory;
    }

    public static Player getReporter() {
        return reporter;
    }

    public static Player getTarget() {
        return target;
    }

    public static Inventory getInventory() {
        return inventory;
    }
}
