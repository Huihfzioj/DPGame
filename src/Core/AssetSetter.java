package Core;

import Core.Enemies.EnemyFactory;
import Core.Enemies.RegularGrim;
import Core.Enemies.RegularGrimFactory;
import Core.Enemies.SkeletonFactory;
import object.*;
import java.util.logging.Logger;

import static Core.GameLogger.LOGGER;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject() {
        int i=0;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = GamePanel.tileSize * 25;
        gp.obj[i].worldY = GamePanel.tileSize * 23;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = GamePanel.tileSize * 21;
        gp.obj[i].worldY = GamePanel.tileSize * 19;
        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = GamePanel.tileSize * 26;
        gp.obj[i].worldY = GamePanel.tileSize * 21;
        i++;
        gp.obj[i] = new OBJ_BlueShield(gp);
        gp.obj[i].worldX = GamePanel.tileSize * 35;
        gp.obj[i].worldY = GamePanel.tileSize * 21;
        i++;
        gp.obj[i] = new OBJ_RedPotion(gp);
        gp.obj[i].worldX = GamePanel.tileSize * 23;
        gp.obj[i].worldY = GamePanel.tileSize * 28;
    }
    public void setEnemy(){
        // LOG: Utilisation des factories pour créer les ennemis
        LOGGER.info("[FACTORY] AssetSetter using factories to spawn enemies");
        int i = 0;
        EnemyFactory grimFactory = new RegularGrimFactory();
        gp.enemies[i] = grimFactory.createEnemy(gp);
        gp.enemies[i].setworldX(GamePanel.tileSize * 25);
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
