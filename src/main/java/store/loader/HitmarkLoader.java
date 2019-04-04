/**
 * 
 */
package store.loader;

import lombok.Getter;
import store.ConfigArchive;
import store.FileStore;
import store.Index;
import store.Indices;
import store.codec.HitmarkDefinition;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Jan 14, 2019
 */
public class HitmarkLoader implements DefinitionLoader {

	private FileStore cache;
	
	@Getter private static HitmarkDefinition[] definitions;
	
	public HitmarkLoader(FileStore cache) {
		this.cache = cache;
		long start = System.currentTimeMillis();
		if (!initialize())
			System.err.println("Failed to load Hit Definitions.");
		else 
			System.out.println("[HitLoader] Took " + (System.currentTimeMillis() - start) + 
					" millis to load " + definitions.length + " definitions.");
	}
	
	/* (non-Javadoc)
	 * @see com.alex.loaders.DefinitionLoader#initialize()
	 */
	@Override
	public boolean initialize() {
		try {
			Index index = cache.getIndexes()[Indices.CONFIG.getIndex()];
			int size = index.getLastFileId(ConfigArchive.HITMARK.getValue());
			definitions = new HitmarkDefinition[size];
			for (int id = 0; id < size; id++) {
				HitmarkDefinition definition = new HitmarkDefinition(id);
				byte[] data = index.getFile(ConfigArchive.HITMARK.getValue(), id);
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

	/* (non-Javadoc)
	 * @see com.alex.loaders.DefinitionLoader#reload()
	 */
	@Override
	public void reload() {
		definitions = null;
		initialize();
	}

}
