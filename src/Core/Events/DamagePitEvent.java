package Core.Events;

import Core.GamePanel;
import Core.GameStates.DialogueState;

//Concrete Strategy
public class DamagePitEvent implements EventAction {

    @Override
    public void execute(GamePanel game) {
        game.player.life--;
        game.ui.message = "You fell into a pit!";
        game.gameState = new DialogueState(game, game.gameState);
    }
}
