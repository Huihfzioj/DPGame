package Core;

import java.awt.*;
import Entities.Player;
public class PlayState implements GameState {
    private GamePanel game;

    public PlayState(GamePanel game) {
        this.game = game;
    }

    @Override
    public void update() {
        game.player.update();       // délégué au player
        // objets, collisions, etc.
    }

    @Override
    public void draw(Graphics2D g2) {
        game.tileM.draw(g2);

        for (int i = 0; i < game.obj.length; i++) {
            if (game.obj[i] != null) {
                game.obj[i].draw(g2, game);
            }
        }

        game.player.draw(g2);
        game.ui.draw(g2);           // affiche UI spécifique au gameplay
    }

    @Override
    public void handleInput(KeyHandler keyHandler) {
        if (keyHandler.ispPressed()) {
            game.setGameState(new PauseState(game,this));
            keyHandler.setpPressed(false);
        }
    }
}

