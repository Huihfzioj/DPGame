package Core.Boosters;

import java.util.logging.Logger;

import static Core.GameLogger.LOGGER;

public class AttackBoostDecorator extends TimedPlayerDecorator {

    private final int bonus;
    private boolean logged = false;

    public AttackBoostDecorator(PlayerComponent wrapped, int bonus, int durationFrames) {
        super(wrapped,durationFrames);
        this.bonus = bonus;
        
        // LOG: Application du décorateur d'attaque
        LOGGER.info("[DECORATOR] AttackBoostDecorator applied - Bonus: +" + bonus + ", Duration: " + durationFrames + " frames");
    }

    @Override
    public int getSpeed() {
        return wrapped.getSpeed();
    }

    @Override
    public int getAttack() {
        return wrapped.getAttack() + bonus;
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
            LOGGER.info("[DECORATOR] AttackBoostDecorator expired");
            logged = true;
        }
    }
}

