package Core.World;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World implements WorldComponent {
    private String name;
    private int worldLevel;
    public List<WorldComponent> components;
    private boolean unlocked;
    private boolean completed;
    private int requiredMemories; // Total memories needed to complete world
    private int collectedMemories; // Memories collected across all zones

    public World(String name, int worldLevel) {
        this.name = name;
        this.worldLevel = worldLevel;
        this.components = new ArrayList<>();
        this.unlocked = worldLevel == 1;
        this.completed = false;
        this.requiredMemories = 0;
        this.collectedMemories = 0;
    }

    public void setRequiredMemories(int requiredMemories) {
        this.requiredMemories = requiredMemories;
    }

    public int getRequiredMemories() {
        return requiredMemories;
    }

    public int getCollectedMemories() {
        // Calculate total collected memories from all zones
        int total = 0;
        for (WorldComponent component : components) {
            if (component instanceof Zone zone) {
                total += zone.getCollectedMemories();
            }
        }
        return total;
    }

    public void collectMemoryInZone(String zoneName) {
        for (WorldComponent component : components) {
            if (component instanceof Zone zone && zone.getName().equals(zoneName)) {
                zone.collectMemory();
                break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getWorldLevel() {
        return worldLevel;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public void update() {
        if (!unlocked) return;

        // Update all components (zones) in this world
        for (WorldComponent component : components) {
            component.update();
        }

        // Check if world is complete (all zones are complete)
        if (!completed) {
            completed = isComplete();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!unlocked) return;

        // Draw all components
        for (WorldComponent component : components) {
            component.draw(g2);
        }
    }

    @Override
    public void addComponent(WorldComponent component) {
        components.add(component);
    }

    @Override
    public void removeComponent(WorldComponent component) {
        components.remove(component);
    }

    @Override
    public WorldComponent getChild(int index) {
        if (index >= 0 && index < components.size()) {
            return components.get(index);
        }
        return null;
    }

    @Override
    public boolean isComplete() {
        // World is complete when all zones are complete
        for (WorldComponent component : components) {
            if (!component.isComplete()) {
                return false;
            }
        }

        // Additionally check total memory requirement
        if (requiredMemories > 0) {
            return getCollectedMemories() >= requiredMemories;
        }

        return true;
    }

    @Override
    public void reset() {
        // Reset all zones in this world
        for (WorldComponent component : components) {
            component.reset();
        }
        completed = false;
    }

    public Zone getCurrentZone() {
        // Find the first incomplete zone
        for (WorldComponent component : components) {
            if (component instanceof Zone zone && !zone.isComplete()) {
                return zone;
            }
        }
        return null;
    }

    public Zone getZoneByName(String name) {
        for (WorldComponent component : components) {
            if (component instanceof Zone zone && zone.getName().equals(name)) {
                return zone;
            }
        }
        return null;
    }
}
