package Core;

import Core.Enemies.EnemyFactory;
import Core.Enemies.RegularGrim;
import Core.Enemies.RegularGrimFactory;
import Core.Enemies.SkeletonFactory;
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
        EnemyFactory grimFactory = new RegularGrimFactory();
        gp.enemies[0] = grimFactory.createEnemy(gp);
        gp.enemies[0].setworldX(GamePanel.tileSize * 23);
        gp.enemies[0].setworldY(GamePanel.tileSize * 20);

        EnemyFactory skeletonFactory = new SkeletonFactory();
        gp.enemies[1] = skeletonFactory.createEnemy(gp);
    }
}
