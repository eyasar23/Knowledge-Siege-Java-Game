package user;

/**
 * Represents a user with a username, password, and profile picture path.
 * This class provides methods to get the username, check the password,
 * and manage the profile picture path.
 */
public class User {
    private String username;
    private String password;
    private String profilePicturePath;

    /**
     * Constructs a User object with the specified username, password, and profile picture path.
     *
     * @param username the username of the user
     * @param password the password for the user
     * @param profilePicturePath the file path to the user's profile picture
     */
    public User(String username, String password, String profilePicturePath) {
        this.username = username;
        this.password = password;
        this.profilePicturePath = profilePicturePath;
    }

    /**
     * Gets the username of the user.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Checks if the provided password matches the user's password.
     *
     * @param inputPassword the password to check
     * @return true if the password matches, false otherwise
     */
    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    /**
     * Getter method of  profile picture path. 
     *
     * @return the profile picture file path
     */
    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    /**
     * Setter method of profile picture path.
     *
     * @param profilePicturePath the new file path for the profile picture
     */
    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }
}
