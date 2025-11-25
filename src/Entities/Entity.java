package Entities;

import java.awt.image.BufferedImage;

public class Entity {
    private int worldx,worldy;
    private int speed;
    private BufferedImage idle,up1,up2,down1,down2,left1,left2,right1,right2;
    private Direction direction;

    public Entity(){

    }

    public Entity(int worldx, int worldy, int speed) {
        this.worldx = worldx;
        this.worldy = worldy;
        this.speed = speed;
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
}
