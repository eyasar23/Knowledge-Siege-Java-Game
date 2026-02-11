/* *********** Pledge of Honor ************************************************ *

I hereby certify that I have completed this lab assignment on my own
without any help from anyone else. I understand that the only sources of authorized
information in this lab assignment are (1) the course textbook, (2) the
materials posted on the course website, and (3) any study notes handwritten by myself.
I have not used, accessed, or received any information from any other unauthorized
source in taking this lab assignment. The effort in the assignment thus belongs
completely to me.
READ AND SIGN BY WRITING YOUR NAME SURNAME, AND STUDENT ID
SIGNATURE: <Emirhan Efe Yaşar , 87050>
********************************************************************************/
package main;


import user.UserManager;
import gui.LoginRegisterPanel;
import gui.ScoreboardPanel;

import javax.swing.*;

/**
 * Entry point for the Knowledge Siege game.
 * Initializes the GUI and sets up the user login/register panel.
 */
public class Main {
    public static void main(String[] args) {

        /**
         * 
         *  to run system is the main method aim.
         * 
         * Initializes:
         * - UserManager to handle user data
         * - Main JFrame with window title, size, and  operation
         * - Sets LoginRegisterPanel as the initial screen
         */
        SwingUtilities.invokeLater(() -> {
            UserManager userManager = new UserManager();
            JFrame frame = new JFrame("Knowledge Siege");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            LoginRegisterPanel loginRegisterPanel = new LoginRegisterPanel(userManager, frame);
            frame.setContentPane(loginRegisterPanel);
            frame.setVisible(true);
        });
    }
}
