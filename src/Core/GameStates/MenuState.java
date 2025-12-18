package Core.GameStates;

import Core.GamePanel;
import Core.KeyHandler;

import java.awt.*;

import static java.lang.Math.abs;


public class MenuState implements GameState {
    private GamePanel gamePanel;

    public MenuState(GamePanel game) {
        this.gamePanel = game;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g2) {
        gamePanel.ui.drawMenuScreen(g2); // crée une méthode drawMenuScreen() dans UI
    }

    @Override
    public void handleInput(KeyHandler keyHandler) {
        if (keyHandler.isEnterPressed()) {
            if (gamePanel.ui.uiMenuIndex == 0){
                gamePanel.setGameState(new PlayState(gamePanel));
            }
            if (gamePanel.ui.uiMenuIndex == 1){
                // Load Game TO BE ADDED
            }
            if (gamePanel.ui.uiMenuIndex == 2){
                System.exit(0);
            }
            keyHandler.setEnterPressed(false);
        }
        if (keyHandler.getDownPressed()) {
            gamePanel.ui.uiMenuIndex+=1;
            if (gamePanel.ui.uiMenuIndex>2){
                gamePanel.ui.uiMenuIndex=0;
            }
            keyHandler.setDownPressed(false);
        }
        if (keyHandler.getUpPressed()) {
            gamePanel.ui.uiMenuIndex-=1;
            if (gamePanel.ui.uiMenuIndex<0){
                gamePanel.ui.uiMenuIndex=2;
            }
            keyHandler.setUpPressed(false);
        }
    }
}
