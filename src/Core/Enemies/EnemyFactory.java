package Core.Enemies;

import Core.GamePanel;
import Core.Entities.Entity;

public interface EnemyFactory {
    Entity createEnemy(GamePanel gp);
}
