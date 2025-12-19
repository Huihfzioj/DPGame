package Core;

import Core.Enemies.RegularGrim;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject() {
    }
    public void setEnemy(){
        gp.enemies[0] = new RegularGrim(gp);
        gp.enemies[0].setworldX(GamePanel.tileSize*23);
        gp.enemies[0].setworldY(GamePanel.tileSize*20);

        gp.enemies[1] = new RegularGrim(gp);
        gp.enemies[1].setworldX(GamePanel.tileSize*23);
        gp.enemies[1].setworldY(GamePanel.tileSize*24);
    }
}
