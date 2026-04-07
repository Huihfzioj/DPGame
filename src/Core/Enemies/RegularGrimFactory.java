package Core.Enemies;

import Core.GamePanel;
import Core.Entities.Entity;
import java.util.logging.Logger;

import static Core.GameLogger.LOGGER;

public class RegularGrimFactory implements EnemyFactory{

    @Override
    public Entity createEnemy(GamePanel gp) {
        // LOG: Factory créant un ennemi
        LOGGER.info("[FACTORY] RegularGrimFactory creating Grim Reaper");
        return new RegularGrim(gp);
    }

}
