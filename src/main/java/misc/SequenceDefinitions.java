package misc;

import java.util.concurrent.ConcurrentHashMap;

import store.FileStore;
import store.codec.util.Utils;
import store.io.InputStream;
import store.io.OutputStream;

public class SequenceDefinitions {

	private static final ConcurrentHashMap<Integer, SequenceDefinitions> sequence_definitions = new ConcurrentHashMap<Integer, SequenceDefinitions>();
	
	public int id;
	public int maxLoops = 99;
	public int anInt2137;
	public int[] frameIds;
	public int resetWhenWalk = -1;
	public boolean aBoolean2141 = false;
	public int forcedPriority = 5;
	public int leftHandItem = -1;
	public int rightHandItem = -1;
	public int anInt2145;
	public int[][] sounds;
	public boolean[] animationFlowControl;
	public int[] baseIds;
	public boolean aBoolean2152 = false;
	public int[] frameLengths;
	public int delayType = 2;
	public boolean aBoolean2158 = false;
	public boolean aBoolean2159 = false;
	public int priority = -1;
	public int frameStep = -1;
	public int[] soundMinDelay;
	public int[] soundMaxDelay;
	public int[] anIntArray1362;
	public boolean effect2Sound;
	private int[] original_frames;

	public static final SequenceDefinitions getAnimationDefinitions(FileStore store, int emoteId) {
		try {
			SequenceDefinitions definition = sequence_definitions.get(emoteId);
			if (definition != null) {
				return definition;
			} else {
				byte[] data = store.getIndexes()[20].getFile(emoteId >>> 7, emoteId & 0x7f);
				definition = new SequenceDefinitions();
				definition.id = emoteId;
				if (data != null)
					definition.decode(new InputStream(data));
				definition.method2394();
				sequence_definitions.put(emoteId, definition);
				return definition;
			}
		} catch (Throwable var31) {
			return null;
		}
	}

