package Core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Core.Entities.Entity;
import Core.GameStates.*;
import Core.World.World;
import Core.World.WorldComponent;
import Core.World.Zone;
import object.OBJ_Heart;
import object.SuperObject;

import javax.imageio.ImageIO;

public class UI {
    GamePanel gamePanel;
    //déclaration de la police
    Font font;
    //BufferedImage keyImage;
    public boolean messageOn = false ;
    public String message = "";
    public ArrayList<String> messages = new ArrayList<>();
    public ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat dformat = new DecimalFormat("#0.00");
    BufferedImage heart_full,heart_half,heart_blank;
    Graphics2D graphics2D;
    public int uiMenuIndex = 0;
    private String zoneTransitionText = null;
    private int zoneTransitionTimer = 0;


    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        try {
            InputStream is= getClass().getResourceAsStream("/Font/x12y16pxMaruMonica.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT,is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //OBJ_Key key = new OBJ_Key(gamePanel);
        //keyImage=key.image;

        //Create Hud object
        SuperObject heart =new OBJ_Heart(gamePanel);
        heart_full=heart.image;
        heart_half=heart.image2;
        heart_blank=heart.image3;

    }

    public void addMessage(String text) {
        messages.add(text);
        messageCounter.add(0);
    }
    //afficher du texte
    public void draw(Graphics2D g2d) {
        this.graphics2D = g2d;
        graphics2D.setFont(font);
        graphics2D.setColor(Color.black);
        if (gamePanel.gameState instanceof MenuState) {
            drawMenuScreen(g2d);
        } else if (gamePanel.gameState instanceof PauseState) {
            drawPauseScreen(g2d);
        }
        if (gamePanel.gameState instanceof PlayState ||
                gamePanel.gameState instanceof PauseState ||
                gamePanel.gameState instanceof CharacterState ||
                gamePanel.gameState instanceof DialogueState) {
            drawPlayerLife();
        }
        if (gamePanel.gameState instanceof PlayState) {
            drawWorldInfo();
            drawZoneTransition();

        }
    }
    public void drawPauseScreen(Graphics2D g2){
        drawPlayerLife();
        g2.setFont(font.deriveFont(Font.BOLD, 50F));
        String text = "Game Paused";
        int x = getXForCenteredText(g2, text);
        int y = gamePanel.screenHeight / 2;

        // Draw text shadow for better visibility
        g2.setColor(Color.white);
        g2.drawString(text, x + 2, y + 2);

        // Draw main text
        g2.setColor(Color.black);
        g2.drawString(text, x, y);

        // Add instruction for resuming
        g2.setFont(font.deriveFont(Font.PLAIN, 20F));
        String instruction = "Press P to resume";
        x = getXForCenteredText(g2, instruction);
        y += 40;  // Position below the main text
        g2.setColor(Color.yellow);
        g2.drawString(instruction, x, y);
    }

    private void drawPlayerLife() {
        int x = GamePanel.tileSize/2;
        int y = GamePanel.tileSize/2;
        int i = 0;

        // Draw Blank Heart
        while (i < gamePanel.player.maxLife/2){
            graphics2D.drawImage(heart_blank,x,y,null);
            i++;
            x += GamePanel.tileSize;
        }

        // Reset
        x = GamePanel.tileSize/2;
        y = GamePanel.tileSize/2;
        i = 0;

        while(i < gamePanel.player.life){
            graphics2D.drawImage(heart_half,x,y,null);
            i++;
            if (i < gamePanel.player.life){
                graphics2D.drawImage(heart_full,x,y,null);
            }
            i++;
            x += GamePanel.tileSize;
        }
    }

    public int getXForCenteredText(Graphics2D g2, String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;
        return x;
    }

    public int getXforAlignToRightText(Graphics2D g2, String text, int tailX){
        if (text == null) {
            text = "-"; // fallback
        }
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
    public void drawMenuScreen(Graphics2D g2) {

        // Draw title
        g2.setFont(font.deriveFont(Font.BOLD,70F));
        String title = "Wraith's Return";
        int x = getXForCenteredText(g2, title);
        int y = gamePanel.screenHeight / 5;
        g2.setColor(Color.gray);
        g2.drawString(title,x+2,y+2);
        g2.setColor(Color.white);
        g2.drawString(title, x, y);
        x= gamePanel.screenWidth/2-55;
        y+=30;
        try{
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Player/Ghost_idle.png"));
            g2.drawImage(image,x,y,GamePanel.tileSize*2,GamePanel.tileSize*2,null);
        }catch (Exception e){
            e.printStackTrace();
        }

        g2.setFont(font.deriveFont(Font.BOLD,30F));
        String[] options = {"New Game", "Load Game", "Quit"};
        int startY = y + 140;
        int spacing = 50;

        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            int optionX = getXForCenteredText(g2,text);
            int optionY = startY + i * spacing;

            // Highlight selected option (optional)
            if (i == uiMenuIndex) { // uiMenuIndex: tracks selected menu item
                g2.setColor(Color.yellow);
            } else {
                g2.setColor(Color.white);
            }
            g2.drawString(text, optionX, optionY);
        }
        g2.setFont(font.deriveFont(Font.TRUETYPE_FONT,30F));
        g2.setColor(Color.lightGray);
        String instruction = "Use ↑ ↓ to navigate and Enter to select";
        g2.drawString(instruction, 20, gamePanel.screenHeight - 40);
    }
    public void drawSubWindow(Graphics2D g2,int x, int y, int width, int height){
        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }
    private void drawWorldInfo() {
        World currentWorld = gamePanel.worldManager.getCurrentWorld();
        Zone currentZone = gamePanel.worldManager.getCurrentZone();

        if (currentWorld != null && currentZone != null) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 16F));

