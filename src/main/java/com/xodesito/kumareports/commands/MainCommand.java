package com.xodesito.kumareports.commands;

import com.xodesito.kumareports.KumaReports;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class MainCommand implements CommandExecutor {

    private final KumaReports plugin;

    public MainCommand(KumaReports plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("kumareports.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&2&lKumaReports &7&l| &cYou don't have permission to execute this command!"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes(
                    '&', "&2&lKumaReports &7&l| &fv" + plugin.getDescription().getVersion() + " by &7Xodesito"));
            sender.sendMessage((ChatColor.translateAlternateColorCodes(
                    '&', "&a/kumareports help &7| &fShows this help message")));
            sender.sendMessage((ChatColor.translateAlternateColorCodes(
                    '&', "&a/kumareports reload &7| &fReloads the plugin")));
            sender.sendMessage((ChatColor.translateAlternateColorCodes(
                    '&', "&a/kumareports checkr <player> &7| &fChecks the reports from a player")));
            sender.sendMessage((ChatColor.translateAlternateColorCodes('&',
                    "&a/kumareports checkp <player> &7| &fChecks the reports to this player")));

            return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                plugin.getJdaManager().shutdownBot();
                plugin.getJdaManager().initDiscordBot();
                EmbedBuilder embed = plugin.getJdaManager().createEmbed("Config reloaded!", "The config of the plugin was reloaded successfully!");
                plugin.getJdaManager().setColorToEmbed(embed, Color.GREEN);
                plugin.getJdaManager().sendEmbedToLogsChannel(embed);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&lKumaReports &7&l| &fReloaded!"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&8&o(( &7&l&oNOTE: &8&oThis will not affect the connection to the database &8&o))"));
                break;
            case "checkr":
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&2&lKumaReports &7&l| &fUsage: /kumareports checkr <player>"));
                    return true;
                }
                // TODO: Check reports from a player
                break;
            case "checkp":
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&2&lKumaReports &7&l| &fUsage: /kumareports checkp <player>"));
                    return true;
                }
                // TODO: Check reports to a player
            default:
                sender.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&', "&2&lKumaReports &7&l| &fv" + plugin.getDescription().getVersion() + " by &7Xodesito"));
                sender.sendMessage((ChatColor.translateAlternateColorCodes(
                        '&', "&a/kumareports help &7| &fShows this help message")));
                sender.sendMessage((ChatColor.translateAlternateColorCodes(
                        '&', "&a/kumareports reload &7| &fReloads the plugin")));
                sender.sendMessage((ChatColor.translateAlternateColorCodes(
                        '&', "&a/kumareports checkr <player> &7| &fChecks the reports from a player")));
                sender.sendMessage((ChatColor.translateAlternateColorCodes('&',
                        "&a/kumareports checkp <player> &7| &fChecks the reports to this player")));
                break;
        }
        return true;
    }
}
