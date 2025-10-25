package de.scholle.manhuntX.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class TeamManager {

    private HashSet<UUID> hunters;
    private HashSet<UUID> runners;

    public TeamManager() {
        this.hunters = new HashSet<>();
        this.runners = new HashSet<>();
    }

    public boolean setHunter(Player player) {
        if (runners.contains(player.getUniqueId())) {
            runners.remove(player.getUniqueId());
        }
        return hunters.add(player.getUniqueId());
    }

    public boolean setRunner(Player player) {
        if (hunters.contains(player.getUniqueId())) {
            hunters.remove(player.getUniqueId());
        }
        return runners.add(player.getUniqueId());
    }

    public boolean removePlayer(Player player) {
        boolean removed = false;
        if (hunters.contains(player.getUniqueId())) {
            hunters.remove(player.getUniqueId());
            removed = true;
        }
        if (runners.contains(player.getUniqueId())) {
            runners.remove(player.getUniqueId());
            removed = true;
        }
        return removed;
    }

    public boolean isHunter(Player player) {
        return hunters.contains(player.getUniqueId());
    }

    public boolean isRunner(Player player) {
        return runners.contains(player.getUniqueId());
    }

    public HashSet<UUID> getHunters() {
        return new HashSet<>(hunters);
    }

    public HashSet<UUID> getRunners() {
        return new HashSet<>(runners);
    }

    public void clearTeams() {
        hunters.clear();
        runners.clear();
    }

    public Player getFirstRunner() {
        if (runners.isEmpty()) return null;
        UUID firstRunnerId = runners.iterator().next();
        return Bukkit.getPlayer(firstRunnerId);
    }

    public Player getRunnerByName(String name) {
        for (UUID runnerId : runners) {
            Player runner = Bukkit.getPlayer(runnerId);
            if (runner != null && runner.getName().equalsIgnoreCase(name)) {
                return runner;
            }
        }
        return null;
    }
}