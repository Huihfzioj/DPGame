package Core.Boosters;

public abstract class TimedPlayerDecorator implements PlayerComponent {

    public PlayerComponent wrapped;
    protected int durationFrames;
    protected int counter = 0;
    protected boolean expired = false;

    protected TimedPlayerDecorator(PlayerComponent wrapped, int durationFrames) {
        this.wrapped = wrapped;
        this.durationFrames = durationFrames;
    }

    @Override
    public void update() {
        wrapped.update();
        counter++;
        if (counter >= durationFrames) {
            expired = true;
        }
    }

    public boolean isExpired() {
        return expired;
    }
}

