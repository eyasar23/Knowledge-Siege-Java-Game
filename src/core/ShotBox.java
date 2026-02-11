package core;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents  box that contains  a question or information.
 * It down the screen and interacts with the player.
 */
public class ShotBox {
    private int x;
    private int y;
    private int speed;
    private boolean isQuestion;
    private String text;
    private BufferedImage icon;
    private int damageOrPoints;

    /**
     *  constructor. Initialize an empty ShotBox.
     */
    public ShotBox() {
        this.x = 0;
        this.y = 0;
        this.speed = 4;
        this.isQuestion = false;
        this.text = "";
        this.icon = null;
        this.damageOrPoints = 0;
    }

    /**
     * Creates a ShotBox  specific properties.
     * @param x  position component
     * @param speed falling speed
     * @param isQuestion true if it's a question, false if info
     * @param text the text shown in the box
     * @param icon image of the box 
     */
    public ShotBox(int x, int speed, boolean isQuestion, String text, BufferedImage icon) {
        this.x = x;
        this.y = 0;
        this.speed = speed;
        this.isQuestion = isQuestion;
        this.text = text;
        this.icon = icon;
        this.damageOrPoints = 0;
    }

    /**
     * Moves the ShotBox down by a  amount.
     * @param amount how much to  down
     */
    public void moveDown(int amount) {
        this.y += amount;
    }

    /**
     * Rese box with new values, used at object pooling.
     * @param x new X position
     * @param y new Y position
     * @param speed new speed
     * @param isQuestion is it a question box
     * @param damageOrPoints value to apply when collided
     */
    public void reset(int x, int y, int speed, boolean isQuestion, int damageOrPoints) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.isQuestion = isQuestion;
        this.damageOrPoints = damageOrPoints;
        this.text = "";
    }

    /**
     * Returns the rectangular area for collision test.
     * @return rectangle bounds of  ShotBox
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }

    public int getDamageOrPoints() {
        return damageOrPoints;
    }

    public void setDamageOrPoints(int damageOrPoints) {
        this.damageOrPoints = damageOrPoints;
    }

    /**
     * Creates a clone of  ShotBox with a different position.
     * @param newX new x position
     * @param newY new y position
     * @return a new ShotBox object
     */
    public ShotBox cloneWithNewPosition(int newX, int newY) {
        ShotBox clone = new ShotBox(newX, this.speed, this.isQuestion, this.text, this.icon);
        clone.setY(newY);
        clone.setDamageOrPoints(this.damageOrPoints);
        return clone;
    }

    /**
     * Draws the box on  screen.
     * @param g graphics objecd
     */
    public void draw(Graphics g) {
        g.setColor(isQuestion ? Color.RED : Color.GREEN);
        g.fillRect(x, y, 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        String symbol = isQuestion ? "❓" : "💡";
        g.drawString(symbol, x + 2, y + 16);
    }
    /**
     * ALl of the getters method that mentioned above constructor
     * @param text question text
     * @return a new ShotBox marked as question
     */
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSpeed() { return speed; }
    public boolean isQuestion() { return isQuestion; }
    public String getText() { return text; }
    public BufferedImage getIcon() { return icon; }
    public void setY(int y) { this.y = y; }

    /**
     * Factor method to create a question-type ShotBox.
     * @param text question text
     * @return a new ShotBox marked as question
     */
    public static ShotBox createQuestion(String text) {
        BufferedImage dummyIcon = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        ShotBox sb = new ShotBox(0, 6, true, text, dummyIcon);
        sb.setDamageOrPoints(-5);
        return sb;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Factor method to create an information-type ShotBox.
     * @param text info text
     * @return a new ShotBox marked as info
     */
    public static ShotBox createInfo(String text) {
        BufferedImage dummyIcon = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        ShotBox sb = new ShotBox(0, 6, false, text, dummyIcon);
        sb.setDamageOrPoints(10);
        return sb;
    }
}
