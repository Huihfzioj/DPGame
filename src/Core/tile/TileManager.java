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
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("/maps/map01.txt"); // Chemin corrigé
    }

    public void getTileImage(){
        try{
            tiles[0] = new tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tile/grass.png"));

            tiles[1] = new tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tile/wall.png"));

            tiles[2] = new tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tile/water.png"));

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

            while((line = br.readLine()) != null && row < gp.maxScreenRow){
                String[] numbers = line.split(" ");
                for(int col = 0; col < gp.maxScreenCol && col < numbers.length; col++){
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
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        for(row = 0; row < gp.maxScreenRow; row++){
            for(col = 0; col < gp.maxScreenCol; col++){
                int tileNum = mapTileNum[col][row];
                if(tileNum >= 0 && tileNum < tiles.length && tiles[tileNum] != null){
                    g2.drawImage(tiles[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
                }
                x += gp.tileSize;
            }
            x = 0;
            y += gp.tileSize;
        }
    }
}