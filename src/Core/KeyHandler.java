package Core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    private boolean upPressed, downPressed, leftPressed, rightPressed,enterPressed,pPressed,spacePressed,cPressed,zPressed,sPressed,qPressed,dPressed;
    //DEBUG
    boolean checkDrawTime=false;

    public KeyHandler(GamePanel gamePanel){
        this.gamePanel=gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
            case KeyEvent.VK_ENTER -> enterPressed = true;
            case KeyEvent.VK_P -> pPressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;
            case KeyEvent.VK_C -> cPressed = true;
            case KeyEvent.VK_Z -> zPressed = true;
            case KeyEvent.VK_Q -> qPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_D -> dPressed = true;
            case KeyEvent.VK_T -> {
                if (checkDrawTime==false){
                    checkDrawTime=true;
                } else if (checkDrawTime==true) {
                    checkDrawTime=false;

                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_RIGHT){
            rightPressed=false;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed=false;
        }
        if(code == KeyEvent.VK_UP){
            upPressed=false;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed=false;
        }
        if(code == KeyEvent.VK_P){
            pPressed=false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed=false;
        }
    }

    public Boolean getUpPressed(){
        return  upPressed;
    }

    public Boolean getDownPressed(){
        return  downPressed;
    }

    public Boolean getLeftPressed(){
        return  leftPressed;
    }

    public Boolean getRightPressed(){
        return  rightPressed;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public void setEnterPressed(boolean enterPressed) {
        this.enterPressed = enterPressed;
    }

    public boolean ispPressed() {
        return pPressed;
    }

    public void setpPressed(boolean pPressed) {
        this.pPressed = pPressed;
    }

    public void setUpPressed(boolean upPressed){
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed){
        this.downPressed = downPressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
    }

    public boolean iscPressed() {
        return cPressed;
    }

    public void setcPressed(boolean cPressed) {
        this.cPressed = cPressed;
    }

    public boolean iszPressed() {
        return zPressed;
    }

    public void setzPressed(boolean zPressed) {
        this.zPressed = zPressed;
    }

    public boolean issPressed() {
        return sPressed;
    }

    public void setsPressed(boolean sPressed) {
        this.sPressed = sPressed;
    }

    public boolean isqPressed() {
        return qPressed;
    }

    public void setqPressed(boolean qPressed) {
        this.qPressed = qPressed;
    }

    public boolean isdPressed() {
        return dPressed;
    }

    public void setdPressed(boolean dPressed) {
        this.dPressed = dPressed;
    }
}
