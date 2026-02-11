package core;

import java.awt.*;

/**
 * Represents the player in the game, x,y , health, and info points.
 * Handles movement, creating player object, health and score management, and draw.
 */
public class Player {
    private String username;
    private int x, y;
    private int infoPoints;
    private int health;
    private final int maxHealth = 100;

    /**
     * Constructor of  a Player object  a given username and starting position.
     *
     * @param username the player's username
     * @param x the starting x-coordinate
     * @param y the starting y-coordinate
     */
    public Player(String username, int x, int y) {
        this.username = username;
        this.x = x;
        this.y = y;
        this.infoPoints = 0;
        this.health = maxHealth;
    }

    /**
     * Gets the bounding rectangle of the player for collision.
     *
     * @return the bounding rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 50);
    }

    /**
     * take damage to  the player's health by this amount.
     *
     * @param amount the amount of damage 
     */
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    /**
     * Increases the player's health by  amount.
     *
     * @param amount the amount to heal
     */
    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }

    /**
     * Adds info points  to the player.
     *
     * @param points number of points to add
     */
    public void addInfoPoints(int points) {
        infoPoints += points;
    }

    /**
     * Checks ifthe player is alive.
     *
     * @return true if health is more than 0, false other situotion
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Applies the effect of a ShotBox to the player.
     * If  a question, affects health. If  info, affects points.
     *
     * @param shot  shotBox that hit player
     */
    public void applyShotBoxEffect(ShotBox shot) {
        if (shot.isQuestion()) {
            this.health += shot.getDamageOrPoints();
        } else {
            this.infoPoints += shot.getDamageOrPoints();
        }
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Getter  method of  username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method of username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * etter  method of  point.
     *
     * @return the info points
     */
    public int getInfoPoints() {
        return infoPoints;
    }

    /**
     * etter  method of  health.
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * etter  method of  maxhealth = 100.
     *
     * @return the max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * getter  method of  x.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * etter  method of  y.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Moves the player left by  distance.
     *
     * @param dx the distance 
     */
    public void moveLeft(int dx) {
        x -= dx;
        if (x < 0) x = 0;
    }

    /**
     * Moves the player right by  distance
     *
     * @param dx the distance 
     * @param panelWidth the width of e panel
     */
    public void moveRight(int dx, int panelWidth) {
        x += dx;
        if (x + 50 > panelWidth) x = panelWidth - 50;
    }

    /**
     * Draws the player as a blue rectangle and health .
     *
     * @param g the Graphics object 
     */
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 50, 50);
        drawHealthBar(g);
    }

    /**
     * Draws the health bar 
     *
     * @param g the Graphics object 
     */
    public void drawHealthBar(Graphics g) {
        int barWidth = 100;
        int barHeight = 10;
        int filled = (int) ((double) health / maxHealth * barWidth);

        g.setColor(Color.RED);
        g.fillRect(x, y - 15, barWidth, barHeight);
        g.setColor(Color.GREEN);
        g.fillRect(x, y - 15, filled, barHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y - 15, barWidth, barHeight);
    }

    /**
     * Resets  points  to 0.
     */
    public void resetInfoPoints() {
        this.infoPoints = 0;
    }
}
