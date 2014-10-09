package org.sourcery.packet;

import java.nio.ByteBuffer;

/**
 * Represents a generic query packet.
 * 
 * @author Martin Tuskevicius
 */
public class QueryPacket {

    public static final byte A2S_INFO = 0x54;
    public static final byte A2S_INFO_RESPONSE = 0x49;
    public static final byte A2S_INFO_GOLDSRC_RESPONSE = 0x6D;
    public static final byte A2S_PLAYER = 0x55;
    public static final byte A2S_PLAYER_RESPONSE = 0x44;
    public static final byte A2S_RULES = 0x56;
    public static final byte A2S_RULES_RESPONSE = 0x45;
    public static final byte A2A_PING = 0x69;
    public static final byte A2A_PING_RESPONSE = 0x6A;
    public static final byte A2S_SERVERQUERY_GETCHALLENGE = 0x57;
    public static final byte A2S_SERVERQUERY_GETCHALLENGE_RESPONSE = 0x41;

    /**
     * The header of a query packet that is not split into multiple packets.
     */
    public static final int SIMPLE_QUERY_HEADER = 0xFFFFFFFF;

    /**
     * The header of a query packet that is split into multiple packets.
     */
    public static final int SPLIT_QUERY_HEADER = 0xFFFFFFFE;

    /**
     * The maximum number of bytes a single query packet can contain.
     */
    public static final int MAXIMUM_PACKET_SIZE = 1400;

    /**
     * The packet header byte.
     */
    public final byte header;

    /**
     * The payload of the packet.
     */
    public final ByteBuffer payload;

    /**
     * Creates a new query packet.
     * 
     * @param header
     *            the header.
     * @param payload
     *            the payload.
     */
    public QueryPacket(byte header, ByteBuffer payload) {
	this.header = header;
	this.payload = payload;
    }
}
