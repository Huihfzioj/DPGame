package Core;

import java.awt.*;


public interface GameState {
    void update();           // Met à jour le jeu dans cet état
    void draw(Graphics2D g2); // Dessine le jeu et l'UI
}
