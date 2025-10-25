package de.scholle.manhuntX.managers;

import de.scholle.manhuntX.ManhuntX;
import de.scholle.manhuntX.util.GameState;
import de.scholle.manhuntX.util.ChallengeType;
import de.scholle.manhuntX.util.MessageFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class GameManager {

    private GameState gameState;
    private ChallengeType challengeType;
    private int survivalTime;
    private int preparationTime;
    private int gameTime;
    private HashMap<UUID, Long> runnerStartTimes;
    private BukkitRunnable gameTimer;

    public GameManager() {
        this.gameState = GameState.WAITING;
        this.challengeType = ChallengeType.KILL_ENDER_DRAGON;
        this.survivalTime = 3600;
        this.preparationTime = 0;
        this.runnerStartTimes = new HashMap<>();
    }

    public void startGame() {
        if (gameState != GameState.WAITING) return;

        gameState = GameState.PREPARATION;
        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();

        // Runners
        for (UUID runnerId : teamManager.getRunners()) {
            Player runner = Bukkit.getPlayer(runnerId);
            if (runner != null) {
                String challengeMessage = getChallengeStartMessage();
                runner.sendTitle(ChatColor.YELLOW + "Start", ChatColor.YELLOW + challengeMessage);
                runnerStartTimes.put(runnerId, System.currentTimeMillis());
            }
        }

        // Hunters
        for (UUID hunterId : teamManager.getHunters()) {
            Player hunter = Bukkit.getPlayer(hunterId);
            if (hunter != null) {
                hunter.sendTitle(ChatColor.YELLOW + "Start", ChatColor.YELLOW + "Kill the Runner");
            }
        }

        if (preparationTime > 0) {
            startPreparationPhase();
        } else {
            startMainGame();
        }
    }

    private void startPreparationPhase() {
        new BukkitRunnable() {
            int timeLeft = preparationTime;

            @Override
            public void run() {
                if (timeLeft <= 0) {
                    cancel();
                    startMainGame();
                    return;
                }

                for (UUID runnerId : ManhuntX.getInstance().getTeamManager().getRunners()) {
                    Player runner = Bukkit.getPlayer(runnerId);
                    if (runner != null) {
                        runner.sendMessage(ChatColor.AQUA + "Preparation time: " + timeLeft + "s");
                    }
                }

                timeLeft--;
            }
        }.runTaskTimer(ManhuntX.getInstance(), 0L, 20L);
    }

    private void startMainGame() {
        gameState = GameState.RUNNING;

        gameTimer = new BukkitRunnable() {
            @Override
            public void run() {
                gameTime++;

                if (challengeType == ChallengeType.SURVIVE_TIME && gameTime >= survivalTime) {
                    endGameWithSurvivalWin();
                }
            }
        };
        gameTimer.runTaskTimer(ManhuntX.getInstance(), 0L, 20L);
    }

    public void endGameWithRunnerWin(String reason) {
        if (gameState != GameState.RUNNING) return;

        gameState = GameState.ENDED;
        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();

        for (UUID runnerId : teamManager.getRunners()) {
            Player runner = Bukkit.getPlayer(runnerId);
            if (runner != null) {
                runner.sendTitle(ChatColor.GREEN + "Won", ChatColor.GREEN + reason);
            }
        }

        for (UUID hunterId : teamManager.getHunters()) {
            Player hunter = Bukkit.getPlayer(hunterId);
            if (hunter != null) {
                hunter.sendTitle(ChatColor.RED + "Lose", ChatColor.RED + reason);
            }
        }

        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }

    public void endGameWithHunterWin(String reason) {
        if (gameState != GameState.RUNNING) return;

        gameState = GameState.ENDED;
        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();

        for (UUID hunterId : teamManager.getHunters()) {
            Player hunter = Bukkit.getPlayer(hunterId);
            if (hunter != null) {
                hunter.sendTitle(ChatColor.GREEN + "Won", ChatColor.GREEN + reason);
            }
        }

        for (UUID runnerId : teamManager.getRunners()) {
            Player runner = Bukkit.getPlayer(runnerId);
            if (runner != null) {
                runner.sendTitle(ChatColor.RED + "Lose", ChatColor.RED + reason);
            }
        }

        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }

    private void endGameWithSurvivalWin() {
        String reason = "Survived " + (survivalTime / 60) + " minutes";
        endGameWithRunnerWin(reason);
    }

    private String getChallengeStartMessage() {
        switch (challengeType) {
            case KILL_ENDER_DRAGON:
                return "Win by killing the Ender Dragon";
            case SURVIVE_TIME:
                return "Win by surviving " + (survivalTime / 60) + " minutes";
            default:
                return "Win the game";
        }
    }

    // Getter & Setter
    public GameState getGameState() { return gameState; }
    public void setGameState(GameState gameState) { this.gameState = gameState; }
    public ChallengeType getChallengeType() { return challengeType; }
    public void setChallengeType(ChallengeType challengeType) { this.challengeType = challengeType; }
    public int getSurvivalTime() { return survivalTime; }
    public void setSurvivalTime(int survivalTime) { this.survivalTime = survivalTime; }
    public int getPreparationTime() { return preparationTime; }
    public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }
    public int getGameTime() { return gameTime; }
}
