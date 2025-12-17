package Core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import static java.awt.SystemColor.text;

public class UI {
    GamePanel gamePanel;
    //déclaration de la police
    Font font, arial_80;
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
        font = new Font("Arial", Font.PLAIN, 30);
        arial_80 = new Font("Arial", Font.BOLD, 50);
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
        graphics2D.setFont(arial_80);
        graphics2D.setColor(Color.black);
        if (gamePanel.gameState instanceof MenuState) {
            drawMenuScreen(g2d);
        } else if (gamePanel.gameState instanceof PauseState) {
            drawPauseScreen();
        } else if (gamePanel.gameState instanceof PlayState) {
            //nothing to insert here the PlayState takes care of it
        }
    }
    public void drawPauseScreen(){
        String text="Game Paused";
        int x = getXForCenteredText(graphics2D,text);
        int y = gamePanel.screenHeight/2;
        graphics2D.drawString(text,x,y);
    }
    public int getXForCenteredText(Graphics2D g2, String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;
        return x;
    }
    public void drawMenuScreen(Graphics2D g2) {
        // Set background
        g2.setColor(Color.darkGray);
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // Draw title
        g2.setFont(arial_80);
        g2.setColor(Color.white);
        String title = "My Awesome Game";
        int x = getXForCenteredText(g2, title);
        int y = gamePanel.screenHeight / 4;
        g2.drawString(title, x, y);

        // Draw menu options
        g2.setFont(font);
        String[] options = {"Start Game", "Options", "Exit"};
        int startY = y + 100;
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

        // Optional: Instructions
        g2.setFont(font);
        g2.setColor(Color.lightGray);
        String instruction = "Use ↑ ↓ to navigate and Enter to select";
        g2.drawString(instruction, 20, gamePanel.screenHeight - 40);
    }
}
