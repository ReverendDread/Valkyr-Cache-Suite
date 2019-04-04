package store.codec;

import store.FileStore;
import store.Indices;
import store.io.InputStream;
import store.io.OutputStream;

public class ParticleDefinition implements AbstractDefinition, Cloneable {

	public boolean aBoolean1818;
	public int anInt1819;
	public short minimumAngleH;
	public short minimumAngleV;
	public int[] anIntArray1823;
	public int minimumSpeed;
	public int anInt1825;
	public int anInt1826;
	public int anInt1827;
	public int endSpeed;
	public int[] anIntArray1829;
	public int changeSpeed;
	public int maximumSize;
	public int endSize;
	public int sizeChange;
	public int minimumStartColour;
	public int maximumStartColour;
	public boolean activeFirst;
	public int fadeColour;
	public int fadingColour;
	public int fadingAlpha;
	public int texture;
	public int minimumLifetime;
	public int maximumLifetime;
	public int minimumParticleRate;
	public int maximumParticleRate;
	public int anInt1845;
	public int[] anIntArray1846;
	public int anInt1847;
	public int[] anIntArray1848;
	public int anInt1850;
	public int anInt1851;
	public int anInt1852;
	public short maximumAngleH;
	public boolean periodic;
	public int anInt1855;
	public int anInt1856;
	public int minimumSetting;
	public int maximumSpeed;
	public int lifetime;
	public boolean uniformColourVariance;
	public boolean aBoolean1862;
	public boolean aBoolean1863;
	public boolean aBoolean1864;
	public int anInt1865;
	public int anInt1866;
	public int anInt1867;
	public int anInt1868;
	public int minimumSize;
	public int anInt1870;
	public int anInt1871;
	public int anInt1872;
	public int anInt1873;
	public boolean aBoolean1874;
	public short maximumAngleV;
	public int anInt1876;
	public boolean aBoolean1877;
	public int anInt1878;
	public int anInt1879;
	public int anInt1880;
	public int anInt1881;
	public int anInt1882;
	public int anInt1883;
	public int anInt1884;
	public int anInt1885;
	public int anInt1886;
	public int opcode2;
	public int opcode29;
	public int id;
	
	public ParticleDefinition(int id) {
		this.id = id;
		activeFirst = true;
		periodic = true;
		aBoolean1877 = true;
		aBoolean1874 = true;
		aBoolean1863 = true;
		uniformColourVariance = true;
	}
	
