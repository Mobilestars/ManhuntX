package de.scholle.manhuntX.listeners;

import de.scholle.manhuntX.ManhuntX;
import de.scholle.manhuntX.managers.GameManager;
import de.scholle.manhuntX.managers.TeamManager;
import de.scholle.manhuntX.util.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        GameManager gameManager = ManhuntX.getInstance().getGameManager();
        TeamManager teamManager = ManhuntX.getInstance().getTeamManager();
        Player player = event.getPlayer();

        if (gameManager.getGameState() == GameState.PREPARATION && teamManager.isHunter(player)) {
            if (event.getFrom().getBlockX() != event.getTo().getBlockX() ||
                    event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
                event.setTo(event.getFrom());
            }
        }
    }
}