package store.loader;

import lombok.Getter;
import store.FileStore;
import store.Index;
import store.codec.NPCDefinition;
import store.codec.util.Utils;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Jun 28, 2018
 */
public class NPCLoader implements DefinitionLoader {

	/**
	 * {@code FileStore} being used to load definitions.
	 */
	private FileStore cache;
	
	/**
	 * Loaded definitions.
	 */
	@Getter private static NPCDefinition[] definitions;
	
	/**
	 * Offset for custom npcs.
	 */
	private static final int OFFSET = 15615;
	
	/**
	 * Creates a new {@code NPCLoader}
	 * @param cache
	 * 			the cache {@code FileStore}.
	 */
	public NPCLoader(FileStore cache) {
		this.cache = cache;
		long start = System.currentTimeMillis();
		if (!initialize())
			System.err.println("Failed to load NPC Definitions.");
		else 
			System.out.println("[NPCLoader] Took " + (System.currentTimeMillis() - start) + 
					" millis to load " + definitions.length + " definitions.");
	}

	@Override
	public boolean initialize() {
		try {
			Index index = cache.getIndexes()[18];
			int size = Utils.getNPCDefinitionsSize(cache);
			definitions = new NPCDefinition[(size - OFFSET)];
			for (int id = 0; id < definitions.length; id++) {
				NPCDefinition definition = new NPCDefinition(id);
				byte[] data = index.getFile(Utils.getConfigArchive(id, 7), Utils.getConfigFile(id, 7));
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
