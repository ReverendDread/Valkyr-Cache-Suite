package suite.controller;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import persistable.Persistable;
import store.loader.LoaderManager;
import suite.Constants;
import suite.Main;
import suite.dialogue.Dialogues;
import utility.FileUtilities;

public class Settings {

	@FXML public TextField cache_dir;
	@FXML public CheckBox focus_on_search;
	@FXML public CheckBox success_notifications;
	@FXML public CheckBox debug;
	
	public Persistable persistable;
	
	public Settings() {
		this.persistable = Constants.settings.load();
	}
	
	public void startup() {
		try {
			cache_dir.setText(persistable.cacheDir);
			focus_on_search.setSelected(persistable.focusOnSearch);
			success_notifications.setSelected(persistable.notifications);
			debug.setSelected(persistable.debug);
		} catch (NullPointerException e) {
			persistable.delete();
			persistable.save();
			System.out.println("Creating new save file, new settings added.");
		}
	}
	
	@FXML public void reset() {
		persistable.cacheDir = null;
		Selection.cache = null;
		cache_dir.setText("");
		persistable.save();
	}
	
	@FXML public void edit_cache_dir() throws IOException {
		DirectoryChooser dir_chooser = new DirectoryChooser();
		dir_chooser.setTitle("Locate Cache Directory..");
		Stage stage = Main.getPrimaryStage();
		File default_dir = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator);
		dir_chooser.setInitialDirectory(default_dir);
		File directory = dir_chooser.showDialog(stage);
		if (directory == null) {
			Dialogues.alert(AlertType.ERROR, "Error", null, "Failed to load cache.");
			return;
		}
		persistable.cacheDir = directory.toString() + "\\";
		cache_dir.setText(persistable.cacheDir);
		persistable.save();
		Dialogues.alert(AlertType.INFORMATION, "Notification", null, "Changes saved successfully, reloading data.");
		Main.getSelection().tabs.getTabs().clear();
		Main.getSelection().createTask("Reloading data...", true, new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				new LoaderManager(Selection.cache);
				return null;
			}	
			
		});
	}
	
	@FXML public void focusOnSearch() {
		persistable.focusOnSearch = focus_on_search.isSelected();
		persistable.save();
	}
	
	@FXML public void toggleNotifications() {
		persistable.notifications = success_notifications.isSelected();
		persistable.save();
	}
	
	@FXML public void toggleDebug() {
		persistable.debug = debug.isSelected();
		persistable.save();
	}
	
}
