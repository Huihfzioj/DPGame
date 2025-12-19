package Core.Enemies;

import Core.GamePanel;
import Entities.Entity;

public interface EnemyFactory {
    Entity createEnemy(GamePanel gp);
}
