package store.loader;

import lombok.Getter;
import store.FileStore;
import store.Index;
import store.codec.SpotDefinition;
import store.codec.util.Utils;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Jun 28, 2018
 */
public class SpotLoader implements DefinitionLoader {

	/**
	 * The {@link FileStore} being used.
	 */
	private FileStore cache;
	
	/**
	 * Array for loaded definitions.
	 */
	@Getter private static SpotDefinition[] definitions;
	
	/**
	 * Creates a new {@code SpotLoader} and loads all spot definitions.
	 * @param cache
	 * 				the cache to load from.
	 */
	public SpotLoader(FileStore cache) {
		this.cache = cache;
		long start = System.currentTimeMillis();
		if (!initialize())
			System.err.println("Failed to load Spot Definitions.");
		else 
			System.out.println("[SpotLoader] Took " + (System.currentTimeMillis() - start) + 
					" millis to load " + definitions.length + " definitions.");
	}
	
	@Override
	public boolean initialize() {
		try {	
			Index index = cache.getIndexes()[21];
			int size = Utils.getGraphicDefinitionsSize(cache);
			definitions = new SpotDefinition[size];
			for (int id = 0; id < size; id++) {
				SpotDefinition definition = new SpotDefinition(id);
				byte[] data = index.getFile(id >>> 8, id & 0xff);
				if (data != null) {
					definition.decode(new InputStream(data));
				}
				definitions[id] = definition;
			}
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void reload() {
		definitions = null;
		initialize();
	}

}
