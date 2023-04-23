package com.xodesito.kumareports;

import com.xodesito.kumareports.mongodb.MongoManager;
import com.xodesito.kumareports.report.Report;
import com.xodesito.kumareports.report.ReportManager;
import dev.dejvokep.boostedyaml.YamlDocument;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Getter
public final class KumaReports extends JavaPlugin {

    private YamlDocument configFile;
    private MongoManager mongoManager;
    private ReportManager reportManager;

    @Override
    public void onEnable() {
        try {
            initFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mongoManager = new MongoManager(this);

        /* Tests */
        reportManager = new ReportManager(this);
        reportManager.createReport(new Report(UUID.randomUUID(), "playerName!!", "Cheating"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initFiles() throws IOException {
        configFile = YamlDocument.create(new File("config.yml"), getResource("config.yml"));
    }

}
