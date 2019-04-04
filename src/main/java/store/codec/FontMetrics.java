/**
 * 
 */
package store.codec;

import store.FileStore;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Feb 2, 2019
 */
public class FontMetrics implements AbstractDefinition, Cloneable {

	private byte[] aByteArray6199;
	private byte[][] aByteArrayArray6198;
	private int anInt6200;
	private int anInt6202;
	private int anInt6197;
	
	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#decode(com.alex.io.InputStream)
	 */
	@Override
	public void decode(InputStream buffer) {
		int i = buffer.readUnsignedByte();
		if (i != 0)
			throw new RuntimeException("Invalid header");
		boolean bool = buffer.readUnsignedByte() == 1;
		aByteArray6199 = new byte[256];
		buffer.readBytes(aByteArray6199, 0, 256);
		if (bool) {
			int[] is_43_ = new int[256];
			int[] is_44_ = new int[256];
			for (int i_45_ = 0; i_45_ < 256; i_45_++)
				is_43_[i_45_] = buffer.readUnsignedByte();
			for (int i_46_ = 0; i_46_ < 256; i_46_++)
				is_44_[i_46_] = buffer.readUnsignedByte();
			byte[][] is_47_ = new byte[256][];
			for (int i_48_ = 0; i_48_ < 256; i_48_++) {
				is_47_[i_48_] = new byte[is_43_[i_48_]];
				byte i_49_ = 0;
				for (int i_50_ = 0; i_50_ < is_47_[i_48_].length; i_50_++) {
					i_49_ += buffer.readByte();
					is_47_[i_48_][i_50_] = i_49_;
				}
			}
			byte[][] is_51_ = new byte[256][];
			for (int i_52_ = 0; i_52_ < 256; i_52_++) {
				is_51_[i_52_] = new byte[is_43_[i_52_]];
				byte i_53_ = 0;
				for (int i_54_ = 0; i_54_ < is_51_[i_52_].length; i_54_++) {
					i_53_ += buffer.readByte();
					is_51_[i_52_][i_54_] = i_53_;
				}
			}
			aByteArrayArray6198 = new byte[256][256];
			for (int index = 0; index < 256; index++) {
				if (index != 32) {
					for (int i_56_ = 0; i_56_ < 256; i_56_++) {
						if (i_56_ != 32)
							aByteArrayArray6198[index][i_56_] = (byte) method392(is_47_, is_51_, is_44_, aByteArray6199, is_43_, index, i_56_);
					}
				}
			}
			anInt6200 = (is_44_[32] + is_43_[32]);
		} else {
			anInt6200 = buffer.readUnsignedByte();
		}
		buffer.readUnsignedByte();
		buffer.readUnsignedByte();
		anInt6202 = buffer.readUnsignedByte();
		anInt6197 = buffer.readUnsignedByte();
	}
	
	private static int method392(byte[][] is, byte[][] is_1_, int[] is_2_, byte[] is_3_, int[] is_4_, int index, int i_5_) {
		int i_7_ = is_2_[index];
		int i_8_ = is_4_[index] + i_7_;
		int i_9_ = is_2_[i_5_];
		int i_10_ = i_9_ + is_4_[i_5_];
		int i_11_ = i_7_;
		if (i_9_ > i_7_)
			i_11_ = i_9_;
		int i_12_ = i_8_;
		if (i_10_ < i_8_)
			i_12_ = i_10_;
		int i_13_ = is_3_[index] & 0xff;
		if ((is_3_[i_5_] & 0xff) < i_13_)
			i_13_ = is_3_[i_5_] & 0xff;
		byte[] is_14_ = is_1_[index];
		byte[] is_15_ = is[i_5_];
		int i_16_ = i_11_ - i_7_;
		int i_17_ = i_11_ - i_9_;
		for (int i_18_ = i_11_; i_18_ < i_12_; i_18_++) {
			int i_19_ = is_14_[i_16_++] + is_15_[i_17_++];
			if (i_19_ < i_13_)
				i_13_ = i_19_;
		}
		return -i_13_;
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
