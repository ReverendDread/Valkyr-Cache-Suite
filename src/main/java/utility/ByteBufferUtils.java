/**
 * 
 */
package utility;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Contains {@link ByteBuffer}-related utility methods.
 * @author Graham
 * @author `Discardedx2
 * @author ReverendDread
 */
public class ByteBufferUtils {

	/**
	 * The modified set of 'extended ASCII' characters used by the client.
	 */
	private static char CHARACTERS[] = { '\u20AC', '\0', '\u201A', '\u0192', '\u201E', '\u2026', '\u2020', '\u2021',
			'\u02C6', '\u2030', '\u0160', '\u2039', '\u0152', '\0', '\u017D', '\0', '\0', '\u2018', '\u2019', '\u201C',
			'\u201D', '\u2022', '\u2013', '\u2014', '\u02DC', '\u2122', '\u0161', '\u203A', '\u0153', '\0', '\u017E',
			'\u0178' };
	
	/**
	 * Gets a null-terminated string from the specified buffer, using a modified
	 * ISO-8859-1 character set.
	 * 
	 * @param buf
	 *            The buffer.
	 * @return The decoded string.
	 */
	public static String getString(ByteBuffer buf) {
		StringBuilder bldr = new StringBuilder();
		int b;
		while ((b = buf.get()) != 0) {
			if (b >= 127 && b < 160) {
				char curChar = CHARACTERS[b - 128];
				if (curChar == 0) {
					curChar = 63;
				}
				
				bldr.append(curChar);
			} else {
				bldr.append((char) b);
			}
		}
		return bldr.toString();
	}
	
	/**
	 * Gets an unsigned short LE type from the buffer.
	 * 
	 * @param buffer
	 * 			The buffer.
	 * @return
	 * 			The value.
	 */
	public static int getUShortLE(ByteBuffer buffer) {
		return buffer.get() + (buffer.get() & 0xFF << 8);
	}
	
	/**
	 * Reads a 'tri-byte' from the specified buffer.
	 * 
	 * @param buffer
	 *            The buffer.
	 * @return The value.
	 */
	public static int getMedium(ByteBuffer buffer) {
		return ((buffer.get() & 0xFF) << 16) | ((buffer.get() & 0xFF) << 8) | (buffer.get() & 0xFF);
	}
	
	/**
	 * Writes a 'tri-byte' to the specified buffer.
	 * 
	 * @param buffer
	 *            The buffer.
	 * @param value
	 *            The value.
	 */
	public static void putMedium(ByteBuffer buffer, int value) {
		buffer.put((byte) (value >> 16));
		buffer.put((byte) (value >> 8));
		buffer.put((byte) value);
	}
	
	/**
	 * Gets a unsigned smart from the buffer.
	 * 
	 * @param buffer
	 *            The buffer.
	 * @return The value.
	 */
	public static int getUnsignedSmart(ByteBuffer buf) {
		int peek = buf.get(buf.position()) & 0xFF;
		if (peek < 128)
			return buf.get() & 0xFF;
		else
			return (buf.getShort() & 0xFFFF) - 32768;
	}
	
	/**
	 * Gets a signed smart from the buffer.
	 * 
	 * @param buffer
	 *            The buffer.
	 * @return The value.
	 */
	public static int getSignedSmart(ByteBuffer buf) {
		int peek = buf.get(buf.position()) & 0xFF;
		if (peek < 128)
			return (buf.get() & 0xFF) - 64;
		else
			return (buf.getShort() & 0xFFFF) - 49152;
	}

	/**
	 * Gets a smart integer from the buffer.
	 * 
	 * @param buffer
	 *            The buffer.
	 * @return The value.
	 */
	public static int getSmartInt(ByteBuffer buffer) {
		if (buffer.get(buffer.position()) < 0) 
			return buffer.getInt() & 0x7fffffff;
		return buffer.getShort() & 0xFFFF;
	}

	/**
	 * Gets a small smart integer from the buffer.
	 * 
	 * @param buffer
	 *            The buffer.
	 * @return The value.
	 */
	public static int getSmallSmartInt(ByteBuffer buffer) {
		if ((buffer.get(buffer.position()) & 0xff) < 128) {
			return (buffer.get() & 0xff) - 1;
		}
		int shortValue = buffer.getShort() & 0xFFFF;
		return shortValue - 32769;
	}

	/**
	 * @param buffer
	 * @param value
	 * @throws IOException 
	 */
	public static void putMedium(DataOutputStream buffer, int value) throws IOException {
		buffer.write((byte) (value >> 16));
		buffer.write((byte) (value >> 8));
		buffer.write((byte) value);	
	}
	
}
