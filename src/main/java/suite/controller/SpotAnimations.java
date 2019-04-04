package suite.controller;

import javax.swing.JOptionPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import misc.CustomTab;
import store.FileStore;
import store.codec.SpotDefinition;
import store.codec.util.Utils;
import store.loader.LoaderManager;
import store.loader.SpotLoader;
import suite.Constants;
import suite.dialogue.Dialogues;
import suite.dialogue.InformationDialogue;
import utility.StringUtilities;

/**
 * 
 * @author ReverendDread
 * Jul 10, 2018
 */
public class SpotAnimations {

	private FileStore cache = Selection.cache;
	private CustomTab tab;
	private SpotDefinition def;
	private ObservableList<SpotDefinition> definitions;
	
	@FXML private ListView<SpotDefinition> graphics_list;
	@FXML private TextField modelId, animation, resizeX, resizeY, rotation, ambient, contrast, recoloring, retexturing, aByte5216, anInt5203, search_bar, aBoolean5215;
	
	private void view_item() {
		tab.setText("Spot Editor - ID: " + def.id);
		modelId.setText("" + def.modelId);
		animation.setText("" + def.animation);
		resizeX.setText("" + def.resizeX);	
		resizeY.setText("" + def.resizeY);
		rotation.setText("" + def.rotation);
		ambient.setText("" + def.ambient);
		contrast.setText("" + def.contrast);
		recoloring.setText(StringUtilities.formatMultiArrayInt(def.recolorToFind, def.recolorToReplace));
		retexturing.setText(StringUtilities.formatMultiArrayInt(def.retextureToFind, def.retextureToReplace));
		aByte5216.setText("" + def.aByte5216);
		anInt5203.setText("" + def.anInt5203);
		aBoolean5215.setText("" + def.aBoolean5215);
	}
	
	@FXML
	public void select_graphics() {
		def = graphics_list.getSelectionModel().getSelectedItem();
		if (def != null)
			view_item();
	}
	
	@FXML
	public void save() {
		try {
			def.modelId = Integer.parseInt(modelId.getText());
			def.animation = Integer.parseInt(animation.getText());
			def.resizeX = Integer.parseInt(resizeX.getText());
			def.resizeY = Integer.parseInt(resizeY.getText());
			def.rotation = Integer.parseInt(rotation.getText());
			def.ambient = Integer.parseInt(ambient.getText());
			def.contrast = Integer.parseInt(contrast.getText());
			def.aBoolean5215 = Boolean.parseBoolean(aBoolean5215.getText());
			def.anInt5203 = Integer.parseInt(anInt5203.getText());
			def.aByte5216 = (byte) Integer.parseInt(aByte5216.getText());
			
			if (!(recoloring.getText().isEmpty()) && !(recoloring.getText().equalsIgnoreCase("null"))) {
				String[] colors = recoloring.getText().split(";");
				def.recolorToFind = new int[colors.length];
				def.recolorToReplace = new int[colors.length];
				for (int index = 0; index < colors.length; index++) {
					String[] color = colors[index].split("=");
					def.recolorToFind[index] = Integer.parseInt(color[0]);
					def.recolorToReplace[index] = Integer.parseInt(color[1]);
				}			
			} else {
				def.recolorToFind = null;
				def.recolorToReplace = null;
			}
			
			if (!(retexturing.getText().isEmpty()) && !(retexturing.getText().equalsIgnoreCase("null"))) {
				String[] textures = retexturing.getText().split(";");
				def.retextureToFind = new int[textures.length];
				def.retextureToReplace = new int[textures.length];
				for (int index = 0; index < textures.length; index++) {
					String[] texture = textures[index].split("=");
					def.retextureToFind[index] = Integer.parseInt(texture[0]);
					def.retextureToReplace[index] = Integer.parseInt(texture[1]);
				}			
			} else {
				def.retextureToFind = null;
				def.retextureToReplace = null;
			}
			
			if (def.save(cache))
				Dialogues.alert(AlertType.INFORMATION, "Success", null, "Your changes were saved successfully.");
			
			startup(tab, true, def.id);
		} catch (Exception e) {
			InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to save, please your check spelling.", "", StringUtilities.getStackTrace(e));
		}
	}
	
	/**
	 * Loads render anim list.
	 * @param stage TODO
	 */
	public void startup(CustomTab tab, boolean refresh, int lastid) {
		this.tab = tab;
		definitions = FXCollections.observableArrayList();
		LoaderManager.reload(LoaderManager.SPOTS);
		definitions.setAll(SpotLoader.getDefinitions());
		graphics_list.getItems().setAll(definitions);
		def = SpotLoader.getDefinitions()[lastid];
		view_item();		
		search_bar.textProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals(""))
					return;
				int id = Integer.parseInt(newValue);
				def = SpotLoader.getDefinitions()[id];
				if (Constants.settings.focusOnSearch) {
					graphics_list.getSelectionModel().select(id);
					graphics_list.scrollTo(id);
				}
				view_item();
			}
			
		});
		
		ContextMenu context_menu = new ContextMenu();
		graphics_list.setContextMenu(context_menu);
		
		MenuItem create = new MenuItem("Create");
		create.setOnAction((ActionEvent event) -> {	
			SpotDefinition blank_def = new SpotDefinition(-1);
			try {
				String input = JOptionPane.showInputDialog(null, "Please input the desired id, or leave blank for default id.");
				if (input.isEmpty())
					blank_def.id = SpotLoader.getDefinitions().length;
				else {
					int id = Integer.parseInt(input);
					if (cache.getIndexes()[21].getFile(Utils.getConfigArchive(id, 8), Utils.getConfigFile(id, 8)) != null) { //File exists
						boolean confirm = JOptionPane.showConfirmDialog(null, "That graphics already exists, would you like to overwrite it?") == 0;
						if (!confirm) {
							blank_def = null;
							return;
						}
					}
					blank_def.id = id;		
				}
				blank_def.save(cache);
				LoaderManager.getLoader(LoaderManager.SPOTS).reload();
				startup(tab, true, blank_def.id);
			} catch (NumberFormatException e) {
				InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to parse input.", "", 
						"Invalid integer was entered for input. Please check that your input is a valid integer using no commas.");
			}
		});
		context_menu.getItems().add(create);
	}
	
}
