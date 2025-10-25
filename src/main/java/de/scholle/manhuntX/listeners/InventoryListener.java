package de.scholle.manhuntX.listeners;

import de.scholle.manhuntX.ManhuntX;
import de.scholle.manhuntX.managers.ConfigManager;
import de.scholle.manhuntX.managers.GUIManager;
import de.scholle.manhuntX.managers.GameManager;
import de.scholle.manhuntX.util.ChallengeType;
import de.scholle.manhuntX.util.MessageFormatter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        String title = event.getView().getTitle();

        if (title.equals("ManhuntX Config")) {
            event.setCancelled(true);
            handleConfigGUI(event, player);
        } else if (title.equals("Select Preparation Time")) {
            event.setCancelled(true);
            handleTimeSelectionGUI(event, player);
        } else if (title.equals("Glowing Settings")) {
            event.setCancelled(true);
            handleGlowSettingsGUI(event, player);
        } else if (title.equals("Select Glowing Time")) {
            event.setCancelled(true);
            handleGlowTimeSelectionGUI(event, player);
        } else if (title.equals("Select Glowing Pause")) {
            event.setCancelled(true);
            handleGlowPauseSelectionGUI(event, player);
        }
    }

    private void handleConfigGUI(InventoryClickEvent event, Player player) {
        int slot = event.getSlot();
        GameManager gameManager = ManhuntX.getInstance().getGameManager();
        ConfigManager configManager = ManhuntX.getInstance().getConfigManager();
        GUIManager guiManager = new GUIManager();

        switch (slot) {
            case 10:
            case 19:
                ChallengeType currentType = gameManager.getChallengeType();
                ChallengeType newType = currentType == ChallengeType.KILL_ENDER_DRAGON ?
                        ChallengeType.SURVIVE_TIME : ChallengeType.KILL_ENDER_DRAGON;
                gameManager.setChallengeType(newType);
                configManager.setChallengeType(newType);
                player.openInventory(guiManager.createConfigGUI());
                break;
            case 12:
            case 21:
                player.openInventory(guiManager.createTimeSelectionGUI());
                break;
            case 13:
                // Toggle Random Roles
                boolean currentRandomRoles = configManager.isRandomRolesEnabled();
                configManager.setRandomRoles(!currentRandomRoles);
                player.openInventory(guiManager.createConfigGUI());
                break;
            case 14:
                // Open Glowing Settings GUI
                player.openInventory(guiManager.createGlowSettingsGUI());
                break;
            case 23:
                // Toggle Glowing Enabled
                boolean currentGlow = configManager.isGlowEnabled();
                configManager.setGlowEnabled(!currentGlow);
                player.openInventory(guiManager.createConfigGUI());
                break;
            case 22:
                // Change Random Runners amount
                int currentRunners = configManager.getRandomRunners();
                int newRunners = currentRunners >= 4 ? 1 : currentRunners + 1; // Cycle through 1-4
                configManager.setRandomRunners(newRunners);
                player.openInventory(guiManager.createConfigGUI());
                break;
            case 26:
                player.closeInventory();
                player.sendMessage(MessageFormatter.formatMessage("Configuration saved!", "green"));
                break;
        }
    }

    private void handleTimeSelectionGUI(InventoryClickEvent event, Player player) {
        int slot = event.getSlot();
        int[] timeSlots = {11, 12, 13, 14, 15, 16};
        int[] times = {0, 5, 10, 15, 30, 60};

        for (int i = 0; i < timeSlots.length; i++) {
            if (slot == timeSlots[i]) {
                ConfigManager configManager = ManhuntX.getInstance().getConfigManager();
                GameManager gameManager = ManhuntX.getInstance().getGameManager();

                configManager.setPreparationTime(times[i]);
                gameManager.setPreparationTime(times[i]);

                player.closeInventory();
                player.sendMessage(MessageFormatter.formatMessage("Preparation time set to " + times[i] + " seconds", "green"));
                break;
            }
        }
    }

    private void handleGlowSettingsGUI(InventoryClickEvent event, Player player) {
        int slot = event.getSlot();
        ConfigManager configManager = ManhuntX.getInstance().getConfigManager();
        GUIManager guiManager = new GUIManager();

        switch (slot) {
            case 10:
            case 19:
                // Toggle Runner Glowing
                boolean currentRunnerGlow = configManager.isRunnerGlowEnabled();
                configManager.setRunnerGlow(!currentRunnerGlow);
                player.openInventory(guiManager.createGlowSettingsGUI());
                break;
            case 12:
            case 21:
                // Toggle Hunter Glowing
                boolean currentHunterGlow = configManager.isHunterGlowEnabled();
                configManager.setHunterGlow(!currentHunterGlow);
                player.openInventory(guiManager.createGlowSettingsGUI());
                break;
            case 14:
                // Open Glowing Time Selection
                player.openInventory(guiManager.createGlowTimeSelectionGUI());
                break;
            case 16:
                // Open Glowing Pause Selection
                player.openInventory(guiManager.createGlowPauseSelectionGUI());
                break;
            case 22:
                // Back to Main Menu
                player.openInventory(guiManager.createConfigGUI());
                break;
        }
    }

    private void handleGlowTimeSelectionGUI(InventoryClickEvent event, Player player) {
        int slot = event.getSlot();
        int[] timeSlots = {11, 12, 13, 14, 15, 16};
        int[] times = {0, 5, 10, 15, 30, 60}; // seconds

        for (int i = 0; i < timeSlots.length; i++) {
            if (slot == timeSlots[i]) {
                ConfigManager configManager = ManhuntX.getInstance().getConfigManager();
                configManager.setGlowTime(times[i]);

                player.closeInventory();
                player.sendMessage(MessageFormatter.formatMessage("Glowing time set to " + formatSeconds(times[i]), "green"));
                break;
            }
        }

        // Back button
        if (slot == 22) {
            GUIManager guiManager = new GUIManager();
            player.openInventory(guiManager.createGlowSettingsGUI());
        }
    }

    private void handleGlowPauseSelectionGUI(InventoryClickEvent event, Player player) {
        int slot = event.getSlot();
        int[] pauseSlots = {11, 12, 13, 14, 15};
        int[] pauses = {30, 60, 180, 300, 600}; // seconds: 30s, 1min, 3min, 5min, 10min

        for (int i = 0; i < pauseSlots.length; i++) {
            if (slot == pauseSlots[i]) {
                ConfigManager configManager = ManhuntX.getInstance().getConfigManager();
                configManager.setGlowPause(pauses[i]);

                player.closeInventory();
                player.sendMessage(MessageFormatter.formatMessage("Glowing pause set to " + formatSeconds(pauses[i]), "green"));
                break;
            }
        }

        // Back button
        if (slot == 22) {
            GUIManager guiManager = new GUIManager();
            player.openInventory(guiManager.createGlowSettingsGUI());
        }
    }

    private String formatSeconds(int seconds) {
        if (seconds < 60) {
            return seconds + "s";
        } else {
            int minutes = seconds / 60;
            return minutes + "min";
        }
    }
}