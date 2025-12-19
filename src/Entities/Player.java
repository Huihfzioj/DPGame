package Entities;

import Core.GamePanel;
import Core.KeyHandler;
import Core.UI;
import Core.UtilityTool;

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
    //public int haskey = 0;
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
        //PLAYER STATUS
        maxLife =6;
        life=maxLife;
    }

    public void getPlayerImage(){


        idle = setup("Ghost_idle");
        right = setup("Ghost_right");
        left = setup("Ghost_left");
        right1 = setup("Ghost_right (1)");
        left1 = setup("Ghost_left (1)");
        up = setup("Ghost_up");
        up1 = setup("Ghost_up (1)");
        down = setup("Ghost_down");
        down1 = setup("Ghost_down (1)");

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

        // CHECK MONSTER COLLISION - ICI SEULEMENT
        int enemiesIndex = gamePanel.ccheker.checkEntity(this, gamePanel.enemies);
        ContactEnemie(enemiesIndex);

        pickUpObject(objectindexe);

        // if collision is false player can move
        if (!collisionOn) {
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

        // DÉPLACER LE COMPTEUR ICI - TOUJOURS ACTIF, PAS SEULEMENT EN CAS DE COLLISION
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 60) { // 60 frames = 1 seconde (à 60 FPS)
                invincible = false;
                invincibleCounter = 0;
            }
        }

        gamePanel.eventHandler.checkEvent();

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



    public void ContactEnemie(int i) {
        if(i != 999) {
            if(!invincible) {
                life -= 1;
                invincible = true;
                invincibleCounter = 0; // Réinitialiser le compteur
            }
        }
    }
    public void DamageEnemie(int i)
    {  if (i != 999){

    }
    }
    public void pickUpObject (int i) {
        if (i != 999){
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
        if(invincible) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        graphics2D.drawImage(image,screenx,screeny,null);

      // Reset alpha
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
       // graphics2D.drawImage(image,screenx,screeny,null);
        // DEBUG
       // graphics2D.setFont(new Font("Arial", Font.PLAIN, 26));
       // graphics2D.setColor(Color.white);
       // graphics2D.drawString("Invincible:" + invincibleCounter, 10, 400);
    }

}