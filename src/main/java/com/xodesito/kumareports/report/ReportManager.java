package com.xodesito.kumareports.report;

import com.xodesito.kumareports.KumaReports;
import com.xodesito.kumareports.jda.JDAManager;
import com.xodesito.kumareports.mongodb.MongoManager;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;

import java.awt.*;
import java.util.UUID;

public class ReportManager {

    private final KumaReports plugin;
    private final MongoManager mongoManager;
    private final JDAManager jdaManager;

    public ReportManager(KumaReports plugin) {
        this.plugin = plugin;
        this.mongoManager = plugin.getMongoManager();
        this.jdaManager = plugin.getJdaManager();
    }

    public void createReport(Report report) {
        mongoManager.insertReport(report.toDocument());
    }

    public void deleteReport(Report report) {
        mongoManager.deleteReport(report.toDocument());
    }

    public void updateReport(Report report) {
        mongoManager.updateReport(report.toDocument());
    }

    public Report getReport(Report report) {
        return (Report) mongoManager.getReport(report.toDocument());
    }

    public Report getReportByReportedUuid(String reportedUuid) {
        return (Report) mongoManager.getReport(new Report(UUID.fromString(reportedUuid), null, null).toDocument());
    }

    public Report getReportByReporterUuid(String reporterUuid) {
        return (Report) mongoManager.getReport(new Report(null, null, reporterUuid).toDocument());
    }

    public void sendReportToDiscord(Report report) {
        EmbedBuilder embed = jdaManager.createEmbed(plugin.getLangFile().getString("discord.report.embed.title"),
                plugin.getLangFile().getString("discord.report.embed.description"));

        // Footer, Thumbnail, Author, Image, Color
        if (plugin.getLangFile().getString("discord.report.embed.footer.text").equalsIgnoreCase("$date$")) {
            plugin.getJdaManager().setFooterToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.footerText")
                    .replace("$date$", String.valueOf(report.getDate())));
        } else {
            plugin.getJdaManager().setFooterToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.footerText"));
        }
        if (plugin.getConfig().getString("discordBot.report.embed.footerIcon").equalsIgnoreCase("noIcon")) {
            plugin.getJdaManager().setFooterToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.footerText"));
        } else {
            plugin.getJdaManager().setFooterToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.footerText"),
                    plugin.getLangFile().getString("discord.report.embed.footer.icon"));
        }
        if (plugin.getConfig().getString("discordBot.report.embed.thumbnail").equalsIgnoreCase("noThumbnail")) {
            plugin.getJdaManager().addThumbnailToEmbed(embed, plugin.getConfig().getString("discordBot.report.embed.thumbnail"));
        } else {
            plugin.getJdaManager().addThumbnailToEmbed(embed, plugin.getConfig().getString("discordBot.report.embed.thumbnail"));
        }
        if (plugin.getConfig().getString("discordBot.report.embed.authorUrl").equalsIgnoreCase("noUrl")
                && plugin.getConfig().getString("discordBot.report.embed.authorIconUrl").equalsIgnoreCase("noIcon")) {
            plugin.getJdaManager().setAuthorToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.authorText"));
        } else {
            plugin.getJdaManager().setAuthorToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.authorText"),
                    plugin.getConfig().getString("discordBot.report.embed.author.url"), plugin.getConfig().getString("discordBot.report.embed.author.iconUrl"));
        }
        if (!plugin.getConfig().getString("discordBot.report.embed.imageUrl").equalsIgnoreCase("noImageUrl")) {
            plugin.getJdaManager().addImageToEmbed(embed, plugin.getConfig().getString("discordBot.report.embed.image"));
        }
        if (!plugin.getConfig().getString("discordBot.report.embed.color").equalsIgnoreCase("noColor")) {
            plugin.getJdaManager().setColorToEmbed(embed, Color.getColor(plugin.getConfig().getString("discord.report.embed.color")));
        }

        // Fields

        if (plugin.getConfig().getBoolean("discordBot.report.embed.fields.reporter")) {
            plugin.getJdaManager().addFieldToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.field.reporter"), report.getReporterName(), true);
        }
        if (plugin.getConfig().getBoolean("discordBot.report.embed.fields.reported")) {
            if (Bukkit.getPlayer(report.getReportedUuid()).getName() == null) {
                plugin.getJdaManager().addFieldToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.field.reported"),
                        report.getReportedUuid().toString(), true);
            } else {
                plugin.getJdaManager().addFieldToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.field.reported"),
                        Bukkit.getPlayer(report.getReportedUuid()).getName(), true);
            }
        }
        if (plugin.getConfig().getBoolean("discordBot.report.embed.fields.reason")) {
            plugin.getJdaManager().addFieldToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.field.reason"), report.getReason(), false);
        }
        if (plugin.getConfig().getBoolean("discordBot.report.embed.fields.reportId")) {
            plugin.getJdaManager().addFieldToEmbed(embed, plugin.getLangFile().getString("discord.report.embed.field.reportId"), report.getId(), false);
        }
    }


}
