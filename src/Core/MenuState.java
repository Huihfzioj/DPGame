package Core;

import java.awt.*;
import java.awt.event.KeyEvent;


public class MenuState implements GameState {
    private GamePanel gamePanel;

    public MenuState(GamePanel game) {
        this.gamePanel = game;
    }

    @Override
    public void update() {
        // Le joueur ne bouge pas en menu
    }

    @Override
    public void draw(Graphics2D g2) {
        gamePanel.ui.drawMenuScreen(g2); // crée une méthode drawMenuScreen() dans UI
    }
}
