package Core.Boosters;

import java.awt.*;

public interface PlayerComponent {
    int getSpeed();
    int getAttack();
    int getDefense();
    void update(); // important for timers
}
