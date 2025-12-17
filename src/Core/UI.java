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
        if (gamePanel.gameState == gamePanel.playState){
        }
        if (gamePanel.gameState == gamePanel.pauseState){
            drawPauseScreen();
        }
    }
    public void drawPauseScreen(){
        String text="Game Paused";
        int x = getXForCenteredText(text);
        int y = gamePanel.screenHeight/2;
        graphics2D.drawString(text,x,y);
    }
    public int getXForCenteredText(String text){
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text,graphics2D).getWidth();
        int x = gamePanel.screenWidth/2 - length/2;
        return x;
    }
}
