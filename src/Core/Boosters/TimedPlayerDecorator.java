package Core.Boosters;

import static Core.GameLogger.LOGGER;

public abstract class TimedPlayerDecorator implements PlayerComponent {

    public PlayerComponent wrapped;
    protected int durationFrames;
    protected int counter = 0;
    protected boolean expired = false;
    protected boolean countdownLogged = false;

    protected TimedPlayerDecorator(PlayerComponent wrapped, int durationFrames) {
        this.wrapped = wrapped;
        this.durationFrames = durationFrames;
    }

    @Override
    public void update() {
        wrapped.update();
        counter++;
        
        // LOG: Afficher le décompte du décorateur
        if (counter % 30 == 0) { // Log tous les 30 frames (0.5s à 60 FPS)
            int remainingFrames = durationFrames - counter;
            LOGGER.info("[DECORATOR] " + this.getClass().getSimpleName() + " - Time remaining: " + remainingFrames + "/" + durationFrames + " frames");
        }
        
        if (counter >= durationFrames) {
            expired = true;
            LOGGER.info("[DECORATOR] " + this.getClass().getSimpleName() + " expired");
        }
    }

    public boolean isExpired() {
        return expired;
    }
}

