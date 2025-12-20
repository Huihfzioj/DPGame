package Core.Enemies;

import Core.GamePanel;
import Core.Entities.Entity;

public class SkeletonFactory implements EnemyFactory{

    @Override
    public Entity createEnemy(GamePanel gp) {
        return new SkeletonLord(gp);
    }

}
