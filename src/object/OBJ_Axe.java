package object;

import Core.Entities.Entity;
import Core.Entities.Type;
import Core.GamePanel;
import Core.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class OBJ_Axe extends SuperObject {
    GamePanel gamePanel;
    public OBJ_Axe (GamePanel gamePanel){
        this.gamePanel = gamePanel;

        type = Type.AXE;
        name = "Woodcutter's Axe";
        image=setup("axe");
        setDown1(image);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "["+ name +"]\n A bit rusty but still \n functional";
    }

    @Override
    public void update() {

    }
}
