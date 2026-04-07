package Core.tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import Core.Events.DamagePitEvent;
import Core.Events.HealingPoolEvent;
import Core.Events.TeleportingEvent;
import Core.GamePanel;
import Core.UtilityTool;

import java.awt.Graphics2D;
import javax.swing.*;
import java.awt.*;

public class TileManager {
    GamePanel gp; // Retirer "Core." car l'import existe déjà
    public tile[] tiles; // Corriger la déclaration du tableau
    public int[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tiles = new tile[10]; // Initialiser correctement
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt"); // Chemin corrigé
    }

    public void getTileImage(){
        setup(0,"grass",false);
        setup(1,"wall",true);
        setup(2,"water",true);
        setup(3,"earth",false);
        setup(4,"tree",true);
        setup(5,"sand",false);
        // EVENT TILES
        setup(6,"heal",false);
        setup(7,"teleport",false);
        setup(8,"pit",false);
    }
    public void  setup(int index, String imagePath,boolean collision){
        UtilityTool uTool=new UtilityTool();
        try{
            tiles[index] = new tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tile/" + imagePath+".png"));
            tiles[index].image = uTool.scaleImage(tiles[index].image,gp.tileSize,gp.tileSize);
            tiles[index].collision=collision;


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                System.out.println("Fichier de carte non trouvé: " + filePath);
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            String line;

            while((line = br.readLine()) != null && row < gp.maxWorldRow){
                String[] numbers = line.split(" ");
                for(int col = 0; col < gp.maxWorldCol && col < numbers.length; col++){
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    switch (num) {
                        case 6 -> gp.eventHandler.addEvent(col, row, new HealingPoolEvent());
                        case 7 -> gp.eventHandler.addEvent(col, row, new TeleportingEvent());
                        case 8 -> gp.eventHandler.addEvent(col, row, new DamagePitEvent());
                    }
                }
                row++;
            }
            br.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        int Worldcol = 0;
        int Worldrow = 0;


        for(Worldrow = 0; Worldrow < gp.maxWorldRow; Worldrow++){
            for(Worldcol = 0; Worldcol < gp.maxWorldCol; Worldcol++){
                int tileNum = mapTileNum[Worldcol][Worldrow];
                if(tileNum >= 0 && tileNum < tiles.length && tiles[tileNum] != null){
                    int worldx = Worldcol * gp.tileSize;
                    int worldy = Worldrow * gp.tileSize;
                    int screenx = worldx - gp.player.getworldX() + gp.player.screenx;
                    int screeny = worldy - gp.player.getworldY() + gp.player.screeny;
                    if ( worldx + gp.tileSize> gp.player.getworldX() - gp.player.screenx &&
                            worldx - gp.tileSize < gp.player.getworldX() + gp.player.screenx  &&
                            worldy + gp.tileSize > gp.player.getworldY() - gp.player.screeny &&
                            worldy - gp.tileSize< gp.player.getworldY() + gp.player.screeny
                          ){
                    g2.drawImage(tiles[tileNum].image, screenx, screeny,null);}
                }

            }

        }
    }
}