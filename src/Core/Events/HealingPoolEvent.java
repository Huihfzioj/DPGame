package Core.Events;

import Core.GamePanel;
import Core.GameStates.DialogueState;

public class HealingPoolEvent implements EventAction{
    @Override
    public void execute(GamePanel game) {
        if (game.keyHandler.isEnterPressed()){
            game.ui.message = "You are standing on a healing pool. \n Your life has been recovered!";
            game.gameState = new DialogueState(game, game.gameState);
            game.player.life=game.player.maxLife;
        }
        game.keyHandler.setEnterPressed(false);
    }
}
