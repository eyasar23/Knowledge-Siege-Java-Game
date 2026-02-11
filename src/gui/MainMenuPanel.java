package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Shows the main menu after login with a welcome e and Start Game button.
 */
public class MainMenuPanel extends JPanel {

    private String username;
    private JFrame parentFrame;

    /**
     * Creates the main menu .
     *
     * @param username the current player  name
     * @param parentFrame the main window frame
     */
    public MainMenuPanel(String username, JFrame parentFrame) {
        this.username = username;
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("WELCOME, " + username + " !", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        JButton startGameButton = new JButton("START GAME");
        add(startGameButton, BorderLayout.CENTER);

        
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GamePanel gamePanel = new GamePanel(username, parentFrame);
                    parentFrame.setContentPane(gamePanel);
                    parentFrame.revalidate();
                    gamePanel.requestFocusInWindow();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(parentFrame, 
                        "An error occurred: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
    }
}
