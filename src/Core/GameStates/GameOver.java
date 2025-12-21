package Core.GameStates;

import Core.Entities.Entity;
import Core.GamePanel;
import Core.KeyHandler;

import java.awt.*;
import Core.Entities.Player;
import object.SuperObject;

public class GameOver implements GameState {
    GamePanel gamePanel;
    GameState previous;

    public GameOver(GamePanel gamePanel, GameState gameState) {
        this.gamePanel = gamePanel;
        this.previous = gameState;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        drawGameOverScreen(g2);
    }

    private void drawGameOverScreen(Graphics2D g2) {
        previous.draw(g2);
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
        text = "Game Over";
        g2.setColor(Color.black);
        x = gamePanel.ui.getXForCenteredText(g2, text);
        y = GamePanel.tileSize * 4;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = gamePanel.ui.getXForCenteredText(g2, text);
        y += GamePanel.tileSize * 3;
        g2.drawString(text, x, y);
        if (gamePanel.ui.uiMenuIndex == 0) {
            g2.drawString(">", x - 40, y);
        }
        text = "Quit";
        x = gamePanel.ui.getXForCenteredText(g2, text);
        y += GamePanel.tileSize * 2;
        g2.drawString(text, x, y);
        if (gamePanel.ui.uiMenuIndex == 1) {
            g2.drawString(">", x - 40, y);
        }
    }

    @Override
    public void handleInput(KeyHandler keyHandler) {
        if (keyHandler.getUpPressed()) {
            gamePanel.ui.uiMenuIndex--;
            if (gamePanel.ui.uiMenuIndex < 0) {
                gamePanel.ui.uiMenuIndex = 1;
            }
            //gamePanel.playSE(9);
            keyHandler.setUpPressed(false);
        }
        if (keyHandler.getDownPressed()) {
            gamePanel.ui.uiMenuIndex++;
            if (gamePanel.ui.uiMenuIndex > 1) {
                gamePanel.ui.uiMenuIndex = 0;
            }
            //gamePanel.playSE(9);
            keyHandler.setDownPressed(false);
        }
        if (keyHandler.isEnterPressed()) {
            if (gamePanel.ui.uiMenuIndex == 0) {
                gamePanel.retry();
                gamePanel.stopMusic();
                gamePanel.setGameState(new PlayState(gamePanel));
            } else if (gamePanel.ui.uiMenuIndex == 1) {
                gamePanel.player = new Player(gamePanel, gamePanel.keyHandler);
                gamePanel.enemies = new Entity[20];
                gamePanel.obj = new SuperObject[10];
                gamePanel.ui.messages.clear();
                gamePanel.ui.messageCounter.clear();
                gamePanel.ui.uiMenuIndex = 0;
                gamePanel.setGameState(new MenuState(gamePanel));
                gamePanel.stopMusic();
            }
            keyHandler.setEnterPressed(false);
        }
    }
}
