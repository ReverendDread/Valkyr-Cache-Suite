package suite.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import suite.Main;
import suite.dialogue.Dialogues;

/**
 * 
 * @author ReverendDread
 * Jul 10, 2018
 */
public class ModelDumper {
	
	/**
	 * Starts the model dumper.
	 */
	public static void dump_models(int[] modelIds) {
		try {
			DirectoryChooser dir_chooser = new DirectoryChooser();
			dir_chooser.setTitle("Locate Dump Directory..");
			Stage stage = Main.getPrimaryStage();
			File default_dir = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator);
			dir_chooser.setInitialDirectory(default_dir);
			File directory = dir_chooser.showDialog(stage);
			if (directory == null) {
				Dialogues.alert(AlertType.ERROR, "Error", null, "Failed to locate dump directory.");
				return;
			}
			if (modelIds == null) {
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Enter models ids");
				dialog.setContentText("Please enter desired ids:");
				Optional<String> result = dialog.showAndWait();
				String[] models = result.get().split(";");
				for (int index = 0; index < models.length; index++) {
					if (!Selection.cache.getIndexes()[7].fileExists(Integer.parseInt(models[index]), 0))
						continue;
					byte[] data = Selection.cache.getIndexes()[7].getFile(Integer.parseInt(models[index]));
					DataOutputStream dos = new DataOutputStream(new FileOutputStream(directory + File.separator + models[index] + ".dat"));
					dos.write(data);
					dos.close();
				}
			} else {
				for (int index = 0; index < modelIds.length; index++) {
					if (!Selection.cache.getIndexes()[7].fileExists(modelIds[index], 0))
						continue;
					byte[] data = Selection.cache.getIndexes()[7].getFile(modelIds[index]);
					DataOutputStream dos = new DataOutputStream(new FileOutputStream(directory + File.separator + modelIds[index] + ".dat"));
					dos.write(data);
					dos.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
