package object;

import Core.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,40,40);
    public int SolidAreaDefaultX = 0 ;
    public int SolidAreaDefaultY = 0 ;
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
}
