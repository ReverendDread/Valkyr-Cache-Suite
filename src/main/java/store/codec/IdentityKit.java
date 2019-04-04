/**
 * 
 */
package store.codec;

import store.ConfigArchive;
import store.FileStore;
import store.Indices;
import store.io.InputStream;
import store.io.OutputStream;

/**
 * @author ReverendDread Sep 21, 2018
 */
public class IdentityKit implements AbstractDefinition {

	public int id;
	public int bodyPartId = -1;
	public int[] bodyModels;
	public int[] headModels = new int[] {-1, -1, -1, -1, -1};
	public boolean nonSelectable = false;
	public int[] recolorToFind;
	public int[] recolorToReplace;
	public int[] retextureToFind;
	public int[] retextureToReplace;

	public IdentityKit(int id) {
		this.id = id;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.alex.definition.AbstractDefinition#decode(com.alex.io.InputStream)
	 */
	@Override
	public void decode(InputStream stream) {
		while (true) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				break;
			if (opcode == 1) {
				bodyPartId = stream.readUnsignedByte();
			} else {
				int length;
				int index;
				if (2 == opcode) {
					length = stream.readUnsignedByte();
					this.bodyModels = new int[length];
					for (index = 0; index < length; ++index) {
						this.bodyModels[index] = stream.readSmartInt();
					}
				} else if (opcode == 3) {
					this.nonSelectable = true;
				} else if (40 == opcode) {
					length = stream.readUnsignedByte();
					this.recolorToFind = new int[length];
					this.recolorToReplace = new int[length];
					for (index = 0; index < length; ++index) {
						this.recolorToFind[index] = stream.readUnsignedShort();
						this.recolorToReplace[index] = stream.readUnsignedShort();
					}
				} else if (opcode == 41) {
					length = stream.readUnsignedByte();
					this.retextureToFind = new int[length];
					this.retextureToReplace = new int[length];
					for (index = 0; index < length; ++index) {
						this.retextureToFind[index] = stream.readUnsignedShort();
						this.retextureToReplace[index] = stream.readUnsignedShort();
					}
				} else {
					if (opcode >= 60 && opcode < 70) {
						this.headModels[opcode - 60] = stream.readSmartInt();
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.alex.definition.AbstractDefinition#encode()
	 */
	@Override
	public byte[] encode() {
		OutputStream stream = new OutputStream();
		
		if (bodyPartId != -1) {
			stream.writeByte(1);
			stream.writeByte(bodyPartId);
		}
		
		if (bodyModels != null) {
			stream.writeByte(2);
			stream.writeByte(bodyModels.length);
			for (int index = 0; index < bodyModels.length; index++) {
				stream.writeSmartInt(bodyModels[index]);
			}
		}
		
		if (nonSelectable) {
			stream.writeByte(3);
		}
		
		if (recolorToFind != null && recolorToReplace != null) {
			stream.writeByte(40);
			stream.writeByte(recolorToFind.length);
			for (int index = 0; index < recolorToFind.length; index++) {
				stream.writeShort(recolorToFind[index]);
				stream.writeShort(recolorToReplace[index]);
			}
		}
		
		if (retextureToFind != null && retextureToReplace != null) {
			stream.writeByte(40);
			stream.writeByte(retextureToFind.length);
			for (int index = 0; index < retextureToFind.length; index++) {
				stream.writeShort(retextureToFind[index]);
				stream.writeShort(retextureToReplace[index]);
			}
		}
		
		if (headModels != null) {
			for (int index = 0; index < headModels.length; index++) {
				stream.writeByte(60 + index);
				stream.writeSmartInt(headModels[index]);
			}
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.alex.definition.AbstractDefinition#save(com.alex.store.FileStore)
	 */
	@Override
	public boolean save(FileStore cache) {
		// TODO Auto-generated method stub
		return cache.getIndexes()[Indices.CONFIG.getIndex()].putFile(ConfigArchive.IDENTITY_KIT.getValue(), id, encode());
	}

}
