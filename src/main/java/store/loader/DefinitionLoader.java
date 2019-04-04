package store.loader;

/**
 * 
 * @author ReverendDread
 * Jun 27, 2018
 */
public interface DefinitionLoader {

	/**
	 * Called when creating a definition loader.
	 * @return
	 */
	public boolean initialize();

	/**
	 * Reloads definitions.
	 */
	public void reload();
	
}