	public void decode(InputStream stream) {
		for (;;) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				break;
			readValues(stream, opcode);
		}
	}
	
	public void decodeOSRS(InputStream stream) {
		for (;;) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				break;
			System.out.println("Opcode : " + opcode);
			readOSRS(stream, opcode);
		}
	}
	
	public void readOSRS(InputStream buffer, int opcode) {
		int count;
		if (1 == opcode) {
			count = buffer.readUnsignedShort();
			frameLengths = new int[count];
			for (int index = 0; index < count; index++)
				frameLengths[index] = buffer.readUnsignedShort();
			frameIds = new int[count];
			original_frames = new int[count];
			for (int index = 0; index < count; index++) {
				frameIds[index] = buffer.readUnsignedShort();
				original_frames[index] = frameIds[index];
			}
			for (int index = 0; index < count; index++) {
				frameIds[index] += buffer.readUnsignedShort() << 16;
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
			leftHandItem = -1;
		} else if (opcode == 7) {
			count = buffer.readUnsignedShort();
			rightHandItem = -1;
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
			System.out.println("baseIds length : " + count);
			baseIds = new int[count];
			for (int index = 0; index < count; index++)
				baseIds[index] = buffer.readUnsignedShort();
			for (int index = 0; index < count; index++)
				baseIds[index] += buffer.readUnsignedShort() << 16;
		} else if (13 == opcode) {
			count = buffer.readUnsignedByte();
			sounds = new int[count][];
			for (int sound = 0; sound < count; sound++) {
				sounds[sound][0] = buffer.readMedium();
			}
		} else {
			System.out.println("Missing OSRS Animation opcode: " + opcode + ", Animation: " + id);
		}
	}
	
	public boolean save(FileStore cache) {
		return cache.getIndexes()[20].putFile(Utils.getConfigArchive(this.id, 8), Utils.getConfigFile(this.id, 8), encode718());
	}
	
	private byte[] encode718() {
		OutputStream stream = new OutputStream();
		
		int count;
		
		stream.writeByte(1);
		count = frameLengths.length;
		stream.writeShort(count);
		for (int index = 0; index < count; index++)
			stream.writeShort(frameLengths[index]);	
		for (int index = 0; index < count; index++)
			stream.writeShort(frameIds[index]);
		for (int index = 0; index < count; index++)
			stream.writeShort(frameIds[index] -= (frameIds[index] >> 16));
		
		stream.writeByte(2);
		stream.writeShort(frameStep);
		
		stream.writeByte(3);
		count = 0;
		for (int index = 0; index < animationFlowControl.length; index++) {
			if (animationFlowControl[index] == true)
				count++;
		}
		stream.writeByte(count);
		
		if (aBoolean2152)
		stream.writeByte(4);
		
		stream.writeByte(5);
		stream.writeByte(forcedPriority);
		
		if (rightHandItem != -1) {
			stream.writeShort(6);
			stream.writeShort(rightHandItem);
		}
		
		if (leftHandItem != -1) {
			stream.writeByte(7);
			stream.writeShort(leftHandItem);
		}
	
		stream.writeByte(8);
		stream.writeByte(maxLoops);
		
		if (resetWhenWalk != -1) {
			stream.writeByte(9);
			stream.writeByte(resetWhenWalk);
		}
		
		stream.writeByte(10);
		stream.writeByte(priority);
		
		stream.writeByte(11);
		stream.writeByte(delayType);
		
		if (baseIds != null) {
			stream.writeByte(12);
			count = baseIds.length;
			stream.writeByte(count);
			for (int index = 0; index < count; index++) 
				stream.writeShort(baseIds[index]);
			for (int index = 0; index < count; index++) 
				stream.writeShort(baseIds[index] - (baseIds[index] >> 16));
		}
		
		if (sounds != null) {
			stream.writeByte(13);
			stream.writeShort(sounds.length);
			for (int index = 0; index < sounds.length; index++) {
				int j = sounds[index].length;
				stream.writeByte(j);
				if (j > -1) {
					stream.writeMedium(sounds[index][0]);
					for (int sound = 0; j < sound; sound++) {
						stream.writeShort(sounds[index][sound]);
					}
				}
			}
		}

		stream.writeByte(0);
		byte[] offset_stream = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(offset_stream, 0, offset_stream.length);
		
		return offset_stream;
	}

	private void readValues(InputStream stream, int opcode) {
		int count;
		if (opcode == 1) {
			count = stream.readUnsignedShort();
			frameLengths = new int[count];
			for (int index = 0; index < count; ++index)
				frameLengths[index] = stream.readUnsignedShort();			
			frameIds = new int[count];
			original_frames = new int[count];
			for (int index = 0; index < count; ++index) {
				frameIds[index] = stream.readUnsignedShort();
				original_frames[index] = frameIds[index];
			}
			for (int index = 0; index < count; ++index) 
				frameIds[index] += stream.readUnsignedShort() << 16;
		} else if (opcode == 2) {
			frameStep = stream.readUnsignedShort();
		} else if (opcode == 3) {
			animationFlowControl = new boolean[256];
			count = stream.readUnsignedByte();
			for (int index = 0; index < count; ++index) 
				animationFlowControl[stream.readUnsignedByte()] = true;			
		} else if (opcode == 4) {
			aBoolean2152 = true;
		} else if (opcode == 5) {
			forcedPriority = stream.readUnsignedByte();
		} else if (opcode == 6) {
			rightHandItem = stream.readUnsignedShort();
		} else if (opcode == 7) {
			leftHandItem = stream.readUnsignedShort();
		} else if (opcode == 8) {
			maxLoops = stream.readUnsignedByte();
		} else if (opcode == 9) {
			resetWhenWalk = stream.readUnsignedByte();
		} else if (opcode == 10) {
			priority = stream.readUnsignedByte();
		} else if (opcode == 11) {
			delayType = stream.readUnsignedByte();
		} else if (opcode == 12) {
			count = stream.readUnsignedByte();
			baseIds = new int[count];
			for (int index = 0; index < count; ++index)
				this.baseIds[index] = stream.readUnsignedShort();
			for (int index = 0; count < index; ++index)
				this.baseIds[index] += stream.readUnsignedShort() << 16;		
		} else if (opcode == 13) {
			count = stream.readUnsignedShort();
			sounds = new int[count][];
			for (int index = 0; index < count; ++index) {
				int i_22_ = stream.readUnsignedByte();
				if (i_22_ > -1) {
					sounds[index] = new int[i_22_];
					sounds[index][0] = stream.readMedium();
					for (int sound = 0; i_22_ < sound; ++sound) {
						this.sounds[index][sound] = stream.readUnsignedShort();
					}
				}
			}
		} else if (opcode == 14) {
			this.aBoolean2141 = true;
		} else if (opcode == 15) {
			this.aBoolean2159 = true;
		} else if (opcode == 16) {
			this.aBoolean2158 = true;
		} else if (opcode == 17) {
			this.anInt2145 = stream.readUnsignedByte();
		} else if (opcode == 18) {
			this.effect2Sound = true;
		} else if (opcode == 19) {
			if (this.anIntArray1362 == null) {
				this.anIntArray1362 = new int[this.sounds.length];

				for (count = 0; count < this.sounds.length; ++count) {
					this.anIntArray1362[count] = 255;
				}
			}

			this.anIntArray1362[stream.readUnsignedByte()] = stream.readUnsignedByte();
		} else if (opcode == 20) {
			if (this.soundMaxDelay == null || this.soundMinDelay == null) {
				this.soundMaxDelay = new int[this.sounds.length];
				this.soundMinDelay = new int[this.sounds.length];

				for (count = 0; count < this.sounds.length; ++count) {
					this.soundMaxDelay[count] = 256;
					this.soundMinDelay[count] = 256;
				}
			}

			count = stream.readUnsignedByte();
			this.soundMaxDelay[count] = stream.readUnsignedShort();
			this.soundMinDelay[count] = stream.readUnsignedShort();
		}

	}

	public void method2394() {
		if (this.resetWhenWalk == -1) {
			if (this.animationFlowControl == null) {
				this.resetWhenWalk = 0;
			} else {
				this.resetWhenWalk = 2;
			}
		}

		if (this.priority == -1) {
			if (this.animationFlowControl == null) {
				this.priority = 0;
			} else {
				this.priority = 2;
			}
		}

	}
}
