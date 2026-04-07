package Core.World;

import Core.Entities.Entity;
import Core.GamePanel;
import object.SuperObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Core.GameLogger.LOGGER;

public class WorldManager implements WorldComponent {
    private List<WorldComponent> worlds;
    private World currentWorld;
    private Zone currentZone;
    private GamePanel gp;

    public WorldManager(GamePanel gp) {
        this.gp = gp;
        this.worlds = new ArrayList<>();
        initializeWorlds();
        LOGGER.info("[COMPOSITE] WorldManager initialized with " + worlds.size() + " worlds");
    }

    private void initializeWorlds() {
        // === WORLD 1: Misty Forest ===
        World forestWorld = new World("Misty Forest", 1);
        forestWorld.setRequiredMemories(3); // Need 3 memory fragments to complete

        // Zone 1-1: Forest Clearing
        Zone startingZone = new Zone("Forest Clearing", 1, "/maps/world01.txt");
        startingZone.setRequiredMemories(1); // This zone has 1 memory fragment
        forestWorld.addComponent(startingZone);
        LOGGER.info("[COMPOSITE] Zone added to World: Forest Clearing -> Misty Forest");

        // Zone 1-2: Deep Forest
        Zone deepForest = new Zone("Deep Forest", 2, "/maps/world02.txt");
        deepForest.setRequiredMemories(1); // This zone has 1 memory fragment
        forestWorld.addComponent(deepForest);
        LOGGER.info("[COMPOSITE] Zone added to World: Deep Forest -> Misty Forest");

        // Zone 1-3: Ancient Ruins
        Zone ancientRuins = new Zone("Ancient Ruins", 5, "/maps/world03.txt");
        ancientRuins.setRequiredMemories(1); // This zone has 1 memory fragment
        forestWorld.addComponent(ancientRuins);
        LOGGER.info("[COMPOSITE] Zone added to World: Ancient Ruins -> Misty Forest");

        worlds.add(forestWorld);
        LOGGER.info("[COMPOSITE] World added to WorldManager: Misty Forest (Level 1)");

        // === WORLD 2: Frozen Mountains ===
        World mountainWorld = new World("Frozen Mountains", 2);
        mountainWorld.setRequiredMemories(4); // Need 4 memory fragments
        mountainWorld.setUnlocked(false); // Locked initially

        // Zone 2-1: Mountain Pass
        Zone mountainPass = new Zone("Mountain Pass", 8, "/maps/world04.txt");
        mountainPass.setRequiredMemories(2);
        mountainWorld.addComponent(mountainPass);
        LOGGER.info("[COMPOSITE] Zone added to World: Mountain Pass -> Frozen Mountains");

        // Zone 2-2: Peak Sanctuary
        Zone peakSanctuary = new Zone("Peak Sanctuary", 10, "/maps/world05.txt");
        peakSanctuary.setRequiredMemories(2);
        mountainWorld.addComponent(peakSanctuary);
        LOGGER.info("[COMPOSITE] Zone added to World: Peak Sanctuary -> Frozen Mountains");

        worlds.add(mountainWorld);
        LOGGER.info("[COMPOSITE] World added to WorldManager: Frozen Mountains (Level 2) - LOCKED");

        // === WORLD 3: Lava Caverns ===
        World lavaWorld = new World("Lava Caverns", 3);
        lavaWorld.setRequiredMemories(5);
        lavaWorld.setUnlocked(false);

        Zone magmaChamber = new Zone("Magma Chamber", 15, "/maps/world06.txt");
        magmaChamber.setRequiredMemories(3);
        lavaWorld.addComponent(magmaChamber);
        LOGGER.info("[COMPOSITE] Zone added to World: Magma Chamber -> Lava Caverns");

        Zone crystalCavern = new Zone("Crystal Cavern", 18, "/maps/world07.txt");
        crystalCavern.setRequiredMemories(2);
        lavaWorld.addComponent(crystalCavern);
        LOGGER.info("[COMPOSITE] Zone added to World: Crystal Cavern -> Lava Caverns");

        worlds.add(lavaWorld);
        LOGGER.info("[COMPOSITE] World added to WorldManager: Lava Caverns (Level 3) - LOCKED");

        // Set initial world and zone
        currentWorld = forestWorld;
        currentZone = startingZone;
        LOGGER.info("[COMPOSITE] Current World set to: Misty Forest");
        LOGGER.info("[COMPOSITE] Current Zone set to: Forest Clearing");
    }

