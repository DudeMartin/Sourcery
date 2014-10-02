package org.sourcery.model;

/**
 * An immutable, instantaneous representation of a player that is connected to a
 * game server.
 * 
 * @author Martin Tuskevicius
 */
public final class Player {

    /**
     * The name (in-game alias) of the player.
     */
    public final String name;

    /**
     * The score.
     */
    public final int score;

    /**
     * The number of seconds that the player has been connected to the server.
     */
    public final float duration;

    /**
     * Creates a new representation of a player.
     * 
     * @param name
     *            the name.
     * @param score
     *            the score.
     * @param duration
     *            the duration.
     */
    public Player(String name, int score, float duration) {
	this.name = name;
	this.score = score;
	this.duration = duration;
    }
}
