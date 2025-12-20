package Core.Enemies;

import Core.GamePanel;
import Core.UtilityTool;
import Entities.Direction;
import Entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class RegularGrim extends Entity {
    GamePanel gamePanel;
    int actionLockCounter = 0;
    public RegularGrim(GamePanel gamePanel){
        this.gamePanel=gamePanel;

        name = "Grim Reaper";
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

        getImages();
    }

    @Override
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null ;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Enemies/Grim Reaper/"+imageName+".png"));
            image = uTool.scaleImage(image, GamePanel.tileSize,GamePanel.tileSize);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  image;
    }

    public void getImages(){
        setIdle(setup("grim_idle"));
        setUp1(setup("grim_up"));
        setUp2(setup("grim_up"));
        setDown1(setup("grim_idle"));
        setDown2(setup("grim_idle"));
        setLeft1(setup("grim_left"));
        setRight1(setup("grim_right"));
        setLeft2(setup("grim_left (1)"));
        setRight2(setup("grim_right (1)"));
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
    }

    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image;

        switch (getDirection()) {
            case UP -> image = getUp1();
            case DOWN -> image = getDown1();
            case LEFT -> image = getLeft1();
            case RIGHT -> image = getRight1();
            default -> image = getIdle();
        }

        int screenX = getworldX() - gamePanel.player.getworldX() + gamePanel.player.screenx;
        int screenY = getworldY() - gamePanel.player.getworldY() + gamePanel.player.screeny;

        if (invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
        }
        if (isDying()){
            dyingAnimation(g2);
        }
        g2.drawImage(image, screenX, screenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
}
