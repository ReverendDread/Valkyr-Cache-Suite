package store.loader;

import lombok.Getter;
import store.FileStore;
import store.Index;
import store.codec.BASDefinition;
import store.codec.util.Utils;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Jun 28, 2018
 */
public class BASLoader implements DefinitionLoader {

	/**
	 * The {@link FileStore} being used.
	 */
	private FileStore cache;
	
	/**
	 * Array for loaded definitions.
	 */
	@Getter private static BASDefinition[] definitions;
	
	/**
	 * Creates a new {@code BASLoader} and loads all BAS definitions.
	 * @param cache
	 * 				the cache to load from.
	 */
	public BASLoader(FileStore cache) {
		this.cache = cache;
		long start = System.currentTimeMillis();
		if (!initialize())
			System.err.println("Failed to load BAS Definitions.");
		else 
			System.out.println("[BASLoader] Took " + (System.currentTimeMillis() - start) + 
					" millis to load " + definitions.length + " definitions.");
	}
	
	@Override
	public boolean initialize() {
		try {
			Index index = cache.getIndexes()[2];
			int size = Utils.getRenderAnimationDefintionsSize(cache);
			definitions = new BASDefinition[size];
			for (int id = 0; id < size; id++) {
				BASDefinition definition = new BASDefinition(id);
				byte[] data = index.getFile(32, id);
				if (data != null)
					definition.decode(new InputStream(data));
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
