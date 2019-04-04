package utility;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class XTEASManager {

    public static final Map<Integer, int[]> maps = new HashMap<Integer, int[]>();
	
	public static final int[] NULL_KEYS = new int[4];

	public static void init() {
		new JsonLoader() {
			
			@Override
			public void load(JsonObject reader, Gson builder) {
				int region = reader.get("region").getAsInt();
				int[] keys = builder.fromJson(reader.getAsJsonArray("keys"), int[].class);
				maps.put(region, keys);
			}

			@Override
			public String filePath() {
				return "./repository/xtea_json/xteas.json";
			}
		}.load();
	}
	
    public static final int[] lookup(int id) {
        int[] keys = maps.get(id);
        if (keys == null)
              return NULL_KEYS;
        return keys;
    }
	
}
