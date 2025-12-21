package Core.Boosters;

import Core.Entities.Player;

public class BasePlayerComponent implements PlayerComponent {
    private final Player player;

    public BasePlayerComponent(Player player) {
        this.player = player;
    }

    @Override
    public int getSpeed() {
        return player.getSpeed(); // Use getter, not direct field
    }

    @Override
    public int getAttack() {
        return player.getAttack(); // CRITICAL: Use getAttack() method, not player.attack field
    }

    @Override
    public int getDefense() {
        return player.getDefense(); // Use getter method
    }

    @Override
    public void update() {
        // nothing by default
    }
}