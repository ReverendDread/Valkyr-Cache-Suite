/**
 * 
 */
package store.codec.osrs;

import store.io.InputStream;

/**
 * @author ReverendDread
 * Sep 8, 2018
 */
public class SequenceDefinition {

	public int[] frameLengths;
	public int id;
	public int[] frameIDs;
	public boolean aBoolean4173;
	public int[] baseIDs;
	public int frameStep = 490274841;
	public boolean aBoolean4176;
	public boolean[] animationFlowControl;
	public int forcedPriority = 1084755117;
	public int leftHandItem = 556718803;
	public int rightHandItem = -1297220983;
	public int maxLoops = -1164419115;
	public int resetWhenWalk = 1486793947;
	public int[] anIntArray4183;
	public int delayType;
	public boolean aBoolean4185;
	public static boolean aBoolean4186 = false;
	public int[][] sounds;
	public int[] anIntArray4188;
	public int priority = 1424483545;
	public int[] anIntArray4190;
	public int[] frameArchives;
	public int[] frameFiles;
	public int[] baseArchives;
	public int[] baseFiles;
	
	public SequenceDefinition(int id, byte[] data) {
		InputStream buffer = new InputStream(data);
		for (;;) {
			int opcode = buffer.readUnsignedByte();
			if (opcode == 0)
				break;
			decode(buffer, opcode);
		}
	}
	
	void decode(InputStream buffer, int opcode) {
		int count;
		if (1 == opcode) {
			count = buffer.readUnsignedShort();
			frameLengths = new int[count];
			for (int index = 0; index < count; index++)
				frameLengths[index] = buffer.readUnsignedShort();
			frameIDs = new int[count];
			frameArchives = new int[count];
			frameFiles = new int[count];
			for (int index = 0; index < count; index++) {
				int frame = buffer.readUnsignedShort();
				frameIDs[index] = frame;
				frameFiles[index] = frame;
			}
			for (int index = 0; index < count; index++) {
				int fileId = buffer.readUnsignedShort();
				this.frameArchives[index] = fileId;
				this.frameIDs[index] = ((fileId << 16) + this.frameIDs[index]);
			}
		} else if (2 == opcode)
			frameStep = buffer.readUnsignedShort();
		else if (opcode == 3) {
			animationFlowControl = new boolean[256];
			count = buffer.readUnsignedByte();
			for (int index = 0; index < count; index++)
				animationFlowControl[buffer.readUnsignedByte()] = true;
		} else if (opcode == 4) {
			count = 1;
		} else if (5 == opcode)
			forcedPriority = buffer.readUnsignedByte();
		else if (6 == opcode) {
			count = buffer.readUnsignedShort();
			leftHandItem = count;
		} else if (opcode == 7) {
			count = buffer.readUnsignedShort();
			rightHandItem = count;
		} else if (opcode == 8)
			maxLoops = buffer.readUnsignedByte();
		else if (opcode == 9)
			resetWhenWalk = buffer.readUnsignedByte();
		else if (10 == opcode)
			priority = buffer.readUnsignedByte();
		else if (opcode == 11)
			delayType = buffer.readUnsignedByte();
		else if (opcode == 12) {
			count = buffer.readUnsignedByte();
			baseIDs = new int[count];
			baseArchives = new int[count];
			baseFiles = new int[count];
			for (int index = 0; index < count; index++) {
				baseIDs[index] = buffer.readUnsignedShort();
				baseArchives[index] = baseIDs[index];
			}
			for (int index = 0; index < count; index++) {
				int file = buffer.readUnsignedShort();
				baseIDs[index] = ((file << 16) + baseIDs[index]);
				baseFiles[index] = file;
			}
		} else if (13 == opcode) {
			count = buffer.readUnsignedByte();
			sounds = new int[count][];
			for (int sound = 0; sound < count; sound++) {
				sounds[sound][0] = buffer.read24BitUnsignedInteger();
			}
		} else {
			System.out.println("Missing OSRS Animation opcode: " + opcode + ", Animation: " + id);
		}
	}
	
}
