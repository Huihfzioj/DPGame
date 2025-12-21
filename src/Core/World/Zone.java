package Core.World;

import Core.Entities.Entity;
import object.SuperObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Core.GameLogger.LOGGER;

public class Zone implements WorldComponent {
    private String name;
    private int levelRequirement;
    private boolean unlocked;
    private boolean completed;
    private int requiredMemories; // Memory fragments needed in this zone
    private int collectedMemories;

    // Zone content
    private List<Entity> enemies;
    private List<SuperObject> objects;
    private String mapFile;

    public Zone(String name, int levelRequirement, String mapFile) {
        this.name = name;
        this.levelRequirement = levelRequirement;
        this.mapFile = mapFile;
        this.unlocked = false;
        this.completed = false;
        this.enemies = new ArrayList<>();
        this.objects = new ArrayList<>();
        LOGGER.info("[COMPOSITE] Zone created: " + name + " (Level " + levelRequirement + ")");
    }

    public void collectMemory() {
        collectedMemories++;
        LOGGER.info("[COMPOSITE] Memory collected in " + name + ": " + collectedMemories + "/" + requiredMemories);
        if (collectedMemories >= requiredMemories && requiredMemories > 0) {
            // All memories collected in this zone
            LOGGER.info("[COMPOSITE] Zone completed: " + name + " - All memories collected!");
            System.out.println("All memories collected in " + name);
        }
    }

    public int getCollectedMemories() {
        return collectedMemories;
    }

    public int getRequiredMemories() {
        return requiredMemories;
    }

    public void setRequiredMemories(int requiredMemories) {
        this.requiredMemories = requiredMemories;
    }

    public void addEnemy(Entity enemy) {
        enemies.add(enemy);
        LOGGER.info("[COMPOSITE] Enemy added to Zone " + name + ": " + enemy.name);
    }

    public void addObject(SuperObject obj) {
        objects.add(obj);
        LOGGER.info("[COMPOSITE] Object added to Zone " + name + ": " + obj.name);
    }

    public List<Entity> getEnemies() {
        return enemies;
    }

    public List<SuperObject> getObjects() {
        return objects;
    }

    public String getMapFile() {
        return mapFile;
    }

    public String getName() {
        return name;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
        if (unlocked) {
            LOGGER.info("[COMPOSITE] Zone unlocked: " + name);
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed) {
            LOGGER.info("[COMPOSITE] Zone marked as completed: " + name);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void addComponent(WorldComponent component) {
        // Leaf node doesn't have children
        throw new UnsupportedOperationException("Zone is a leaf node");
    }

    @Override
    public void removeComponent(WorldComponent component) {
        throw new UnsupportedOperationException("Zone is a leaf node");
    }

    @Override
    public WorldComponent getChild(int index) {
        throw new UnsupportedOperationException("Zone is a leaf node");
    }

    @Override
    public boolean isComplete() {
        // Zone is complete when:
        // 1. All enemies are defeated, AND
        // 2. All required memory fragments are collected (if any are required)

        // Check enemies
        for (Entity enemy : enemies) {
            if (enemy != null && enemy.isAlive()) {
                return false;
            }
        }

        // Check memories (only if requiredMemories > 0)
        if (requiredMemories > 0) {
            return collectedMemories >= requiredMemories;
        }

        return true; // No memory requirement, just enemy requirement
    }

    @Override
    public void reset() {
        // Reset all enemies in this zone
        for (Entity enemy : enemies) {
            if (enemy != null) {
                enemy.life = enemy.maxLife;
            }
        }
        completed = false;
    }
}
