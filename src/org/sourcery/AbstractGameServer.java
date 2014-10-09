package org.sourcery;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;

import org.sourcery.packet.QueryPacket;

/**
 * A base implementation of a generic game server.
 * 
 * @author Martin Tuskevicius
 */
public abstract class AbstractGameServer implements GameServer {

    protected final InetSocketAddress addr;
    protected final DatagramChannel queryChannel;

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
	queryChannel = DatagramChannel.open();
	queryChannel.connect(addr);
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
	ByteBuffer buf = ByteBuffer.allocate(query.payload.rewind().remaining() + 5);
	buf.order(ByteOrder.LITTLE_ENDIAN);
	buf.putInt(QueryPacket.SIMPLE_QUERY_HEADER);
	buf.put(query.header);
	buf.put(query.payload);
	buf.flip();
	queryChannel.write(buf);
    }

    /**
     * Reads a query packet.
     * 
     * @return the query.
     * @throws IOException
     *             if a communication error occurs.
     */
    protected QueryPacket readQuery() throws IOException {
	ByteBuffer buf = ByteBuffer.allocate(QueryPacket.MAXIMUM_PACKET_SIZE).order(ByteOrder.LITTLE_ENDIAN);
	queryChannel.read(buf);
	buf.flip();
	if (buf.getInt() == QueryPacket.SPLIT_QUERY_HEADER) {
	    return constructResponse(buf);
	}
	byte header = buf.get();
	ByteBuffer payload = buf.slice().order(ByteOrder.LITTLE_ENDIAN);
	return new QueryPacket(header, payload);
    }

    /**
     * Constructs a query packet by joining multiple packets together, reading
     * them as necessary.
     * 
     * @param buf
     *            the data of the initial packet.
     * @return the query.
     * @throws IOException
     *             if a communication error occurs.
     * @throws CompressedPacketException
     *             if the response is compressed.
     */
    protected abstract QueryPacket constructResponse(ByteBuffer buf) throws IOException;

    /**
     * Measures the time it takes to send a server information request and
     * receive a response from the server.
     */
    @Override
    public long measurePing() {
	long timeSent = System.nanoTime();
	getInformation();
	return TimeUnit.MILLISECONDS.convert(System.nanoTime() - timeSent, TimeUnit.NANOSECONDS);
    }

    /**
     * Closes the channel used for querying.
     */
    @Override
    public void close() throws IOException {
	queryChannel.close();
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
