package Core.Events;

import Core.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Context class
public class EventHandler {

    private final GamePanel gamePanel;
    private final Rectangle eventRect;
    private final int eventRectDefaultX, eventRectDefaultY;
    private long lastEventTime = 0;
    private final long EVENT_COOLDOWN_MS = 4000;

    private final List<EventEntry> events = new ArrayList<>();

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void addEvent(int col, int row, EventAction action) {
        events.add(new EventEntry(col, row, action));
    }

    public void checkEvent() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEventTime < EVENT_COOLDOWN_MS) return;

        for (EventEntry event : events) {
            if (hit(event.col, event.row)) {
                event.action.execute(gamePanel);
                lastEventTime = currentTime;
                break; // Only trigger one event per check
            }
        }
    }

    private boolean hit(int eventCol, int eventRow) {
        boolean hit = false;

        gamePanel.player.solidArea.x = gamePanel.player.getworldX() + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.getworldY() + gamePanel.player.solidArea.y;
        eventRect.x = eventCol * GamePanel.tileSize + eventRect.x;
        eventRect.y = eventRow * GamePanel.tileSize + eventRect.y;

        if (gamePanel.player.solidArea.intersects(eventRect)) {
            hit = true;
        }

        // Reset positions
        gamePanel.player.solidArea.x = gamePanel.player.getSolidAreaDefaultX();
        gamePanel.player.solidArea.y = gamePanel.player.getSolidAreaDefaultY();
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    // Inner class for pairing event coordinates with actions
    private static class EventEntry {
        int col, row;
        EventAction action;

        public EventEntry(int col, int row, EventAction action) {
            this.col = col;
            this.row = row;
            this.action = action;
        }
    }
}
