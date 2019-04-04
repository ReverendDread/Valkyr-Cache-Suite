package suite.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import misc.CustomTab;
import store.FileStore;
import store.codec.ObjectDefinition;
import store.loader.LoaderManager;
import store.loader.ObjectLoader;
import suite.Constants;
import suite.Main;
import suite.dialogue.Dialogues;
import suite.dialogue.InformationDialogue;
import utility.StringUtilities;

public class ObjectEditor {

	private FileStore cache = Selection.cache;
	private ObjectDefinition def;
	private CustomTab tab;
	
	private ObservableList<ObjectDefinition> definitions;
	
	@FXML private ListView<ObjectDefinition> object_list;	
	@FXML private TextField name, size_x, size_y, clip_type, animation, configFileId, configId, option1, model_ids, model_types, search_bar, 
							offsetX, offsetY, offsetHeight, modelSizeX, modelSizeY, modelHeight, contrast, ambient, 
							transformations, option2, option3, option4, option5, modified_colors, modified_textures;	
	@FXML private CheckBox projectile_clipped, isSolid, nonFlatShading;
	
	@FXML
	private void select_object() {
		def = object_list.getSelectionModel().getSelectedItem();
		if (def != null)
			view_item();
	}
	
	@FXML
	public void save() {
		try {
			def.name = name.getText();
			def.sizeX = Integer.parseInt(size_x.getText());
			def.sizeY = Integer.parseInt(size_y.getText());
			def.clipType = Integer.parseInt(clip_type.getText());
			def.objectAnimation = Integer.parseInt(animation.getText());
			def.options[0] = option1.getText();
			def.options[1] = option2.getText();
			def.options[2] = option3.getText();
			def.options[3] = option4.getText();
			def.options[4] = option5.getText();
			def.projectileCliped = !projectile_clipped.isSelected();
			def.isSolid = isSolid.isSelected();
			def.nonFlatShading = nonFlatShading.isSelected();
			def.offsetX = Integer.parseInt(offsetX.getText());
			def.offsetY = Integer.parseInt(offsetY.getText());
			def.offsetHeight = Integer.parseInt(offsetHeight.getText());
			def.modelSizeX = Integer.parseInt(modelSizeX.getText());
			def.modelSizeY = Integer.parseInt(modelSizeY.getText());
			def.modelSizeHeight = Integer.parseInt(modelHeight.getText());
			def.contrast = Integer.parseInt(contrast.getText());
			def.ambient = Integer.parseInt(ambient.getText());
			
			//Configs
			if (!transformations.getText().isEmpty()) {
				String[] trans = transformations.getText().split(";");
				def.transformations = new int[trans.length];
				def.configFileId = Integer.parseInt(configFileId.getText());
				def.configId = Integer.parseInt(configId.getText());
				for (int transform = 0; transform < trans.length; transform++) {
					int id = Integer.parseInt(trans[transform]);;
					def.transformations[transform] = id;
				}
			} else {
				def.transformations = null;
				def.configFileId = Integer.parseInt(configFileId.getText());
				def.configId = Integer.parseInt(configId.getText());
			}
			
			//Models
			if (!model_ids.getText().isEmpty() && !model_types.getText().isEmpty()) {
				String[] models = model_ids.getText().split(";");
				String[] types = model_types.getText().split(";");
				int length = types.length;
				def.models = new int[length][];
				def.shapes = new byte[length];
				for (int type = 0; type < length; type++) {
					def.shapes[type] = Byte.parseByte(types[type]);
					int count = models.length;
					def.models[type] = new int[count];
					for (int model = 0; count > model; model++) {
						def.models[type][model] = Integer.parseInt(models[model]);
					}
				}
			} else {
				def.models = null;
				def.shapes = null;
			}
			
			//Colors
			String[] colors = modified_colors.getText().split(";");
			if (!modified_colors.getText().isEmpty() && !modified_colors.getText().equalsIgnoreCase("null")) {
				def.originalColors = new short[colors.length];
				def.modifiedColors = new short[colors.length];
				for (int index = 0; index < colors.length; index++) {
					String[] color = colors[index].split("=");
					def.originalColors[index] =  (short) Integer.parseInt(color[0]);
					def.modifiedColors[index] = (short) Integer.parseInt(color[1]);
				}
			} else {
				def.originalColors = null;
				def.modifiedColors = null;
			}
			
			//Textures
			String[] textures = modified_textures.getText().split(";");
			if (!modified_textures.getText().isEmpty() && !modified_textures.getText().equalsIgnoreCase("null")) {
				def.retextureToFind = new short[textures.length];
				def.retextureToReplace = new short[textures.length];
				for (int index = 0; index < textures.length; index++) {
					String[] texture = textures[index].split("=");
					def.retextureToFind[index] = (short) Integer.parseInt(texture[0]);
					def.retextureToReplace[index] = (short) Integer.parseInt(texture[1]);
				}
			} else {
				def.retextureToFind = null;
				def.retextureToReplace = null;
			}
			
			if (def.save(cache))
				Dialogues.alert(AlertType.INFORMATION, "Success", null, "Your changes were saved successfully.");
		} catch (Exception e) {
			InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to save, please your check spelling.", "", StringUtilities.getStackTrace(e));
			e.printStackTrace();
		}
		LoaderManager.getLoader(LoaderManager.OBJECT).reload();
		startup(tab, true, def.id);
	}
		
