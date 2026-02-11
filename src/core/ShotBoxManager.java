package core;

import java.awt.*;
import java.util.*;
import java.util.List;
/**
 * Manages the behavior of ShotBox objects without using an object pool.
 */
public class ShotBoxManager {
    private final List<ShotBox> activeShots;
    private final Player player;
    private final int maxShots;

    /**
     * Constructor for ShotBoxManager.
     * @param player the player in the game
     * @param maxShots maximum number of active shots
     */
    public ShotBoxManager(Player player, int maxShots) {
        this.player = player;
        this.activeShots = new ArrayList<>();
        this.maxShots = maxShots;
    }

    /**
     * Updates all shots: moves them and checks collisions.
     */
    public void update() {
        Iterator<ShotBox> it = activeShots.iterator();
        while (it.hasNext()) {
            ShotBox s = it.next();
            s.moveDown(6);
            if (s.getY() > 600 || s.getBounds().intersects(player.getBounds())) {
                if (s.getBounds().intersects(player.getBounds())) {
                    player.applyShotBoxEffect(s);
                }
                it.remove(); 
            }
        }
    }

    /**
     * Draws all active ShotBoxes on the screen.
     * @param g the Graphics object 
     */
    public void draw(Graphics g) {
        for (ShotBox s : activeShots) {
            if (s.getY() >= 0 && s.getY() <= 600) {
                g.setColor(s.isQuestion() ? Color.RED : Color.GREEN);
                g.fillRect(s.getX(), s.getY(), 20, 20);
            }
        }
    }

    /**
     * Adds a new ShotBox to the game.
     * @param shot the ShotBox to add
     */
    public void addShot(ShotBox shot) {
        if (activeShots.size() < maxShots) {
            activeShots.add(shot);
        }
    }

    /**
     * Returns the list of active shots.
     */
    public List<ShotBox> getActiveShots() {
        return activeShots;
    }
}
