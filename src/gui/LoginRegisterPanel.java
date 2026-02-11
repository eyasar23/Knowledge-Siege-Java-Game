package gui;

import user.UserManager;

import javax.swing.*;
import java.awt.*;

/**
 * This panel gives the user log in or register.
 * It  have a button to view the scoreboard.
 */
public class LoginRegisterPanel extends JPanel {

    private UserManager userManager;
    private JFrame parentFrame;

    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Creates the login and registration screen.
     *
     * @param userManager handles login and register logic
     * @param parentFrame the main game window
     */
    public LoginRegisterPanel(UserManager userManager, JFrame parentFrame) {
        this.userManager = userManager;
        this.parentFrame = parentFrame;

        setLayout(new GridLayout(5, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        add(loginButton);
        add(registerButton);

        JButton scoreboardButton = new JButton("View Scoreboard");
        scoreboardButton.addActionListener(e -> {
            JFrame scoreboardFrame = new JFrame("Scoreboard");
            scoreboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            scoreboardFrame.add(new ScoreboardPanel());
            scoreboardFrame.pack();
            scoreboardFrame.setLocationRelativeTo(null);
            scoreboardFrame.setVisible(true);
        });
        add(scoreboardButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (userManager.login(username, password)) {
                JOptionPane.showMessageDialog(parentFrame, "Login successful!");
                goToMainMenu(username);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Incorrect username or password!");
            }
        });

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (userManager.register(username, password)) {
                JOptionPane.showMessageDialog(parentFrame, "Registration successful!");
                goToMainMenu(username);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Username already exists!");
            }
        });
    }

    /**
     * going screen to the main menu.
     *
     * @param username the current user name
     */
    private void goToMainMenu(String username) {
        MainMenuPanel mainMenu = new MainMenuPanel(username, parentFrame);
        parentFrame.setContentPane(mainMenu);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
