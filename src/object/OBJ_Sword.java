package object;

import Core.GamePanel;
import Core.UtilityTool;
import Core.Entities.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class OBJ_Sword extends Entity {
    GamePanel gamePanel;

    public OBJ_Sword(GamePanel gp){
        gamePanel = gp;
        name = "Normal Sword";
        setDown1(setup("sword_normal"));
        attackValue = 1;
        description = "["+ name +"]\n"+"An old wooden sword";
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

    @Override
    public void update() {

    }
}
