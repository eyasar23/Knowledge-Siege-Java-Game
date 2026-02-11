package core;

import java.io.*;
import java.util.*;

/**
 * The ScoreManager class gives saving and loading of  scores
 * from  "scoreb.txt".
 * 
 * It provides :
 * -  store new score entries (username, score)
 * - Read existing scores from file
 * - Sort them in  order by score
 */
public class ScoreSaverLoader {
    
    
    private static final String FILE_PATH = "scoreb.txt";

    /**
     * Appends a new score entry to the score file.
     *
     * @param username the name of the player
     * @param score    the score achieved by the player
     */
    public static void saveScore(String username, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + "," + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    /**
     * Loads all saved scores from the file and returns them as a list
     * of ScoreEntry objects sorted in proper order.
     *
     * @return a sorted list of ScoreEntry instances 
     */
    public static List<ScoreEntry> loadScores() {
        List<ScoreEntry> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        list.add(new ScoreEntry(parts[0], Integer.parseInt(parts[1])));
                    } catch (NumberFormatException ignored) {
                        
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }

        
        list.sort((a, b) -> b.getScore() - a.getScore());
        return list;
    }
}
