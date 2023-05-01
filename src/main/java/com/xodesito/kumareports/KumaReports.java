package com.xodesito.kumareports;

import com.xodesito.api.file.FileConfig;
import com.xodesito.kumareports.commands.MainCommand;
import com.xodesito.kumareports.commands.MainTabComplete;
import com.xodesito.kumareports.commands.ReportCommand;
import com.xodesito.kumareports.commands.ReportTabComplete;
import com.xodesito.kumareports.jda.JDAManager;
import com.xodesito.kumareports.menus.CheckReportsFromMenu;
import com.xodesito.kumareports.menus.CheckReportsToMenu;
import com.xodesito.kumareports.menus.MenuListener;
import com.xodesito.kumareports.mongodb.MongoManager;
import com.xodesito.kumareports.report.ReportManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class KumaReports extends JavaPlugin {

    private MongoManager mongoManager;
    private ReportManager reportManager;
    private FileConfig langFile;
    private JDAManager jdaManager;
    private CheckReportsFromMenu checkReportsFromMenu;
    private CheckReportsToMenu checkReportsToMenu;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        initFiles();
        if (!getConfig().getBoolean("database.mongodb.enabled")) {
            getLogger().severe("MongoDB is not enabled, disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (getConfig().getString("database.mongodb.url").equals("noSpecified")) {
            getLogger().severe("MongoDB URL is not specified, disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        System.out.println(getLangFile().getString("discord.report.embed.field.reported"));
        mongoManager = new MongoManager(this);
        jdaManager = new JDAManager(this);
        reportManager = new ReportManager(this);
        checkReportsFromMenu = new CheckReportsFromMenu(this);

        jdaManager.initDiscordBot();

        regCommands();
        regListeners();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initFiles() {
        langFile = new FileConfig(this, "lang.yml");
    }

    public void regCommands() {
        getCommand("kumareports").setExecutor(new MainCommand(this));
        getCommand("report").setExecutor(new ReportCommand(this));
        getCommand("kumareports").setTabCompleter(new MainTabComplete(this));
        getCommand("report").setTabCompleter(new ReportTabComplete(this));
    }

    public void regListeners() {
        getServer().getPluginManager().registerEvents(new MenuListener(this, checkReportsToMenu, checkReportsFromMenu), this);
    }

}
