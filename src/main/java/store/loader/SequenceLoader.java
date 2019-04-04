/**
 * 
 */
package store.loader;

import store.FileStore;
import store.codec.SequenceDefinition;

/**
 * Loads sequences into a cached array.
 * @author ReverendDread
 * Aug 2, 2018
 */
public class SequenceLoader implements DefinitionLoader {

	/**
	 * Cached sequence definition.
	 */
	private SequenceDefinition[] definitions;
	
	/**
	 * Cache being used.
	 */
	private FileStore cache;
	
	/**
	 * Creates a new sequence loader.
	 * @param cache
	 * 				the cache being used.
	 */
	public SequenceLoader(FileStore cache) {
		this.cache = cache;
		long start = System.currentTimeMillis();
		if (initialize()) 
			System.err.println("Failed to load Sequence Definitions.");
		else
			System.out.println("[SequenceLoader] Took " + (System.currentTimeMillis() - start) + 
					" millis to load " + definitions.length + " definitions.");
	}
	
	/* (non-Javadoc)
	 * @see com.alex.loaders.DefinitionLoader#initialize()
	 */
	@Override
	public boolean initialize() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.alex.loaders.DefinitionLoader#reload()
	 */
	@Override
	public void reload() {
		// TODO Auto-generated method stub

	}

}
