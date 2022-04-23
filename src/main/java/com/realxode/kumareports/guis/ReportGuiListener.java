package com.realxode.kumareports.guis;

import com.realxode.kumareports.KumaReports;
import com.realxode.kumareports.utils.DiscordWebhook;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.realxode.kumareports.utils.TextHandler.color;

public class ReportGuiListener implements Listener {

    private final KumaReports plugin;

    public ReportGuiListener(KumaReports plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(ReportGui.getInventory())) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().equals(Material.AIR)) return;

        final Player player = (Player) e.getWhoClicked();


        for (String key : plugin.guiConfig.getConfigurationSection("gui.items").getKeys(false)) {
            ConfigurationSection items = plugin.guiConfig.getConfigurationSection("gui.items." + key);
            int slot = items.getInt("slot");
            if (e.getRawSlot() == slot) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH;mm;ss");
                LocalDateTime now = LocalDateTime.now();
                plugin.reportsConfig.set("reports." + ReportGui.getTarget().getUniqueId() + "." + dtf.format(now) + "." + "reason", items.getString("reason"));
                plugin.reportsConfig.set("reports." + ReportGui.getTarget().getUniqueId() + "." + dtf.format(now) + "." + "reported-by",
                        ReportGui.getReporter().getName());
                try {
                    plugin.reportsConfig.save(plugin.reportsFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                player.closeInventory();
                player.sendMessage(color("&6&lREPORTS > &fYou successfully reported &7&o" + ReportGui.getTarget().getName() + "&f with the reason: "
                        + items.getString("reason") + "."));
                DiscordWebhook discordWebhook = new DiscordWebhook(plugin.getUrl());
                dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                discordWebhook.addEmbed(new DiscordWebhook.EmbedObject().setTitle("New Report!")
                        .setDescription("The player " + ReportGui.getTarget().getName() + " has been reported 5 times.")
                        .addField("Reporter:", ReportGui.getTarget().getName(), true)
                        .addField("Reason:", items.getString("reason"), true)
                        .setFooter(dtf.format(now),
                                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fes.vexels.com%2Fsvg-png%2Freloj%2F&psig=AOvVaw31VbxdlNPeHFeaGDDJNbDl&ust=1650640033405000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCJCCrbO3pfcCFQAAAAAdAAAAABA1")
                        .setThumbnail("https://www.google.com/url?sa=i&url=https%3A%2F%2Fflyclipart.com%2Freport-png-icon-free-download-report-png-235859&psig=AOvVaw3dFwnsI9_GunA5GzybgBGD&ust=1650640300938000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCPCYkrO4pfcCFQAAAAAdAAAAABAc"));

                try {
                    discordWebhook.execute();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        }
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(ReportGui.getInventory())) {
            e.setCancelled(true);
        }
    }


}
