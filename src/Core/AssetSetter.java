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
        int i = 0;
        EnemyFactory grimFactory = new RegularGrimFactory();
        gp.enemies[i] = grimFactory.createEnemy(gp);
        gp.enemies[i].setworldX(GamePanel.tileSize * 23);
        gp.enemies[i].setworldY(GamePanel.tileSize * 20);
        i++;
        EnemyFactory skeletonFactory = new SkeletonFactory();
        gp.enemies[i] = skeletonFactory.createEnemy(gp);
        gp.enemies[i].setworldX(GamePanel.tileSize * 24);
        gp.enemies[i].setworldY(GamePanel.tileSize * 20);
        i++;
        gp.enemies[i] = grimFactory.createEnemy(gp);
        gp.enemies[i].setworldX(GamePanel.tileSize * 24);
        gp.enemies[i].setworldY(GamePanel.tileSize * 37);
        i++;
        gp.enemies[i] = skeletonFactory.createEnemy(gp);
        gp.enemies[i].setworldX(GamePanel.tileSize * 34);
        gp.enemies[i].setworldY(GamePanel.tileSize * 42);
        i++;
        gp.enemies[i] = skeletonFactory.createEnemy(gp);
        gp.enemies[i].setworldX(GamePanel.tileSize * 38);
        gp.enemies[i].setworldY(GamePanel.tileSize * 42);
    }
}
