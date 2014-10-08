package org.sourcery.model;

/**
 * Represents the operating system that a server machine is running.
 * 
 * @author Martin Tuskevicius
 */
public enum Environment {

    /**
     * The Windows operating system.
     */
    WINDOWS,

    /**
     * The Linux operating system.
     */
    LINUX,

    /**
     * The OS X operating system.
     */
    MAC;

    /**
     * Returns the environment matching the input.
     * 
     * @param e
     *            the input.
     * @return an <code>Environment</code> object.
     * @throws IllegalArgumentException
     *             if the input matches no environment.
     */
    public static Environment getEnvironment(byte e) {
	switch (e) {
	case 'W':
	case 'w':
	    return WINDOWS;
	case 'L':
	case 'l':
	    return LINUX;
	case 'M':
	case 'm':
	case 'O':
	case 'o':
	    return MAC;
	default:
	    throw new IllegalArgumentException();
	}
    }
}
