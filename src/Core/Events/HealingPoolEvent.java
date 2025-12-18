package Core.Events;

import Core.GamePanel;
import Core.GameStates.DialogueState;

public class HealingPoolEvent implements EventAction{
    @Override
    public boolean execute(GamePanel game) {
        if (!game.keyHandler.isEnterPressed()) {
            return false; // Not executed yet
        }

        game.ui.message = "You are standing on a healing pool. \nYour life has been recovered!";
        game.gameState = new DialogueState(game, game.gameState);
        game.player.life = game.player.maxLife;

        game.keyHandler.setEnterPressed(false);
        return true; // Successfully executed
    }
}
