package store.loader;

import lombok.Getter;
import store.FileStore;
import store.Indices;
import store.codec.ItemDefinition;
import store.codec.util.Utils;
import store.io.InputStream;

/**
 * @author ReverendDread
 * Jun 25, 2018
 */
public class ItemLoader implements DefinitionLoader {

	/**
	 * The {@link FileStore} being used.
	 */
	private FileStore cache;
	
	/**
	 * Array for loaded definitions.
	 */
	@Getter private static ItemDefinition[] definitions;
	
	/**
	 * This value is an offset for item extra items in the cache.
	 */
	private static final int OFFSET = 22314;
	
	/**
	 * Creates a new {@code ItemLoader} and loads all item definitions.
	 * @param cache
	 * 				the cache to load from.
	 */
	public ItemLoader(FileStore cache) {
		this.cache = cache;
		long start = System.currentTimeMillis();
		if (!initialize())
			System.err.println("Failed to load Item Definitions.");
		else 
			System.out.println("[ItemLoader] Took " + (System.currentTimeMillis() - start) + 
					" millis to load " + definitions.length + " definitions.");
	}
	
	@Override
	public boolean initialize() {
		try {
			int size = Utils.getItemDefinitionsSize(cache);
			definitions = new ItemDefinition[(size - OFFSET)];
			for (int id = 0; id < definitions.length; id++) {
				ItemDefinition definition = load(id);
				if (definition.noteTemplate != -1) {
					toNote(definition);
				}
				definitions[id] = definition;
			}
			return true;
		} catch (Throwable e) { 
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Force loads an {@code ItemDefinition}.
	 * @param id
	 * 			the id to load.
	 * @return
	 * 			the item definition.
	 */
	public ItemDefinition load(int id) {
		ItemDefinition definition = new ItemDefinition(id);
		byte[] data = cache.getIndexes()[Indices.ITEMS.getIndex()].getFile(Utils.getConfigArchive(id, 8), Utils.getConfigFile(id, 8));
		if (data != null)
			definition.decode(new InputStream(data));	
		return definition;
	}
	
	/**
	 * Converts an {@code ItemDefinition} to note form.
	 * @param definition
	 */
	private void toNote(ItemDefinition definition) {
		ItemDefinition item = load(definition.notedID);
		definition.members = item.members;
		definition.cost = item.cost;
		definition.name = item.name;
		definition.stackable = 1;
	}
	
	@Override
	public void reload() {
		definitions = null;
		initialize();
	}
	
}