	/**
	 * Refreshes and displays the selected item.
	 * 
	 * @param def
	 */
	private void view_item() {
		tab.setText("Object Editor - " + def.name + ", ID: " + def.id);
		this.name.setText(def.name);
		this.size_x.setText("" + def.sizeX);
		this.size_y.setText("" + def.sizeY);
		this.clip_type.setText("" + def.clipType);
		this.animation.setText("" + def.objectAnimation);
		this.configFileId.setText("" + def.configFileId);
		this.configId.setText("" + def.configId);
		this.option1.setText("" + def.options[0]);
		this.option2.setText("" + def.options[1]);
		this.option3.setText("" + def.options[2]);
		this.option4.setText("" + def.options[3]);
		this.option5.setText("" + def.options[4]);
		this.modelHeight.setText("" + def.modelSizeHeight);
		this.modelSizeX.setText("" + def.modelSizeX);
		this.modelSizeY.setText("" + def.modelSizeY);
		this.offsetX.setText("" + def.offsetX);
		this.offsetY.setText("" + def.offsetY);
		this.offsetHeight.setText("" + def.offsetHeight);
		this.nonFlatShading.setSelected(def.nonFlatShading);
		this.ambient.setText("" + def.ambient);
		this.contrast.setText("" + def.contrast);
		this.transformations.setText(StringUtilities.formatIntArrayToString(def.transformations));
		String types = "";
		String models = "";	
		if (def.shapes != null && def.models != null) {
			int length = def.shapes.length;
			for (int type = 0; type < length; type++) {
				types = types + def.shapes[type] + ";";
				int count = def.models[type].length;
				for (int model = 0; count > model; model++) {
					models = models + def.models[type][model] + ";";
				}
			}	
		}
		this.modified_colors.setText(StringUtilities.formatMultiArrayShort(def.originalColors, def.modifiedColors));
		this.modified_textures.setText(StringUtilities.formatMultiArrayShort(def.retextureToFind, def.retextureToReplace));
		this.model_ids.setText(models);
		this.model_types.setText(types);
		this.projectile_clipped.setSelected(def.projectileCliped);
		this.isSolid.setSelected(def.isSolid);
	}
	
	//65229, 65458

	/**
	 * Loads object list.
	 * @param stage TODO
	 */
	public void startup(CustomTab tab, boolean refresh, int lastid) {
		this.tab = tab;
		definitions = FXCollections.observableArrayList();
		if (refresh)
			definitions.clear();
		
		definitions.addAll(ObjectLoader.getDefinitions());
		object_list.setItems(definitions);
		
		def = ObjectLoader.getDefinitions()[lastid];
		view_item();
		
		search_bar.textProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals(""))
					return;
				int id = Integer.parseInt(newValue);
				def = ObjectLoader.getDefinitions()[id];
				if (Constants.settings.focusOnSearch) {
					object_list.getSelectionModel().select(id);
					object_list.scrollTo(id);
				}
				view_item();
			}
			
		});
		
		ContextMenu context_menu = new ContextMenu();
		object_list.setContextMenu(context_menu);
		
		MenuItem create = new MenuItem("Create");
		create.setOnAction((ActionEvent event) -> {	
			ObjectDefinition blank_def = new ObjectDefinition(-1);
			try {
				blank_def.id = ObjectLoader.getDefinitions().length;
				blank_def.save(cache);
				def = blank_def;
				LoaderManager.getLoader(LoaderManager.OBJECT).reload();
				startup(tab, true, blank_def.id);
			} catch (NumberFormatException e) {
				InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to create new object.", "", 
						"Please notify ReverendDread about this error, and a way to reproduce it.");
			}
		});
		
		MenuItem export = new MenuItem("Export as dat");
		export.setOnAction((event) -> {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Choose your export directory.");
			Stage stage = Main.getPrimaryStage();
			File default_dir = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator);
			chooser.setInitialDirectory(default_dir);
			File directory = chooser.showDialog(stage);
			if (directory == null)
				return;
			try {
				ObjectDefinition selectedObject = object_list.getSelectionModel().getSelectedItem();
				byte[] data = selectedObject.encode();
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(directory + File.separator + selectedObject.id + ".dat"));
				dos.write(data);
				dos.close();
			} catch (IOException e) {
				Dialogues.alert(AlertType.ERROR, "Error", "An error has occured.", StringUtilities.getStackTrace(e));
			}
		});
		
		context_menu.getItems().addAll(create, export);
		
	}
	
}
