/**
 * 
 */
package store.codec;

import store.FileStore;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Jan 14, 2019
 */
public class FloDefinition implements AbstractDefinition {

	private int color;
	private int texture;
	private boolean occlude;
	private int blending;
	private int scale;
	private boolean blockShadow;
	private boolean aBoolean4061;
	private int anInt4055;
	private boolean aBoolean4063;
	private int waterColor;
	private int waterScale;
	private int waterIntensity;
	private int anInt4067;
	private int anInt4068;
	private int anInt4069;
	
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
				//Probably should leave this as rgb so i can use rgb color picker in editor
				color = convertRGBtoHSL(stream.read24BitUnsignedInteger());
			} else if (opcode == 2) {
				texture = stream.readUnsignedByte();
			} else if (opcode == 3) {
				texture = stream.readUnsignedShort();
				if (texture == 65535) {
					texture = -1;
				}
			} else if (opcode == 5) {
				occlude = false;
			} else if (opcode == 7) {
				//Probably should leave this as rgb so i can use rgb color picker in editor
				blending = convertRGBtoHSL(stream.read24BitUnsignedInteger());
			} else if (opcode == 8) {			
				//minimap tile color i think?
			} else if (opcode == 9) {
				scale = stream.readUnsignedShort() << 2;
			} else if (opcode == 10) {
				blockShadow = false;
			} else if (opcode == 11) {
				anInt4055 = stream.readUnsignedByte();
			} else if (opcode == 12) {
				aBoolean4063 = true;
			} else if (opcode == 13) {
				waterColor = stream.read24BitUnsignedInteger();
			} else if (opcode == 14) {
				waterScale = stream.readUnsignedByte() << 2;
			} else if (opcode == 16) {
				waterIntensity = stream.readUnsignedByte();
			} else if (opcode == 20) {
				anInt4067 = stream.readUnsignedShort();
			} else if (opcode == 21) {
				anInt4068 = stream.readUnsignedByte();
			} else if (opcode == 22) {
				anInt4069 = stream.readUnsignedShort();
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
		return false;
	}
	
	/**
	 * Converts an rgb value to its hsl equivalent.
	 * @param rgb
	 * 			the rgb value to convert.
	 * @return
	 * 			the converted rgb to hsl.
	 */
	private int convertRGBtoHSL(int rgb) {
		double r = (double) (rgb >> 16 & 0xff) / 256.0;
		double g = (double) (rgb >> 8 & 0xff) / 256.0;
		double b = (double) (rgb & 0xff) / 256.0;
		double min = r;
		if (g < min)
			min = g;
		if (b < min)
			min = b;
		double max = r;
		if (g > max)
			max = g;
		if (b > max)
			max = b;
		double h = 0.0;
		double s = 0.0;
		double l = (max + min) / 2.0;
		if (max != min) {
			if (l < 0.5)
				s = (max - min) / (min + max);
			if (l >= 0.5)
				s = (max - min) / (2.0 - max - min);
			if (r == max)
				h = (g - b) / (max - min);
			else if (g == max)
				h = (b - r) / (max - min) + 2.0;
			else if (b == max)
				h = 4.0 + (r - g) / (max - min);
		}
		h /= 6.0;
		int hue = (int) (256.0 * h);
		int saturation = (int) (s * 256.0);
		int luminance = (int) (256.0 * l);
		if (saturation < 0)
			saturation = 0;
		else if (saturation > 255)
			saturation = 255;
		if (luminance < 0)
			luminance = 0;
		else if (luminance > 255)
			luminance = 255;
		if (luminance > 243)
			saturation >>= 4;
		else if (luminance > 217)
			saturation >>= 3;
		else if (luminance > 192)
			saturation >>= 2;
		else if (luminance > 179)
			saturation >>= 1;
		return (((hue & 0xff) >> 2 << 10) + (saturation >> 5 << 7) + (luminance >> 1));
	}

}
