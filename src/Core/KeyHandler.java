package Core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    private boolean upPressed, downPressed, leftPressed, rightPressed;
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

        if (code == KeyEvent.VK_RIGHT){
            rightPressed=true;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed=true;
        }
        if(code == KeyEvent.VK_UP){
            upPressed=true;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed=true;
        }
        if(code == KeyEvent.VK_P) {
            if (gamePanel.gameState == gamePanel.playState) {
                gamePanel.gameState = gamePanel.pauseState;
            } else if (gamePanel.gameState == gamePanel.pauseState){
                gamePanel.gameState=gamePanel.playState;
            }
        }
        //DEBUG
        if(code == KeyEvent.VK_T){
            if (checkDrawTime==false){
                checkDrawTime=true;
            } else if (checkDrawTime==true) {
                checkDrawTime=false;
                
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
}
