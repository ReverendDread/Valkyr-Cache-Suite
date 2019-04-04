package store.codec;

import store.FileStore;
import store.Indices;
import store.io.InputStream;
import store.io.OutputStream;

public class MagnetDefinition implements AbstractDefinition, Cloneable {

	public int id;
	public int anInt1922;
	public int anInt1933;
	public int anInt1934;
	public int anInt1935;
	public int anInt1936;
	public int anInt1920;
	public int anInt1940;
	public int anInt1942;
	public int anInt1939;
	public boolean aBoolean1927;
	
	public MagnetDefinition(int id) {
		this.id = id;
		anInt1939 = 0;
		aBoolean1927 = false;	
	}
	
	@Override
	public void decode(InputStream stream) {
		while (true) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				break;
			if (1 == opcode)
				anInt1922 = stream.readUnsignedShort();
			else if (2 == opcode)
				stream.readUnsignedByte();
			else if (opcode == 3) {
				anInt1933 = stream.readInt();
				anInt1934 = stream.readInt();
				anInt1935 = stream.readInt();
			} else if (4 == opcode) {
				anInt1936 = stream.readUnsignedByte();
				anInt1920 = stream.readInt();
			} else if (6 == opcode)
				anInt1940 = stream.readUnsignedByte();
			else if (opcode == 8)
				anInt1942 = 4;
			else if (9 == opcode)
				anInt1939 = -1;
			else if (opcode == 10)
				aBoolean1927 = true;
		}
	}

	@Override
	public byte[] encode() {
		OutputStream stream = new OutputStream();
		
		stream.writeByte(1);
		stream.writeShort(anInt1922);
		
		stream.writeByte(3);
		stream.writeInt(anInt1933);
		stream.writeInt(anInt1934);
		stream.writeInt(anInt1935);
		
		stream.writeByte(4);
		stream.writeByte(anInt1936);
		stream.writeInt(anInt1920);
		
		stream.writeByte(6);
		stream.writeByte(anInt1940);
		
		if (anInt1942 == 4)
			stream.writeByte(8);
		
		if (anInt1939 == -1)
			stream.writeByte(9);
		
		if (aBoolean1927)
			stream.writeByte(10);
		
		stream.writeByte(0);
		byte[] flipped = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(flipped, 0, flipped.length);
		return flipped;
	}

	@Override
	public boolean save(FileStore cache) {
		return cache.getIndexes()[Indices.PARTICLES.getIndex()].putFile(1, id, encode());
	}

}
