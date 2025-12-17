package Core;

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
        game.ui.drawPauseScreen();   // affiche overlay pause
    }

    public GameState getPreviousState() {
        return previousState;
    }
}

