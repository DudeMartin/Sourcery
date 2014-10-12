package org.sourcery.util;

/**
 * A utility class for reading and writing various data types to and from a byte
 * array.
 * 
 * @author Martin Tuskevicius
 */
public class ByteVector {

    /**
     * The <code>byte</code> array.
     */
    public final byte[] b;
    
    /**
     * The current index.
     */
    public int index;

    /**
     * Creates a new byte vector.
     * 
     * @param b
     *            the input <code>byte</code> array.
     */
    public ByteVector(byte[] b) {
	this.b = b;
    }

    /**
     * Creates a new byte vector by allocating an array with the specified
     * length.
     * 
     * @param length
     *            the length of the internal <code>byte</code> array.
     */
    public ByteVector(int length) {
	this(new byte[length]);
    }

    /**
     * Reads a byte.
     * 
     * @return the byte.
     */
    public byte readByte() {
	return b[index++];
    }

    /**
     * Reads the specified number of bytes.
     * 
     * @param num
     *            the number of bytes to read.
     * @return an array containing the read bytes.
     */
    public byte[] readBytes(int num) {
	byte[] bytes = new byte[num];
	System.arraycopy(b, index, bytes, 0, num);
	index += num;
	return bytes;
    }

    /**
     * Reads a little-endian four-byte integer.
     * 
     * @return the little-endian integer.
     */
    public int readLittleEndianInt() {
	return ((readByte() & 0xFF) << 0) | 
		((readByte() & 0xFF) << 8) | 
		((readByte() & 0xFF) << 16)  | 
		((readByte() & 0xFF) << 24);
    }

    /**
     * Reads a null-terminated (<code>'\0'</code>) string.
     * 
     * @return the string.
     */
    public String readString() {
	StringBuilder bldr = new StringBuilder();
	for (char c; (c = (char) readByte()) != '\0';) {
	    bldr.append(c);
	}
	return bldr.toString();
    }

    /**
     * Writes a byte.
     * 
     * @param i
     *            the byte.
     */
    public void writeByte(byte i) {
	b[index++] = i;
    }

    /**
     * Writes an array of bytes.
     * 
     * @param bytes
     *            the bytes.
     */
    public void writeBytes(byte[] bytes) {
	int length = bytes.length;
	System.arraycopy(bytes, 0, b, index, length);
	index += length;
    }

    /**
     * Writes a little-endian four-byte integer.
     * 
     * @param i
     *            the little-endian integer.
     */
    public void writeLittleEndianInt(int i) {
	writeByte((byte) i);
	writeByte((byte) (i >> 8));
	writeByte((byte) (i >> 16));
	writeByte((byte) (i >> 24));
    }

    /**
     * Resets the position back to 0.
     */
    public void reset() {
	index = 0;
    }
}
