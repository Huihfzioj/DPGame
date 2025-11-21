package Core;

import javax.swing.*;

import Core.tile.TileManager;

import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize=16; //default size of mc and npc in the game
    final int scale=3;
    public final int tileSize=originalTileSize*scale; //64*64 tile
    public final int maxScreenCol=16;
    public final int maxScreenRow=12;
    public final int screenWidth= tileSize*maxScreenCol;
    public final int screenHeight= tileSize*maxScreenRow;

    int playerX=100;
    int playerY=100;
    int speed=4;
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    TileManager tileM= new TileManager(this);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler); //Listen to key user input
        this.setFocusable(true); //Make the panel focus on getting key input
    }

    public void startGameThread(){
        gameThread=new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while(gameThread != null){

            long currentTime = System.nanoTime();

            update(); //update infos
            repaint(); //redraw the screen with the new information
        }
    }

    //function to update information for the scene such as player positions
    public void update(){
        if (keyHandler.getUpPressed()){
            playerY-=speed;
        }
        else if (keyHandler.getDownPressed()){
            playerY+=speed;
        }
        else if (keyHandler.getLeftPressed()){
            playerX-=speed;
        }
        else if (keyHandler.getRightPressed()){
            playerX+=speed;
        }
    }

    //function to draw relevant components during the update
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        tileM.draw((Graphics2D)graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(playerX,playerY,tileSize,tileSize);
        graphics2D.dispose(); //release resources(memory) if unneeded
    }
}
