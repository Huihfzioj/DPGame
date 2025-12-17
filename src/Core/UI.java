package Core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import javax.imageio.ImageIO;

import static java.awt.SystemColor.text;

public class UI {
    GamePanel gamePanel;
    //déclaration de la police
    Font font;
    //BufferedImage keyImage;
    public boolean messageOn = false ;
    public String message = "";
    int messageCounter=0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat dformat = new DecimalFormat("#0.00");
    BufferedImage heart_full,heart_half,heart_blank;
    Graphics2D graphics2D;
    public int uiMenuIndex = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        try {
            InputStream is= getClass().getResourceAsStream("/Font/x12y16pxMaruMonica.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT,is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //OBJ_Key key = new OBJ_Key(gamePanel);
        //keyImage=key.image;

        //Create Hud object
        SuperObject heart =new OBJ_Heart(gamePanel);
        heart_full=heart.image;
        heart_half=heart.image2;
        heart_blank=heart.image3;

    }

    public void showMessage(String text) {
        message = text;
        messageOn=true;
    }
    //afficher du texte
    public void draw(Graphics2D g2d) {
        this.graphics2D = g2d;
        graphics2D.setFont(font);
        graphics2D.setColor(Color.black);
        if (gamePanel.gameState instanceof MenuState) {
            drawMenuScreen(g2d);
        } else if (gamePanel.gameState instanceof PauseState) {
            drawPauseScreen(g2d);
        } else if (gamePanel.gameState instanceof PlayState) {
            drawPlayerLife();
        }
    }
    public void drawPauseScreen(Graphics2D g2){
        drawPlayerLife();
        g2.setFont(font.deriveFont(Font.BOLD, 50F));
        String text = "Game Paused";
        int x = getXForCenteredText(g2, text);
        int y = gamePanel.screenHeight / 2;

        // Draw text shadow for better visibility
        g2.setColor(Color.white);
        g2.drawString(text, x + 2, y + 2);

        // Draw main text
        g2.setColor(Color.black);
        g2.drawString(text, x, y);

        // Add instruction for resuming
        g2.setFont(font.deriveFont(Font.PLAIN, 20F));
        String instruction = "Press P to resume";
        x = getXForCenteredText(g2, instruction);
        y += 40;  // Position below the main text
        g2.setColor(Color.yellow);
        g2.drawString(instruction, x, y);
    }

    private void drawPlayerLife() {
        int x = GamePanel.tileSize/2;
        int y = GamePanel.tileSize/2;
        int i = 0;

        // Draw Blank Heart
        while (i < gamePanel.player.maxLife/2){
            graphics2D.drawImage(heart_blank,x,y,null);
            i++;
            x += GamePanel.tileSize;
        }

        // Reset
        x = GamePanel.tileSize/2;
        y = GamePanel.tileSize/2;
        i = 0;

        while(i < gamePanel.player.life){
            graphics2D.drawImage(heart_half,x,y,null);
            i++;
            if (i < gamePanel.player.life){
                graphics2D.drawImage(heart_full,x,y,null);
            }
            i++;
            x += GamePanel.tileSize;
        }
    }

    public int getXForCenteredText(Graphics2D g2, String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;
        return x;
    }
    public void drawMenuScreen(Graphics2D g2) {

        // Draw title
        g2.setFont(font.deriveFont(Font.BOLD,70F));
        String title = "Wraith's Return";
        int x = getXForCenteredText(g2, title);
        int y = gamePanel.screenHeight / 5;
        g2.setColor(Color.gray);
        g2.drawString(title,x+2,y+2);
        g2.setColor(Color.white);
        g2.drawString(title, x, y);
        x= gamePanel.screenWidth/2-55;
        y+=30;
        try{
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_idle.png"));
            g2.drawImage(image,x,y,GamePanel.tileSize*2,GamePanel.tileSize*2,null);
        }catch (Exception e){
            e.printStackTrace();
        }

        g2.setFont(font.deriveFont(Font.BOLD,30F));
        String[] options = {"New Game", "Load Game", "Quit"};
        int startY = y + 140;
        int spacing = 50;

        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            int optionX = getXForCenteredText(g2,text);
            int optionY = startY + i * spacing;

            // Highlight selected option (optional)
            if (i == uiMenuIndex) { // uiMenuIndex: tracks selected menu item
                g2.setColor(Color.yellow);
            } else {
                g2.setColor(Color.white);
            }
            g2.drawString(text, optionX, optionY);
        }
        g2.setFont(font.deriveFont(Font.TRUETYPE_FONT,30F));
        g2.setColor(Color.lightGray);
        String instruction = "Use ↑ ↓ to navigate and Enter to select";
        g2.drawString(instruction, 20, gamePanel.screenHeight - 40);
    }
}
