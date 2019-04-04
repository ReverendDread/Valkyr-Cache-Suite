package store.loader;

import lombok.Getter;
import store.FileStore;
import store.Index;
import store.codec.ObjectDefinition;
import store.codec.util.Utils;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Jun 28, 2018
 */
public class ObjectLoader implements DefinitionLoader {

	/**
	 * The {@link FileStore} being used.
	 */
	private FileStore cache;
	
	/**
	 * Array for loaded definitions.
	 */
	@Getter private static ObjectDefinition[] definitions;
	
	/**
	 * Creates a new {@code ObjectLoader} and loads all object definitions.
	 * @param cache
	 * 			the cache to load from.
	 */
	public ObjectLoader(FileStore cache) {
		this.cache = cache;
		long start = System.currentTimeMillis();
		if (!initialize())
			System.err.println("Failed to load Object Definitions.");
		else 
			System.out.println("[ObjectLoader] Took " + (System.currentTimeMillis() - start) + 
					" millis to load " + definitions.length + " definitions.");
	}

	@Override
	public boolean initialize() {
		try {
			Index index = cache.getIndexes()[16];
			int size = Utils.getObjectDefinitionsSize(cache);
			definitions = new ObjectDefinition[size];
			for (int id = 0; id < size; id++) {
				ObjectDefinition definition = new ObjectDefinition(id);
				byte[] data = index.getFile(Utils.getConfigArchive(id, 8), Utils.getConfigFile(id, 8));
				if (data != null) {
					definition.decode(new InputStream(data));
					definition.method3287();
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
