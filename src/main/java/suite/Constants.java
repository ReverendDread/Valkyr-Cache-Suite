package suite;

import java.io.File;

import persistable.Persistable;

public final class Constants {

	public static final String FAVICON;
	public static final String DEFAULT_SAVE_DIR;
	public static final String WINDOW_NAME;
	public static final int MODEL_VIEWER_WIDTH, MODEL_VIEWER_HEIGHT;
	public static int REVISION;
	public static Persistable settings;

	static {
		WINDOW_NAME = "Valkyr Cache Suite";
		FAVICON = "https://i.imgur.com/iPQJMxk.jpg";
		DEFAULT_SAVE_DIR = System.getProperty("user.home") + File.separator + ".valkyr";
		MODEL_VIEWER_WIDTH = 800;
		MODEL_VIEWER_HEIGHT = 800;
		settings = new Persistable().load();
		REVISION = 718;
	}

}
