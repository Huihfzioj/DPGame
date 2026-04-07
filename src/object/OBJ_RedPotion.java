package object;

import Core.Entities.Entity;
import Core.Entities.Type;
import Core.GamePanel;
import Core.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class OBJ_RedPotion extends SuperObject{
    int value = 5;
    GamePanel gamePanel;
    public OBJ_RedPotion(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        type = Type.CONSUMABLE;
        name = "Red Potion";
        image = setup("potion_red");
        setDown1(image);
        description = "[Red Potion]\n Heals your life force\n by "+ value +".";
    }
    public void use (Entity entity){
        gamePanel.ui.message = "You used the " + name +"\n"+" Your life force has been \n recovered by " + value + ".";
        entity.life += value;
        if (gamePanel.player.life > gamePanel.player.maxLife){
            gamePanel.player.life = gamePanel.player.maxLife;
        }
        gamePanel.playSE(2);
    }
}