            // Draw world name and zone name
            String worldText = "World: " + currentWorld.getName();
            String zoneText = "Zone: " + currentZone.getName();

            int x = gamePanel.screenWidth - 200;
            int y = 30;

            graphics2D.drawString(worldText, x, y);
            graphics2D.drawString(zoneText, x, y + 25);

            // Draw memory info
            String memoryText = "Memories: " + currentZone.getCollectedMemories() +
                    "/" + currentZone.getRequiredMemories();
            graphics2D.drawString(memoryText, x, y + 50);

            // Draw enemy info
            int aliveEnemies = 0;
            for (Entity enemy : currentZone.getEnemies()) {
                if (enemy != null && enemy.isAlive()) {
                    aliveEnemies++;
                }
            }
            String enemyText = "Enemies: " + aliveEnemies;
            graphics2D.drawString(enemyText, x, y + 75);

            // Draw progress
            if (currentWorld.isUnlocked()) {
                String progress = "Progress: " + getWorldProgress(currentWorld) + "%";
                graphics2D.drawString(progress, x, y + 100);
            }

            // Debug: Show if zone is complete
            if (currentZone.isComplete()) {
                graphics2D.setColor(Color.GREEN);
                graphics2D.drawString("ZONE COMPLETE!", x, y + 125);
            }
        } else {
            // Debug: Show why world/zone is null
            graphics2D.setColor(Color.RED);
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 16F));
            graphics2D.drawString("World: " + (currentWorld != null ? currentWorld.getName() : "NULL"),
                    gamePanel.screenWidth - 200, 30);
            graphics2D.drawString("Zone: " + (currentZone != null ? currentZone.getName() : "NULL"),
                    gamePanel.screenWidth - 200, 50);
        }
    }

    private int getWorldProgress(World world) {
        if (world == null) return 0;

        int totalZones = 0;
        int completedZones = 0;

        for (int i = 0; i < world.components.size(); i++) {
            WorldComponent component = world.getChild(i);
            if (component instanceof Zone zone) {
                totalZones++;
                if (zone.isComplete()) {
                    completedZones++;
                }
            }
        }

        if (totalZones == 0) return 0;
        int progress = (completedZones * 100) / totalZones;
        return progress;
    }

    private int countAliveEnemies(Zone zone) {
        int alive = 0;
        for (Entity enemy : zone.getEnemies()) {
            if (enemy != null && enemy.isAlive()) {
                alive++;
            }
        }
        return alive;
    }

    public void onZoneChanged(Zone newZone) {
        zoneTransitionText = "Entering: " + newZone.getName();
        zoneTransitionTimer = 120; // 2 seconds at 60 FPS
    }

    private void drawZoneTransition() {
        if (zoneTransitionTimer > 0 && zoneTransitionText != null) {
            zoneTransitionTimer--;

            graphics2D.setFont(font.deriveFont(Font.BOLD, 32F));
            graphics2D.setColor(new Color(0, 0, 0, 180));
            graphics2D.fillRoundRect(
                    gamePanel.screenWidth / 4,
                    gamePanel.screenHeight / 2 - 35,
                    gamePanel.screenWidth / 2,
                    70,
                    20, 20
            );

            graphics2D.setColor(Color.WHITE);
            int x = getXForCenteredText(graphics2D, zoneTransitionText);
            int y = gamePanel.screenHeight / 2 + 10;
            graphics2D.drawString(zoneTransitionText, x, y);

            if (zoneTransitionTimer == 0) {
                zoneTransitionText = null;
            }
        }
    }

}
