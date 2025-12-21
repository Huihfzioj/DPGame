package Core.Events;

import Core.GamePanel;
import Core.GameStates.DialogueState;

import static Core.GameLogger.LOGGER;

public class TeleportingEvent implements EventAction{
    @Override
    public boolean execute(GamePanel game) {
        LOGGER.info("[STRATEGY] TeleportingEvent executing - Player teleported to (37, 10)");
        game.player.setworldX(GamePanel.tileSize*37);
        game.player.setworldY(GamePanel.tileSize*10);
        game.ui.message = "Teleport!";
        game.gameState = new DialogueState(game, game.gameState);
        return true;
    }
}
