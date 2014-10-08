package org.sourcery.util;

import java.nio.ByteBuffer;

/**
 * A collection of buffer-related utilities.
 * 
 * @author Martin Tuskevicius
 */
public class BufferUtilities {

    /**
     * Reads a null-terminated (<code>'\0'</code>) string from a buffer.
     * 
     * @param buf
     *            the buffer.
     * @return the string.
     */
    public static String getString(ByteBuffer buf) {
	StringBuilder b = new StringBuilder();
	for (char c; (c = (char) buf.get()) != '\0';) {
	    b.append(c);
	}
	return b.toString();
    }
}
