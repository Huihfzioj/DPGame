package Core.Boosters;

public class SpeedBoostDecorator extends TimedPlayerDecorator {

    private final int bonus;

    public SpeedBoostDecorator(PlayerComponent wrapped, int bonus, int durationFrames) {
        super(wrapped, durationFrames);
        this.bonus = bonus;
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
}

