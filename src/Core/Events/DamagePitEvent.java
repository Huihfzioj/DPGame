package Core.Events;

import Core.GamePanel;
import Core.GameStates.DialogueState;

import static Core.GameLogger.LOGGER;

//Concrete Strategy
public class DamagePitEvent implements EventAction {

    @Override
    public boolean execute(GamePanel game) {
        LOGGER.info("[STRATEGY] DamagePitEvent executing - Player fell into pit");
        game.player.life--;
        game.ui.message = "You fell into a pit!";
        game.gameState = new DialogueState(game, game.gameState);
        return true;
    }
}
