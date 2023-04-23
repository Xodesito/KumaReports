package com.xodesito.kumareports;

import com.xodesito.kumareports.commands.MainCommand;
import com.xodesito.kumareports.mongodb.MongoManager;
import com.xodesito.kumareports.report.ReportManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class KumaReports extends JavaPlugin {

    private MongoManager mongoManager;
    private ReportManager reportManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
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
        mongoManager = new MongoManager(this);

        regCommands();


//        /* Tests */
//        reportManager = new ReportManager(this);
//        reportManager.createReport(new Report(UUID.randomUUID(), "playerName!!", "Cheating"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void regCommands() {
        getCommand("kumareports").setExecutor(new MainCommand(this));
    }


}
