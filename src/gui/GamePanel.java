package gui;

import core.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * The GamePanel class represents the main gameplay screen.
 * It handles rendering of the player, enemies , and ShotBoxes.
 * It  manages and generates movement, score display, collision detection, and level progression.
 * Also it has main GUI elements.
 */
public class GamePanel extends JPanel implements KeyListener {

    private GameEngine engine;
    private Player player;
    private List<KnowledgeKeeper> keepers;
    private Map<KnowledgeKeeper, CooldownTimer> cooldownMap;
    private List<ShotBox> shots;
    private LevelManager levelManager;
    private LevelGUI levelLabel;
    private Map<KnowledgeKeeper, Timer> shootTimers = new HashMap<>();

    public List<String> visibleShotTexts = new ArrayList<>();

    private Timer loopTimer;
    private Timer movementTimer;
    private Set<Integer> pressedKeys = new HashSet<>();
    private JFrame parentFrame;

    /**
     * Constructs the GamePanel and initializes all major gameplay components.
     * This includes player creation, enemy setup, GUI setup, and game loop startup.
     *
     * @param username     the name of the player
     * @param parentFrame  the main game window
     * @throws IOException if image files for enemies cannot be loaded
     */
    public GamePanel(String username, JFrame parentFrame) throws IOException {
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        setBackground(Color.GRAY);
        this.parentFrame = parentFrame;

        player = new Player(username, 600, 800);
        keepers = new ArrayList<>();
        shots = new ArrayList<>();
        cooldownMap = new HashMap<>();
        levelManager = new LevelManager();
        levelLabel = new LevelGUI();
        setLayout(null);
        levelLabel.setBounds(10, 10, 200, 30);
        add(levelLabel);

        initializeKeepersWithImages();

        engine = new GameEngine(player, keepers, cooldownMap, shots, levelManager);

        loopTimer = new Timer(16, e -> {
            handleMovement();
            engine.tick();
            updateGame();
            repaint();
        });
        loopTimer.start();
        engine.setGamePanel(this);
    }

    /**
     * Handles player movement based on currently pressed keys.
     * Allows left and right movement within panel bounds.
     */
    private void handleMovement() {
        int moveSpeed = 6;
        if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
            player.moveLeft(moveSpeed);
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            player.moveRight(moveSpeed, getWidth());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
    }

