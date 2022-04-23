package com.realxode.kumareports.cmds;

import com.realxode.kumareports.KumaReports;
import com.realxode.kumareports.guis.ReportGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.realxode.kumareports.utils.TextHandler.color;

public class ReportCommand implements CommandExecutor {

    private final KumaReports plugin;

    public ReportCommand(KumaReports plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Only for players!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSpecify a player!"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(color("&cThe player does not exist or is not online."));
            return true;
        }
        if (target == player) {
            player.sendMessage(color("&cYou can't report yourself."));
            return true;
        }
        ReportGui reportGui = new ReportGui(plugin, player, target);
        player.openInventory(reportGui.reportGui());
        return false;
    }
}
