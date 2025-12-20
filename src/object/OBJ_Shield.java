package object;

import Core.GamePanel;
import Core.UtilityTool;
import Core.Entities.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class OBJ_Shield extends Entity {

    GamePanel gamePanel;

    public OBJ_Shield(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        name = "Wood Shield";
        setDown1(setup("shield_wood"));
        defenseValue = 1;
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
