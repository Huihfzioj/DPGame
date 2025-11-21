package Core.tile;
import javax.imageio.ImageIO;

import main.GamePanel;
public class TileManager {
    Core.GamePanel gp;
    tile[] = new tile[10];
    int mapTileNum[][];
    
    public TileManager(GamePanel gp){
        this.gp = gp;
    tile= new tile[10];
    mapTileNum=new int[gp.maxScreenCol][gp.maxScreenRow];
    getTileImage();
    loadMap("/maps/map01.txt");
    }
    public void getTileImage(){
        try{
            tile[0] = new tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tile/grass.png"));
            tile[1] = new tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tile/wall.png"));
            tile[2] = new tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tile/water.png"));
            
        }catch(Exception e){
            
        }catch(Exception e){
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void loadMap(String filePath){
        try{
            // on utilise InputStreamReader pour importer fichier texte
            
            InputStream is = getClass().getResourceAsStream("/maps/map01.txt");
            // on utilise BufferedReader pour lire le contenu du fichier texte
            BufferedReader br = new BufferedReader (new InputStreamReader(is));

            int col=0;
            int row=0;
            while(col<gp.maxScreenCol && row<gp.maxScreenRow){
                String line = br.readLine();
                while(col<gp.maxScreenCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row]=num;
                    col++;
                }
                if(col==gp.maxScreenCol){
                    col=0;
                    row++;
                }
                br.close();
        
            }

        }
        catch(Exception e){

        }

        
    }
    public void draw(Graphics2D g2){
        int col =0;
        int row =0;
        int x=0;
        int y=0;
        while(col<gp.maxScreenCol && row<gp.maxScreenRow){
            g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x+=gp.tileSize;
           while(col<gp.maxScreenCol && row<gp.maxScreenRow){
            int tileNum=mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x+=gp.tileSize;
           if(col==gp.maxScreenCol){
            col=0;
            x=0;
            row++;
            y+=gp.tileSize;
           }
        }
    }
    
}
