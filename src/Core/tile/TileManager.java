package Core.tile;
import main.GamePanel;
public class TileManager {
    GamePanel gp;
    tile[] = new tile[10];
    
    public TileManager(GamePanel gp){
        this.gp = gp;
    tile= new tile[10];
    getTileImage();
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
    public void draw(Graphics2D g2){
        g2.drawImage(tile[0].image,0,0,gp.tileSize,gp.tileSize,null);
        
    }
    
}
