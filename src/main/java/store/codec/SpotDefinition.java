package store.codec;

import store.FileStore;
import store.Indices;
import store.io.InputStream;
import store.io.OutputStream;

public class SpotDefinition implements AbstractDefinition, Cloneable {

	public int anInt5203;
	public int modelId;
	public int animation;
	public int[] recolorToFind;
	public int[] recolorToReplace;
	public int[] retextureToFind;
	public int[] retextureToReplace;
	public int resizeY;
	public int rotation;
	public int ambient;
	public int contrast;
	public boolean aBoolean5215;
	public byte aByte5216;
	public int resizeX;
	public int id;
	
	public SpotDefinition(int id) {
		this.id = id;
		resizeY = 128;
		resizeX = 128;
		animation = 0;
		rotation = 0;
		ambient = 0;
		contrast = 0;
		aBoolean5215 = false;
		aByte5216 = (byte) 0;
		anInt5203 = 128;
	}

	@Override
	public void decode(InputStream stream) {
		try {
			while (true) {
				
				int opcode = stream.readUnsignedByte();
				
				if (opcode == 0)
					break;
				
				if (1 == opcode)
					modelId =  stream.readSmartInt();
				else if (opcode == 2)
					animation = stream.readSmartInt();
				else if (4 == opcode)
					resizeX = stream.readUnsignedShort();
				else if (opcode == 5)
					resizeY = stream.readUnsignedShort();
				else if (opcode == 6)
					rotation = stream.readUnsignedShort();
				else if (7 == opcode)
					ambient = stream.readUnsignedByte();
				else if (8 == opcode)
					contrast = stream.readUnsignedByte();
				else if (9 == opcode) {
					aByte5216 = (byte) 3;
					anInt5203 = 0;
				} else if (10 == opcode)
					aBoolean5215 = true;
				else if (opcode == 11)
					aByte5216 = (byte) 1;
				else if (12 == opcode)
					aByte5216 = (byte) 4;
				else if (opcode == 13)
					aByte5216 = (byte) 5;
				else if (14 == opcode) {
					aByte5216 = (byte) 2;
					anInt5203 = stream.readUnsignedByte();
				} else if (opcode == 15) {
					aByte5216 = (byte) 3;
					anInt5203 = stream.readUnsignedShort();
				} else if (16 == opcode) {
					aByte5216 = (byte) 3;
					anInt5203 = stream.readInt();
				} else if (40 == opcode) {
					int length = stream.readUnsignedByte();
					recolorToFind = new int[length];
					recolorToReplace = new int[length];
					for (int index = 0; index < length; index++) {
						recolorToFind[index] = (short) stream.readUnsignedShort();
						recolorToReplace[index] = (short) stream.readUnsignedShort();
					}
				} else if (41 == opcode) {
					int length = stream.readUnsignedByte();
					retextureToFind = new int[length];
					retextureToReplace = new int[length];
					for (int index = 0; index < length; index++) {
						retextureToFind[index] = (short) stream.readUnsignedShort();
						retextureToReplace[index] = (short) stream.readUnsignedShort();
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] encode() {
		OutputStream stream = new OutputStream();
		
		if (this.modelId != -1) {
			stream.writeByte(1);
			stream.writeSmartInt(this.modelId);
		}
		
		if (this.animation != -1) {
			stream.writeByte(2);
			stream.writeSmartInt(this.animation);
		}
		
		if (this.resizeX != 0) {
			stream.writeByte(4);
			stream.writeShort(this.resizeX);
		}
		
		if (this.resizeY != 0) {
			stream.writeByte(5);
			stream.writeShort(this.resizeY);
		}
		
		if (this.rotation != 0) {
			stream.writeByte(6);
			stream.writeShort(this.rotation);
		}
		
		if (this.ambient != 0) {
			stream.writeByte(7);
			stream.writeByte(this.ambient);
		}
		
		if (this.contrast != 0) {
			stream.writeByte(8);
			stream.writeByte(this.contrast);
		}
		
		if (this.aByte5216 == 3 && anInt5203 == 0) {
			stream.writeByte(9);
		}
		
		if (this.aBoolean5215) {
			stream.writeByte(10);
		}
		
		if (this.aByte5216 == 1) {
			stream.writeByte(11);
		}

		if (this.aByte5216 == 4) {
			stream.writeByte(12);
		}
		
		if (this.aByte5216 == 5) {
			stream.writeByte(13);
		}
		
		if (this.aByte5216 == 4 && anInt5203 > 0) {
			stream.writeByte(14);
			stream.writeByte(anInt5203);
		}

		//TODO opcode 15 & 16
		
		if (this.recolorToFind != null && this.recolorToReplace != null) {
			stream.writeByte(40);
			stream.writeByte(this.recolorToFind.length);
			for (int data = 0; data < this.recolorToFind.length; ++data) {
				stream.writeShort(this.recolorToFind[data]);
				stream.writeShort(this.recolorToReplace[data]);
			}
		}

		if (this.retextureToFind != null && this.retextureToReplace != null) {
			stream.writeByte(41);
			stream.writeByte(this.retextureToFind.length);
			for (int data = 0; data < this.retextureToFind.length; ++data) {
				stream.writeShort(this.retextureToFind[data]);
				stream.writeShort(this.retextureToReplace[data]);
			}
		}
		
		stream.writeByte(0);
		byte[] payload = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(payload, 0, payload.length);
		return payload;
	}

	@Override
	public boolean save(FileStore cache) {
		return cache.getIndexes()[Indices.SPOT_ANIM.getIndex()].putFile(id >>> 8, id & 0xff, encode());
	}
	
	@Override
	public String toString() {
		return "" + this.id;
	}
	
	@Override
	public Cloneable clone() {
		return this;
	}

}
