package core;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Base class for all Knowledge Keepers in the game like SL, TA, and Professor.
 */
public abstract class KnowledgeKeeper {
    protected String name;
    protected int speed = 2;
    protected Point position;
    protected List<ShotBox> shots;
    protected Random random;
    protected String imagePath;
    protected boolean movingRight = true;
    protected ImageIcon imageIcon;

    /**
     * Constructor for KnowledgeKeeper.
     * @param name the name of character
     * @param speed is speed
     * @param position includes x y 
     */
    public KnowledgeKeeper(String name, int speed, Point position) {
        this.name = name;
        this.speed = speed;
        this.position = position;
        this.random = new Random();
        this.imagePath = null;
    }

    /**
     * Shoots a ShotBox depending on the level.
     * @param level current  level
     * @return a ShotBox object
     */
    public abstract ShotBox shoot(int level);

    /**
     * Moves the keeper left or right 
     * @param panelWidth width of the game panel
     */
    public void move(int panelWidth) {
        position.x += speed;
        if (position.x > panelWidth - 40 || position.x < 0) {
            speed = -speed;
        }
    }
    public abstract int getShotSpeed();

    /**
     * Moves the keeper  random directions.
     */
    public void moveRandomly() {
        int moveAmount = speed * 5;
        position.x += (random.nextBoolean() ? -moveAmount : moveAmount);
        position.x = Math.max(0, Math.min(position.x, 720));
    }

    /**
     * Moves the keeper smoothly with direction 
     * @param panelWidth width of the game panel
     */
    public void moveSmoothly(int panelWidth) {
        if (Math.random() < 0.01) {
            movingRight = !movingRight;
        }
        if (movingRight) {
            position.x += speed;
            if (position.x >= panelWidth - 40) {
                movingRight = false;
            }
        } else {
            position.x -= speed;
            if (position.x <= 0) {
                movingRight = true;
            }
        }
    }

    /**
     * Draws the keeper on the screen.
     * @param g Graphics object
     * @param observer component that observes the image loading
     */
    public void draw(Graphics g, Component observer) {
        if (imageIcon != null) {
            imageIcon.paintIcon(observer, g, position.x, position.y);
        } else {
            g.setColor(Color.RED);
            g.fillRect(position.x, position.y, 40, 40);
        }
    }

    /**
     * @return x-coordinate
     */
    public int getX() {
        return position.x;
    }

    /**
     * @return y-coordinate
     */
    public int getY() {
        return position.y;
    }

    /**
     * @return bounding rectangle 
     */
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, 40, 40);
    }

    /**
     * Gets  damage or point this keeper gives.
     * @param isQuestion true if it's a question 
     * @return damage _ point value
     */
    public abstract int getDamageOrPoints(boolean isQuestion);

    /**
     * Setter of the movement speed.
     * @param s new speed
     */
    public void setSpeed(int s) {
        this.speed = s;
    }

    /**
     * @return list of shots
     */
    public List<ShotBox> getShots() {
        return shots;
    }

    /**
     * @return image path o keeper
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @return image icon for drawing
     */
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    /**
     * Setter the image icon.
     * @param imageIcon image icon to use
     */
    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    /**
     * @return current position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Set the position.
     * @param position new position
     */
    public void setPosition(Point position) {
        this.position = position;
    }
}
