package core;

/**
 * Manage the current level and determines the  points
 * to get to next level.
 */
public class LevelManager {
    private int level;

    /**
     * Creates a LevelManager at level 1.
     */
    public LevelManager() {
        this.level = 1;
    }

    /**
     * Gets the current level.
     *
     * @return the  level
     */
    public int getLevel() {
        return level;
    }

    /**
     * moving the game to the next level.
     */
    public void advanceLevel() {
        level++;
    }

    /**
     * Returns the number  points need to pass the  level.
     *
     * @return the required points to pass the current level
     */
    public int getRequiredPoints() {
        switch (level) {
            case 1:
                return 50;
            case 2:
                return 100;
            case 3:
                return 150;
            default:
                return Integer.MAX_VALUE; 
        }
    }
}
