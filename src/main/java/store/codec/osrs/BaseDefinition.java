/**
 * 
 */
package store.codec.osrs;

import store.io.InputStream;
import store.io.OutputStream;

/**
 * @author ReverendDread
 * Sep 6, 2018
 */
public class BaseDefinition {

	public int id;
	public int count;
	public int[] transformations;
	public int[][] skinList;	
	
	public BaseDefinition(int id, byte[] data) {
		this.id = id;
		InputStream stream = new InputStream(data);
		count = stream.readUnsignedByte();
		transformations = new int[count];
		skinList = new int[count][];
		for (int opcode = 0; opcode < count; opcode++)
			transformations[opcode] = stream.readUnsignedByte();
		for (int skin = 0; skin < count; skin++)
			skinList[skin] = new int[stream.readUnsignedByte()];
		for (int skin = 0; skin < count; skin++) {
			for (int subSkin = 0; (subSkin < skinList[skin].length); subSkin++)
				skinList[skin][subSkin] = stream.readUnsignedByte();
		}
	}

	public byte[] encode() {
		OutputStream stream = new OutputStream();
		int count = transformations.length;
		stream.writeByte(transformations.length);
		for (int opcode = 0; opcode < count; opcode++) {
			stream.writeByte(transformations[opcode]);
		}
		for (int opcode = 0; opcode < count; opcode++) {
			stream.writeByte(0);
		}
		for (int opcode = 0; opcode < count; opcode++) {
			stream.writeShort(-1);
		}
		for (int skin = 0; skin < count; skin++) {
			stream.writeByte(skinList[skin].length);
		}
		for (int skin = 0; skin < count; skin++) {
			for (int subSkin = 0; (subSkin < skinList[skin].length); subSkin++)
				stream.writeByte(skinList[skin][subSkin]);
		}
		byte[] flipped = new byte[stream.getIndex()];
		stream.flipBuffer(flipped, 0, flipped.length);
		return flipped;
	}

}
