package Core.GameStates;

import Core.GamePanel;
import Core.KeyHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterState implements GameState{

    private GamePanel gamePanel;
    private GameState previousState;

    public CharacterState(GamePanel gamePanel, GameState previousState){
        this.gamePanel = gamePanel;
        this.previousState = previousState;
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        previousState.draw(g2);
        drawCharacterScreen(g2);
    }

    private void drawCharacterScreen(Graphics2D g2) {
        final int frameX = GamePanel.tileSize;
        final int frameY = GamePanel.tileSize;
        final int frameHeight = GamePanel.tileSize*10;
        final int frameWidth = GamePanel.tileSize*6;
        gamePanel.ui.drawSubWindow(g2,frameX,frameY,frameWidth,frameHeight);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX = frameX + 20;
        int textY = frameY + GamePanel.tileSize;
        final int lineHeight = 35;

        List<String> lines = Arrays.asList(
                "Level", "Life", "Strength", "Dexterity",
                "Attack", "Defense", "Exp", "Next Level",
                "Memory frag.", "Weapon", "Shield"
        );

        for (int i = 0; i < lines.size(); i++){
            g2.drawString(lines.get(i),textX,textY);
            if (lines.get(i).equals("Weapon")){
                textY += 20;
            }
            if (lines.get(i).equals("Memory frag.")){
                textY += 15;
            }
            textY += lineHeight;
        }

        List<String> lines2 = Arrays.asList(
                String.valueOf(gamePanel.player.level),
                gamePanel.player.life + "/" + gamePanel.player.maxLife,
                String.valueOf(gamePanel.player.strength),
                String.valueOf(gamePanel.player.dexterity),
                String.valueOf(gamePanel.player.attack),
                String.valueOf(gamePanel.player.defense),
                String.valueOf(gamePanel.player.exp),
                String.valueOf(gamePanel.player.nextLevelExp),
                String.valueOf(gamePanel.player.memoryFragments)
        );


        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + GamePanel.tileSize;
        String value;
        for (int i = 0; i < lines2.size(); i++){
            value = lines2.get(i);
            textX = gamePanel.ui.getXforAlignToRightText(g2,value,tailX);
            g2.drawString(value,textX,textY);
            textY += lineHeight;
        }
        g2.drawImage(gamePanel.player.currentWeapon.getDown1(),tailX - GamePanel.tileSize, textY-14, null);
        textY += GamePanel.tileSize;
        g2.drawImage(gamePanel.player.currentShield.getDown1(),tailX - GamePanel.tileSize, textY-14,null);
    }

    @Override
    public void handleInput(KeyHandler keyHandler) {
        if (keyHandler.iscPressed()) {
            gamePanel.setGameState(previousState);
            keyHandler.setcPressed(false);
        }
    }
}
