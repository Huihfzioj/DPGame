package object;

import Core.Entities.Type;
import Core.GamePanel;
import Core.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class OBJ_BlueShield extends SuperObject{
    GamePanel gamePanel;

    public OBJ_BlueShield(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        type = Type.SHIELD;
        name = "Blue Shield";
        image = setup("shield_blue");
        setDown1(image);
        defenseValue = 3;
        description = "["+ name +"]\n"+"Shiny blue shield";
    }

    @Override
    public void update() {

    }
}
