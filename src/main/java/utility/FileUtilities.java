package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import suite.Constants;

/**
 * @author ReverendDread
 * Jun 22, 2018
 */
public class FileUtilities {
	
	/**
	 * Loads a {@code Serializable} object and returns its as an {@code Object}.
	 * @param file_name
	 * 			the file to load.
	 * @return
	 */
	public static final Object load(String file_name) {
		File file = new File(Constants.DEFAULT_SAVE_DIR + File.separator + file_name);
		if (!file.exists())
			return null;
		Object object = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(Constants.DEFAULT_SAVE_DIR + File.separator + file_name));
			object = in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * Saves a {@code Serializable} object as bytecode.
	 * @param object
	 * 			the {@code Serializable} object to save.
	 * @param file
	 * 			the file to save as.
	 */
	public static final void save(Serializable object, File file) {
		try {
			File directory = new File(Constants.DEFAULT_SAVE_DIR);
			if (!directory.exists())
				directory.mkdirs();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(directory + File.separator + file));
			out.writeObject(object);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes the desired file in the save directory.
	 * @param file_name
	 * 			the files name.
	 */
	public static final void delete(String file_name) {
		File file = new File(Constants.DEFAULT_SAVE_DIR + File.separator + file_name);
		try {
			if (!file.exists())
				return;
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
