package core;

import java.util.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * The GameEngine class manages the core logic of the game.
 * It handles player state, enemy  behavior, shotbox updates,
 * and level progression using a timer-based  loop 
 * 
 * respnsibilies
 * - Starting/stopping the main game loop
 * - Moving KnowledgeKeepers and managing  cooldowns
 * - Updating ShotBox positions and  collisions
 * - Transitioning between levels or to victory screen
 */
public class GameEngine {

    private int keeperMoveCounter = 0; 
    private Player player;
    private List<KnowledgeKeeper> keepers;
    private Map<KnowledgeKeeper, CooldownTimer> cooldownMap;
    private List<ShotBox> shots;
    private LevelManager levelManager;
    private gui.GamePanel gamePanel;
    private Timer mainTimer;

    /**
     * Sets the reference to the GamePanel for UI updates.
     * Should be called after initialization if needed.
     */
    public void setGamePanel(gui.GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Constructs a GameEngine instance with all  components.
     * @param player the main player object
     * @param keepers list of KnowledgeKeepers 
     * @param cooldownMap cooldown timers for each KnowledgeKeeper
     * @param shots the active list of ShotBoxes on screen
     * @param levelManager manages game level logic
     */
    public GameEngine(Player player, List<KnowledgeKeeper> keepers,
                      Map<KnowledgeKeeper, CooldownTimer> cooldownMap,
                      List<ShotBox> shots, LevelManager levelManager) {
        this.player = player;
        this.keepers = keepers;
        this.cooldownMap = cooldownMap;
        this.shots = shots;
        this.levelManager = levelManager;
    }

    /**
     * Starts the main game loop using a Swing Timer 
     */
    public void start() {
        mainTimer = new Timer(16, e -> tick());
        mainTimer.start();
    }

    /**
     * Stops the main game loop.
     */
    public void stop() {
        if (mainTimer != null) mainTimer.stop();
    }

    /**
     *  tick method executed each frame.
     * Updates game state: player state,  movement, and shotbox logic.
     */
    public void tick() {
        handlePlayerState();
        moveKeepers();
        updateShots();
    }

    /**
     * Checks player status: death, level up, or game victory.
     * Triggers score saving and transitions if needed.
     */
    private void handlePlayerState() {
        if (!player.isAlive()) {
            ScoreSaverLoader.saveScore(player.getUsername(), player.getInfoPoints());
            stop();
        } else if (player.getInfoPoints() >= levelManager.getRequiredPoints()) {
            if (player.getInfoPoints() >= 150) {
                
                ScoreSaverLoader.saveScore(player.getUsername(), player.getInfoPoints());
                SwingUtilities.invokeLater(() -> {
                    JFrame victoryFrame = new JFrame("Victory!");
                    victoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    victoryFrame.add(new gui.VictoryPanel());
                    victoryFrame.pack();
                    victoryFrame.setLocationRelativeTo(null);
                    victoryFrame.setVisible(true);
                });
                stop();
            } else {
               
                ScoreSaverLoader.saveScore(player.getUsername(), player.getInfoPoints());
                levelManager.advanceLevel();
                player.resetInfoPoints();
                stop();
            }
        }
    }

    /**
     * Moves all KnowledgeKeepers . 
     * Every 30 ticks, triggers random movement occcur.
     */
    private void moveKeepers() {
        for (KnowledgeKeeper k : keepers) {
            k.moveSmoothly(800);
        }

        keeperMoveCounter++;
        if (keeperMoveCounter % 30 == 0) {
            for (KnowledgeKeeper k : keepers) {
                k.moveRandomly();
            }
        }
    }

    /**
     * Updates all active ShotBoxes:
     * - Moves them down by their speed
     * - Detects collision with player and applies effects
     * - Requests new shots from KnowledgeKeepers if cooldown ready
     */
    private void updateShots() {
        Iterator<ShotBox> it = shots.iterator();
        while (it.hasNext()) {
            ShotBox s = it.next();

            
            s.moveDown(s.getSpeed());

           
            if (s.getY() > 1400) {
                it.remove();
            } 
            
            else if (s.getBounds().intersects(player.getBounds())) {
                player.applyShotBoxEffect(s);

                
                if (!s.getText().isEmpty() && gamePanel != null) {
                    gamePanel.visibleShotTexts.add(s.getText());
                }

                it.remove();
            }
        }

         
        for (KnowledgeKeeper keeper : keepers) {
            CooldownTimer cd = cooldownMap.get(keeper);
            if (cd != null && cd.isReady()) {
                ShotBox shot = keeper.shoot(levelManager.getLevel());
                if (shot != null) {
                    shots.add(shot);
                    cd.reset();
                }
            }
        }
    }
}
