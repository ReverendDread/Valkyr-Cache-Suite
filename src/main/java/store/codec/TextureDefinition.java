/**
 * 
 */
package store.codec;

import lombok.Getter;
import lombok.Setter;
import store.FileStore;
import store.Indices;
import store.codec.util.Constants;
import store.io.InputStream;
import store.io.OutputStream;

/**
 * @author ReverendDread
 * Jan 10, 2019
 */
@Getter @Setter
public class TextureDefinition implements AbstractDefinition, Cloneable {

	private int id;
    public int field1777;
    public boolean field1778;
    public int[] field1779;
    public int[] field1780;
    public int[] field1781;
    public int[] field1786;
    public int field1782;
    public int field1783;	
    public int[] pixels;
    
	/**
	 * Creates a new texture definition.
	 * @param id
	 */
	public TextureDefinition(int id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#decode(com.alex.io.InputStream)
	 */
	@Override
	public void decode(InputStream stream) {
		field1777 = stream.readUnsignedShort();
		field1778 = stream.readByte() != 0;
		int count = stream.readUnsignedByte();
		field1779 = new int[count];
		for (int index = 0; index < count; index++) {
			field1779[index] = stream.readUnsignedShort();
		}
		if (count > 1) {
			field1780 = new int[count - 1];
			for (int index = 0; index < count - 1; index++) {
				field1780[index] = stream.readUnsignedByte();
			}
			field1781 = new int[count - 1];
			for (int index = 0; index < count - 1; ++index) {
				field1781[index] = stream.readUnsignedByte();
			}
		}
		field1786 = new int[count];
		for (int index = 0; index < count; ++index) {
			field1786[index] = stream.readInt();
		}
		field1783 = stream.readUnsignedByte();
		field1782 = stream.readUnsignedByte();
	}

	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#encode()
	 */
	@Override
	public byte[] encode() {		
		OutputStream stream = new OutputStream();
		stream.writeShort(field1777);
		stream.writeByte(field1778 ? 1 : 0);
		int count = field1779.length;
		stream.writeByte(count);
		for (int file = 0; file < field1779.length; file++) {
			stream.writeShort(field1779[file]);
		}
		if (count > 1) {
			for (int file = 0; file < count - 1; file++) {
				stream.writeByte(field1780[file]);
			}
			for (int file = 0; file < count - 1; file++) {
				stream.writeByte(field1781[file]);
			}
		}
		for (int index = 0; index < count; ++index) {
			stream.writeInt(field1786[index]);
		}
		stream.writeByte(field1783);
		stream.writeByte(field1782);	
		byte[] offset_stream = new byte[stream.getIndex()];
		stream.setOffset(0);
		stream.flipBuffer(offset_stream, 0, offset_stream.length);	
		return offset_stream;
	}

	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#save(com.alex.store.FileStore)
	 */
	@Override
	public boolean save(FileStore cache) {
		return cache.getIndexes()[Indices.TEXTURES_DIFFUSE.getIndex()].putFile(id, 0, Constants.GZIP_COMPRESSION, encode(), null, true, false, -1, -1);
	}
	
	@Override
	public String toString() {
		return "" + this.id;
	}
	
    static int adjustRGB(int var0, double var1) {
        double var3 = (double) (var0 >> 16) / 256.0D;
        double var5 = (double) (var0 >> 8 & 255) / 256.0D;
        double var7 = (double) (var0 & 255) / 256.0D;
        var3 = Math.pow(var3, var1);
        var5 = Math.pow(var5, var1);
        var7 = Math.pow(var7, var1);
        int var9 = (int) (var3 * 256.0D);
        int var10 = (int) (var5 * 256.0D);
        int var11 = (int) (var7 * 256.0D);
        return var11 + (var10 << 8) + (var9 << 16);
    }

}
