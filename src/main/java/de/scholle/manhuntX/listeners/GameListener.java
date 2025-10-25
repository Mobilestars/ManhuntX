package de.scholle.manhuntX.listeners;

import de.scholle.manhuntX.ManhuntX;
import de.scholle.manhuntX.managers.GameManager;
import de.scholle.manhuntX.managers.TeamManager;
import de.scholle.manhuntX.util.ChallengeType;
import de.scholle.manhuntX.util.GameState;
import de.scholle.manhuntX.util.MessageFormatter;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GameListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        GameManager gameManager = ManhuntX.getInstance().getGameManager();
        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();

        if (gameManager.getGameState() != GameState.RUNNING) return;

        if (teamManager.isRunner(player)) {
            Player killer = player.getKiller();
            String reason = killer != null ? killer.getName() + " killed the runner " + player.getName() + "!" :
                    player.getName() + " died!";
            gameManager.endGameWithHunterWin(reason);
        }
    }

    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) return;

        GameManager gameManager = ManhuntX.getInstance().getGameManager();
        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();

        if (gameManager.getGameState() != GameState.RUNNING) return;
        if (gameManager.getChallengeType() != ChallengeType.KILL_ENDER_DRAGON) return;

        Player killer = event.getEntity().getKiller();
        if (killer != null && teamManager.isRunner(killer)) {
            String reason = killer.getName() + " killed the Ender Dragon!";
            gameManager.endGameWithRunnerWin(reason);
        }
    }
}