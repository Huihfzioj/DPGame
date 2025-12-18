package Core;

import Core.Events.DamagePitEvent;
import Core.Events.EventHandler;
import Core.GameStates.GameState;
import Core.GameStates.MenuState;
import Core.tile.TileManager;
import Entities.Player;
import object.SuperObject;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    public final static int originalTileSize=16; //default size of mc and npc in the game
    public final static int scale=3;
    public final static int tileSize=originalTileSize*scale; //64*64 tile
    public final int maxScreenCol=16;
    public final int maxScreenRow=12;
    public final int screenWidth= tileSize*maxScreenCol;
    public final int screenHeight= tileSize*maxScreenRow;
    public TileManager tileM = new TileManager(this);
    public UI ui=new UI(this) ;
  // world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    final int FPS = 60;
    Sound sound = new Sound();
    KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker ccheker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public Player player = new Player(this,keyHandler);
    public SuperObject obj[] = new SuperObject[10];
    public EventHandler eventHandler = new EventHandler(this);

    public GameState gameState;
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler); //Listen to key user input
        this.setFocusable(true); //Make the panel focus on getting key input
        this.gameState=new MenuState(this);
        setUpEvents();
    }
    public void setupGame (){

        aSetter.setObject();
        playMusic(0);
    }

    private void setUpEvents() {
        // Example of adding multiple events using strategy pattern
        eventHandler.addEvent(24, 21, new DamagePitEvent());
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

    public void setGameState(GameState newState) {
        this.gameState = newState;
    }

    public void update() {
        if (gameState !=null) {
            gameState.handleInput(keyHandler);
            gameState.update();
        }
    }

    //function to draw relevant components during the update
    public void paintComponent (Graphics g) {

        super.paintComponent (g);
        Graphics2D g2 = (Graphics2D)g;
        //DEBUG

        long drawStart = 0;
        if(keyHandler.checkDrawTime==true){
            drawStart=System.nanoTime();
        }
        if(gameState != null) {
            gameState.draw(g2);
        }
        //DEBUG
        if(keyHandler.checkDrawTime==true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: "+passed,10,400);
            System.out.println("Draw Time: "+passed);
        }


        g2.dispose ();
    }
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}