    /**
     * Draws the game world including player, enemies, projectiles, score, health, and level.
     * Also displays the recent educational texts collected by the player.
     *
     * @param g the graphics object 
     */
    private void drawGame(Graphics g) {
        player.draw(g);

        for (KnowledgeKeeper k : keepers) {
            k.draw(g, this);
        }

        for (ShotBox s : shots) {
            s.draw(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + player.getInfoPoints(), 10, getHeight() - 20);
        g.drawString("Level: " + levelManager.getLevel(), getWidth() / 2 - 40, 30);
        g.drawString("Health: " + player.getHealth(), getWidth() - 100, getHeight() - 40);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        int baseX = getWidth() - 280;
        int baseY = 60;

        g.drawString("Active Educational Content:", baseX, baseY);
        baseY += 20;

        int maxDisplay = 8;
        for (int i = Math.max(0, visibleShotTexts.size() - maxDisplay); i < visibleShotTexts.size(); i++) {
            g.drawString("- " + visibleShotTexts.get(i), baseX, baseY);
            baseY += 20;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Initializes KnowledgeKeepers (SLs, TAs, Professors) based on current level.
     * Sets up their icons, positions, and shooting logic using Swing Timers.
     */
    private void initializeKeepersWithImages() throws IOException {
        stopShootTimers();
        keepers.clear();
        cooldownMap.clear();

        int level = levelManager.getLevel();

        try {
            if (level == 1) {
                keepers.add(new SL("Abdullah", 400, 60, ImageIO.read(new File("resources/images/sl1.png")), FileLoader.getPath("sl1.png")));
                keepers.add(new SL("nesrin", 800, 60, ImageIO.read(new File("resources/images/sl2.png")), FileLoader.getPath("sl2.png")));
                keepers.add(new SL("ekin", 1100, 60, ImageIO.read(new File("resources/images/sl5.png")), FileLoader.getPath("sl3.png")));
                keepers.add(new SL("şükrü", 1400, 60, ImageIO.read(new File("resources/images/sl4.png")), FileLoader.getPath("sl4.png")));
            } else if (level == 2) {
                keepers.add(new SL("burak", 100, 60, ImageIO.read(new File("resources/images/sl3.png")), FileLoader.getPath("sl5.png")));
                keepers.add(new SL("ozan", 250, 60, ImageIO.read(new File("resources/images/sl6.png")), FileLoader.getPath("sl6.png")));
                keepers.add(new TA("Hamza", 400, 60, ImageIO.read(new File("resources/images/ta1.png")), FileLoader.getPath("ta1.png")));
                keepers.add(new TA("fatma", 550, 60, ImageIO.read(new File("resources/images/ta2.png")), FileLoader.getPath("ta2.png")));
            } else if (level == 3) {
                keepers.add(new TA("Vahide", 200, 60, ImageIO.read(new File("resources/images/ta3.png")), FileLoader.getPath("ta3.png")));
                keepers.add(new TA("Zekiya", 400, 60, ImageIO.read(new File("resources/images/ta4.png")), FileLoader.getPath("ta4.png")));
                keepers.add(new Professor("Atilla", 600, 60, ImageIO.read(new File("resources/images/prof1.png")), FileLoader.getPath("prof1.png")));
                keepers.add(new Professor("Oznur", 750, 60, ImageIO.read(new File("resources/images/prof2.png")), FileLoader.getPath("prof2.png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (KnowledgeKeeper k : keepers) {
            cooldownMap.put(k, new CooldownTimer(2000));
            CooldownTimer cooldown = cooldownMap.get(k);

            Timer shootTimer = new Timer(40, e -> {
                if (cooldown != null && cooldown.isReady()) {
                    boolean isQuestion = new Random().nextBoolean();
                    ShotBox shot;

                    if (isQuestion) {
                        shot = ShotBox.createQuestion("Sample question");
                    } else {
                        shot = ShotBox.createInfo("Sample info");
                    }

                    shot.setY(k.getY());
                    shot = shot.cloneWithNewPosition(k.getX(), k.getY());
                    shots.add(shot);
                    cooldown.reset();
                }
            });
            shootTimer.start();
            shootTimers.put(k, shootTimer);
        }
    }

    /**
     * Stops and clears all active enemy shooting timers.
     * Called during level transitions to prevent timer leaks.
     */
    private void stopShootTimers() {
        for (Timer t : shootTimers.values()) {
            if (t != null) {
                t.stop();
            }
        }
        shootTimers.clear();
    }

    /**
     * Updates game logic including win/loss condition checks.
     * Transitions levels or shows appropriate end panels.
     */
    public void updateGame() {
        int level = levelManager.getLevel();
        int points = player.getInfoPoints();

        if (!player.isAlive()) {
            stopGame();
            showGameOverPanel();
            return;
        }

        if ((level == 1 && points >= 50) || (level == 2 && points >= 100)) {
            levelManager.advanceLevel();
            player.resetInfoPoints();
            initializeKeepersSafely();
        } else if (level == 3 && points >= 150) {
            showVictoryPanel();
        }
    }

    /**
     * Stops all active game timers (movement, loop, enemy fire).
     * Called during game over or level transitions.
     */
    public void stopGame() {
        if (loopTimer != null) loopTimer.stop();
        if (movementTimer != null) movementTimer.stop();
        stopShootTimers();
    }

    /**
     * Shows the victory panel when the player wins the final level.
     * Provides navigation back to the main menu or exits the game.
     */
    private void showVictoryPanel() {
        stopGame();
        SwingUtilities.invokeLater(() -> {
            VictoryPanel victoryPanel = new VictoryPanel();
            Component[] components = ((JPanel) victoryPanel.getComponent(1)).getComponents();
            JButton mainMenuButton = (JButton) components[0];
            JButton exitButton = (JButton) components[1];

            mainMenuButton.addActionListener(e -> {
                MainMenuPanel menu = new MainMenuPanel(player.getUsername(), parentFrame);
                parentFrame.setContentPane(menu);
                parentFrame.revalidate();
                parentFrame.repaint();
            });

            exitButton.addActionListener(e -> System.exit(0));

            parentFrame.setContentPane(victoryPanel);
            parentFrame.revalidate();
            parentFrame.repaint();
        });
    }

    /**
     * Displays a Game Over dialog and returns the player to the main menu.
     */
    private void showGameOverPanel() {
        stopGame();
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(parentFrame, "Game Over! Your health dropped to 0.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            MainMenuPanel menu = new MainMenuPanel(player.getUsername(), parentFrame);
            parentFrame.setContentPane(menu);
            parentFrame.revalidate();
            parentFrame.repaint();
        });
    }

    /**
     * Wrapper for safe reinitialization of enemies with images during level changes.
     */
    private void initializeKeepersSafely() {
        try {
            initializeKeepersWithImages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
