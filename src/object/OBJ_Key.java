package object;

import Core.Entities.Entity;
import Core.GamePanel;
import Core.UtilityTool;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject {
    GamePanel gp;

    public OBJ_Key(GamePanel gp) {


        name = "Key";
        description = "["+ name +"]\n"+"A key that opens locked \n doors hiding memories";
        image = setup("key");
        image = uTool.scaleImage(image,GamePanel.tileSize,GamePanel.tileSize);
        setDown1(image);
    }
}