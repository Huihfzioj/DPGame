package Entities;

import Core.GamePanel;
import Core.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static Core.GamePanel.tileSize;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;
    BufferedImage idle,right,left,right1,left1,up,up1,down,down1;
    // La variable 'direction' locale a été SUPPRIMÉE.
    int spriteCounter=0;
    int spriteNumber=1;
    public final int screenx;
    public final int screeny;
    int haskey = 0;
    public Player(GamePanel gamePanel,KeyHandler keyHandler){
        this.gamePanel=gamePanel;
        this.keyHandler=keyHandler;
        screenx = gamePanel.screenWidth/2 - (gamePanel.tileSize/2);
        screeny = gamePanel.screenHeight/2 - (gamePanel.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 6;
        SolidAreaDefaultX = solidArea.x;
        SolidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        this.setworldX(gamePanel.tileSize *23);
        this.setworldY(gamePanel.tileSize *21);
        this.setSpeed(4);
        // Initialisation de la direction par défaut
        this.setDirection(Direction.DOWN);
    }

    public void getPlayerImage(){
        try{
            idle= ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_idle.png"));
            right= ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_right.png"));
            left= ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_left.png"));
            right1=ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_right (1).png"));
            left1=ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_left (1).png"));
            up=ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_up.png"));
            up1=ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_up (1).png"));
            down=ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_down.png"));
            down1=ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_down (1).png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        // Utilisation des setters pour mettre à jour la direction dans l'objet Entity
        if (keyHandler.getUpPressed()){
            this.setDirection(Direction.UP);
        }
        else if (keyHandler.getDownPressed()){
            this.setDirection(Direction.DOWN);
        }
        else if (keyHandler.getLeftPressed()){
            this.setDirection(Direction.LEFT);
        }
        else if (keyHandler.getRightPressed()){
            this.setDirection(Direction.RIGHT);
        }
        else {
            this.setDirection(Direction.NotSpecified);
        }

        // check tile collision
        collisionOn = false;
        gamePanel.ccheker.chektile(this);
        // check object collision
        int objectindexe = gamePanel.ccheker.checkObject(this,true);
        pickUpObject(objectindexe);
        // if collision is false player can move
        if (collisionOn == false) {
            // Utilisation du getter pour lire la direction
            switch(this.getDirection()) {
                case UP:
                    this.setworldY(this.getworldY()-this.getSpeed());
                    break;
                case DOWN:
                    this.setworldY(this.getworldY()+this.getSpeed());
                    break;
                case LEFT:
                    this.setworldX(this.getworldX()-this.getSpeed());
                    break;
                case RIGHT:
                    this.setworldX(this.getworldX()+this.getSpeed());
                    break;
                case NotSpecified:
                    break; // Ne bouge pas
            }
        }

        spriteCounter++;
        if (spriteCounter > 50){
            if (spriteNumber == 1){
                spriteNumber = 2;
            }
            else if (spriteNumber == 2){
                spriteNumber = 1;
            }
            // Correction : Reset du spriteCounter après le cycle
            else{
                spriteCounter = 0;
            }
        }
    }
    public void pickUpObject (int i) {
        if (i != 999){
            String objectName = gamePanel.obj[i].name;

            switch (objectName) {
                case "Key":
                    gamePanel.playSE(1);
                    haskey++;
                    gamePanel.obj[i] = null;
                    System.out.println("Key:"+haskey);
                    break;
                case "Door":
                    gamePanel.playSE(3);
                    if (haskey > 0) {
                        gamePanel.obj[i] = null;
                        haskey--;
                    }
                    System.out.println("Key:"+haskey);
                    break;
                case "Boots" :
                    gamePanel.playSE(2);
                    this.setSpeed(this.getSpeed() + 2);
                    gamePanel.obj[i] = null;
                    break;
            }
        }
    }

    public void draw(Graphics2D graphics2D){
        BufferedImage image=null;

        // Utilisation du getter pour lire la direction
        switch(this.getDirection()){
            case UP :
                if (spriteNumber == 1){
                    image=up;
                }
                if (spriteNumber == 2){
                    image=up1;
                }
                break;
            case DOWN :
                if (spriteNumber == 1){
                    image=down;
                }
                if (spriteNumber == 2){
                    image=down1;
                }
                break;
            case LEFT:
                if (spriteNumber == 1){
                    image=left;
                }
                if (spriteNumber == 2){
                    image=left1;
                }
                break;
            case RIGHT:
                if (spriteNumber == 1){
                    image=right;
                }
                if (spriteNumber == 2){
                    image=right1;
                }
                break;
            default:
                image=idle;
        }
        graphics2D.drawImage(image,screenx,screeny,tileSize,tileSize,null);
    }

}