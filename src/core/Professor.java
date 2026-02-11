package core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
/**
 * Represents an prof that throw questions or information.
 */
public class Professor extends KnowledgeKeeper {
    private List<ShotBox> questions;
    private List<ShotBox> informations;
    private int shotIndex;
    /**
     * Creates an prof  with a name, position, icon, and image path.
     *
     * @param name       the name of  prof
     * @param x          the x-coordinate of  prof
     * @param y          the y-coordinate of  prof
     * @param icon       the icon of  prof
     * @param imagePath  path to  prof image file
     */
    public Professor(String name, int x, int y, BufferedImage icon, String imagePath) {
        super(name, 0, new Point(x, y));
        this.speed = 10;
        this.shotIndex = 0;

        try {
            questions = FileLoader.getQuestions("question.txt");
            informations = FileLoader.getInformations("info.txt");
        } catch (IOException e) {
            e.printStackTrace();
            questions = new ArrayList<>();
            informations = new ArrayList<>();
        }

        
        this.imagePath = imagePath;
        if (icon != null) {
            this.imageIcon = new ImageIcon(icon.getScaledInstance(90, 90, Image.SCALE_SMOOTH));
        }
    }
    /**
     * Makes the profo shoot a ShotBox
     *
     * @param level the current game level 
     * @return a new ShotBox or null same cases
     */
    @Override
    public ShotBox shoot(int level) {
        if (questions.isEmpty() || informations.isEmpty()) return null;

        boolean isQuestion = new Random().nextBoolean();
        List<ShotBox> list = isQuestion ? questions : informations;

        ShotBox base = list.get(shotIndex % list.size());
        shotIndex++;

        ShotBox shot = base.cloneWithNewPosition(getX(), getY());
        shot.setDamageOrPoints(isQuestion ? -10 : 20);
        shot.setSpeed(getShotSpeed()); 

        return shot;
    }
    /**
     * Draw  prof on  screen using  image 
     *
     * @param g the Graphics object
     * @param observer Component(includes jframe,jpanel used as image observer
     */
    @Override
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
    @Override
    public int getDamageOrPoints(boolean isQuestion) {
        return isQuestion ? -10 : 20;
    }
    @Override
    public int getShotSpeed() {
        return 10; 
    }

    /**
     * Gets the profr's icon.
     *
     * @return image icon
     */
    @Override
    public ImageIcon getImageIcon() {
        return super.getImageIcon();
    }
}
