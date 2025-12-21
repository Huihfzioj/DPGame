package Core.GameStates;

import java.awt.*;

import Core.GamePanel;
import Core.KeyHandler;
import Core.Entities.Entity;

public class PlayState implements GameState {
    private GamePanel game;

    public PlayState(GamePanel game) {
        this.game = game;
    }

    @Override
    public void update() {
        for (int i = 0; i < game.enemies.length; i++){
            if (game.enemies[i] != null ){
                if (game.enemies[i].isAlive() && !game.enemies[i].isDying()){
                    game.enemies[i].update();
                }
                if (!game.enemies[i].isAlive()){
                    game.enemies[i] = null;
                }
            }
        }
        game.player.update();       // délégué au player
        // objets, collisions, etc.
        if (game.player.life <= 0){
            game.gameState = new GameOver(game,this);
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

