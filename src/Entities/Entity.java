package Entities;

import Core.GamePanel;
import Core.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    private int worldx,worldy;
    public int speed;
    private BufferedImage idle,up1,up2,down1,down2,left1,left2,right1,right2;
    private Direction direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea ;
    public boolean collisionOn = false ;
    public int SolidAreaDefaultX, SolidAreaDefaultY;
    public String name;
    //CHARACTER STATUS
    public int maxLife;
    public int life;
    public boolean invincible = false;
    public int  invincibleCounter = 0;
    public int type;
    public Entity(){

    }

    public Entity(int worldx, int worldy, int speed) {
        this.worldx = worldx;
        this.worldy = worldy;
        this.speed = speed;
    }

    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null ;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Player/"+imageName+".png"));
            image = uTool.scaleImage(image, GamePanel.tileSize,GamePanel.tileSize);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  image;
    }

    public void update() {


    }

    public void draw(Graphics2D graphics2D){

    }
    // Getter for x
    public int getworldX() {
        return worldx;
    }

    // Setter for x
    public void setworldX(int worldx) {
        this.worldx = worldx;
    }

    // Getter for y
    public int getworldY() {
        return worldy;
    }

    // Setter for y
    public void setworldY(int worldy) {
        this.worldy = worldy;
    }

    // Getter for speed
    public int getSpeed() {
        return speed;
    }

    // Setter for speed
    public void setSpeed(int speed) {
        this.speed = speed;
    }



    // Getter pour direction (celui qui manque)
    public Direction getDirection() {
        return direction;
    }

    // Setter pour direction (celui qui manque)
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public BufferedImage getIdle() {
        return idle;
    }

    public void setIdle(BufferedImage idle) {
        this.idle = idle;
    }

    public BufferedImage getUp1() {
        return up1;
    }

    public void setUp1(BufferedImage up1) {
        this.up1 = up1;
    }

    public BufferedImage getUp2() {
        return up2;
    }

    public void setUp2(BufferedImage up2) {
        this.up2 = up2;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public void setDown1(BufferedImage down1) {
        this.down1 = down1;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public void setDown2(BufferedImage down2) {
        this.down2 = down2;
    }

    public BufferedImage getLeft1() {
        return left1;
    }

    public void setLeft1(BufferedImage left1) {
        this.left1 = left1;
    }

    public BufferedImage getLeft2() {
        return left2;
    }

    public void setLeft2(BufferedImage left2) {
        this.left2 = left2;
    }

    public BufferedImage getRight1() {
        return right1;
    }

    public void setRight1(BufferedImage right1) {
        this.right1 = right1;
    }

    public BufferedImage getRight2() {
        return right2;
    }

    public void setRight2(BufferedImage right2) {
        this.right2 = right2;
    }

    // Getters and Setters for Animation Counters

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    // Getters and Setters for Collision (Rectangle, boolean, default positions)

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public boolean isCollisionOn() { // Convention pour les booléens
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public int getSolidAreaDefaultX() {
        return SolidAreaDefaultX;
    }

    public void setSolidAreaDefaultX(int solidAreaDefaultX) {
        SolidAreaDefaultX = solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return SolidAreaDefaultY;
    }

    public void setSolidAreaDefaultY(int solidAreaDefaultY) {
        SolidAreaDefaultY = solidAreaDefaultY;
    }

}
