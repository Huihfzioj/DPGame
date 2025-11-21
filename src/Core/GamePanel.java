package Core;

import Core.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize=16; //default size of mc and npc in the game
    final int scale=3;
    public final int tileSize=originalTileSize*scale; //64*64 tile
    public final int maxScreenCol=16;
    public final int maxScreenRow=12;
    public final int screenWidth= tileSize*maxScreenCol;
    public final int screenHeight= tileSize*maxScreenRow;
    TileManager tileM = new TileManager(this);

    final int FPS = 60;

    int playerX=100;
    int playerY=100;
    int speed=4;
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

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
        double drawInterval = (double) 1000000000 /FPS; //1 second over FPS -> we draw the screen every 0.016 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            update(); //update infos
            repaint(); //redraw the screen with the new information

            try {
                double remainingTime = (nextDrawTime - System.nanoTime())/1000000; // need to convert to milliseconds to be used in the sleep function
                if(remainingTime< 0){
                    remainingTime=0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

        Graphics2D graphics2D = (Graphics2D) graphics;
        tileM.draw((Graphics2D) graphics);
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(playerX,playerY,tileSize,tileSize);
        graphics2D.dispose(); //release resources(memory) if unneeded
    }
}