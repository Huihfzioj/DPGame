package Core.World;

import java.awt.*;

public interface WorldComponent {
    void update();
    void draw(Graphics2D g2);
    void addComponent(WorldComponent component);
    void removeComponent(WorldComponent component);
    WorldComponent getChild(int index);
    boolean isComplete();
    void reset();
}

