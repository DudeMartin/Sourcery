package org.sourcery.model;

/**
 * Represents a server type.
 * 
 * @author Martin Tuskevicius
 */
public enum ServerType {

    /**
     * A dedicated game server.
     */
    DEDICATED,

    /**
     * A non-dedicated, client-hosted, server.
     */
    NON_DEDICATED,

    /**
     * A server used for exclusively for spectating.
     */
    TELEVISION;

    /**
     * Returns the server type matching the input.
     * 
     * @param t
     *            the input.
     * @return a <code>ServerType</code> object.
     * @throws IllegalArgumentException
     *             if the input matches no server type.
     */
    public static ServerType getServerType(byte t) {
	switch (t) {
	case 'D':
	case 'd':
	    return DEDICATED;
	case 'L':
	case 'l':
	    return NON_DEDICATED;
	case 'P':
	case 'p':
	    return TELEVISION;
	default:
	    throw new IllegalArgumentException();
	}
    }
}
