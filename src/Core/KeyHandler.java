package Core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    private boolean upPressed, downPressed, leftPressed, rightPressed,enterPressed,pPressed,spacePressed;
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
}
