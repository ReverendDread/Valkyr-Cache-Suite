package store.codec.image;

import java.util.ArrayList;

import store.FileStore;
import store.Indices;
import store.io.InputStream;
import store.io.OutputStream;

/**
 * @author ReverendDread
 * Jun 22, 2018
 */
public class MaterialLoader {

	/**
	 * Cached materials.
	 */
	private ArrayList<MaterialRaw> materials;
	
	/**
	 * 
	 */
	private FileStore cache;
	
	/**
	 * Create a new material loader.
	 * @param cache
	 * 			cache file store.
	 */
	public MaterialLoader(FileStore cache) {
		this.materials = new ArrayList<MaterialRaw>();
		this.cache = cache;
		init();
	}
	
	/**
	 * Decode existing materials
	 */
	public void init() {
		InputStream buffer = new InputStream(cache.getIndexes()[26].getFile(0, 0));
		int count = buffer.readUnsignedShort();
		for (int index = 0; index < count; index++) {
			if (buffer.readUnsignedByte() == 1)
				materials.add(index, new MaterialRaw(index));
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).hdr = buffer.readUnsignedByte() == 0;
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).aBoolean518 = buffer.readUnsignedByte() == 1;
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).transparent = buffer.readUnsignedByte() == 1;
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).shadow_factor = buffer.readByte();
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).brightness = buffer.readByte();
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).effectId = buffer.readByte();
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).effectParam1 = buffer.readByte();
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).color = buffer.readUnsignedShort();
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).speedHorizontal = buffer.readByte();
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).speedVertical = buffer.readByte();
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).aBoolean527 = buffer.readUnsignedByte() == 1;
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).aBoolean544 = buffer.readUnsignedByte() == 1;
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).aByte537 = buffer.readByte();
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).aBoolean540 = buffer.readUnsignedByte() == 1;
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).repeatTexture = buffer.readUnsignedByte() == 1;
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).aBoolean543 = buffer.readUnsignedByte() == 1;
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).anInt519 = buffer.readUnsignedByte();
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).effectParam2 = buffer.readInt();
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				materials.get(index).blendType = buffer.readUnsignedByte();
		}
	}
	
	/**
	 * Encode cached materials.
	 * @return
	 * 		buffer data
	 */
	public final byte[] encode() {
		OutputStream buffer = new OutputStream();
		buffer.writeShort(materials.size());
		int count = materials.size();
		for (int index = 0; index < count; index++) {
			buffer.writeByte(materials.get(index) != null ? 1 : 0);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).hdr ? 0 : 1);		
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).aBoolean518 ? 1 : 0);		
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null) 
				buffer.writeByte(materials.get(index).transparent ? 1 : 0);			
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).shadow_factor);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).brightness);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).effectId);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).effectParam1);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeShort(materials.get(index).color);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).speedHorizontal);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).speedVertical);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).aBoolean527 ? 1 : 0);			
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).aBoolean544 ? 1 : 0);	
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).aByte537);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).aBoolean540 ? 1 : 0);	
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).repeatTexture ? 1 : 0);	
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).aBoolean543 ? 1 : 0);	
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).anInt519);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeInt(materials.get(index).effectParam2);
		}
		for (int index = 0; index < count; index++) {
			if (materials.get(index) != null)
				buffer.writeByte(materials.get(index).blendType);
		}
		byte[] flipped = new byte[buffer.getIndex()];
		buffer.setOffset(0);
		buffer.flipBuffer(flipped, 0, flipped.length);
		return flipped;
	}
	
	public final boolean save() {
		return cache.getIndexes()[Indices.MATERIALS_RAW.getIndex()].putFile(0, 0, encode());
	}

	public ArrayList<MaterialRaw> getMaterials() {
		return materials;
	}
	
	public void setMaterial(int index, MaterialRaw material) {
		materials.set(index - 1, material);
	}
	
	public void addMaterial(int index, MaterialRaw material) {
		materials.add(index, material);
	}
	
	public boolean addMaterial(MaterialRaw material) {
		return materials.add(material);
	}
	
}
