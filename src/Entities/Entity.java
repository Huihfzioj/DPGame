package Entities;

import java.awt.image.BufferedImage;

public class Entity {
    private int x,y;
    private int speed;
    private BufferedImage idle,up1,up2,down1,down2,left1,left2,right1,right2;
    private Direction direction;

    public Entity(){

    }

    public Entity(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    // Getter for x
    public int getX() {
        return x;
    }

    // Setter for x
    public void setX(int x) {
        this.x = x;
    }

    // Getter for y
    public int getY() {
        return y;
    }

    // Setter for y
    public void setY(int y) {
        this.y = y;
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