    public void updateBasedOnPlayerLevel(int playerLevel) {

        // Unlock worlds based on player level
        for (WorldComponent component : worlds) {
            if (component instanceof World world) {
                boolean wasUnlocked = world.isUnlocked();
                if (playerLevel >= world.getWorldLevel() * 3) {
                    world.setUnlocked(true);
                    if (!wasUnlocked) {
                        LOGGER.info("[COMPOSITE] World unlocked: " + world.getName() + " (Level " + world.getWorldLevel() + ")");
                        gp.ui.addMessage("World unlocked: " + world.getName() + "!");
                    }
                }
            }
        }

        // Unlock zones in current world based on player level
        if (currentWorld != null) {
            for (int i = 0; i < currentWorld.components.size(); i++) {
                WorldComponent component = currentWorld.getChild(i);
                if (component instanceof Zone zone) {
                    boolean wasUnlocked = zone.isUnlocked();
                    if (!wasUnlocked && playerLevel >= zone.getLevelRequirement()) {
                        zone.setUnlocked(true);
                        gp.ui.addMessage("Zone unlocked: " + zone.getName() + "!");
                    }
                }
            }
        }
    }

    public void checkWorldProgression() {

        if (currentWorld != null) {

            if (currentWorld.isComplete() && !currentWorld.isCompleted()) {
                // Mark world as completed first to prevent spamming
                currentWorld.setCompleted(true);

                // World completed! Unlock next world
                int currentIndex = worlds.indexOf(currentWorld);
                if (currentIndex + 1 < worlds.size()) {
                    World nextWorld = (World) worlds.get(currentIndex + 1);
                    nextWorld.setUnlocked(true);
                    gp.ui.addMessage("New world unlocked: " + nextWorld.getName() + "!");
                }
            }
        }

        if (currentZone != null) {

            if (currentZone.isComplete() && !currentZone.isCompleted()) {
                // Mark zone as completed first to prevent spamming
                currentZone.setCompleted(true);

                // Zone completed! Move to next zone or complete world
                Zone nextZone = getNextZone();
                if (nextZone != null && nextZone.isUnlocked()) {
                    currentZone = nextZone;
                    loadZoneContent(currentZone);
                    gp.ui.onZoneChanged(currentZone);
                    gp.ui.addMessage("Entering: " + currentZone.getName() + "!");

                    // Populate the new zone
                    gp.aSetter.populateZone(currentZone);
                    gp.aSetter.setObject();
                    gp.aSetter.setEnemy();
                } else if (nextZone == null) {
                    System.out.println("No next zone available - must be last zone in world");
                } else if (!nextZone.isUnlocked()) {
                    System.out.println("Next zone " + nextZone.getName() + " is locked");
                }
            }
        }
    }

    private Zone getNextZone() {
        if (currentWorld == null) return null;

        System.out.println("Looking for next zone after: " +
                (currentZone != null ? currentZone.getName() : "null"));

        // Get all zones in current world
        List<Zone> zones = new ArrayList<>();
        for (int i = 0; i < currentWorld.components.size(); i++) {
            WorldComponent component = currentWorld.getChild(i);
            if (component instanceof Zone zone) {
                zones.add(zone);
            }
        }

        // Find current zone index
        int currentIndex = zones.indexOf(currentZone);

        if (currentIndex + 1 < zones.size()) {
            Zone nextZone = zones.get(currentIndex + 1);
            return nextZone;
        }

        System.out.println("No next zone found");
        return null;
    }

    public void loadZoneContent(Zone zone) {
        if (zone == null) return;

        // Clear current enemies and objects
        gp.enemies = new Entity[20];
        gp.obj = new SuperObject[10];

        // Load zone enemies
        List<Entity> zoneEnemies = zone.getEnemies();
        for (int i = 0; i < Math.min(zoneEnemies.size(), gp.enemies.length); i++) {
            gp.enemies[i] = zoneEnemies.get(i);
        }

        // Load zone objects
        List<SuperObject> zoneObjects = zone.getObjects();
        for (int i = 0; i < Math.min(zoneObjects.size(), gp.obj.length); i++) {
            gp.obj[i] = zoneObjects.get(i);
        }

        // Load zone map
        gp.tileM.loadMap(zone.getMapFile());
    }

    // Getters
    public World getCurrentWorld() {
        return currentWorld;
    }

    public Zone getCurrentZone() {
        return currentZone;
    }

    public List<WorldComponent> getWorlds() {
        return worlds;
    }

    // WorldComponent interface implementation
    @Override
    public void update() {
        if (currentWorld != null) {
            currentWorld.update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (currentWorld != null) {
            currentWorld.draw(g2);
        }
    }

    @Override
    public void addComponent(WorldComponent component) {
        worlds.add(component);
    }

    @Override
    public void removeComponent(WorldComponent component) {
        worlds.remove(component);
    }

    @Override
    public WorldComponent getChild(int index) {
        if (index >= 0 && index < worlds.size()) {
            return worlds.get(index);
        }
        return null;
    }

    @Override
    public boolean isComplete() {
        // All worlds are complete
        for (WorldComponent world : worlds) {
            if (!world.isComplete()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void reset() {
        for (WorldComponent world : worlds) {
            world.reset();
        }
        // Reset to first world and zone
        currentWorld = (World) worlds.get(0);
        currentZone = currentWorld.getCurrentZone();
    }
}
