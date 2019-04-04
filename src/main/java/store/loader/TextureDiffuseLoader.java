/**
 * 
 */
package store.loader;

import lombok.Getter;
import store.FileStore;
import store.Index;
import store.Indices;
import store.codec.TextureDefinition;
import store.codec.util.Utils;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Dec 9, 2018
 */
public class TextureDiffuseLoader implements DefinitionLoader {

	/**
	 * The {@link FileStore} being used.
	 */
	private FileStore cache;
	
	/** Loaded definitions */
	@Getter private static TextureDefinition[] definitions;
	
	public TextureDiffuseLoader(FileStore cache) {
		this.cache = cache;
		long start = System.currentTimeMillis();
		if (!initialize())
			System.err.println("Failed to load Texture definition.");
		else 
			System.out.println("[TextureLoader] Took " + (System.currentTimeMillis() - start) + 
					" millis to load " + definitions.length + " definitions.");
	}
	
	/* (non-Javadoc)
	 * @see com.alex.loaders.DefinitionLoader#initialize()
	 */
	@Override
	public boolean initialize() {
		try {
			Index index = cache.getIndexes()[Indices.TEXTURES_DIFFUSE.getIndex()];
			int size = Utils.getTextureDiffuseSize(cache);
			definitions = new TextureDefinition[size];
			for (int id = 0; id < size; id++) {
				TextureDefinition definition = new TextureDefinition(id);
				byte[] data = index.getFile(id);
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
