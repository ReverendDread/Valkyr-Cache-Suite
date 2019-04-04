package suite.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javax.swing.JOptionPane;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import misc.CustomTab;
import store.FileStore;
import store.codec.NPCDefinition;
import store.codec.util.Utils;
import store.loader.LoaderManager;
import store.loader.NPCLoader;
import suite.Constants;
import suite.Main;
import suite.dialogue.Dialogues;
import suite.dialogue.InformationDialogue;
import utility.StringUtilities;

public class NPCEditor {

	private FileStore cache = Selection.cache;
	private NPCDefinition def;
	private CustomTab tab;
	
	private ObservableList<NPCDefinition> definitions;
	
	@FXML
	public ListView<NPCDefinition> npc_list;
	
	@FXML private TextField name, combat_level, size, height, width, walk_mask, 
				respawn_dir, render, model_ids, chat_heads, modified_colors, modified_textures, 
				options, search_bar, ambient_sound, overhead_icon;
	
	@FXML
	public TextArea client_scripts;
	
	@FXML
	public CheckBox visable;
	
	@FXML
	public void select_npc() {
		def = npc_list.getSelectionModel().getSelectedItem();
		if (def != null)
			view_item();
	}
	
	/**
	 * Refreshes and displays the selected npc.
	 */
	private void view_item() {
		tab.setText("NPC Editor - " + def.name + ", ID: " + def.id);
		this.name.setText(def.name);
		this.combat_level.setText("" + def.combatLevel);
		this.size.setText("" + def.size);
		this.height.setText("" + def.npcHeight);
		this.width.setText("" + def.npcWidth);
		this.walk_mask.setText("" + def.walkMask);
		this.respawn_dir.setText("" + def.respawnDirection);
		this.render.setText("" + def.renderEmote);
		this.model_ids.setText(StringUtilities.formatIntArrayToString(def.modelIds));
		this.chat_heads.setText(StringUtilities.formatIntArrayToString(def.npcChatHeads));
		this.modified_colors.setText(StringUtilities.formatMultiArrayInt(def.originalModelColors, def.modifiedModelColors));
		this.modified_textures.setText(StringUtilities.formatMultiArrayInt(def.originalTextureColors, def.modifiedTextureColors));
		this.options.setText(StringUtilities.formatStringArrayToString(def.options));
		this.visable.setSelected(!def.invisibleOnMap);
		this.ambient_sound.setText("TODO");
		this.overhead_icon.setText("" + def.headIcon);
		loadClientScripts();
	}
	
	/**
	 * Loads item list.
	 * @param stage TODO
	 */
	public void startup(CustomTab tab, boolean refresh, int lastid) {
		this.tab = tab;
		definitions = FXCollections.observableArrayList();
		if (refresh) {
			definitions.clear();
		}
		definitions.addAll(NPCLoader.getDefinitions());
		npc_list.setItems(definitions);
		
		def = NPCLoader.getDefinitions()[lastid];
		view_item();
		
		ContextMenu context_menu = new ContextMenu();
		
		MenuItem add = new MenuItem("Create");
		add.setOnAction((ActionEvent event) -> {
			NPCDefinition blank_def = new NPCDefinition(-1);
			try {
				String input = JOptionPane.showInputDialog(null, "Please input the desired id, or leave blank for default id.");
				if (input.isEmpty()) 
					blank_def.id = NPCLoader.getDefinitions().length;
				else {
					int id = Integer.parseInt(input);
					if (cache.getIndexes()[18].getFile(Utils.getConfigArchive(id, 7), Utils.getConfigFile(id, 7)) != null) { //File exists
						boolean confirm = JOptionPane.showConfirmDialog(null, 
									"That object already exists, would you like to overwrite it?") == JOptionPane.YES_OPTION;
						if (!confirm) {
							blank_def = null;
							return;
						}
					}
					blank_def.id = id;		
				}
				blank_def.save(cache);
				LoaderManager.getLoader(LoaderManager.NPC).reload();
				startup(tab, true, blank_def.id);
				view_item();
			} catch (NumberFormatException e) {
				InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to parse input.", "", 
						"Invalid integer was entered for input. Please check that your input is a valid integer using no commas.");
			}
		});
		
