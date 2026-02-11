package gui;

import javax.swing.*;

/**
 * A simple GUI label  displays the current g level.
 *  extends JLabel and updates its text to show the level n.
 */
public class LevelGUI extends JLabel {

    /**
     * Updates the label text to reflect the given game level.
     *
     * @param level the current level to be displayed
     */
    public void updateLevel(int level) {
        setText("Seviye: " + level);
    }
}
