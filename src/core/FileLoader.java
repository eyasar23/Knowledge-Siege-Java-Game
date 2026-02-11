package core;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import user.User;

/**
 * FileLoader is aimed for loading files, reading resources such as images,
 * questions, and information from resource files, and converting them into objects
 * used in the game.
 */
public class FileLoader {

    private static final String USER_FILE = "users.txt";
    private static final String QUESTIONS_FILE = "questions.txt";
    private static final String INFO_FILE = "info.txt";

    /**
     * Reads lines from a given file in the resources directory.
     * @param fileName name of the file to read from
     * @return list of lines read from the file
     */
    public static List<String> getLines(String fileName) {
        List<String> lines = new ArrayList<>();
        InputStream is = FileLoader.class.getClassLoader().getResourceAsStream(fileName);

        if (is == null) {
            System.err.println("Warning: File not found -> " + fileName);
            return lines; 
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }

        return lines;
    }

    /**
     * Loads an image from the specified  path.
     * @param path path to  image resource
     * @return BufferedImage object
     * @throws IOException if image cannot be found or read
     */
    public static BufferedImage loadImage(String path) throws IOException {
        InputStream is = FileLoader.class.getClassLoader().getResourceAsStream(path);
        if (is == null) throw new IOException("Image file not found: " + path);
        return ImageIO.read(is);
    }

    /**
     * Converts a list of strings into a list of ShotBox objects.
     * @param lines content strings
     * @param isQuestion true if ShotBox is a question, false if information
     * @param icon image icon to be used
     * @param speed movement speed of the ShotBox
     * @return list of ShotBox objects
     */
    public static List<ShotBox> convertToShotBoxes(List<String> lines, boolean isQuestion, BufferedImage icon, int speed) {
        List<ShotBox> shots = new ArrayList<>();
        for (String line : lines) {
            shots.add(new ShotBox(0, speed, isQuestion, line, icon));
        }
        return shots;
    }

    /**
     * Returns a subset of ShotBoxes from the given lines.
     * @param lines original list of strings
     * @param start starting index
     * @param amount number of ShotBoxes 
     * @param isQuestion whether the ShotBoxes are questions or information
     * @param icon image icon to assign
     * @param speed movement speed
     * @return list of ShotBox objects
     */
    public static List<ShotBox> getSubsets(List<String> lines, int start, int amount, boolean isQuestion, BufferedImage icon, int speed) {
        List<ShotBox> subset = new ArrayList<>();
        for (int i = start; i < Math.min(start + amount, lines.size()); i++) {
            subset.add(new ShotBox(0, speed, isQuestion, lines.get(i), icon));
        }
        return subset;
    }

    /**
     * Loads question strings from the default questions file.
     * @return list of questions
     * @throws IOException if file reading fails
     */
    public static List<String> loadQuestions() throws IOException {
        return getLines(QUESTIONS_FILE);
    }

    /**
     * Loads information strings from the default info file.
     * @return list of information
     * @throws IOException if file reading fails
     */
    public static List<String> loadInformations() throws IOException {
        return getLines(INFO_FILE);
    }

    /**
     * Convert lines from a file into question ShotBoxes.
     * @param fileName file to read from
     * @return list of ShotBox objects represent questions
     * @throws IOException if file cannot reading 
     */
    public static List<ShotBox> getQuestions(String fileName) throws IOException {
        List<String> lines = getLines(fileName);
        List<ShotBox> questions = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                questions.add(ShotBox.createQuestion(line.trim()));
            }
        }
        return questions;
    }

    /**
     * Convertlines from a file into information ShotBoxes.
     * @param fileName file to read from
     * @return list of ShotBox objects represent information
     * @throws IOException if file  cannot reading 
     */
    public static List<ShotBox> getInformations(String fileName) throws IOException {
        List<String> lines = getLines(fileName);
        List<ShotBox> infos = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                infos.add(ShotBox.createInfo(line.trim()));
            }
        }
        return infos;
    }

    /**
     * Loads the first 10 questions for TA level with specified icon.
     * @param iconPath path to icon image
     * @return list of ShotBox question objects
     * @throws IOException if file or image loading fails
     */
    public static List<ShotBox> getTAQuestions(String iconPath) throws IOException {
        return getSubsets(loadQuestions(), 0, 10, true, loadImage(iconPath), 3);
    }

    /**
     * Loads the first 10 information items for TA level with specified icon.
     * @param iconPath path to icon image
     * @return list of ShotBox information objects
     * @throws IOException if file or image loading fails
     */
    public static List<ShotBox> getTAInformations(String iconPath) throws IOException {
        return getSubsets(loadInformations(), 0, 10, false, loadImage(iconPath), 3);
    }

    /**
     * Loads users from the user file.
     * @return list of User objects
     */
    /**
     * same as previous ta informations for ga and proQ
     * /**
     * Loads the first 10 information items for TA level with specified icon.
     * @param iconPath path to icon image
     * @return list of ShotBox information objects
     * @throws IOException if file or image loading fails
     */
     
    
    public static List<ShotBox> getGAQuestions(String iconPath) throws IOException {
        return getSubsets(loadQuestions(), 10, 10,true, loadImage(iconPath), 3);
    }

    public static List<ShotBox> getGAInformations(String iconPath) throws IOException {
        return getSubsets(loadInformations(), 10, 10,false, loadImage(iconPath), 3);
    }

    public static List<ShotBox> getPROQuestions(String iconPath) throws IOException {
        return getSubsets(loadQuestions(), 20, 10,true, loadImage(iconPath), 3);
    }

    public static List<ShotBox> getPROInformations(String iconPath) throws IOException {
        return getSubsets(loadInformations(), 20, 10,false, loadImage(iconPath), 3);
    }
    
    
    /**
     * Loads users from the user file.
     * @return list of User objects
     */
    
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (InputStream is = FileLoader.class.getClassLoader().getResourceAsStream(USER_FILE)) {
            if (is == null) throw new IOException("User file not found.");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String username = parts[0];
                        String password = parts[1];
                        String avatarPath = parts.length > 2 ? parts[2] : "";
                        users.add(new User(username, password, avatarPath));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Saves a user to the user file.
     * @param user User object to be saved
     */
    public static void saveUser(User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE, true))) {
            writer.println(user.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the full path for a resource image file.
     * @param fileName image file name
     * @return full path stringi
     */
    public static String getPath(String fileName) {
        return "resources/images/" + fileName;
    }

}
