package Core.Boosters;

import java.util.logging.Logger;

import static Core.GameLogger.LOGGER;

public class SpeedBoostDecorator extends TimedPlayerDecorator {

    private final int bonus;
    private boolean logged = false;

    public SpeedBoostDecorator(PlayerComponent wrapped, int bonus, int durationFrames) {
        super(wrapped, durationFrames);
        this.bonus = bonus;
        
        // LOG: Application du décorateur de vitesse
        LOGGER.info("[DECORATOR] SpeedBoostDecorator applied - Bonus: +" + bonus + ", Duration: " + durationFrames + " frames");
    }

    @Override
    public int getSpeed() {
        return wrapped.getSpeed() + bonus;
    }

    @Override
    public int getAttack() {
        return wrapped.getAttack();
    }

    @Override
    public int getDefense() {
        return wrapped.getDefense();
    }
    
    @Override
    public void update() {
        super.update();
        
        // LOG: Expiration du décorateur
        if (isExpired() && !logged) {
            LOGGER.info("[DECORATOR] SpeedBoostDecorator expired");
            logged = true;
        }
    }
}

