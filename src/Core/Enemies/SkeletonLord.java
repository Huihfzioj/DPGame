package Core.Enemies;

import Core.GamePanel;
import Core.UtilityTool;
import Entities.Direction;
import Entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SkeletonLord extends Entity {
    GamePanel gamePanel;
    int actionLockCounter = 0;
    public SkeletonLord(GamePanel gamePanel){
        this.gamePanel=gamePanel;

        name = "Skeleton Lord";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        setDirection(Direction.DOWN); // or any default
        solidArea = new Rectangle();
        solidArea.x = 10;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        setSolidAreaDefaultX(solidArea.x);
        setSolidAreaDefaultY(solidArea.y);
        setworldX(GamePanel.tileSize * 23);
        setworldY(GamePanel.tileSize * 21);


        getImages();
    }

    @Override
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null ;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Enemies/Skeleton Lord/"+imageName+".png"));
            image = uTool.scaleImage(image, GamePanel.tileSize,GamePanel.tileSize);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  image;
    }

    public void getImages(){
        setIdle(setup("skeletonlord_up_1"));
        setUp1(setup("skeletonlord_up_1"));
        setUp2(setup("skeletonlord_up_2"));
        setDown1(setup("skeletonlord_down_1"));
        setDown2(setup("skeletonlord_down_2"));
        setLeft1(setup("skeletonlord_left_1"));
        setRight1(setup("skeletonlord_right_1"));
        setLeft2(setup("skeletonlord_left_2"));
        setRight2(setup("skeletonlord_right_2"));
    }

    public void setAction(){
        actionLockCounter++;

        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if (i <=25 ){
                setDirection(Direction.UP);
            }
            if (i > 25 && i <= 50){
                setDirection(Direction.DOWN);
            }
            if (i > 50 && i <= 75){
                setDirection(Direction.LEFT);
            }
            if (i > 75 && i <= 100){
                setDirection(Direction.RIGHT);
            }

            actionLockCounter=0;
        }
    }
    @Override
    public void update() {

        setAction();

        collisionOn = false;
        gamePanel.ccheker.chektile(this);

        if (!collisionOn) {
            switch (getDirection()) {
                case UP -> setworldY(getworldY() - speed);
                case DOWN -> setworldY(getworldY() + speed);
                case LEFT -> setworldX(getworldX() - speed);
                case RIGHT -> setworldX(getworldX() + speed);
            }
        }

        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 60) { // 60 frames = 1 seconde (à 60 FPS)
                invincible = false;
                invincibleCounter = 0;
            }
        }

        spriteCounter++;
        if (spriteCounter > 20) { // faster than player = more aggressive feel
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (getDirection()) {
            case UP -> {
                image = (spriteNum == 1) ? getUp1() : getUp2();
            }
            case DOWN -> {
                image = (spriteNum == 1) ? getDown1() : getDown2();
            }
            case LEFT -> {
                image = (spriteNum == 1) ? getLeft1() : getLeft2();
            }
            case RIGHT -> {
                image = (spriteNum == 1) ? getRight1() : getRight2();
            }
            default -> image = getIdle();
        }

        screenX = getworldX() - gamePanel.player.getworldX() + gamePanel.player.screenx;
        screenY = getworldY() - gamePanel.player.getworldY() + gamePanel.player.screeny;
        super.draw(g2);
        if (invincible){
            hpBar = true;
            hpBarCounter = 0;
            changeAlpha(g2,0.4f);
        }
        if (isDying()){
            dyingAnimation(g2);
        }
        g2.drawImage(image, screenX, screenY, null);
        changeAlpha(g2,1f);
    }


}
