package user;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class gives  registration and login.
 * It saves users to a local file (users.txt) using a simple username password .
 */
public class UserManager {
    private Map<String, String> users = new HashMap<>();
    private final String USER_FILE = "users.txt";

    /**
     * When a UserManager  created, it load existing users from file.
     */
    public UserManager() {
        loadUsers();
    }

    /**
     * Registers  new user if the username doesn't  exist.
     *
     * @param username the new user name
     * @param password  password for  user
     * @return true if registration is ok, false if user  exists
     */
    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, password);
        saveUsers();
        return true;
    }

    /**
     * Checks if the given username and password are correct.
     *
     * @param username the user's name
     * @param password the user's password
     * @return true if login info is correct, false oyher situation
     */
    public boolean login(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    /**
     * Saves all users to the text file .
     */
    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (String name : users.keySet()) {
                writer.write(name + ":" + users.get(name));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads users from the file if it exists.
     *  each line should be username:password
     */
    private void loadUsers() {
        File file = new File(USER_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
