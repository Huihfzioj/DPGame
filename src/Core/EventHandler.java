package Core;

import Entities.Direction;

import java.awt.*;

public class EventHandler {

    GamePanel gamePanel;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;
    private long lastEventTime = 0;
    private final long EVENT_COOLDOWN_MS = 4000;

    public EventHandler(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        eventRect=new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEventTime > EVENT_COOLDOWN_MS) {
            if (hit(24,21)){
                damagePit(new DialogueState(gamePanel, gamePanel.gameState));
                lastEventTime = currentTime; // Reset cooldown
            }
        }
    }

    private void damagePit(GameState gameState) {
        gamePanel.ui.message = "You fell into a pit! ";
        gamePanel.player.life -= 1;
        gamePanel.gameState = gameState;
    }

    public boolean hit (int eventCol, int eventRow){
        boolean hit = false;

        gamePanel.player.solidArea.x = gamePanel.player.getworldX() + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.getworldY() + gamePanel.player.solidArea.y;
        eventRect.x = eventCol * GamePanel.tileSize + eventRect.x;
        eventRect.y = eventRow * GamePanel.tileSize + eventRect.y;

        if (gamePanel.player.solidArea.intersects(eventRect)){
            hit=true;
        }

        gamePanel.player.solidArea.x = gamePanel.player.getSolidAreaDefaultX();
        gamePanel.player.solidArea.y = gamePanel.player.getSolidAreaDefaultY();
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }
}
