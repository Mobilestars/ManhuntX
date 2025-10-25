package de.scholle.manhuntX.managers;

import de.scholle.manhuntX.ManhuntX;
import de.scholle.manhuntX.util.ChallengeType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class GUIManager {

    public Inventory createConfigGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, "ManhuntX Config");

        ConfigManager configManager = ManhuntX.getInstance().getConfigManager();
        GameManager gameManager = ManhuntX.getInstance().getGameManager();

        // Existing items...
        ItemStack challengeItem = new ItemStack(Material.ENDER_EYE);
        ItemMeta challengeMeta = challengeItem.getItemMeta();
        challengeMeta.setDisplayName(ChatColor.GOLD + "Challenge Type");
        challengeMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Current: " + gameManager.getChallengeType().getDisplayName(),
                ChatColor.GRAY + "Click to toggle between modes"
        ));
        challengeItem.setItemMeta(challengeMeta);
        inv.setItem(10, challengeItem);

        ItemStack challengeStatus = new ItemStack(
                gameManager.getChallengeType() == ChallengeType.KILL_ENDER_DRAGON ? Material.LIME_DYE : Material.GRAY_DYE
        );
        ItemMeta statusMeta = challengeStatus.getItemMeta();
        statusMeta.setDisplayName(
                gameManager.getChallengeType() == ChallengeType.KILL_ENDER_DRAGON ? ChatColor.GREEN + "Kill Ender Dragon" : ChatColor.RED + "Survive Time"
        );
        challengeStatus.setItemMeta(statusMeta);
        inv.setItem(19, challengeStatus);

        ItemStack prepTimeItem = new ItemStack(Material.CLOCK);
        ItemMeta prepMeta = prepTimeItem.getItemMeta();
        prepMeta.setDisplayName(ChatColor.GOLD + "Preparation Time");
        prepMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Current: " + configManager.getPreparationTime() + "s",
                ChatColor.GRAY + "Click to change preparation time"
        ));
        prepTimeItem.setItemMeta(prepMeta);
        inv.setItem(12, prepTimeItem);

        ItemStack prepTimeStatus = new ItemStack(Material.YELLOW_DYE);
        ItemMeta prepStatusMeta = prepTimeStatus.getItemMeta();
        prepStatusMeta.setDisplayName(ChatColor.YELLOW + "Configure Time");
        prepTimeStatus.setItemMeta(prepStatusMeta);
        inv.setItem(21, prepTimeStatus);

        ItemStack randomRolesItem = new ItemStack(Material.SPLASH_POTION);
        ItemMeta randomRolesMeta = randomRolesItem.getItemMeta();
        boolean randomRoles = configManager.isRandomRolesEnabled();
        randomRolesMeta.setDisplayName(ChatColor.GOLD + "Random Roles");
        randomRolesMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Current: " + (randomRoles ? "Enabled" : "Disabled"),
                ChatColor.GRAY + "Click to toggle random roles"
        ));
        randomRolesItem.setItemMeta(randomRolesMeta);
        inv.setItem(13, randomRolesItem);

        ItemStack randomRunnersItem = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta runnersMeta = randomRunnersItem.getItemMeta();
        int runners = configManager.getRandomRunners();
        runnersMeta.setDisplayName(ChatColor.GOLD + "Random Runners");
        runnersMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Current: " + runners,
                ChatColor.GRAY + "Click to change number of runners"
        ));
        randomRunnersItem.setItemMeta(runnersMeta);
        inv.setItem(22, randomRunnersItem);

        // New Spectral Arrow Item for Glowing Settings
        ItemStack glowItem = new ItemStack(Material.SPECTRAL_ARROW);
        ItemMeta glowMeta = glowItem.getItemMeta();
        boolean glowEnabled = configManager.isGlowEnabled();
        glowMeta.setDisplayName(ChatColor.GOLD + "Glowing Settings");
        glowMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Current: " + (glowEnabled ? "Enabled" : "Disabled"),
                ChatColor.GRAY + "Click to configure glowing settings"
        ));
        glowItem.setItemMeta(glowMeta);
        inv.setItem(14, glowItem);

        // Dye to enable/disable glowing
        ItemStack glowStatus = new ItemStack(glowEnabled ? Material.LIME_DYE : Material.GRAY_DYE);
        ItemMeta glowStatusMeta = glowStatus.getItemMeta();
        glowStatusMeta.setDisplayName(glowEnabled ?
                ChatColor.GREEN + "Glowing Enabled" :
                ChatColor.RED + "Glowing Disabled");
        glowStatus.setItemMeta(glowStatusMeta);
        inv.setItem(23, glowStatus);

        ItemStack saveItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta saveMeta = saveItem.getItemMeta();
        saveMeta.setDisplayName(ChatColor.GREEN + "Save Configuration");
        saveItem.setItemMeta(saveMeta);
        inv.setItem(26, saveItem);

        return inv;
    }

    public Inventory createTimeSelectionGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, "Select Preparation Time");

        ConfigManager configManager = ManhuntX.getInstance().getConfigManager();
        int currentTime = configManager.getPreparationTime();

        int[] times = {0, 5, 10, 15, 30, 60};
        int[] slots = {11, 12, 13, 14, 15, 16};

        for (int i = 0; i < times.length; i++) {
            ItemStack timeItem = new ItemStack(Material.GRAY_DYE);
            ItemMeta meta = timeItem.getItemMeta();

            if (times[i] == currentTime) {
                timeItem.setType(Material.LIME_DYE);
                meta.setDisplayName(ChatColor.GREEN + String.valueOf(times[i]) + " seconds");
            } else {
                meta.setDisplayName(ChatColor.WHITE + String.valueOf(times[i]) + " seconds");
            }

            timeItem.setItemMeta(meta);
            inv.setItem(slots[i], timeItem);
        }

        return inv;
    }

    // New GUI for Glowing Settings
    public Inventory createGlowSettingsGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, "Glowing Settings");

        ConfigManager configManager = ManhuntX.getInstance().getConfigManager();

        // Runner Glowing Toggle
        ItemStack runnerGlowItem = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta runnerMeta = runnerGlowItem.getItemMeta();
        boolean runnerGlow = configManager.isRunnerGlowEnabled();
        runnerMeta.setDisplayName(ChatColor.GOLD + "Runner Glowing");
        runnerMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Current: " + (runnerGlow ? "Enabled" : "Disabled"),
                ChatColor.GRAY + "Click to toggle runner glowing"
        ));
        runnerGlowItem.setItemMeta(runnerMeta);
        inv.setItem(10, runnerGlowItem);

        ItemStack runnerStatus = new ItemStack(runnerGlow ? Material.LIME_DYE : Material.GRAY_DYE);
        ItemMeta runnerStatusMeta = runnerStatus.getItemMeta();
        runnerStatusMeta.setDisplayName(runnerGlow ?
                ChatColor.GREEN + "Runner Glow Enabled" :
                ChatColor.RED + "Runner Glow Disabled");
        runnerStatus.setItemMeta(runnerStatusMeta);
        inv.setItem(19, runnerStatus);

        // Hunter Glowing Toggle
        ItemStack hunterGlowItem = new ItemStack(Material.BOW);
        ItemMeta hunterMeta = hunterGlowItem.getItemMeta();
        boolean hunterGlow = configManager.isHunterGlowEnabled();
        hunterMeta.setDisplayName(ChatColor.GOLD + "Hunter Glowing");
        hunterMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Current: " + (hunterGlow ? "Enabled" : "Disabled"),
                ChatColor.GRAY + "Click to toggle hunter glowing"
        ));
        hunterGlowItem.setItemMeta(hunterMeta);
        inv.setItem(12, hunterGlowItem);

        ItemStack hunterStatus = new ItemStack(hunterGlow ? Material.LIME_DYE : Material.GRAY_DYE);
        ItemMeta hunterStatusMeta = hunterStatus.getItemMeta();
        hunterStatusMeta.setDisplayName(hunterGlow ?
                ChatColor.GREEN + "Hunter Glow Enabled" :
                ChatColor.RED + "Hunter Glow Disabled");
        hunterStatus.setItemMeta(hunterStatusMeta);
        inv.setItem(21, hunterStatus);

        // Glowing Time Selection
        ItemStack glowTimeItem = new ItemStack(Material.CLOCK);
        ItemMeta glowTimeMeta = glowTimeItem.getItemMeta();
        int currentGlowTime = configManager.getGlowTime();
        glowTimeMeta.setDisplayName(ChatColor.GOLD + "Glowing Time");
        glowTimeMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Current: " + formatSeconds(currentGlowTime),
                ChatColor.GRAY + "Click to change glowing duration"
        ));
        glowTimeItem.setItemMeta(glowTimeMeta);
        inv.setItem(14, glowTimeItem);

        // Glowing Pause Selection
        ItemStack glowPauseItem = new ItemStack(Material.REPEATER);
        ItemMeta glowPauseMeta = glowPauseItem.getItemMeta();
        int currentGlowPause = configManager.getGlowPause();
        glowPauseMeta.setDisplayName(ChatColor.GOLD + "Glowing Pause");
        glowPauseMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Current: " + formatSeconds(currentGlowPause),
                ChatColor.GRAY + "Click to change pause duration"
        ));
        glowPauseItem.setItemMeta(glowPauseMeta);
        inv.setItem(16, glowPauseItem);

        // Back Button
        ItemStack backItem = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backItem.getItemMeta();
        backMeta.setDisplayName(ChatColor.YELLOW + "Back to Main Menu");
        backItem.setItemMeta(backMeta);
        inv.setItem(22, backItem);

        return inv;
    }

    // GUI for Glowing Time Selection
    public Inventory createGlowTimeSelectionGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, "Select Glowing Time");

        ConfigManager configManager = ManhuntX.getInstance().getConfigManager();
        int currentTime = configManager.getGlowTime();
        List<Integer> glowTimes = configManager.getGlowTimes();

        int[] slots = {11, 12, 13, 14, 15, 16};

        for (int i = 0; i < glowTimes.size() && i < slots.length; i++) {
            ItemStack timeItem = new ItemStack(Material.GRAY_DYE);
            ItemMeta meta = timeItem.getItemMeta();

            if (glowTimes.get(i) == currentTime) {
                timeItem.setType(Material.LIME_DYE);
                meta.setDisplayName(ChatColor.GREEN + formatSeconds(glowTimes.get(i)));
            } else {
                meta.setDisplayName(ChatColor.WHITE + formatSeconds(glowTimes.get(i)));
            }

            timeItem.setItemMeta(meta);
            inv.setItem(slots[i], timeItem);
        }

        // Back Button
        ItemStack backItem = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backItem.getItemMeta();
        backMeta.setDisplayName(ChatColor.YELLOW + "Back to Glowing Settings");
        backItem.setItemMeta(backMeta);
        inv.setItem(22, backItem);

        return inv;
    }

    // GUI for Glowing Pause Selection
    public Inventory createGlowPauseSelectionGUI() {
        Inventory inv = Bukkit.createInventory(null, 27, "Select Glowing Pause");

        ConfigManager configManager = ManhuntX.getInstance().getConfigManager();
        int currentPause = configManager.getGlowPause();
        List<Integer> glowPauses = configManager.getGlowPauses();

        int[] slots = {11, 12, 13, 14, 15};

        for (int i = 0; i < glowPauses.size() && i < slots.length; i++) {
            ItemStack pauseItem = new ItemStack(Material.GRAY_DYE);
            ItemMeta meta = pauseItem.getItemMeta();

            if (glowPauses.get(i) == currentPause) {
                pauseItem.setType(Material.LIME_DYE);
                meta.setDisplayName(ChatColor.GREEN + formatSeconds(glowPauses.get(i)));
            } else {
                meta.setDisplayName(ChatColor.WHITE + formatSeconds(glowPauses.get(i)));
            }

            pauseItem.setItemMeta(meta);
            inv.setItem(slots[i], pauseItem);
        }

        // Back Button
        ItemStack backItem = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backItem.getItemMeta();
        backMeta.setDisplayName(ChatColor.YELLOW + "Back to Glowing Settings");
        backItem.setItemMeta(backMeta);
        inv.setItem(22, backItem);

        return inv;
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