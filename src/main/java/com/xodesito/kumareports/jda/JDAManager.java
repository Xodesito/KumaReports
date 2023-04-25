package com.xodesito.kumareports.jda;

import com.xodesito.kumareports.KumaReports;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class JDAManager {

    private final KumaReports plugin;
    private JDA jda;

    public JDAManager(KumaReports plugin) {
        this.plugin = plugin;
    }

    public void initDiscordBot() {
        if (!plugin.getConfig().getBoolean("discordBot.enabled")) {
            plugin.getLogger().severe("Discord Bot is not enabled, disabling plugin...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
        if (plugin.getConfig().getString("discordBot.token").equals("noSpecified")) {
            plugin.getLogger().severe("Discord Bot Token is not specified, disabling plugin...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
        /* JDA starting */
        jda = JDABuilder.createDefault(plugin.getConfig().getString("discordBot.token")).setActivity(
                        Activity.of(Activity.ActivityType.valueOf(plugin.getConfig().getString("discordBot.activity.type")),
                                plugin.getConfig().getString("discordBot.activity.text"))).setStatus(OnlineStatus.valueOf(plugin.getConfig().getString("discordBot.status")))
                .build();

    }

    public void shutdownBot() {
        jda.shutdown();
    }

    public void sendEmbed() {
        String id = plugin.getConfig().getString("discordBot.guildId");
        String channelId = plugin.getConfig().getString("discordBot.reportsChannel");

        System.out.println(id + " // " + channelId);

        jda.getGuildById(plugin.getConfig().getString("discordBot.guildId"))
                .getTextChannelById(plugin.getConfig().getString("discordBot.channelId")).sendMessage("Test").queue();
    }
}
