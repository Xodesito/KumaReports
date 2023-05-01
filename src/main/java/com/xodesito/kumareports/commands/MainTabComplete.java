package com.xodesito.kumareports.commands;

import com.xodesito.kumareports.KumaReports;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainTabComplete implements TabCompleter {

    private final KumaReports plugin;

    public MainTabComplete(KumaReports plugin) {
        this.plugin = plugin;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("help");
            list.add("reload");
            list.add("checkfrom");
            list.add("checkto");
            return list;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("checkfrom")) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (player.getName().startsWith(args[1])) {
                    return List.of(player.getName());
                }
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("checkto")) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (player.getName().startsWith(args[1])) {
                    return List.of(player.getName());
                }
            }
        } else if (args.length >= 2 && args[0].equalsIgnoreCase("reload")) {
            return List.of("put this blank");

        }
        return null;
    }

}
