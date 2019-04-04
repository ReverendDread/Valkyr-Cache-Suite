package store.loader;

import java.util.ArrayList;

import lombok.Getter;
import store.FileStore;

public class LoaderManager {

	private final FileStore cache;
	@Getter private static ArrayList<DefinitionLoader> loaders;
	public static final int ITEM = 0, NPC = 1, OBJECT = 2, SPOTS = 3, PARTICLE = 4, BAS = 5, SPRITE = 6, TEXTURE = 7, HITMARK = 8; 
	
	/**
	 * Creates a new loader manager.
	 * @param cache
	 */
	public LoaderManager(FileStore cache) {
		this.cache = cache;
		System.out.println("Initializing LoaderManager...");
		loaders = new ArrayList<DefinitionLoader>();
		loadDefinitions();
	}

	/**
	 * Initializes the loaders.
	 */
	private void loadDefinitions() {
		try {
			loaders.add(new ItemLoader(cache));
			loaders.add(new NPCLoader(cache));
			loaders.add(new ObjectLoader(cache));
			loaders.add(new SpotLoader(cache));
			loaders.add(new ParticleLoader(cache));
			loaders.add(new BASLoader(cache));
			loaders.add(new SpriteLoader(cache));
			loaders.add(new TextureDiffuseLoader(cache));
			loaders.add(new HitmarkLoader(cache));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reloads the desired {@code DefinitionLoader}.
	 * @param loader
	 * 			the loader value.
	 */
	public static void reload(int loader) {
		loaders.get(loader).reload();
	}
	
	/**
	 * Gets the desired loader.
	 * @param loader
	 * 			the loader value.
	 * @return
	 * 			the {@code DefinitionLoader}.
	 */
	public static DefinitionLoader getLoader(int loader) {
		return loaders.get(loader);
	}
	
}
