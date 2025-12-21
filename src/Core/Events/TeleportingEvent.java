package Core.Events;

import Core.Boosters.AttackBoostDecorator;
import Core.Boosters.SpeedBoostDecorator;
import Core.GamePanel;
import Core.GameStates.DialogueState;

public class TeleportingEvent implements EventAction{
    @Override
    public boolean execute(GamePanel game) {
        game.player.setworldX(GamePanel.tileSize*37);
        game.player.setworldY(GamePanel.tileSize*10);
        game.ui.message = "Teleport!";
        game.playerComponent = new SpeedBoostDecorator(
                game.playerComponent,
                5,
                60*5
        );
        game.gameState = new DialogueState(game, game.gameState);
        return true;
    }
}
