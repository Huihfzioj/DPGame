package Core.Events;

import Core.GamePanel;
import Core.GameStates.DialogueState;

public class TeleportingEvent implements EventAction{
    @Override
    public boolean execute(GamePanel game) {
        game.player.setworldX(GamePanel.tileSize*37);
        game.player.setworldY(GamePanel.tileSize*10);
        game.ui.message = "Teleport!";
        game.gameState = new DialogueState(game, game.gameState);
        return true;
    }
}
