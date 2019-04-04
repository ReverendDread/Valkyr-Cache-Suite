/**
 * 
 */
package store.codec.osrs;

import java.util.Map;

import store.FileStore;
import store.codec.AbstractDefinition;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Sep 18, 2018
 */
public class NPCDefinition implements AbstractDefinition {

	public int walkAnimation;
	public int rotate180Animation;
	public int rotate90RightAnimation;
	public int rotate90LeftAnimation;
	public int anInt2189;
	public int anInt2165;
	public int idleAnimation;
	public int size;
	public String name;
	public int[] models;
	public String[] options;
	public int[] recolorToFind;
	public int[] recolorToReplace;
	public int[] retextureToFind;
	public int[] retextureToReplace;
	public int[] chatHeads;
	public int rotation;
	public int headIcon;
	public int contrast;
	public int ambient;
	public boolean renderPriority;
	public int resizeY;
	public int resizeX;
	public int combatLevel;
	public int varbit;
	public int varp;
	public int[] transforms;
	public boolean clickable;
	public boolean aBoolean6164;
	public boolean aBoolean6165;
	public boolean invisable;
	public Map<Integer, Object> params;

	public NPCDefinition(byte[] data) {
		models = new int[] { 0 };
		resizeX = 128;
		resizeY = 128;
		renderPriority = true;
		invisable = true;
		aBoolean6164 = true;
		aBoolean6165 = true;
		name = "null";
		options = new String[5];
		decode(new InputStream(data));
	}
	
	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#decode(com.alex.io.InputStream)
	 */
	@Override
	public void decode(InputStream stream) {
		while (true) {
			
			int opcode = stream.readUnsignedByte();
			
			if (opcode == 0)
				break;
			
			if (opcode == 1) {
				int length = stream.readUnsignedByte();
				models = new int[length];
				for (int index = 0; index < length; index++) {
					models[index] = stream.readUnsignedShort();
				}
			} else if (opcode == 2) {
				name = stream.readString();
			} else if (opcode == 12) {
				size = stream.readUnsignedByte();
			} else if (opcode == 13) {
				idleAnimation = stream.readUnsignedShort();
			} else if (opcode == 14) {
				walkAnimation = stream.readUnsignedShort();
			} else if (opcode == 15) {
				anInt2165 = stream.readUnsignedShort();
			} else if (opcode == 16) {
				anInt2189 = stream.readUnsignedShort();
			} else if (opcode == 17) {
				walkAnimation = stream.readUnsignedShort();
				rotate180Animation = stream.readUnsignedShort();
				rotate90RightAnimation = stream.readUnsignedShort();
				rotate90LeftAnimation = stream.readUnsignedShort();
			} else if (opcode >= 30 && opcode < 35) {
				options[opcode - 30] = stream.readString();
				if (options[opcode - 30].equalsIgnoreCase("hidden") || options[opcode - 30].equalsIgnoreCase("null")) {
					options[opcode - 30] = null;
				}
			} else if (opcode == 40) {
				int length = stream.readUnsignedByte();
				recolorToFind = new int[length];
				recolorToReplace = new int[length];
				for (int index = 0; index < length; ++index) {
					recolorToFind[index] = stream.readUnsignedShort();
					recolorToReplace[index] = stream.readUnsignedShort();
				}
			} else if (opcode == 41) {
				int length = stream.readUnsignedByte();
				retextureToFind = new int[length];
				retextureToReplace = new int[length];
				for (int index = 0; index < length; ++index) {
					retextureToFind[index] = stream.readUnsignedShort();
					retextureToReplace[index] = stream.readUnsignedShort();
				}
			} else if (opcode == 60) {
				int length = stream.readUnsignedByte();
				chatHeads = new int[length];
				for (int index = 0; index < length; index++) {
					chatHeads[index] = stream.readUnsignedShort();
				}
			} else if (opcode == 93) {
				invisable = false;
			} else if (opcode == 95) {
				combatLevel = stream.readUnsignedShort();
			} else if (opcode == 97) {
				resizeX = stream.readUnsignedShort();
			} else if (opcode == 98) {
				resizeY = stream.readUnsignedShort();
			} else if (opcode == 99) {
				renderPriority = false;
			} else if (opcode == 100) {
				ambient = stream.readByte();
			} else if (opcode == 101) {
				contrast = stream.readByte();
			} else if (opcode == 102) {
				headIcon = stream.readUnsignedShort();
			} else if (opcode == 103) {
				rotation = stream.readUnsignedShort();
			} else if (opcode == 106 || opcode == 118) {
				varbit = stream.readUnsignedShort();
				if (varbit == 65535)
					varbit = -1;
				varp = stream.readUnsignedShort();
				if (varp == 65535)
					varp = -1;
				int transform = -1;
				if (opcode == 118) {
					transform = stream.readUnsignedShort();
					if (transform == 65535)
						transform = -1;
				}
				int length = stream.readUnsignedByte();
				transforms = new int[2 + length];
				for (int index = 0; index <= length; index++) {
					transforms[index] = stream.readUnsignedShort();
					if (65535 == transforms[index]) 
						transforms[index] = -1;
				}
				transforms[length + 1] = transform;
			} else if (opcode == 107) {
				clickable = false;
			} else if (opcode == 109) {
				aBoolean6164 = false;
			} else if (opcode == 111) {
				aBoolean6165 = false;
			}	
		}
	}

	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#encode()
	 */
	@Override
	public byte[] encode() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#save(com.alex.store.FileStore)
	 */
	@Override
	public boolean save(FileStore cache) {
		// TODO Auto-generated method stub
		return false;
	}

}
