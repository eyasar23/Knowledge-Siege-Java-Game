package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the victory screen  when t player wins .
 * Includes a  message and  buttons.
 */
public class VictoryPanel extends JPanel {

    /**
     * Constructor of  the victory panel with  message and buttons  exit the game.
     */
    public VictoryPanel() {
        setLayout(new BorderLayout());

        JLabel victoryLabel = new JLabel("Victory! Congratulations! 🎉", SwingConstants.CENTER);
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(victoryLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton mainMenuButton = new JButton("Return to Main Menu");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(mainMenuButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
