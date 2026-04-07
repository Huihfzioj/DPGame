package Core.GameStates;

import Core.GamePanel;
import Core.KeyHandler;

import java.awt.*;

public class PauseState implements GameState {
    private GamePanel game;
    private GameState previousState;

    public PauseState(GamePanel game, GameState previousState) {
        this.game = game;
        this.previousState = previousState;
    }

    @Override
    public void update() {
        // On ne met rien à jour, le jeu est gelé
    }

    @Override
    public void draw(Graphics2D g2) {
        previousState.draw(g2);      // affiche le jeu gelé
        game.ui.draw(g2);             // This will set up fonts properly
    }
    public GameState getPreviousState() {
        return previousState;
    }

    @Override
    public void handleInput(KeyHandler keyHandler) {
        if (keyHandler.ispPressed()) {
            game.setGameState(previousState);
            keyHandler.setpPressed(false);
        }
    }
}

