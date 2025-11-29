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
    BufferedImage keyImage;
    public boolean messageOn = false ;
    public String message = "";
    int messageCounter=0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat dformat = new DecimalFormat("#0.00");
    BufferedImage heart_full,heart_half,heart_blank;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        font = new Font("Arial", Font.PLAIN, 30);
        arial_80 = new Font("Arial", Font.BOLD, 50);
        OBJ_Key key = new OBJ_Key(gamePanel);
        keyImage=key.image;

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

        if (gameFinished==true) {
            //afficher un message au centre de l'ecran

            g2d.setFont(font);
            g2d.setColor(Color.white);
            String text ;
            int textlengh;
            int x,y;
            text = "you found the treasure!";
            textlengh = (int)g2d.getFontMetrics().getStringBounds(text,g2d).getWidth();
             x = gamePanel.screenWidth/2 - textlengh/2 ;
             y = gamePanel.screenHeight/2 - (gamePanel.tileSize*3);
             g2d.drawString(text, x,y );
             //affichage de temps
            text = "your time is:"+dformat.format(playTime)+"!";
            textlengh = (int)g2d.getFontMetrics().getStringBounds(text,g2d).getWidth();
            x = gamePanel.screenWidth/2 - textlengh/2 ;
            y = gamePanel.screenHeight/2 + (gamePanel.tileSize*4);
            g2d.drawString(text, x,y );
             //afficher congratulations et arreter le jeu

            g2d.setFont(arial_80);
            g2d.setColor(Color.yellow);
            text = "Congratulations!";
            textlengh = (int)g2d.getFontMetrics().getStringBounds(text,g2d).getWidth();
            x = gamePanel.screenWidth/ 2 - textlengh/2 ;
            y = gamePanel.screenHeight/2 + (gamePanel.tileSize*2);
            g2d.drawString(text, x,y );
            gamePanel.gameThread=null;



        }else {


            g2d.setFont(font);
            g2d.setColor(Color.white);
            g2d.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
            //dessiner le texte on va afficher le numéro de key
            g2d.drawString("x " + gamePanel.player.haskey, 74, 65);
            //Time
            playTime+=(double) 1/60;
            g2d.drawString("Time: " + dformat.format(playTime), gamePanel.tileSize*11, 65);
            //message
            if (messageOn == true) {
                g2d.setFont(g2d.getFont().deriveFont(20f));
                g2d.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize * 5);
                messageCounter++;
                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }

}
