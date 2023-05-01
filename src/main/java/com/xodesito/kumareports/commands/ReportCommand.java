package com.xodesito.kumareports.commands;

import com.xodesito.kumareports.KumaReports;
import com.xodesito.kumareports.report.Report;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.xodesito.api.text.ChatUtil.translate;

public class ReportCommand implements CommandExecutor {

    private final KumaReports plugin;

    public ReportCommand(KumaReports plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(translate(plugin.getLangFile().getString("command.onlyPlayers")));
            return true;
        }
        Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage(translate(plugin.getLangFile().getString("report.invalidArguments")));
            return true;
        }
        Player reported = plugin.getServer().getPlayer(args[0]);
        if (reported == null) {
            player.sendMessage(translate(plugin.getLangFile().getString("report.invalidPlayer")));
            return true;
        }
        StringBuilder reason = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }
        Report report = new Report(player.getUniqueId(), reported.getName(), reason.toString());
        plugin.getReportManager().createReport(report);
        player.sendMessage(translate(plugin.getLangFile().getString("report.reportedPlayer"))
                .replace("$player$", reported.getName())
                .replace("$reason$", reason.toString()));
        plugin.getReportManager().sendReportToDiscord(report);
        plugin.getLogger().info(plugin.getLangFile().getString("report.consoleReportReceived")
                .replace("$player$", reported.getName())
                .replace("$reason$", reason.toString())
                .replace("$reporter$", player.getName()));
        return false;
    }

}
