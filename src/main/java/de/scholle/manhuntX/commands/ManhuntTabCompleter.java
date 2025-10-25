package de.scholle.manhuntX.commands;

import de.scholle.manhuntX.ManhuntX;
import de.scholle.manhuntX.managers.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ManhuntTabCompleter implements org.bukkit.command.TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("manhuntx")) {
            if (args.length == 1) {
                if (sender.hasPermission("manhuntx.op")) {
                    completions.add("set");
                    completions.add("start");
                    completions.add("config");
                }
                if (sender.hasPermission("manhuntx.runner")) {
                    completions.add("runner");
                }
                if (sender.hasPermission("manhuntx.hunter")) {
                    completions.add("hunter");
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
                if (sender.hasPermission("manhuntx.op")) {
                    completions.add("hunter");
                    completions.add("runner");
                }
            } else if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            }
        } else if (command.getName().equalsIgnoreCase("compass")) {
            if (sender instanceof Player) {
                TeamManager teamManager = ManhuntX.getInstance().getTeamManager();
                for (UUID runnerId : teamManager.getRunners()) {
                    Player runner = Bukkit.getPlayer(runnerId);
                    if (runner != null) {
                        completions.add(runner.getName());
                    }
                }
            }
        }

        return completions;
    }
}