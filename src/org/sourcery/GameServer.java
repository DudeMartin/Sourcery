package org.sourcery;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import org.sourcery.model.Player;
import org.sourcery.model.info.ServerInformation;

/**
 * Represents a game server.
 * 
 * <p>
 * This interface defines methods for communicating with the game server using
 * two protocols:
 * 
 * <ul>
 * <li>the server query protocol
 * <li>the RCON (remote console) protocol.
 * </ul>
 * 
 * Communication is performed in a blocking manner. When no more communication
 * will be made, the connection to the server can (and should) be closed using
 * the <code>close</code> method.
 * 
 * @author Martin Tuskevicius
 */
public interface GameServer extends Closeable {

    /**
     * Attempts to authorize RCON access.
     * 
     * @param rconPassword
     *            the RCON password.
     * @return <code>true</code> if authorization was successful,
     *         <code>false</code> otherwise.
     * @throws IOException
     *             if a server communication error occurs.
     */
    boolean rconAuthorize(String rconPassword) throws IOException;

    /**
     * Executes an RCON command.
     * 
     * @param command
     *            the command.
     * @return the server's textualized response.
     * @throws IOException
     *             if a server communication error occurs.
     * @throws RconAuthorizationException
     *             if RCON access was not authorized.
     */
    String rcon(String command) throws IOException;

    /**
     * Retrieves basic information about this server.
     * 
     * @return the server information.
     */
    ServerInformation getInformation();

    /**
     * Retrieves basic information about the players currently connected to this
     * server.
     * 
     * @return the players.
     */
    Player[] getPlayers();

    /**
     * Retrieves the server rules (configuration variables).
     * 
     * @return the rules.
     */
    Map<String, String> getRules();

    /**
     * Measures the ping to this server.
     * 
     * @return the measured ping.
     */
    long measurePing();
}