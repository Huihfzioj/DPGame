package Core.GameStates;

import Core.GamePanel;
import Core.KeyHandler;

import java.awt.*;

public class DialogueState implements GameState{

    private GamePanel gamePanel;
    private GameState previousState;

    public DialogueState(GamePanel gamePanel, GameState previousState){
        this.gamePanel = gamePanel;
        this.previousState = previousState;
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        drawDialogueScreen(g2);
    }

    @Override
    public void handleInput(KeyHandler keyHandler) {
        if (keyHandler.ispPressed() && gamePanel.player.life != 0) {
            gamePanel.setGameState(previousState);
            keyHandler.setpPressed(false);
        }
    }

    private void drawDialogueScreen(Graphics2D g2) {
        previousState.draw(g2);
        // window
        int x = GamePanel.tileSize*2;
        int y = GamePanel.tileSize/2;
        int width = gamePanel.screenWidth - (GamePanel.tileSize*4);
        int height = GamePanel.tileSize*5;

        gamePanel.ui.drawSubWindow(g2,x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32));
        x += GamePanel.tileSize;
        y += GamePanel.tileSize;
        String[] lines = gamePanel.ui.message.split("\n"); // split by newline

        for (String line : lines) {
            g2.drawString(line, x, y);
            y += g2.getFontMetrics().getHeight(); // move down for next line
        }
    }
}
