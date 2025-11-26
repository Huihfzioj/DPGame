package Core.tile;

import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import Core.GamePanel;
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
        try{
            tiles[0] = new tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tile/grass.png"));

            tiles[1] = new tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tile/wall.png"));

            tiles[2] = new tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tile/water.png"));
            tiles[2].collision = true;
            tiles[3] = new tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tile/earth.png"));
            tiles[4] = new tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tile/tree.png"));
            tiles[4].collision = true;
            tiles[5] = new tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tile/sand.png"));

        } catch(Exception e){
            e.printStackTrace(); // Un seul bloc catch suffit
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
                    g2.drawImage(tiles[tileNum].image, screenx, screeny, gp.tileSize, gp.tileSize, null);}
                }

            }

        }
    }
}