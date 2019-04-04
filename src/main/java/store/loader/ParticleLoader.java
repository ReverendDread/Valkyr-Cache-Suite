package store.loader;

import lombok.Getter;
import store.FileStore;
import store.Index;
import store.codec.ParticleDefinition;
import store.codec.util.Utils;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Jun 28, 2018
 */
public class ParticleLoader implements DefinitionLoader {

	/**
	 * The {@link FileStore} being used.
	 */
	private FileStore cache;
	
	/**
	 * Array for loaded definitions.
	 */
	@Getter private static ParticleDefinition[] definitions;
	
	/**
	 * Creates a new {@code ParticleLoader} and loads all particle definitions.
	 * @param cache
	 * 				the cache to load from.
	 */
	public ParticleLoader(FileStore cache) {
		this.cache = cache;
		long start = System.currentTimeMillis();
		if (!initialize())
			System.err.println("Failed to load Particle Definitions.");
		else 
			System.out.println("[ParticleLoader] Took " + (System.currentTimeMillis() - start) + 
					" millis to load " + definitions.length + " definitions.");
	}
	
	@Override
	public boolean initialize() {
		try {
			Index index = cache.getIndexes()[27];
			int size = Utils.getParticleConfigSize(cache);
			definitions = new ParticleDefinition[size];
			for (int id = 0; id < size; id++) {
				ParticleDefinition definition = new ParticleDefinition(id);
				byte[] data = index.getFile(0, id);
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
