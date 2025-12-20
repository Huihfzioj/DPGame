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
    int slotCol = 0;
    int slotRow = 0;

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
        drawInventory(g2);
    }

    private void drawInventory(Graphics2D g2) {
        //Frame
        int frameX = GamePanel.tileSize*9;
        int frameY = GamePanel.tileSize;
        int frameWidth = GamePanel.tileSize*6;
        int frameHeight = GamePanel.tileSize*5;
        gamePanel.ui.drawSubWindow(g2,frameX,frameY,frameWidth,frameHeight);

        //Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        //Draw player's items
        for(int i = 0; i < gamePanel.player.inventory.size();i++){
            g2.drawImage(gamePanel.player.inventory.get(i).getDown1(),slotX,slotY,null);
            slotX += GamePanel.tileSize;

            if (i == 4 || i == 9 || i == 14){
                slotX =slotXstart;
                slotY += GamePanel.tileSize;
            }
        }

        //Cursor
        int cursorX = slotXstart + (GamePanel.tileSize * slotCol);
        int cursorY = slotYstart + (GamePanel.tileSize * slotRow);
        int cursorWidth = GamePanel.tileSize;
        int cursorHeight = GamePanel.tileSize;
        // Draw cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

        //Description frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = GamePanel.tileSize * 3;
        gamePanel.ui.drawSubWindow(g2,dFrameX,dFrameY,dFrameWidth,dFrameHeight);
        //Draw description text
        int textX = dFrameX + 20;
        int textY = dFrameY + GamePanel.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot();

        if (itemIndex < gamePanel.player.inventory.size()){
            String[] lines = gamePanel.player.inventory.get(itemIndex).description.split("\n"); // split by newline

            for (String line : lines) {
                g2.drawString(line, textX, textY);
                textY += g2.getFontMetrics().getHeight(); // move down for next line
            }
        }
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
        if (keyHandler.iszPressed()){
            if (slotRow !=0 ){
                slotRow--;
                gamePanel.playSE(8);
            }
            keyHandler.setzPressed(false);
        }
        if (keyHandler.isqPressed()){
            if (slotCol !=0 ){
                slotCol--;
                gamePanel.playSE(8);
            }
            keyHandler.setqPressed(false);
        }
        if(keyHandler.isdPressed()){
            if (slotCol != 4){
                slotCol++;
                gamePanel.playSE(8);
            }
            keyHandler.setdPressed(false);
        }
        if(keyHandler.issPressed()){
            if (slotRow != 3){
                slotRow++;
                gamePanel.playSE(8);
            }
            keyHandler.setsPressed(false);
        }
    }
    public int getItemIndexOnSlot(){
        return slotCol + (slotRow *5 );
    }
}
