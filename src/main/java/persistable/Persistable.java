package persistable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import suite.Constants;
import utility.ConfigBuilder;
import utility.FileUtilities;
import utility.JsonLoader;

public class Persistable implements Serializable {
	
	private static final long serialVersionUID = -7227610820978123662L;

	public boolean debug;
	public String cacheDir;
	public boolean focusOnSearch;
	public boolean notifications;
	public int revision;
	private ConfigBuilder builder = new ConfigBuilder(this.getClass());
	
	public Persistable load() {
		Persistable instance = new Persistable();
		JsonLoader loader = new SaveLoader(instance);
		File file = new File(loader.filePath());
		System.out.println("Loading persistable");
		if (!file.exists()) {
			try {
				if (file.getParentFile().mkdirs()) {
					file.createNewFile();
					instance.save();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		loader.load();
		return instance;
	}
	
	public void save() {
		System.out.println("Loading persistable");
		builder.add("debug", debug);
		builder.add("cache-dir", cacheDir == null || cacheDir.isEmpty() ? "" : cacheDir);	
		builder.add("focus-search", focusOnSearch);
		builder.add("revision", revision);
		builder.add("notifications", notifications);
		builder.save();
	}
	
	public void delete() {
		FileUtilities.delete(Persistable.class.getName() + ".json");
	}
	
	@RequiredArgsConstructor
	class SaveLoader extends JsonLoader {

		private final Persistable persistable;
		
		/* (non-Javadoc)
		 * @see utility.JsonLoader#load(com.google.gson.JsonObject, com.google.gson.Gson)
		 */
		@Override
		public void load(JsonObject reader, Gson builder) {
			persistable.debug = reader.get("debug").getAsBoolean();
			persistable.cacheDir = reader.get("cache-dir").getAsString();
			persistable.focusOnSearch = reader.get("focus-search").getAsBoolean();
			persistable.revision = reader.get("revision").getAsInt();
			persistable.notifications = reader.get("notifications").getAsBoolean();
		}

		/* (non-Javadoc)
		 * @see utility.JsonLoader#filePath()
		 */
		@Override
		public String filePath() {
			return Constants.DEFAULT_SAVE_DIR + File.separator + Persistable.class.getSimpleName() + ".json";
		}
		
		@Override
		public JsonLoader load() {
	        try (FileReader fileReader = new FileReader(Paths.get(filePath()).toFile())) {	        	
	            JsonParser parser = new JsonParser();
	            JsonObject element = (JsonObject) parser.parse(fileReader);
	            Gson builder = new GsonBuilder().create();
	            load(element, builder);	           
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
			return this;
		}
		
	}
	
}
