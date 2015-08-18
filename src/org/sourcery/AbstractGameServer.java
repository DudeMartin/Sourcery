package org.sourcery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.sourcery.packet.QueryPacket;
import org.sourcery.util.ByteVector;

/**
 * A base implementation of a generic game server.
 * 
 * @author Martin Tuskevicius
 */
public abstract class AbstractGameServer implements GameServer {

    protected final InetSocketAddress addr;
    protected final DatagramSocket querySocket;

    /**
     * Constructs an abstract representation of a game server.
     * 
     * @param addr
     *            the server address.
     * @throws IOException
     *             if a channel could not be prepared.
     */
    public AbstractGameServer(InetSocketAddress addr) throws IOException {
	this.addr = addr;
	querySocket = new DatagramSocket();
	querySocket.connect(addr);
    }

    /**
     * Sends a query packet.
     * 
     * @param query
     *            the query.
     * @throws IOException
     *             if a communication error occurs.
     */
    protected final void sendQuery(QueryPacket query) throws IOException {
	ByteVector out = new ByteVector(query.payload.length + 5);
	out.writeLittleEndianInt(QueryPacket.SIMPLE_QUERY_HEADER);
	out.writeByte(query.header);
	out.writeBytes(query.payload);
	querySocket.send(new DatagramPacket(out.b, out.index));
    }

    /**
     * Reads a query packet.
     * 
     * @return the query.
     * @throws IOException
     *             if a communication error occurs.
     */
    protected QueryPacket readQuery() throws IOException {
	ByteVector in = new ByteVector(QueryPacket.MAXIMUM_PACKET_SIZE);
	DatagramPacket packet = new DatagramPacket(in.b, in.b.length);
	querySocket.receive(packet);
	if (in.readLittleEndianInt() == QueryPacket.SPLIT_QUERY_HEADER) {
	    return constructSplitQuery(packet);
	}
	byte header = in.readByte();
	byte[] payload = in.readBytes(packet.getLength() - 5);
	return new QueryPacket(header, payload);
    }

    /**
     * Constructs a split query by joining multiple packets together, reading
     * them as necessary.
     * 
     * <p>
     * The format of a split query response varies based on the game engine.
     * 
     * @param init
     *            the initial packet.
     * @return the constructed query.
     * @throws IOException
     *             if a communication error occurs.
     * @throws CompressedPacketException
     *             if the response is compressed.
     */
    protected abstract QueryPacket constructSplitQuery(DatagramPacket init) throws IOException;

    /**
     * Measures the time it takes to send a server information request and
     * receive a response from the server.
     */
    @Override
    public long measurePing() {
	long timeSent = System.nanoTime();
	getInformation();
	return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timeSent);
    }

    /**
     * Closes the socket used for querying.
     */
    @Override
    public void close() throws IOException {
	querySocket.close();
    }

    /**
     * Returns the address of this game server.
     * 
     * @return the address.
     */
    public final InetSocketAddress address() {
	return addr;
    }
}