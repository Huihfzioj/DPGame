package Core.Events;

import Core.Boosters.AttackBoostDecorator;
import Core.Boosters.SpeedBoostDecorator;
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
        game.playerComponent = new AttackBoostDecorator(
                game.playerComponent,
                10,
                60*5
        );
        game.aSetter.setEnemy();
        game.keyHandler.setEnterPressed(false);
        return true; // Successfully executed
    }
}
