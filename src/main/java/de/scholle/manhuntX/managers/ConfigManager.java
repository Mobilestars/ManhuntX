package de.scholle.manhuntX.managers;

import de.scholle.manhuntX.ManhuntX;
import de.scholle.manhuntX.util.ChallengeType;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public class ConfigManager {

    private FileConfiguration config;
    private String prefix;
    private List<Integer> preparationTimes;
    private List<Integer> glowTimes;
    private List<Integer> glowPauses;

    public ConfigManager() {
        this.preparationTimes = Arrays.asList(0, 5, 10, 15, 30, 60);
        this.glowTimes = Arrays.asList(0, 5, 10, 15, 30, 60);
        this.glowPauses = Arrays.asList(30, 60, 180, 300, 600);
    }

    public void loadConfig() {
        ManhuntX.getInstance().saveDefaultConfig();
        config = ManhuntX.getInstance().getConfig();
        prefix = config.getString("prefix", "ManhuntX");

        // Lade die Listen aus der config, falls vorhanden, sonst verwende Defaults
        preparationTimes = config.getIntegerList("preparation-times");
        if (preparationTimes.isEmpty()) {
            preparationTimes = Arrays.asList(0, 5, 10, 15, 30, 60);
        }

        glowTimes = config.getIntegerList("glow.times");
        if (glowTimes.isEmpty()) {
            glowTimes = Arrays.asList(0, 5, 10, 15, 30, 60);
        }

        glowPauses = config.getIntegerList("glow.pauses");
        if (glowPauses.isEmpty()) {
            glowPauses = Arrays.asList(30, 60, 180, 300, 600);
        }
    }

    public void saveConfig() {
        ManhuntX.getInstance().saveConfig();
    }

    public String getPrefix() {
        return prefix;
    }

    public List<Integer> getPreparationTimes() {
        return preparationTimes;
    }

    public void setPreparationTime(int time) {
        config.set("preparation-time", time);
        saveConfig();
    }

    public int getPreparationTime() {
        return config.getInt("preparation-time", 0);
    }

    public void setChallengeType(ChallengeType type) {
        config.set("challenge-type", type.toString());
        saveConfig();
    }

    public ChallengeType getChallengeType() {
        String type = config.getString("challenge-type", "KILL_ENDER_DRAGON");
        return ChallengeType.valueOf(type);
    }

    public void setSurvivalTime(int minutes) {
        config.set("survival-time", minutes * 60);
        saveConfig();
    }

    // Diese Methoden sind bereits in deinem ConfigManager vorhanden:
    public void setGlowEnabled(boolean enabled) {
        config.set("glow.enabled", enabled);
        saveConfig();
    }

    public boolean isGlowEnabled() {
        return config.getBoolean("glow.enabled", false);
    }

    public void setRunnerGlow(boolean enabled) {
        config.set("glow.runner", enabled);
        saveConfig();
    }

    public boolean isRunnerGlowEnabled() {
        return config.getBoolean("glow.runner", false);
    }

    public void setHunterGlow(boolean enabled) {
        config.set("glow.hunter", enabled);
        saveConfig();
    }

    public boolean isHunterGlowEnabled() {
        return config.getBoolean("glow.hunter", false);
    }

    public void setGlowTime(int seconds) {
        config.set("glow.time", seconds);
        saveConfig();
    }

    public int getGlowTime() {
        return config.getInt("glow.time", 30); // Default: 30s glowing
    }

    public void setGlowPause(int seconds) {
        config.set("glow.pause", seconds);
        saveConfig();
    }

    public int getGlowPause() {
        return config.getInt("glow.pause", 300); // Default: 5min pause
    }

    public int getSurvivalTime() {
        return config.getInt("survival-time", 3600);
    }

    public void setRandomRoles(boolean enabled) {
        config.set("random-roles", enabled);
        saveConfig();
    }

    public boolean isRandomRolesEnabled() {
        return config.getBoolean("random-roles", false);
    }

    public void setRandomRunners(int amount) {
        config.set("random-runners", amount);
        saveConfig();
    }

    public int getRandomRunners() {
        return config.getInt("random-runners", 1);
    }

    public List<Integer> getGlowTimes() {
        return glowTimes;
    }

    public List<Integer> getGlowPauses() {
        return glowPauses;
    }
}