package Core.Boosters;

public class AttackBoostDecorator extends TimedPlayerDecorator {

    private final int bonus;

    public AttackBoostDecorator(PlayerComponent wrapped, int bonus, int durationFrames) {
        super(wrapped,durationFrames);
        this.bonus = bonus;
    }

    @Override
    public int getSpeed() {
        return wrapped.getSpeed();
    }

    @Override
    public int getAttack() {
        return wrapped.getAttack() + 3;
    }

    @Override
    public int getDefense() {
        return wrapped.getDefense();
    }
}

