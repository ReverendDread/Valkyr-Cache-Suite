/**
 * 
 */
package utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import suite.Constants;

/**
 * Handles creating configuration files.
 * @author ReverendDread
 * Mar 25, 2019
 */
public class ConfigBuilder {
	
	/**
	 * The class type creating the config.
	 */
	private final Class<?> type;
	
	/**
	 * The {@code JsonObject} assosiated with this config.
	 */
	private JsonObject object = new JsonObject();
	
	/**
	 * The {@code GsonBuilder} assosiated with this config.
	 */
	private final Gson builder = new GsonBuilder().setPrettyPrinting().create();
	
	/**
	 * Crates a new {@code ConfigBuilder} object.
	 * @param type
	 * 			the class type creating the config.
	 */
	public ConfigBuilder(Class<?> type) {
		this.type = type;
	}
	
	/**
	 * Creates a new {@code ConfigBuilder} object.
	 * @return
	 */
	public static final ConfigBuilder create(Class<?> type) {
		return new ConfigBuilder(type);
	}
	
	/**
	 * Adds a config to the builder.
	 * 
	 * @param property
	 * 			the property name.
	 * @param value
	 * 			the value as a {@code JsonElement} value.
	 * @return
	 */
	public final ConfigBuilder add(String property, Object value) {
		object.add(property, builder.toJsonTree(value));
		return this;
	}
	
	/**
	 * Adds a config to the builder.
	 * 
	 * @param property
	 * 			the property name.
	 * @param value
	 * 			the value as a {@code Number} value.
	 * @return
	 */
	public final ConfigBuilder add(String property, Number value) {
		object.addProperty(property, value);
		return this;
	}
	
	/**
	 * Adds a config to the builder.
	 * 
	 * @param property
	 * 			the property name.
	 * @param value
	 * 			the value as a {@code String} value.
	 * @return
	 */
	public final ConfigBuilder add(String property, String value) {
		object.addProperty(property, value);
		return this;
	}
	
	/**
	 * Adds a config to the builder.
	 * 
	 * @param property
	 * 			the property name.
	 * @param value
	 * 			the value as a {@code Boolean} value.
	 * @return
	 */
	public final ConfigBuilder add(String property, Boolean value) {
		object.addProperty(property, value);
		return this;
	}
	
	/**
	 * Serializes the config as a {@code Json} object.
	 * @return
	 * @throws IOException
	 */
	public final boolean save() {
		Path path = Paths.get(Constants.DEFAULT_SAVE_DIR, type.getSimpleName() + ".json");
		File file = path.toFile();
		file.getParentFile().setWritable(true); //Ensures its writable.
		if (!file.getParentFile().exists()) {
			try {
				file.getParentFile().mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(builder.toJson(object));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
