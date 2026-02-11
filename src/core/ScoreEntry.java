package core;

/**
 * Represents a score entry in the scoreboard,
 *  a username and the  score.
 */
public class ScoreEntry {
    private String username;
    private int score;

    /**
     * Constructor of  a ScoreEntry with username and score.
     *
     * @param username name of user
     * @param score     score achieved by  user
     */
    public ScoreEntry(String username, int score) {
        this.username = username;
        this.score = score;
    }

    /**
     * Getter the username of this  entry.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter the scoreof  this entry.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }
}
