package Core;

import Core.Enemies.RegularGrimFactory;
import Core.Enemies.SkeletonFactory;
import Core.Entities.Entity;
import Core.World.Zone;
import object.*;
import java.util.logging.Logger;

import static Core.GameLogger.LOGGER;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AssetSetter {
    GamePanel gp;
    private Random random;
    private Set<String> occupiedPositions; // Track all occupied positions

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
        this.random = new Random();
        this.occupiedPositions = new HashSet<>();
    }

    public void setObject() {
        Zone currentZone = gp.worldManager.getCurrentZone();
        if (currentZone != null) {
            List<SuperObject> objects = currentZone.getObjects();
            for (int i = 0; i < Math.min(objects.size(), gp.obj.length); i++) {
                gp.obj[i] = objects.get(i);
            }
        }
    }

    public void setEnemy() {
        Zone currentZone = gp.worldManager.getCurrentZone();
        if (currentZone != null) {
            List<Entity> enemies = currentZone.getEnemies();

            gp.enemies = new Entity[20];

            for (int i = 0; i < Math.min(enemies.size(), gp.enemies.length); i++) {
                gp.enemies[i] = enemies.get(i);
            }

            System.out.println("Loaded " + enemies.size() + " enemies from zone: " + currentZone.getName());
        }
    }

    // Helper method to check if a tile is solid (collision)
    private boolean isSolidTile(int worldX, int worldY) {
        // Convert world coordinates to tile coordinates
        int tileCol = worldX / GamePanel.tileSize;
        int tileRow = worldY / GamePanel.tileSize;

        // Check if coordinates are within bounds
        if (tileCol < 0 || tileCol >= gp.maxWorldCol || tileRow < 0 || tileRow >= gp.maxWorldRow) {
            return true; // Out of bounds counts as solid
        }

        // Get tile number at this position
        int tileNum = gp.tileM.mapTileNum[tileCol][tileRow];

        // Check if tile exists and has collision
        if (tileNum >= 0 && tileNum < gp.tileM.tiles.length && gp.tileM.tiles[tileNum] != null) {
            return gp.tileM.tiles[tileNum].collision;
        }

        return false; // Default to non-solid if tile doesn't exist
    }

    // Helper method to check if a position is occupied (including player position)
    private boolean isPositionOccupied(int worldX, int worldY) {
        // Check player position
        int playerX = gp.player.getworldX();
        int playerY = gp.player.getworldY();
        int playerDistanceX = Math.abs(worldX - playerX);
        int playerDistanceY = Math.abs(worldY - playerY);

        // Don't place entities too close to player (within 3 tiles)
        if (playerDistanceX < GamePanel.tileSize * 3 && playerDistanceY < GamePanel.tileSize * 3) {
            return true;
        }

        // Check if position is already marked as occupied
        String positionKey = worldX + "," + worldY;
        return occupiedPositions.contains(positionKey);
    }

    // Helper to check event tiles (pits, healing pools, teleports)
    private boolean isEventTile(int worldX, int worldY) {
        // Convert world coordinates to tile coordinates
        int tileCol = worldX / GamePanel.tileSize;
        int tileRow = worldY / GamePanel.tileSize;

        // Check if coordinates are within bounds
        if (tileCol < 0 || tileCol >= gp.maxWorldCol || tileRow < 0 || tileRow >= gp.maxWorldRow) {
            return false;
        }

        // Check for event tiles (6: heal, 7: teleport, 8: pit)
        int tileNum = gp.tileM.mapTileNum[tileCol][tileRow];
        return tileNum == 6 || tileNum == 7 || tileNum == 8;
    }

    // Find a valid position for an entity
    private int[] findValidPosition(int minCol, int maxCol, int minRow, int maxRow, int attempts) {
        for (int i = 0; i < attempts; i++) {
            int col = random.nextInt(maxCol - minCol) + minCol;
            int row = random.nextInt(maxRow - minRow) + minRow;

            int worldX = col * GamePanel.tileSize;
            int worldY = row * GamePanel.tileSize;

            // Check if position is valid
            if (!isSolidTile(worldX, worldY) &&
                    !isEventTile(worldX, worldY) &&
                    !isPositionOccupied(worldX, worldY)) {

                // Mark this position as occupied
                String positionKey = worldX + "," + worldY;
                occupiedPositions.add(positionKey);

                // Also mark surrounding positions to prevent crowding
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nearX = worldX + (dx * GamePanel.tileSize);
                        int nearY = worldY + (dy * GamePanel.tileSize);
                        String nearKey = nearX + "," + nearY;
                        occupiedPositions.add(nearKey);
                    }
                }

                return new int[]{worldX, worldY};
            }
        }

        // If no valid position found after attempts, return null
        System.out.println("Warning: Could not find valid position after " + attempts + " attempts");
        return null;
    }

    public void populateZone(Zone zone) {
        // Clear the zone and reset occupied positions
        zone.getEnemies().clear();
        zone.getObjects().clear();
        occupiedPositions.clear();

        // Always mark player's starting position as occupied
        int playerX = gp.player.getworldX();
        int playerY = gp.player.getworldY();
        String playerKey = playerX + "," + playerY;
        occupiedPositions.add(playerKey);

        // Mark area around player as occupied
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                int nearX = playerX + (dx * GamePanel.tileSize);
                int nearY = playerY + (dy * GamePanel.tileSize);
                String nearKey = nearX + "," + nearY;
                occupiedPositions.add(nearKey);
            }
        }

        LOGGER.info("[FACTORY] AssetSetter using factories to spawn enemies");
        RegularGrimFactory grimFactory = new RegularGrimFactory();
        SkeletonFactory skeletonFactory = new SkeletonFactory();

        if (zone.getName().equals("Forest Clearing")) {
            populateForestClearing(zone, grimFactory, skeletonFactory);
        } else if (zone.getName().equals("Deep Forest")) {
            populateDeepForest(zone, grimFactory, skeletonFactory);
        } else if (zone.getName().equals("Ancient Ruins")) {
            populateAncientRuins(zone, grimFactory, skeletonFactory);
        }
    }

    private void populateForestClearing(Zone zone, RegularGrimFactory grimFactory, SkeletonFactory skeletonFactory) {
        // Define safe area boundaries for Forest Clearing
        // Use smaller boundaries to ensure walkable areas
        int minCol = 20;
        int maxCol = 35;
        int minRow = 15;
        int maxRow = 25;

        // Debug: Show collision map for this area
        //debugCollisionMapArea(minCol, maxCol, minRow, maxRow, "Forest Clearing");

        // Add enemies with valid positions
        for (int i = 0; i < 2; i++) {
            int[] position = findValidPosition(minCol, maxCol, minRow, maxRow, 50);
            if (position != null) {
                Entity enemy;
                if (i % 2 == 0) {
                    enemy = grimFactory.createEnemy(gp);
                } else {
                    enemy = skeletonFactory.createEnemy(gp);
                }
                enemy.setworldX(position[0]);
                enemy.setworldY(position[1]);
                zone.addEnemy(enemy);
            }
        }

        // Add memory fragment
        int[] memoryPos = findValidPosition(minCol, maxCol, minRow, maxRow, 50);
        if (memoryPos != null) {
            OBJ_Memory memory1 = new OBJ_Memory(gp);
            memory1.worldX = memoryPos[0];
            memory1.worldY = memoryPos[1];
            zone.addObject(memory1);
        }

        // Add key
        int[] keyPos = findValidPosition(minCol, maxCol, minRow, maxRow, 50);
        if (keyPos != null) {
            OBJ_Key key = new OBJ_Key(gp);
            key.worldX = keyPos[0];
            key.worldY = keyPos[1];
            zone.addObject(key);
        }
    }

    private void populateDeepForest(Zone zone, RegularGrimFactory grimFactory, SkeletonFactory skeletonFactory) {
        // Define safe area boundaries for Deep Forest
        int minCol = 25;
        int maxCol = 40;
        int minRow = 20;
        int maxRow = 30;

        // Debug: Show collision map for this area
        //debugCollisionMapArea(minCol, maxCol, minRow, maxRow, "Deep Forest");

        // Add enemies
        for (int i = 0; i < 3; i++) {
            int[] position = findValidPosition(minCol, maxCol, minRow, maxRow, 50);
            if (position != null) {
                Entity enemy;
                if (i == 0) {
                    enemy = skeletonFactory.createEnemy(gp);
                } else {
                    enemy = grimFactory.createEnemy(gp);
                }
                enemy.setworldX(position[0]);
                enemy.setworldY(position[1]);
                zone.addEnemy(enemy);
            }
        }

        // Add memory fragment
        int[] memoryPos = findValidPosition(minCol + 5, maxCol, minRow + 5, maxRow, 50);
        if (memoryPos != null) {
            OBJ_Memory memory1 = new OBJ_Memory(gp);
            memory1.worldX = memoryPos[0];
            memory1.worldY = memoryPos[1];
            zone.addObject(memory1);
        }

        // Add health potion
        int[] potionPos = findValidPosition(minCol, maxCol, minRow, maxRow, 50);
        if (potionPos != null) {
            OBJ_RedPotion potion = new OBJ_RedPotion(gp);
            potion.worldX = potionPos[0];
            potion.worldY = potionPos[1];
            zone.addObject(potion);
        }
    }

    private void populateAncientRuins(Zone zone, RegularGrimFactory grimFactory, SkeletonFactory skeletonFactory) {
        // Define safe area boundaries for Ancient Ruins
        int minCol = 30;
        int maxCol = 40;
        int minRow = 25;
        int maxRow = 35;

        // Debug: Show collision map for this area
        //debugCollisionMapArea(minCol, maxCol, minRow, maxRow, "Ancient Ruins");

        // Add enemies
        for (int i = 0; i < 4; i++) {
            int[] position = findValidPosition(minCol, maxCol, minRow, maxRow, 50);
            if (position != null) {
                Entity enemy;
                if (i < 2) {
                    enemy = grimFactory.createEnemy(gp);
                } else {
                    enemy = skeletonFactory.createEnemy(gp);
                }
                enemy.setworldX(position[0]);
                enemy.setworldY(position[1]);
                zone.addEnemy(enemy);
            }
        }

        // Add memory fragment
        int[] memoryPos = findValidPosition(minCol + 3, maxCol - 3, minRow + 3, maxRow - 3, 50);
        if (memoryPos != null) {
            OBJ_Memory memory1 = new OBJ_Memory(gp);
            memory1.worldX = memoryPos[0];
            memory1.worldY = memoryPos[1];
            zone.addObject(memory1);
        }

        // Add loot items
        String[] lootTypes = {"OBJ_RedPotion", "OBJ_Key"};
        for (String lootType : lootTypes) {
            int[] lootPos = findValidPosition(minCol, maxCol, minRow, maxRow, 50);
            if (lootPos != null) {
                SuperObject loot = null;
                switch (lootType) {
                    case "OBJ_RedPotion":
                        loot = new OBJ_RedPotion(gp);
                        break;
                    case "OBJ_Key":
                        loot = new OBJ_Key(gp);
                        break;
                }
                if (loot != null) {
                    loot.worldX = lootPos[0];
                    loot.worldY = lootPos[1];
                    zone.addObject(loot);
                }
            }
        }
    }

    /*
    // Debug method to show collision map for a specific area
    private void debugCollisionMapArea(int minCol, int maxCol, int minRow, int maxRow, String zoneName) {
        System.out.println("=== Collision Map for " + zoneName + " (Cols " + minCol + "-" + maxCol + ", Rows " + minRow + "-" + maxRow + ") ===");

        for (int row = minRow; row < maxRow; row++) {
            for (int col = minCol; col < maxCol; col++) {
                if (col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow) {
                    int tileNum = gp.tileM.mapTileNum[col][row];
                    char symbol = '.';

                    if (tileNum >= 0 && tileNum < gp.tileM.tiles.length && gp.tileM.tiles[tileNum] != null) {
                        if (gp.tileM.tiles[tileNum].collision) {
                            symbol = '#'; // Solid tile
                        } else if (tileNum == 6 || tileNum == 7 || tileNum == 8) {
                            symbol = 'E'; // Event tile
                        }
                    }
                    System.out.print(symbol);
                }
            }
            System.out.println();
        }
        System.out.println("=== End Collision Map ===\n");
    }

    // Debug method to validate all placements
    public void validatePlacements(Zone zone) {
        System.out.println("=== Validating Placements for " + zone.getName() + " ===");

        // Check enemies
        for (Entity enemy : zone.getEnemies()) {
            if (enemy != null) {
                int worldX = enemy.getworldX();
                int worldY = enemy.getworldY();
                boolean solid = isSolidTile(worldX, worldY);
                boolean event = isEventTile(worldX, worldY);

                if (solid) {
                    System.out.println("ERROR: Enemy at " + worldX + "," + worldY + " is on solid tile!");
                }
                if (event) {
                    System.out.println("ERROR: Enemy at " + worldX + "," + worldY + " is on event tile!");
                }
            }
        }

        // Check objects
        for (SuperObject obj : zone.getObjects()) {
            if (obj != null) {
                int worldX = obj.worldX;
                int worldY = obj.worldY;
                boolean solid = isSolidTile(worldX, worldY);
                boolean event = isEventTile(worldX, worldY);

                if (solid) {
                    System.out.println("ERROR: Object at " + worldX + "," + worldY + " is on solid tile!");
                }
                if (event) {
                    System.out.println("ERROR: Object at " + worldX + "," + worldY + " is on event tile!");
                }
            }
        }
    }*/
}
