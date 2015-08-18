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
 * <li>the server query protocol,
 * <li>the RCON (remote console) protocol.
 * </ul>
 * 
 * <p>
 * Communication is performed in a blocking manner. However, it is important to
 * recognize that certain interactions are done over different sockets, namely,
 * querying and RCON. As a result, these interactions do not block each other;
 * two queries will block each other, but a <i>single</i> query and an RCON
 * interaction will not. When no more communication will be made, the connection
 * to the server can (and should) be closed using the <code>close</code> method.
 * 
 * <p>
 * Implementations of this interface are designed with synchronous use in mind.
 * They make no guarantees to implement functionality to support concurrent use,
 * like queuing packets. Certain operations, require the exchange of multiple
 * packets through the same socket, which rely on an orderly sequence of
 * actions. For example, attempting to RCON authorize multiple times
 * concurrently may yield undefined behavior. As a result, unless otherwise
 * stated, external synchronization is required for concurrent interaction.
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
     * @return the measured ping (in milliseconds).
     */
    long measurePing();
}