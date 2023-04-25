package com.xodesito.kumareports.jda;

import com.xodesito.kumareports.KumaReports;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;

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
        try {
            jda = JDABuilder.createDefault(plugin.getConfig().getString("discordBot.token")).setActivity(
                            Activity.of(Activity.ActivityType.valueOf(plugin.getConfig().getString("discordBot.activity.type")),
                                    plugin.getConfig().getString("discordBot.activity.text"))).setStatus(OnlineStatus.valueOf(plugin.getConfig().getString("discordBot.status")))
                    .build().awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void shutdownBot() {
        jda.shutdown();
    }

    public void sendEmbed(EmbedBuilder embed) {
        String id = plugin.getConfig().getString("discordBot.guildId");
        String channelId = plugin.getConfig().getString("discordBot.reportsChannel");

        System.out.println(id + " // " + channelId);

        Guild guild = jda.getGuildById(id);
        System.out.println(guild);

        if (guild == null) {
            System.out.println("Guild is null: ID = " + id);
            return;
        }

        TextChannel channel = guild.getTextChannelById(channelId);
        System.out.println(channel);

        if (channel == null) {
            System.out.println("Channel is null: ID = " + channelId);
            return;
        }

        channel.sendMessage(MessageCreateData.fromEmbeds(embed.build())).queue();

    }

    public EmbedBuilder createEmbed(String title, String description) {
        return new EmbedBuilder().setTitle(title).setDescription(description);
    }

    public void setTitleToEmbed(EmbedBuilder embed, String title) {
        embed.setTitle(title);
    }

    public void setDescriptionToEmbed(EmbedBuilder embed, String description) {
        embed.setDescription(description);
    }

    public void setFooterToEmbed(EmbedBuilder embed, String text) {
        embed.setFooter(text);
    }

    public void setAuthorToEmbed(EmbedBuilder embed, String name) {
        embed.setAuthor(name);
    }

    public void setFooterToEmbed(EmbedBuilder embed, String text, String url) {
        embed.setFooter(text);
    }

    public void setAuthorToEmbed(EmbedBuilder embed, String name, String url, String iconUrl) {
        embed.setAuthor(name, url, iconUrl);
    }

    public void setColorToEmbed(EmbedBuilder embed, Color color) {
        embed.setColor(color);
    }

    public void addFieldToEmbed(EmbedBuilder embed, String title, String description, boolean inline) {
        embed.addField(title, description, inline);
    }

    public void addImageToEmbed(EmbedBuilder embed, String url) {
        embed.setImage(url);
    }

    public void addThumbnailToEmbed(EmbedBuilder embed, String url) {
        embed.setThumbnail(url);
    }
}
