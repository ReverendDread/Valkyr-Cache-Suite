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
public class FluDefinition implements AbstractDefinition {

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
