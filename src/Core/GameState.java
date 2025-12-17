package Core;

import java.awt.*;


public interface GameState {
    void update();
    void draw(Graphics2D g2);
    void handleInput(KeyHandler keyHandler);
}

