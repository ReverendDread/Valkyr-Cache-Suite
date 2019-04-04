/**
 * 
 */
package store.codec.osrs;

import store.FileStore;
import store.codec.AbstractDefinition;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Sep 18, 2018
 */
public class SpotDefinition implements AbstractDefinition {

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
	
	/* (non-Javadoc)
	 * @see com.alex.definition.AbstractDefinition#decode(com.alex.io.InputStream)
	 */
	@Override
	public void decode(InputStream stream) {
		// TODO Auto-generated method stub
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
