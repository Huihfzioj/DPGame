package object;

import Core.Entities.Entity;
import Core.GamePanel;
import Core.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject extends Entity {
    public BufferedImage image ,image2,image3;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,40,40);
    public int SolidAreaDefaultX = 0 ;
    public int SolidAreaDefaultY = 0 ;
    UtilityTool uTool = new UtilityTool();
    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.getworldX() + gp.player.screenx;
        int screenY = worldY - gp.player.getworldY() + gp.player.screeny;

        if(worldX + gp.tileSize > gp.player.getworldX() - gp.player.screenx &&
                worldX - gp.tileSize < gp.player.getworldX() + gp.player.screenx &&
                worldY + gp.tileSize > gp.player.getworldY() - gp.player.screeny &&
                worldY - gp.tileSize < gp.player.getworldY() + gp.player.screeny) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null ;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/"+imageName+".png"));
            image = uTool.scaleImage(image, GamePanel.tileSize,GamePanel.tileSize);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  image;
    }
}
