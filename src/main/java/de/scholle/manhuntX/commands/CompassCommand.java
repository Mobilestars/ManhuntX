package de.scholle.manhuntX.commands;

import de.scholle.manhuntX.ManhuntX;
import de.scholle.manhuntX.managers.TeamManager;
import de.scholle.manhuntX.util.MessageFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CompassCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageFormatter.formatMessage("Only players can use this command!", "red"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("manhuntx.hunter")) {
            player.sendMessage(MessageFormatter.formatMessage("No permission!", "red"));
            return true;
        }

        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();

        if (!teamManager.isHunter(player)) {
            player.sendMessage(MessageFormatter.formatMessage("You are not a hunter!", "red"));
            return true;
        }

        Player targetRunner = null;

        if (args.length > 0) {
            targetRunner = teamManager.getRunnerByName(args[0]);
            if (targetRunner == null) {
                player.sendMessage(MessageFormatter.formatMessage("Runner not found!", "red"));
                return true;
            }
        } else {
            targetRunner = teamManager.getFirstRunner();
            if (targetRunner == null) {
                player.sendMessage(MessageFormatter.formatMessage("No runners available!", "red"));
                return true;
            }
        }

        giveTrackingCompass(player, targetRunner);
        player.sendMessage(MessageFormatter.formatMessage("Compass now tracking: " + targetRunner.getName(), "green"));

        return true;
    }

    private void giveTrackingCompass(Player hunter, Player runner) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName(MessageFormatter.formatMessage("Tracker: " + runner.getName(), "red"));
        compass.setItemMeta(meta);

        hunter.getInventory().addItem(compass);

        Bukkit.getScheduler().runTaskTimer(ManhuntX.getInstance(), () -> {
            if (hunter.getInventory().contains(Material.COMPASS)) {
                hunter.setCompassTarget(runner.getLocation());
            }
        }, 0L, 20L);
    }
}