package de.scholle.manhuntX;

import de.scholle.manhuntX.commands.ManhuntCommand;
import de.scholle.manhuntX.commands.CompassCommand;
import de.scholle.manhuntX.commands.ManhuntTabCompleter;
import de.scholle.manhuntX.listeners.PlayerListener;
import de.scholle.manhuntX.listeners.InventoryListener;
import de.scholle.manhuntX.listeners.GameListener;
import de.scholle.manhuntX.managers.ConfigManager;
import de.scholle.manhuntX.managers.GameManager;
import de.scholle.manhuntX.managers.TeamManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ManhuntX extends JavaPlugin {

    private static ManhuntX instance;
    private GameManager gameManager;
    private TeamManager teamManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configManager = new ConfigManager();
        this.teamManager = new TeamManager();
        this.gameManager = new GameManager();

        registerCommands();
        registerListeners();

        configManager.loadConfig();

        getLogger().info("ManhuntX has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ManhuntX has been disabled!");
    }

    private void registerCommands() {
        getCommand("manhuntx").setExecutor(new ManhuntCommand());
        getCommand("compass").setExecutor(new CompassCommand());
        getCommand("manhuntx").setTabCompleter(new ManhuntTabCompleter());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new GameListener(), this);
    }

    public static ManhuntX getInstance() {
        return instance;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}