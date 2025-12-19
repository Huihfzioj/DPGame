package Core.Enemies;

import Core.GamePanel;
import Entities.Entity;

public class RegularGrimFactory implements EnemyFactory{

    @Override
    public Entity createEnemy(GamePanel gp) {
        return new RegularGrim(gp);
    }

}
