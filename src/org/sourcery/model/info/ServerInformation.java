package org.sourcery.model.info;

/**
 * An immutable, instantaneous container of basic information about a game
 * server.
 * 
 * @author Martin Tuskevicius
 */
public interface ServerInformation {

    /**
     * Returns the name of the server.
     * 
     * @return the name.
     */
    String name();

    /**
     * Returns the name of the currently loaded map.
     * 
     * @return the map name.
     */
    String map();

    /**
     * Returns the name of the game.
     * 
     * @return the game name.
     */
    String game();

    /**
     * Returns the number of connected players.
     * 
     * @return the player count.
     */
    int players();

    /**
     * Returns the maximum number of players the server will allow to connect.
     * 
     * @return the maximum player count.
     */
    int maximumPlayers();

    /**
     * Returns the number of bots.
     * 
     * @return the bot count.
     */
    int bots();
}