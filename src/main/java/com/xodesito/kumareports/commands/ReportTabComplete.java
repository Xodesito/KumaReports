package com.xodesito.kumareports.commands;

import com.xodesito.kumareports.KumaReports;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class ReportTabComplete implements TabCompleter {

    private final KumaReports plugin;

    public ReportTabComplete(KumaReports plugin) {
        this.plugin = plugin;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {

            if (plugin.getLangFile().getString("report.tabComplete.1").equalsIgnoreCase("$playerList$")) {
                StringBuilder players = new StringBuilder();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    players.append(player.getName());
                }
                return List.of(players.toString());
            } else {
                return List.of(plugin.getLangFile().getString("report.tabComplete.1"));
            }

        }
        if (args.length >= 2) {
            return List.of(plugin.getLangFile().getString("report.tabComplete.2"));
        }
        return null;
    }

}
