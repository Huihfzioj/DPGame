package Core.Events;

import Core.GamePanel;

//Strategy Interface
public interface EventAction {
    boolean execute(GamePanel game);
}
