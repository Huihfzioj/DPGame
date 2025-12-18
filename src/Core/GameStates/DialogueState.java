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

        drawSubWindow(g2,x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32));
        x += GamePanel.tileSize;
        y += GamePanel.tileSize;
        g2.drawString(gamePanel.ui.message,x,y);
    }

    public void drawSubWindow(Graphics2D g2,int x, int y, int width, int height){
        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }
}
