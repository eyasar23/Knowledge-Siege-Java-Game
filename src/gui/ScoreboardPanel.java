package gui;

import core.ScoreSaverLoader;
import core.ScoreEntry;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ScoreboardPanel is a custom JPanel    displays
 * the list of high scores got from ScoreManager.
 */
public class ScoreboardPanel extends JPanel {

    /**
     * Constructor of  the scoreboard panel.
     * 
     * This panel:
     * - Uses a vertical BoxLayout to stack components.
     * - Displays a title and a list of username-score .
     * - Reads scores from the ScoreManager .
     */
    public ScoreboardPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        
        JLabel title = new JLabel("Scoreboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        
        List<ScoreEntry> scores = ScoreSaverLoader.loadScores();
        for (ScoreEntry entry : scores) {
            JLabel label = new JLabel(entry.getUsername() + " - " + entry.getScore());
            label.setForeground(Color.LIGHT_GRAY);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(label);
        }
    }
}