		MenuItem duplicate = new MenuItem("Duplicate");
		duplicate.setOnAction((ActionEvent event) -> {
			try {	
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Create a duplicate");
				dialog.setHeaderText("");
				dialog.setContentText("Please enter the desired ID:");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					int id = Integer.parseInt(result.get());
					NPCDefinition definition = npc_list.getSelectionModel().getSelectedItem();
					if (cache.getIndexes()[18].getFile(Utils.getConfigArchive(id, 7), Utils.getConfigFile(id, 7)) != null) {	
						boolean confirm = JOptionPane.showConfirmDialog(null, "This already exists, would you like to overwrite it?", 
								"Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
						if (confirm) {
							NPCDefinition clone = (NPCDefinition) definition.clone();
							clone.id = id;
							def = clone;
							save();
						} else {
							return;
						}
					}
					startup(tab, true, id);
				}

			} catch (Throwable e) {
				InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, null, "", 
						StringUtilities.getStackTrace(e));
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
				NPCDefinition selectedNpc = npc_list.getSelectionModel().getSelectedItem();
				byte[] data = selectedNpc.encode();
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(directory + File.separator + selectedNpc.id + ".dat"));
				dos.write(data);
				dos.close();
			} catch (IOException e) {
				Dialogues.alert(AlertType.ERROR, "Error", "An error has occured.", StringUtilities.getStackTrace(e));
			}
		});
		
		search_bar.textProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				boolean byId = newValue.isEmpty() ? false : Character.isDigit(newValue.charAt(0));
				if (!byId) {
					if (oldValue != null && (newValue.length() < oldValue.length())) {
						npc_list.setItems(definitions);
					}
					ObservableList<NPCDefinition> filtered = FXCollections.observableArrayList();
					for (NPCDefinition npc : npc_list.getItems()) {
						if (npc != null && npc.name.toUpperCase().contains(newValue.toUpperCase()))
							filtered.add(npc);
					}
					npc_list.setItems(filtered);
				} else {
					int id = Integer.parseInt(newValue);
					def = NPCLoader.getDefinitions()[id];
					if (Constants.settings.focusOnSearch) {
						npc_list.getSelectionModel().select(id);
						npc_list.scrollTo(id);
					}
					view_item();
				}
			}
			
		});
		
		context_menu.getItems().addAll(add, duplicate, export);
		
		npc_list.setContextMenu(context_menu);
	}
	
	@FXML
	public void save() {
		try {
			def.name = name.getText();
			def.combatLevel = Integer.parseInt(combat_level.getText());
			def.size = Integer.parseInt(size.getText());
			def.npcHeight = Integer.parseInt(height.getText());
			def.npcWidth = Integer.parseInt(width.getText());
			def.walkMask = (byte) Integer.parseInt(walk_mask.getText());
			def.renderEmote = Integer.parseInt(render.getText());
			def.respawnDirection = (byte) Integer.parseInt(respawn_dir.getText());
			def.headIcon = Integer.parseInt(overhead_icon.getText());
			if (!options.getText().isEmpty()) {
				String[] split = options.getText().split(";");
				for (int index = 0; index < def.options.length; index++) {
					String option = split[index];
					def.options[index] = option.equalsIgnoreCase("null") ? null : option;
				}
			}
			
			if (!model_ids.getText().isEmpty()) {
				String[] models = model_ids.getText().split(";");
				def.modelIds = new int[models.length];
				for (int index = 0; index < def.modelIds.length; index++) {
					def.modelIds[index] = Integer.parseInt(models[index]);
				}
			} else {
				def.modelIds = null;
			}
			
			if (!chat_heads.getText().isEmpty()) {
				String[] heads = chat_heads.getText().split(";");
				def.npcChatHeads = new int[heads.length];
				for (int index = 0; index < def.npcChatHeads.length; index++) {
					def.npcChatHeads[index] = Integer.parseInt(heads[index]);
				}
			} else {
				def.npcChatHeads = null;
			}
			
			String[] colors = modified_colors.getText().split(";");
			if (!modified_colors.getText().isEmpty() && !modified_colors.getText().equalsIgnoreCase("null")) {
				def.originalModelColors = new int[colors.length];
				def.modifiedModelColors = new int[colors.length];
				for (int index = 0; index < colors.length; index++) {
					String[] color = colors[index].split("=");
					def.originalModelColors[index] = Integer.parseInt(color[0]);
					def.modifiedModelColors[index] = Integer.parseInt(color[1]);
				}
			} else {
				def.originalModelColors = null;
				def.modifiedModelColors = null;
			}
			
			String[] textures = modified_textures.getText().split(";");
			if (!modified_textures.getText().isEmpty() && !modified_textures.getText().equalsIgnoreCase("null")) {
				def.originalTextureColors = new int[textures.length];
				def.modifiedTextureColors = new int[textures.length];
				for (int index = 0; index < textures.length; index++) {
					String[] texture = textures[index].split("=");
					def.originalTextureColors[index] = Integer.parseInt(texture[0]);
					def.modifiedTextureColors[index] = Integer.parseInt(texture[1]);
				}		
			} else {
				def.originalTextureColors = null;
				def.modifiedTextureColors = null;
			}
			
			def.invisibleOnMap = visable.isSelected();
			saveClientScripts();
			if (def.save(cache))
				Dialogues.alert(AlertType.INFORMATION, "Success", null, "Your changes were saved successfully.");
		} catch (Exception e) {
			InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to save, please your check spelling.", "", StringUtilities.getStackTrace(e));
		}
		LoaderManager.getLoader(LoaderManager.NPC).reload();
		startup(tab, true, def.id);
	}
	
	@SuppressWarnings({ "unchecked" })
	private void saveClientScripts() {
		String text = client_scripts.getText();
		if (text.isEmpty()) {
			def.params = null; return;
		}
		String[] line = text.split("\\n");
		Map replacement = new HashMap(1);
		for (int index = 0; index < line.length; index++) {
			String[] script = line[index].split(",");
			String key = script[0].split(":")[1].replaceAll(" ", "");
			String value = script[1].split(":")[1].replaceAll(" ", "");
			try {
				replacement.put(Integer.parseInt(key), Integer.parseInt(value));
			} catch (Exception e) {
				replacement.put(Integer.parseInt(key), value);
			}
		}
		def.params = (HashMap) replacement;
	}
	
	/**
	 * Loads client scripts for item into text area.
	 */
	private void loadClientScripts() {
		String text = "";
		String sep = System.getProperty("line.separator");
		if (def.params == null) {
			client_scripts.setText(text);
			return;
		}
		for (Iterator interator = def.params.keySet().iterator(); interator.hasNext(); text = text + sep) {
			int key = ((Integer) interator.next()).intValue();
			Object value = def.params.get(Integer.valueOf(key));
			text = text + "KEY: " + key + ", VALUE: " + value;
		}
		client_scripts.setText(text);
	}

}
