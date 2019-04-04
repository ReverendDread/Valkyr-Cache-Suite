/**
 * 
 */
package store.loader;

import lombok.Getter;
import store.FileStore;
import store.codec.image.SpriteContainer;
import store.codec.util.Utils;

/**
 * @author ReverendDread
 * Sep 10, 2018
 */
public class SpriteLoader implements DefinitionLoader {

	/**
	 * The cache.
	 */
	private FileStore cache;
	
	/**
	 * Cached sprites
	 */
	@Getter public static SpriteContainer[] sprites;

	/**
	 * Creates and initializes a new sprite loader.
	 * @param cache
	 */
	public SpriteLoader(FileStore cache) {
		this.cache = cache;
		long start = System.currentTimeMillis();
		if (!initialize())
			System.err.println("Failed to load sprites.");
		else 
			System.out.println("[SpriteLoader] Took " + (System.currentTimeMillis() - start) + 
					" millis to load " + sprites.length + " sprite archives.");
	}
	
	/**
	 * Checks if cached sprite images needs cleaned up.
	 * @return
	 */
	public static void cleanup() {
		if (needsCleanup()) {
			for (int index = 0; index < sprites.length; index++) {
				if (sprites[index] != null && !sprites[index].getImages().isEmpty())
					sprites[index].getImages().clear();
			}
			System.gc();
		}
	}
	
	/**
	 * Checks if the image decoder needs to be cleaned up.
	 * @return
	 */
	public static boolean needsCleanup() {
		int loaded = 0;
		for (int index = 0; index < sprites.length; index++) {
			if (sprites[index] != null && sprites[index].isLoaded())
				loaded++;
		}
		return loaded >= 100;
	}
	
	/* (non-Javadoc)
	 * @see com.alex.loaders.DefinitionLoader#initialize()
	 */
	@Override
	public boolean initialize() {
		try {
			int size = Utils.getSpriteDefinitionSize(cache);
			sprites = new SpriteContainer[size];
			for (int archive = 0; archive < size; archive++) {
				SpriteContainer sprite = new SpriteContainer(cache, archive, 0);
				sprites[archive] = sprite;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.alex.loaders.DefinitionLoader#reload()
	 */
	@Override
	public void reload() {
		sprites = null;
		initialize();
	}
	
}
