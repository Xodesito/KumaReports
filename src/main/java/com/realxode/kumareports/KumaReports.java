package com.realxode.kumareports;

import com.realxode.kumareports.cmds.ReportCommand;
import com.realxode.kumareports.guis.ReportGuiListener;
import com.realxode.kumareports.utils.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class KumaReports extends JavaPlugin {

    private String url;
    // gui
    public File guiFile = new File(this.getDataFolder() + "/gui.yml");
    public FileConfiguration guiConfig = YamlConfiguration.loadConfiguration(guiFile);

    // reports
    public File reportsFile = new File(this.getDataFolder() + "/reports.yml");
    public FileConfiguration reportsConfig = YamlConfiguration.loadConfiguration(reportsFile);

    @Override
    public void onEnable() {
        // PLACEHOLDER API
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().log(Level.WARNING, "The plugin requires PlaceholderAPI to work. Stopping plugin...");
            this.getPluginLoader().disablePlugin(this);
        }

        // CONFIG
        this.saveDefaultConfig();
        saveYml(guiConfig, guiFile);
        saveYml(reportsConfig, reportsFile);

        // DISCORD WEBHOOK
        url = this.getConfig().getString("WEBHOOK-URL");
        if (url.equalsIgnoreCase("YOUR-WEBHOOK-URL")) {
            getLogger().log(Level.WARNING, "The webhook URL is not set.");
            getLogger().log(Level.WARNING, "To set it, go to config.yml and change the value of the \"WEBHOOK-URL\" property to the URL of your webhook.");
            getLogger().log(Level.WARNING, "Stopping plugin...");
            this.getPluginLoader().disablePlugin(this);
            return;
        }
        DiscordWebhook discordWebhook = new DiscordWebhook(url);
        discordWebhook.setUsername("ITALIA GANGA");
        discordWebhook.setContent(this.getConfig().getString("start-message"));
        try {
            discordWebhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // CMD-EVENT REGISTER
        this.getCommand("report").setExecutor(new ReportCommand(this));
        Bukkit.getPluginManager().registerEvents(new ReportGuiListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void saveYml(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getUrl() {
        return url;
    }
}
