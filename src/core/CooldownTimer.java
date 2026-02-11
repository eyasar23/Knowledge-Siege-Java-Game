package core;

/**
 * This class gives cooldown timing logic idea for things like  shooting.
 */
public class CooldownTimer {
    private long cooldownMillis;
    private long lastShotTime;

    /**
     * Creates a new cooldown timer with a given inpıt.
     * @param cooldownMillis how long to wait between actions 
     */
    public CooldownTimer(long cooldownMillis) {
        this.cooldownMillis = cooldownMillis;
        this.lastShotTime = System.nanoTime(); 
    }

    /**
     * Checks wether the cooldown time has passed.
     * @return true if ready for next action, false others
     */
    public boolean isReady() {
        return (System.nanoTime() - lastShotTime) >= (cooldownMillis * 1_000_000);
    }

    /**
     * Resets  cooldown then, the timer starts again.
     */
    public void reset() {
        lastShotTime = System.nanoTime();
    }
}
