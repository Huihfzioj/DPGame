package Core.GameStates;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Core.GamePanel;
import Core.KeyHandler;
import Core.Entities.Entity;
import Core.World.Zone;

public class PlayState implements GameState {
    private GamePanel game;

    public PlayState(GamePanel game) {
        this.game = game;
    }

    @Override
    public void update() {
        // First, update all enemies
        game.eventHandler.checkEvent();
        for (int i = 0; i < game.enemies.length; i++){
            if (game.enemies[i] != null ){
                if (game.enemies[i].isAlive() && !game.enemies[i].isDying()){
                    game.enemies[i].update();
                }
                if (!game.enemies[i].isAlive()){
                    // IMPORTANT: When enemy dies, also mark it dead in the current zone
                    syncEnemyDeathWithZone(game.enemies[i]);
                    game.enemies[i] = null;

                    // Check if zone is now complete after enemy death
                    checkZoneCompletion();
                }
            }
        }

        game.player.update();

        if (game.player.life <= 0){
            game.setGameState(new GameOver(game,this));
        }
        game.worldManager.checkWorldProgression();
    }

    private void syncEnemyDeathWithZone(Entity deadEnemy) {
        if (deadEnemy == null) return;

        Zone currentZone = game.worldManager.getCurrentZone();
        if (currentZone == null) return;

        // Find and mark the corresponding enemy in the zone as dead
        for (Entity zoneEnemy : currentZone.getEnemies()) {
            if (zoneEnemy == deadEnemy ||
                    (zoneEnemy.getworldX() == deadEnemy.getworldX() &&
                            zoneEnemy.getworldY() == deadEnemy.getworldY())) {
                zoneEnemy.life = 0;
                System.out.println("Enemy defeated in zone: " + currentZone.getName());
                break;
            }
        }
    }

    private void checkZoneCompletion() {
        Zone currentZone = game.worldManager.getCurrentZone();
        if (currentZone == null) return;

        boolean allEnemiesDead = true;

        // Check all enemies in the zone
        for (Entity zoneEnemy : currentZone.getEnemies()) {
            if (zoneEnemy != null && zoneEnemy.isAlive()) {
                allEnemiesDead = false;
                break;
            }
        }

        // Check memories if required
        boolean memoriesCollected = true;
        if (currentZone.getRequiredMemories() > 0) {
            memoriesCollected = currentZone.getCollectedMemories() >= currentZone.getRequiredMemories();
        }

        if (allEnemiesDead && memoriesCollected && !currentZone.isCompleted()) {
            System.out.println("Zone " + currentZone.getName() + " is now complete!");

            // Add UI message
            game.ui.addMessage("Zone completed: " + currentZone.getName() + "!");

            // Check for world progression
            game.worldManager.checkWorldProgression();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        game.tileM.draw(g2);

        for (int i = 0; i < game.obj.length; i++) {
            if (game.obj[i] != null) {
                game.obj[i].draw(g2, game);
            }
        }

        for (Entity enemy : game.enemies) {
            if (enemy != null) {
                enemy.draw(g2);
            }
        }

        drawMessages(g2);
        game.player.draw(g2);
        game.ui.draw(g2);           // affiche UI spécifique au gameplay
    }

    private void drawMessages(Graphics2D g2) {
        int messageX = GamePanel.tileSize;
        int messageY = GamePanel.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));
        for (int i=0; i < game.ui.messages.size(); i++){
            if ( game.ui.messages.get(i) != null){
                g2.setColor(Color.black);
                g2.drawString(game.ui.messages.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(game.ui.messages.get(i), messageX, messageY);
                int counter = game.ui.messageCounter.get(i) + 1;
                game.ui.messageCounter.set(i,counter);
                messageY += 50;

                if (game.ui.messageCounter.get(i) > 180){
                    game.ui.messages.remove(i);
                    game.ui.messageCounter.remove(i);
                }
            }
        }
    }

    @Override
    public void handleInput(KeyHandler keyHandler) {
        if (keyHandler.ispPressed()) {
            game.setGameState(new PauseState(game,this));
            keyHandler.setpPressed(false);
        }
        if (keyHandler.iscPressed()){
            game.setGameState(new CharacterState(game,this));
            keyHandler.setcPressed(false);
        }
    }
}

