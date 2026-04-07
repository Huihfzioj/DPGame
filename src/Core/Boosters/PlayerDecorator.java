package Core.Boosters;

import java.awt.*;

public abstract class PlayerDecorator implements PlayerComponent {

    public PlayerComponent wrapped;

    public PlayerDecorator(PlayerComponent wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public int getSpeed() {
        return wrapped.getSpeed();
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
        wrapped.update();
    }
}
