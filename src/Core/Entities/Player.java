package Core.Entities;

import Core.GamePanel;
import Core.GameStates.DialogueState;
import Core.GameStates.GameState;
import Core.KeyHandler;
import object.OBJ_Key;
import object.OBJ_Shield;
import object.OBJ_Sword;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;
    BufferedImage idle,right,left,right1,left1,up,up1,down,down1;
    // La variable 'direction' locale a été SUPPRIMÉE.
    int spriteCounter=0;
    int spriteNumber=1;
    public final int screenx;
    public final int screeny;
    //public int haskey = 0;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gamePanel,KeyHandler keyHandler){
        this.gamePanel=gamePanel;
        this.keyHandler=keyHandler;
        screenx = gamePanel.screenWidth/2 - (gamePanel.tileSize/2);
        screeny = gamePanel.screenHeight/2 - (gamePanel.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 6;
        SolidAreaDefaultX = solidArea.x;
        SolidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues(){
        this.setworldX(gamePanel.tileSize *23);
        this.setworldY(gamePanel.tileSize *21);
        this.setSpeed(4);
        // Initialisation de la direction par défaut
        this.setDirection(Direction.DOWN);
        //PLAYER STATUS
        maxLife =6;
        life=maxLife;
        level = 1;
        strength = 1; // The more strength the player has the more damage he inflicts
        dexterity = 1; // The more dexterity the player has the less damage he receives
        exp = 0;
        nextLevelExp = 5;
        memoryFragments = 0;
        currentWeapon = new OBJ_Sword(gamePanel);
        currentShield = new OBJ_Shield(gamePanel);
        attack = getAttack();
        defense = getDefense();
    }

    public void setItems(){
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gamePanel));
    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return strength * currentWeapon.attackValue;
    }
    public int getDefense() {
        return dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage(){


        idle = setup("Ghost_idle");
        right = setup("Ghost_right");
        left = setup("Ghost_left");
        right1 = setup("Ghost_right (1)");
        left1 = setup("Ghost_left (1)");
        up = setup("Ghost_up");
        up1 = setup("Ghost_up (1)");
        down = setup("Ghost_down");
        down1 = setup("Ghost_down (1)");

    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("Ghost_upAttack");
        attackUp2 = setup("Ghost_upAttack");
        attackLeft1 = setup("Ghost_leftAttack");
        attackLeft2 = setup("Ghost_leftAttack1");
        attackRight1 = setup("Ghost_rightAttack");
        attackRight2 = setup("Ghost_rightAttack1");
        attackDown1 = setup("Ghost_downAttack");
        attackDown2 = setup("Ghost_downAttack");
    }

    public void update(){
        if (keyHandler.isSpacePressed() && !attacking) {
            attacking = true;
            spriteCounter = 0;
            keyHandler.setSpacePressed(false);
        }

        if (attacking) {
            attacking();
        }

        // ALWAYS read movement input
        if (keyHandler.getUpPressed()){
            setDirection(Direction.UP);
        }
        else if (keyHandler.getDownPressed()){
            setDirection(Direction.DOWN);
        }
        else if (keyHandler.getLeftPressed()){
            setDirection(Direction.LEFT);
        }
        else if (keyHandler.getRightPressed()){
            setDirection(Direction.RIGHT);
        }
        else {
            setDirection(Direction.NotSpecified);
        }

        // check tile collision
        collisionOn = false;
        gamePanel.ccheker.chektile(this);

        // check object collision
        int objectindexe = gamePanel.ccheker.checkObject(this,true);

        // CHECK MONSTER COLLISION - ICI SEULEMENT
        int enemiesIndex = gamePanel.ccheker.checkEntity(this, gamePanel.enemies);
        ContactEnemie(enemiesIndex);

        pickUpObject(objectindexe);

        // if collision is false player can move
        if (!collisionOn) {
            // Utilisation du getter pour lire la direction
            switch(this.getDirection()) {
                case UP:
                    this.setworldY(this.getworldY()-this.getSpeed());
                    break;
                case DOWN:
                    this.setworldY(this.getworldY()+this.getSpeed());
                    break;
                case LEFT:
                    this.setworldX(this.getworldX()-this.getSpeed());
                    break;
                case RIGHT:
                    this.setworldX(this.getworldX()+this.getSpeed());
                    break;
                case NotSpecified:
                    break; // Ne bouge pas
            }
        }

        // DÉPLACER LE COMPTEUR ICI - TOUJOURS ACTIF, PAS SEULEMENT EN CAS DE COLLISION
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 60) { // 60 frames = 1 seconde (à 60 FPS)
                invincible = false;
                invincibleCounter = 0;
            }
        }

        gamePanel.eventHandler.checkEvent();

        spriteCounter++;
        if (spriteCounter > 50){
            if (spriteNumber == 1){
                spriteNumber = 2;
            }
            else if (spriteNumber == 2){
                spriteNumber = 1;
            }
            // Correction : Reset du spriteCounter après le cycle
            else{
                spriteCounter = 0;
            }
        }
    }

    private void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5){
            spriteNumber = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25){
            spriteNumber = 2;

            // Save the current worldX, worldY, solidArea
            int currentWorldX = getworldX();
            int currentWorldY = getworldY();
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            switch (getDirection()){
                case UP -> setworldY(getworldY()-attackArea.height);
                case DOWN -> setworldY(getworldY()+attackArea.height);
                case LEFT -> setworldX(getworldX()-attackArea.width);
                case RIGHT -> setworldX(getworldX()+attackArea.width);
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int enemyIndex = gamePanel.ccheker.checkEntity(this, gamePanel.enemies);
            damageEnemy(enemyIndex);

            setworldX(currentWorldX);
            setworldY(currentWorldY);
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25){
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void damageEnemy(int enemyIndex) {
        if (enemyIndex != 999){
            if (!gamePanel.enemies[enemyIndex].invincible){
                gamePanel.playSE(5);
                int damage = attack - gamePanel.enemies[enemyIndex].defense;
                if (damage < 0){
                    damage = 0;
                }
                gamePanel.enemies[enemyIndex].life -= damage;
                gamePanel.ui.addMessage(damage + " damage !");
                gamePanel.enemies[enemyIndex].invincible = true;
                gamePanel.enemies[enemyIndex].damageReaction(gamePanel);
                if (gamePanel.enemies[enemyIndex].life <= 0){
                    gamePanel.enemies[enemyIndex].dying = true;
                    gamePanel.ui.addMessage("Killed the "+ gamePanel.enemies[enemyIndex].name+"!");
                    gamePanel.ui.addMessage( "Exp "+ gamePanel.enemies[enemyIndex].exp+"!");
                    exp += gamePanel.enemies[enemyIndex].exp;
                    checkLevelUp();
                }
            }
        }
    }

    private void checkLevelUp() {
        if (exp >= nextLevelExp){
            level++;
            nextLevelExp *=2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gamePanel.playSE(7);
            GameState previous = gamePanel.gameState;
            gamePanel.ui.message = "You are level " + level + " now!\n";
            gamePanel.setGameState(new DialogueState(gamePanel,previous));
        }
    }


    public void ContactEnemie(int i) {
        if(i != 999) {
            Entity enemy = gamePanel.enemies[i];

            // Ignore damage if enemy is invincible
            if (enemy.invincible) {
                return;
            }

            if (!invincible) {
                gamePanel.playSE(6);
                int damage = gamePanel.enemies[i].attack - defense;
                if (damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
                invincibleCounter = 0;
            }
        }
    }
    public void pickUpObject (int i) {
        if (i != 999){
            String text;
            if(inventory.size() != maxInventorySize){
                inventory.add(gamePanel.obj[i]);
                gamePanel.playSE(1);
                text = "Got a " + gamePanel.obj[i].name + "!";
            }
            else{
                text = "You cannot carry any more !";
            }
            gamePanel.ui.addMessage(text);
            gamePanel.obj[i]=null;
        }
    }

    public void draw(Graphics2D graphics2D){
        BufferedImage image=null;

        // Utilisation du getter pour lire la direction
        switch(this.getDirection()){
            case UP :
                if (!attacking){
                    if (spriteNumber == 1) image=up;
                    if (spriteNumber == 2) image=up1;
                }
                else {
                    if (spriteNumber == 1) image=attackUp1;
                    if (spriteNumber == 2) image=attackUp2;
                }
                break;
            case DOWN :
                if (!attacking){
                    if (spriteNumber == 1) image=down;
                    if (spriteNumber == 2) image=down1;
                }
                else {
                    if (spriteNumber == 1) image=attackDown1;
                    if (spriteNumber == 2) image=attackDown2;
                }
                break;
            case LEFT:
                if (!attacking) {
                    if (spriteNumber == 1) image=left;
                    if (spriteNumber == 2) image=left1;
                }
                else {
                    if (spriteNumber == 1) image=attackLeft1;
                    if (spriteNumber == 2) image=attackLeft2;
                }
                break;
            case RIGHT:
                if (!attacking) {
                    if (spriteNumber == 1) image=right;
                    if (spriteNumber == 2) image=right1;
                }
                else {
                    if (spriteNumber == 1) image=attackRight1;
                    if (spriteNumber == 2) image=attackRight2;
                }
                break;
            default:
                image=idle;
        }
        if(invincible) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        graphics2D.drawImage(image,screenx,screeny,null);

      // Reset alpha
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
       // graphics2D.drawImage(image,screenx,screeny,null);
        // DEBUG
       // graphics2D.setFont(new Font("Arial", Font.PLAIN, 26));
       // graphics2D.setColor(Color.white);
       // graphics2D.drawString("Invincible:" + invincibleCounter, 10, 400);
    }
}