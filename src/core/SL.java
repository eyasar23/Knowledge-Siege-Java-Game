package core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents an SL  that throw questions or information.
 */
public class SL extends KnowledgeKeeper {
    private List<ShotBox> questions;
    private List<ShotBox> informations;
    private int shotIndex;

    /**
     * Creates an SL enemy with a name, position, icon, and image path.
     *
     * @param name       the name of  SL
     * @param x          the x-coordinate of  SL
     * @param y          the y-coordinate of  SL
     * @param icon       the icon of  SL
     * @param imagePath  path to  SL image file
     */
    public SL(String name, int x, int y, BufferedImage icon, String imagePath) {
        super(name, 0, new Point(x, y));

        
        try {
            questions = FileLoader.getQuestions("question.txt");
            informations = FileLoader.getInformations("info.txt");
        } catch (IOException e) {
            e.printStackTrace();
            questions = new ArrayList<>();
            informations = new ArrayList<>();
        }

       
        this.imagePath = imagePath;
        this.speed = 4;
        this.shotIndex = 0;

        if (icon != null) {
            this.imageIcon = new ImageIcon(icon.getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        }
    }

    /**
     * Makes the SL shoot a ShotBox
     *
     * @param level the current game level 
     * @return a new ShotBox or null same cases
     */
    @Override
    public ShotBox shoot(int level) {
        if (questions.isEmpty() || informations.isEmpty()) {
            return null;
        }

        boolean isQuestion = new Random().nextBoolean();
        List<ShotBox> list = isQuestion ? questions : informations;

        ShotBox baseShot = list.get(shotIndex % list.size());
        shotIndex++;

        ShotBox newShot = baseShot.cloneWithNewPosition(getX(), getY());
        newShot.setDamageOrPoints(isQuestion ? -5 : 10);
        newShot.setSpeed(getShotSpeed());  
        return newShot;
    }

    /**
     * Draw  SL on  screen using  image 
     *
     * @param g the Graphics object
     * @param observer Component(includes jframe,jpanel used as image observer
     */
    public void draw(Graphics g, Component observer) {
        if (getImageIcon() != null) {
            getImageIcon().paintIcon(observer, g, getPosition().x, getPosition().y);
        } else {
            g.setColor(Color.RED);
            g.fillRect(getPosition().x, getPosition().y, 40, 40);
        }
    }

    /**
     * Returns   shot is a question or info.
     *
     * @param isQuestion true if the shot is a question
     * @return the damage or points
     */
    public int getDamageOrPoints(boolean isQuestion) {
        return isQuestion ? -5 : 10;
    }
    @Override
    public int getShotSpeed() {
        return 6; 
    }

    /**
     * Return the SL's image icon.
     *
     * @return the image icon
     */
    @Override
    public ImageIcon getImageIcon() {
        return super.getImageIcon();
    }
}
