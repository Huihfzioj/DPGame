package Core;

import Entities.Entity;
import Entities.Direction; // Ajout de l'import pour être explicite

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;

    }
    public void chektile(Entity entity){
        int entityLeftWorldX = entity.getworldX() + entity.solidArea.x;
        int entityRightWorldX = entity.getworldX() + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getworldY()+ entity.solidArea.y;
        int entityBottomWorldY = entity.getworldY() + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        // Utilisation du getter et des constantes de l'énumération
        switch(entity.getDirection()) {
            case UP:
                entityTopRow = (entityTopWorldY - entity.getSpeed())/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tiles[tileNum1].collision == true || gp.tileM.tiles[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case DOWN:
                entityBottomRow = (entityBottomWorldY + entity.getSpeed())/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tiles[tileNum1].collision == true || gp.tileM.tiles[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed())/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tiles[tileNum1].collision == true || gp.tileM.tiles[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed())/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tiles[tileNum1].collision == true || gp.tileM.tiles[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case NotSpecified:
                // Pas de mouvement, pas besoin de vérifier la collision
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.getworldX() + entity.solidArea.x;
                entity.solidArea.y = entity.getworldY() + entity.solidArea.y;

                // Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.getDirection()) {
                    case UP:
                        entity.solidArea.y -= entity.getSpeed();
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }

                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case DOWN:
                        entity.solidArea.y += entity.getSpeed();
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }

                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case LEFT:
                        entity.solidArea.x -= entity.getSpeed();
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }

                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case RIGHT:
                        entity.solidArea.x += entity.getSpeed();
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }

                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                }

                entity.solidArea.x = entity.getSolidAreaDefaultX();
                entity.solidArea.y = entity.getSolidAreaDefaultY();
                gp.obj[i].solidArea.x = gp.obj[i].SolidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].SolidAreaDefaultY;
            }
        }

        return index;
    }
    public int checkEntity(Entity entity, Entity[] target) {

        int index = 999;

        for (int i = 0; i < target.length; i++) {

            if (target[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.getworldX() + entity.solidArea.x;
                entity.solidArea.y = entity.getworldY() + entity.solidArea.y;

                // Get the object's solid area position
                target[i].solidArea.x = target[i].getworldX() + target[i].solidArea.x;
                target[i].solidArea.y = target[i].getworldY() + target[i].solidArea.y;

                switch (entity.getDirection()) {
                    case UP: entity.solidArea.y -= entity.speed; break;
                    case DOWN: entity.solidArea.y += entity.speed; break;
                    case LEFT: entity.solidArea.x -= entity.speed; break;
                    case RIGHT: entity.solidArea.x += entity.speed; break;
                }

                if (entity.solidArea.intersects(target[i].solidArea)) {
                    if (target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.getSolidAreaDefaultX();
                entity.solidArea.y = entity.getSolidAreaDefaultY();
                target[i].solidArea.x = target[i].SolidAreaDefaultX;
                target[i].solidArea.y = target[i].SolidAreaDefaultY;
            }
        }

        return index;
    }
    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        // Get entity's solid area position
        entity.solidArea.x = entity.getworldX() + entity.solidArea.x;
        entity.solidArea.y = entity.getworldY() + entity.solidArea.y;
        // Get the object's solid area position
        gp.player.solidArea.x = gp.player.getworldX() + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.getworldY() + gp.player.solidArea.y;

        switch(entity.getDirection()) {
            case UP: entity.solidArea.y -= entity.speed; break;
            case DOWN: entity.solidArea.y += entity.speed; break;
            case LEFT: entity.solidArea.x -= entity.speed; break;
            case RIGHT: entity.solidArea.x += entity.speed; break;
        }

        if(entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.getSolidAreaDefaultX();
        entity.solidArea.y = entity.getSolidAreaDefaultY();
        gp.player.solidArea.x = gp.player.SolidAreaDefaultX;
        gp.player.solidArea.y = gp.player.SolidAreaDefaultY;
        return contactPlayer;
    }

}