	@Override
	public void decode(InputStream stream) {
		try {			
			while (true) {	
				int opcode = stream.readUnsignedByte();				
				if (opcode == 0)
					break;				
				if (1 == opcode) {
					minimumAngleH = (short) stream.readUnsignedShort();
					maximumAngleH = (short) stream.readUnsignedShort();
					minimumAngleV = (short) stream.readUnsignedShort();
					maximumAngleV = (short) stream.readUnsignedShort();
				} else if (2 == opcode)
					opcode2 = stream.readUnsignedByte();
				else if (3 == opcode) {
					int min = stream.readInt();
					int max = stream.readInt();
					minimumSpeed = min;
					maximumSpeed = max;
				} else if (opcode == 4) {
					anInt1819 = stream.readUnsignedByte();
					anInt1847 = stream.readByte();
				} else if (5 == opcode)
					minimumSize = maximumSize = (stream.readUnsignedShort());
				else if (6 == opcode) {
					minimumStartColour = stream.readInt();
					maximumStartColour = stream.readInt();
				} else if (7 == opcode) {
					minimumLifetime = stream.readUnsignedShort();
					maximumLifetime = stream.readUnsignedShort();
				} else if (opcode == 8) {
					minimumParticleRate = stream.readUnsignedShort();
					maximumParticleRate = stream.readUnsignedShort();
				} else if (9 == opcode) {
					int i_3_ = stream.readUnsignedByte();
					anIntArray1823 = new int[i_3_];
					for (int i_4_ = 0; i_4_ < i_3_; i_4_++)
						anIntArray1823[i_4_] = stream.readUnsignedShort();
				} else if (10 == opcode) {
					int i_5_ = stream.readUnsignedByte();
					anIntArray1829 = new int[i_5_];
					for (int i_6_ = 0; i_6_ < i_5_; i_6_++)
						anIntArray1829[i_6_] = stream.readUnsignedShort();
				} else if (12 == opcode)
					anInt1850 = stream.readByte();
				else if (opcode == 13)
					anInt1851 = stream.readByte();
				else if (14 == opcode)
					anInt1852 = stream.readUnsignedShort();
				else if (15 == opcode)
					texture = stream.readUnsignedShort();
				else if (opcode == 16) {
					activeFirst = stream.readUnsignedByte() == 1;
					anInt1855 = stream.readUnsignedShort();
					lifetime = stream.readUnsignedShort();
					periodic = stream.readUnsignedByte() == 1;
				} else if (opcode == 17)
					anInt1873 = stream.readUnsignedShort();
				else if (18 == opcode)
					fadeColour = stream.readInt();
				else if (opcode == 19)
					minimumSetting = stream.readUnsignedByte();
				else if (opcode == 20)
					fadingColour = stream.readUnsignedByte();
				else if (21 == opcode)
					fadingAlpha = stream.readUnsignedByte();
				else if (opcode == 22)
					endSpeed = stream.readInt();
				else if (23 == opcode)
					changeSpeed = stream.readUnsignedByte();
				else if (24 == opcode)
					uniformColourVariance = false;
				else if (25 == opcode) {
					int i_7_ = stream.readUnsignedByte();
					anIntArray1846 = new int[i_7_];
					for (int i_8_ = 0; i_8_ < i_7_; i_8_++)
						anIntArray1846[i_8_] = stream.readUnsignedShort();
				} else if (opcode == 26)
					aBoolean1877 = false;
				else if (opcode == 27)
					endSize = (stream.readUnsignedShort());
				else if (opcode == 28)
					sizeChange = stream.readUnsignedByte();
				else if (29 == opcode)
					opcode29 = stream.readShort();
				else if (30 == opcode)
					aBoolean1818 = true;
				else if (opcode == 31) {
					minimumSize = (stream.readUnsignedShort());
					maximumSize = (stream.readUnsignedShort());
				} else if (32 == opcode)
					aBoolean1874 = false;
				else if (33 == opcode)
					aBoolean1862 = true;
				else if (34 == opcode)
					aBoolean1863 = false;				
			}	
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] encode() {
		OutputStream stream = new OutputStream();
		
		int length;
	
		if (minimumAngleH != 0 || minimumAngleH != 0 || minimumAngleV != 0 || maximumAngleV != 0) {
			stream.writeByte(1); //opcode 1
			stream.writeShort(minimumAngleH);
			stream.writeShort(maximumAngleH);
			stream.writeShort(minimumAngleV);
			stream.writeShort(maximumAngleV);
		}

		if (opcode2 != 0) {
			stream.writeByte(2); //opcode 2
			stream.writeByte(opcode2);
		}
		
		if (maximumSpeed > 0) {
			stream.writeByte(3); //opcode 3
			stream.writeInt(minimumSpeed);
			stream.writeInt(maximumSpeed);
		}
		
		if (anInt1819 != 0 && anInt1847 != 0) {
			stream.writeByte(4); //opcode 4
			stream.writeByte(anInt1819);
			stream.writeByte(anInt1847);
		}
	
		if (minimumSize == maximumSize) {
			stream.writeByte(5);//opcode 5
			stream.writeShort(minimumSize);
		} else {
			stream.writeByte(31); //opcode 31
			stream.writeShort(minimumSize);
			stream.writeShort(maximumSize);
		}
		
		if (minimumStartColour != 0 || maximumStartColour != 0) {
			stream.writeByte(6); //opcode 6
			stream.writeInt(minimumStartColour);
			stream.writeInt(maximumStartColour);
		}
		
		if (maximumLifetime > 0) { 
			stream.writeByte(7); //opcode 7
			stream.writeShort(minimumLifetime);
			stream.writeShort(maximumLifetime);
		}
		
		if (maximumParticleRate > 0) {
			stream.writeByte(8); //opcode 8
			stream.writeShort(minimumParticleRate);
			stream.writeShort(maximumParticleRate);
		}
		
		if (anIntArray1823 != null) {
			stream.writeByte(9); //opcode 9
			length = anIntArray1823.length;
			stream.writeByte(length);
			for (int index = 0; index < length; index++) {
				stream.writeShort(anIntArray1823[index]);
			}
		}
		
		if (anIntArray1829 != null) {
			stream.writeByte(10); //opcode 10
			length = anIntArray1829.length;
			stream.writeByte(length);
			for (int index = 0; index < length; index++) {
				stream.writeShort(anIntArray1829[index]);
			}
		}
		
		if (anInt1850 != 0) {
			stream.writeByte(12); //opcode 12
			stream.writeByte(anInt1850);
		}
		
		if (anInt1851 != 0) {
			stream.writeByte(13); //opcode 13
			stream.writeByte(anInt1851);
		}
		
		if (anInt1852 != 0) {
			stream.writeByte(14); //opcode 14
			stream.writeShort(anInt1852);
		}
		
		if (texture != 0) {
			stream.writeByte(15); //opcode 15
			stream.writeShort(texture);
		}
		
		stream.writeByte(16); //opcode 16
		stream.writeByte(activeFirst ? 1 : 0);
		stream.writeShort(anInt1855);
		stream.writeShort(lifetime);
		stream.writeByte(periodic ? 1 : 0);
		
		if (anInt1873 != 0) {
			stream.writeByte(17); //opcode 17
			stream.writeShort(anInt1873);
		}
		
		if (fadeColour != 0) {
			stream.writeByte(18); //opcode 18
			stream.writeInt(fadeColour);
		}
		
		if (minimumSetting != 0) {
			stream.writeByte(19); //opcode 19
			stream.writeByte(minimumSetting);
		}
		
		if (fadingColour != 0) {
			stream.writeByte(20); //opcode 20
			stream.writeByte(fadingColour);
		}
		
		if (fadingAlpha != 0) {
			stream.writeByte(21); //opcode 21
			stream.writeByte(fadingAlpha);
		}
		
		if (endSpeed != 0) {
			stream.writeByte(22); //opcode 22
			stream.writeInt(endSpeed);
		}
		
		if (changeSpeed != 0) {
			stream.writeByte(23); //opcode 23
			stream.writeByte(changeSpeed);
		}
		
		if (!uniformColourVariance) //opcode 24
			stream.writeByte(24);
		
		if (anIntArray1846 != null) {
			stream.writeByte(25); //opcode 25
			length = anIntArray1846.length;
			stream.writeByte(length);
			for (int index = 0; index < length; index++) {
				stream.writeShort(anIntArray1846[index]);
			}
		}
		
		if (!aBoolean1877) //opcode 26
			stream.writeByte(26);		
		
		if (endSize != 0) {
			stream.writeByte(27); //opcode 27
			stream.writeShort(endSize);
		}
		
		if (sizeChange != 0) {
			stream.writeByte(28); //opcode 28
			stream.writeByte(sizeChange);
		}
		
		if (opcode29 != 0) {
			stream.writeByte(29); //opcode 29
			stream.writeShort(opcode29);
		}
		
		if (aBoolean1818) //opcode 30
			stream.writeByte(30);
		
		if (!aBoolean1874) //opcode 32
			stream.writeByte(32);
		
		if (aBoolean1862) //opcode 33
			stream.writeByte(33);
		
		if (!aBoolean1863) //opcode 34
			stream.writeByte(34);	
		
		stream.writeByte(0);
		byte[] streamPayload = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(streamPayload, 0, streamPayload.length);	
		
		return streamPayload;
	}

	@Override
	public boolean save(FileStore cache) {
		return cache.getIndexes()[Indices.PARTICLES.getIndex()].putFile(0, id, encode());
	}
	
	@Override
	public String toString() {
		return "" + this.id;
	}

}
