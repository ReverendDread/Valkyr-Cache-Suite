package store.codec;

import java.util.HashMap;
import java.util.Map;

import store.FileStore;
import store.Indices;
import store.codec.util.BitUtils;
import store.codec.util.Utils;
import store.io.InputStream;
import store.io.OutputStream;

public class SequenceDefinition implements AbstractDefinition, Cloneable {

	public int id;
	public int[] frameLengths;
	public int anInt4171;
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
	public Map<Object, Long> params = null;
	
	@Override
	public void decode(InputStream stream) {
		while (true) {
			
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				break;
			
			if (1 == opcode) {
				int i_2_ = stream.readUnsignedShort();
				frameLengths = new int[i_2_];
				for (int i_3_ = 0; i_3_ < i_2_; i_3_++)
					frameLengths[i_3_] = stream.readUnsignedShort();
				frameIDs = new int[i_2_];
				for (int i_4_ = 0; i_4_ < i_2_; i_4_++)
					frameIDs[i_4_] = stream.readUnsignedShort();
				for (int i_5_ = 0; i_5_ < i_2_; i_5_++)
					frameIDs[i_5_] += (stream.readUnsignedShort()) << 16;
			} else if (2 == opcode)
				frameStep = stream.readUnsignedShort();
			else if (opcode == 3) {
				animationFlowControl = new boolean[256];
				int i_6_ = stream.readUnsignedByte();
				for (int i_7_ = 0; i_7_ < i_6_; i_7_++)
					animationFlowControl[stream.readUnsignedByte()] = true;
			} else if (5 == opcode)
				forcedPriority = stream.readUnsignedByte();
			else if (6 == opcode)
				leftHandItem = stream.readUnsignedShort();
			else if (opcode == 7)
				rightHandItem = stream.readUnsignedShort();
			else if (opcode == 8)
				maxLoops = stream.readUnsignedByte();
			else if (opcode == 9)
				resetWhenWalk = stream.readUnsignedByte();
			else if (10 == opcode)
				priority = stream.readUnsignedByte();
			else if (opcode == 11)
				delayType = stream.readUnsignedByte();
			else if (opcode == 12) {
				int i_8_ = stream.readUnsignedByte();
				baseIDs = new int[i_8_];
				for (int i_9_ = 0; i_9_ < i_8_; i_9_++)
					baseIDs[i_9_] = stream.readUnsignedShort();
				for (int i_10_ = 0; i_10_ < i_8_; i_10_++)
					baseIDs[i_10_] = ((stream.readUnsignedShort() << 16) + baseIDs[i_10_]);
			} else if (13 == opcode) {
				int i_11_ = stream.readUnsignedShort();
				sounds = new int[i_11_][];
				for (int i_12_ = 0; i_12_ < i_11_; i_12_++) {
					int i_13_ = stream.readUnsignedByte();
					if (i_13_ > 0) {
						sounds[i_12_] = new int[i_13_];
						sounds[i_12_][0] = stream.read24BitUnsignedInteger();
						for (int i_14_ = 1; i_14_ < i_13_; i_14_++)
							sounds[i_12_][i_14_] = stream.readUnsignedShort();
					}
				}
			} else if (14 == opcode)
				aBoolean4185 = true;
			else if (opcode == 15)
				aBoolean4176 = true;
			else if (16 == opcode) {
				/* empty */
			} else if (18 == opcode)
				aBoolean4173 = true;
			else if (19 == opcode) {
				if (null == anIntArray4188) {
					anIntArray4188 = new int[sounds.length];
					for (int i_15_ = 0; i_15_ < sounds.length; i_15_++)
						anIntArray4188[i_15_] = 255;
				}
				anIntArray4188[stream.readUnsignedByte()] = stream.readUnsignedByte();
			} else if (20 == opcode) {
				if (null == anIntArray4183 || anIntArray4190 == null) {
					anIntArray4183 = new int[sounds.length];
					anIntArray4190 = new int[sounds.length];
					for (int i_16_ = 0; i_16_ < sounds.length; i_16_++) {
						anIntArray4183[i_16_] = 256;
						anIntArray4190[i_16_] = 256;
					}
				}
				int i_17_ = stream.readUnsignedByte();
				anIntArray4183[i_17_] = stream.readUnsignedShort();
				anIntArray4190[i_17_] = stream.readUnsignedShort();
			} else if (opcode == 249) {
				int size = stream.readUnsignedByte();
				if (params == null) {
					int pow = BitUtils.nextPowerOfTwo(size);
					params = new HashMap<Object, Long>(pow);
				}
				for (int index = 0; index < size; index++) {
					boolean isString = stream.readUnsignedByte() == 1;
					int value = stream.read24BitUnsignedInteger();
					Object key;
					if (isString)
						key = stream.readString();
					else
						key = stream.readInt();
					params.put(key, (long) value);
				}
			}
			
		}
	}

	@Override
	public byte[] encode() {
		OutputStream stream = new OutputStream();
		return null;
	}

	@Override
	public boolean save(FileStore cache) {
		return cache.getIndexes()[Indices.SEQUENCES.getIndex()].putFile(Utils.getConfigArchive(id, 7), Utils.getConfigFile(id, 7), encode());
	}

}
