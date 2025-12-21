package object;

import Core.GamePanel;

public class OBJ_Memory extends SuperObject{
    GamePanel gp;

    public OBJ_Memory(GamePanel gp) {


        name = "Memory Fragment";
        description = "["+ name +"]\n"+"A memory of the ghost \n character past life";
        image = setup("memory");
        image = uTool.scaleImage(image,GamePanel.tileSize,GamePanel.tileSize);
        setDown1(image);
    }
}
