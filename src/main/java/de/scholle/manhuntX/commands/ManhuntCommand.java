package de.scholle.manhuntX.commands;

import de.scholle.manhuntX.ManhuntX;
import de.scholle.manhuntX.managers.GUIManager;
import de.scholle.manhuntX.managers.TeamManager;
import de.scholle.manhuntX.util.MessageFormatter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManhuntCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "set":
                handleSetCommand(sender, args);
                break;
            case "start":
                handleStartCommand(sender);
                break;
            case "config":
                handleConfigCommand(sender);
                break;
            case "runner":
                handleRunnerCommand(sender);
                break;
            case "hunter":
                handleHunterCommand(sender);
                break;
            default:
                sendHelp(sender);
                break;
        }

        return true;
    }

    private void handleSetCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("manhuntx.op")) {
            sender.sendMessage(MessageFormatter.formatMessage("No permission!", "red"));
            return;
        }

        if (args.length < 3) {
            sender.sendMessage(MessageFormatter.formatMessage("Usage: /manhuntx set <hunter|runner> <player>", "red"));
            return;
        }

        Player target = Bukkit.getPlayer(args[2]);
        if (target == null) {
            sender.sendMessage(MessageFormatter.formatMessage("Player not found!", "red"));
            return;
        }

        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();

        switch (args[1].toLowerCase()) {
            case "hunter":
                teamManager.setHunter(target);
                sender.sendMessage(MessageFormatter.formatMessage(target.getName() + " is now a hunter!", "green"));
                break;
            case "runner":
                teamManager.setRunner(target);
                sender.sendMessage(MessageFormatter.formatMessage(target.getName() + " is now a runner!", "green"));
                break;
            default:
                sender.sendMessage(MessageFormatter.formatMessage("Usage: /manhuntx set <hunter|runner> <player>", "red"));
                break;
        }
    }

    private void handleStartCommand(CommandSender sender) {
        if (!sender.hasPermission("manhuntx.op")) {
            sender.sendMessage(MessageFormatter.formatMessage("No permission!", "red"));
            return;
        }

        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();

        if (teamManager.getHunters().isEmpty()) {
            sender.sendMessage(MessageFormatter.formatMessage("You need at least one hunter to start the game!", "red"));
            return;
        }

        if (teamManager.getRunners().isEmpty()) {
            sender.sendMessage(MessageFormatter.formatMessage("You need at least one runner to start the game!", "red"));
            return;
        }

        ManhuntX.getInstance().getGameManager().startGame();
        sender.sendMessage(MessageFormatter.formatMessage("Game started!", "green"));
    }

    private void handleConfigCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageFormatter.formatMessage("Only players can use this command!", "red"));
            return;
        }

        if (!sender.hasPermission("manhuntx.op")) {
            sender.sendMessage(MessageFormatter.formatMessage("No permission!", "red"));
            return;
        }

        Player player = (Player) sender;
        GUIManager guiManager = new GUIManager();
        player.openInventory(guiManager.createConfigGUI());
    }

    private void handleRunnerCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageFormatter.formatMessage("Only players can use this command!", "red"));
            return;
        }

        if (!sender.hasPermission("manhuntx.runner")) {
            sender.sendMessage(MessageFormatter.formatMessage("No permission!", "red"));
            return;
        }

        Player player = (Player) sender;
        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();
        teamManager.setRunner(player);
        player.sendMessage(MessageFormatter.formatMessage("You are now a runner!", "green"));
    }

    private void handleHunterCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageFormatter.formatMessage("Only players can use this command!", "red"));
            return;
        }

        if (!sender.hasPermission("manhuntx.hunter")) {
            sender.sendMessage(MessageFormatter.formatMessage("No permission!", "red"));
            return;
        }

        Player player = (Player) sender;
        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();
        teamManager.setHunter(player);
        player.sendMessage(MessageFormatter.formatMessage("You are now a hunter!", "green"));
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(MessageFormatter.formatMessage("ManhuntX Commands:", "gold"));
        if (sender.hasPermission("manhuntx.op")) {
            sender.sendMessage(MessageFormatter.formatMessage("/manhuntx set <hunter|runner> <player>", "white"));
            sender.sendMessage(MessageFormatter.formatMessage("/manhuntx start", "white"));
            sender.sendMessage(MessageFormatter.formatMessage("/manhuntx config", "white"));
        }
        if (sender.hasPermission("manhuntx.runner")) {
            sender.sendMessage(MessageFormatter.formatMessage("/manhuntx runner", "white"));
        }
        if (sender.hasPermission("manhuntx.hunter")) {
            sender.sendMessage(MessageFormatter.formatMessage("/manhuntx hunter", "white"));
        }
    }
}
