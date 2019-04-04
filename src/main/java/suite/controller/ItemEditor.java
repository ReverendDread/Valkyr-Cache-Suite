package suite.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import store.codec.ItemDefinition;
import store.loader.ItemLoader;
import suite.Constants;
import suite.Main;
import suite.dialogue.Dialogues;
import suite.dialogue.InformationDialogue;
import utility.StringUtilities;

/**
 * 
 * @author ReverendDread
 * Jul 10, 2018
 */
public class ItemEditor {

	private FileStore cache = Selection.cache;
	private ItemDefinition def;
	private CustomTab tab;
	
	private ObservableList<ItemDefinition> definitions;
	
	@FXML private ListView<ItemDefinition> item_list;
	@FXML private TextField name, cost, team, stackable, equip_type, equip_slot, noted_item, note_item, lend_item, lent_item,
			zoom, rotation_x, rotation_y, offset_x, offset_y, inventory_model, male_equip_1, male_equip_2, male_equip_3,
			female_equip_1, female_equip_2, female_equip_3, modified_colors, modified_textures, inventory_options,
			ground_options, search_bar, stack_ids, stack_amounts, resize_x, resize_y, resize_z, femaleOffsetX, femaleOffsetY, 
			femaleOffsetZ, maleOffsetX, maleOffsetY, maleOffsetZ;
	@FXML private TextArea clientScripts;	
	@FXML private CheckBox members, unnoted;
	
	@FXML 
	private void select_item() {
		def = item_list.getSelectionModel().getSelectedItem();
		if (def != null)
			view_item();
	}
	
	@FXML 
	private void dump_models() {
		if (this.def != null) {
			ModelDumper.dump_models(new int[] { def.inventory_model, def.maleModel0, def.maleModel1, 
					def.maleModel2, def.femaleModel0, def.femaleModel1, def.femaleModel2 });
		}
	}

	/**
	 * Refreshes and displays the selected item.
	 * 
	 * @param def
	 */
	private void view_item() {
		tab.setText("Item Editor - " + def.name + ", ID: " + def.id);
		this.name.setText(def.name);
		this.cost.setText("" + def.cost);
		this.team.setText("" + def.team);
		this.stackable.setText("" + (def.stackable >= 1 ? "True" : "False"));
		this.equip_type.setText("" + def.equipType);
		this.equip_slot.setText("" + def.equipSlot);
		this.noted_item.setText("" + def.notedID);
		this.note_item.setText("" + def.noteTemplate);
		this.lend_item.setText("" + def.lendedItemId);
		this.unnoted.setSelected(def.unnoted);
		this.members.setSelected(def.members);
		this.lent_item.setText("" + def.lendedItemId);
		this.zoom.setText("" + def.zoom);
		this.rotation_x.setText("" + def.rotation_x);
		this.rotation_y.setText("" + def.rotation_y);
		this.offset_x.setText("" + def.offset_x);
		this.offset_y.setText("" + def.offset_y);
		this.inventory_model.setText("" + def.inventory_model);
		this.male_equip_1.setText("" + def.maleModel0);
		this.male_equip_2.setText("" + def.maleModel1);
		this.male_equip_3.setText("" + def.maleModel2);
		this.female_equip_1.setText("" + def.femaleModel0);
		this.female_equip_2.setText("" + def.femaleModel1);
		this.female_equip_3.setText("" + def.femaleModel2);
		this.maleOffsetX.setText("" + def.maleModelOffsetX);
		this.maleOffsetY.setText("" + def.maleModelOffsetY);
		this.maleOffsetZ.setText("" + def.maleModelOffsetZ);
		this.femaleOffsetX.setText("" + def.femaleModelOffsetX);
		this.femaleOffsetY.setText("" + def.femaleModelOffsetY);
		this.femaleOffsetZ.setText("" + def.femaleModelOffsetZ);
		this.resize_x.setText("" + def.resizeX);
		this.resize_y.setText("" + def.resizeY);
		this.resize_z.setText("" + def.resizeZ);
		this.stack_ids.setText("" + StringUtilities.formatIntArrayToString(def.stackIds));
		this.stack_amounts.setText("" + StringUtilities.formatIntArrayToString(def.stackAmounts));
		this.inventory_options.setText(StringUtilities.formatStringArrayToString(def.inventory_options));
		this.ground_options.setText(StringUtilities.formatStringArrayToString(def.ground_options));
		this.modified_colors.setText(StringUtilities.formatMultiArrayInt(def.colorsToFind, def.colorsToReplace));
		this.modified_textures.setText(StringUtilities.formatMultiArrayShort(def.retexturesToFind, def.retexturesToReplace));
		loadClientScripts();
	}

	/**
	 * Loads item list.
	 * @param stage TODO
	 */
	public void startup(CustomTab tab, boolean refresh, int lastid) {
		this.tab = tab;
		definitions = FXCollections.observableArrayList();
		definitions.addAll(ItemLoader.getDefinitions());
		if (!refresh) {
			addChangeListeners();
			addContextMenu();
			item_list.setItems(definitions);
		} else {
			def = ItemLoader.getDefinitions()[lastid];
			view_item();
		}
	}

	/**
	 * Loads client scripts for item into text area.
	 */
	private void loadClientScripts() {
		String text = "";
		String sep = System.getProperty("line.separator");
		if (def.params == null) {
			clientScripts.setText(text);
			return;
		}
		for (Iterator interator = def.params.keySet().iterator(); interator.hasNext(); text = text + sep) {
			int key = ((Integer) interator.next()).intValue();
			Object value = def.params.get(Integer.valueOf(key));
			text = text + "KEY: " + key + ", VALUE: " + value;
		}
		clientScripts.setText(text);
	}

	@FXML
	public void save() {
		try {
			def.name = name.getText();
			def.cost = Integer.parseInt(cost.getText());
			def.team = Integer.parseInt(team.getText());
			boolean stacking = Boolean.parseBoolean(stackable.getText());
			def.stackable = stacking ? 1 : 0;
			def.notedID = Integer.parseInt(noted_item.getText());
			def.noteTemplate = Integer.parseInt(note_item.getText());
			def.lendedItemId = Integer.parseInt(lent_item.getText());
			def.switchLendItemId = Integer.parseInt(lend_item.getText());
			def.members = members.isSelected();
			def.unnoted = unnoted.isSelected();
			def.rotation_x = Integer.parseInt(rotation_x.getText());
			def.rotation_y = Integer.parseInt(rotation_y.getText());
			def.inventory_model = Integer.parseInt(inventory_model.getText());
			def.zoom = Integer.parseInt(zoom.getText());
			def.offset_x = Integer.parseInt(offset_x.getText());
			def.offset_y = Integer.parseInt(offset_y.getText());
			def.equipType = Integer.parseInt(equip_type.getText());
			def.equipSlot = Integer.parseInt(equip_slot.getText());
			def.maleModel0 = Integer.parseInt(male_equip_1.getText());
			def.maleModel1 = Integer.parseInt(male_equip_2.getText());
			def.maleModel2 = Integer.parseInt(male_equip_3.getText());
			def.femaleModel0 = Integer.parseInt(female_equip_1.getText());
			def.femaleModel1 = Integer.parseInt(female_equip_2.getText());
			def.femaleModel2 = Integer.parseInt(female_equip_3.getText());
			def.maleModelOffsetX = Integer.parseInt(maleOffsetX.getText());
			def.maleModelOffsetY = Integer.parseInt(maleOffsetY.getText());
			def.maleModelOffsetZ = Integer.parseInt(maleOffsetZ.getText());
			def.femaleModelOffsetX = Integer.parseInt(femaleOffsetX.getText());
			def.femaleModelOffsetY = Integer.parseInt(femaleOffsetY.getText());
			def.femaleModelOffsetZ = Integer.parseInt(femaleOffsetZ.getText());
			def.resizeX = Integer.parseInt(resize_x.getText());
			def.resizeY = Integer.parseInt(resize_y.getText());
			def.resizeZ = Integer.parseInt(resize_z.getText());
			if (!stack_ids.getText().isEmpty()) {
				String[] stackIds = stack_ids.getText().split(";");
				def.stackIds = new int[stackIds.length];
				for (int index = 0; index < stackIds.length; index++) {
					int id = Integer.parseInt(stackIds[index]);
					def.stackIds[index] = id;
				}
			} else {
				def.stackIds = null;
			}
			
			if (!stack_amounts.getText().isEmpty()) {
				String[] stackAmounts = stack_amounts.getText().split(";");
				def.stackAmounts = new int[stackAmounts.length];
				for (int index = 0; index < stackAmounts.length; index++) {
					int id = Integer.parseInt(stackAmounts[index]);
					def.stackAmounts[index] = id;
				}
			} else {
				def.stackAmounts = null;
			}
			
			String[] inventoryOptions = inventory_options.getText().split(";");
			for (int index = 0; index < inventoryOptions.length; index++) {
				String option = inventoryOptions[index];
				def.inventory_options[index] = option.equals("null") ? null : option;
			}	
			
			String[] groundOptions = ground_options.getText().split(";");
			for (int index = 0; index < groundOptions.length; index++) {
				String option = groundOptions[index];
				def.ground_options[index] = option.equals("null") ? null : option;
			}	
			
			String[] colors = modified_colors.getText().split(";");
			if (!modified_colors.getText().isEmpty() && !modified_colors.getText().equalsIgnoreCase("null")) {
				def.colorsToFind = new int[colors.length];
				def.colorsToReplace = new int[colors.length];
				for (int index = 0; index < colors.length; index++) {
					String[] color = colors[index].split("=");
					def.colorsToFind[index] =  Integer.parseInt(color[0]);
					def.colorsToReplace[index] = Integer.parseInt(color[1]);
				}
			} else {
				def.colorsToFind = null;
				def.colorsToReplace = null;
			}
			
			String[] textures = modified_textures.getText().split(";");
			if (!modified_textures.getText().isEmpty() && !modified_textures.getText().equalsIgnoreCase("null")) {
				def.retexturesToFind = new short[textures.length];
				def.retexturesToReplace = new short[textures.length];
				for (int index = 0; index < textures.length; index++) {
					String[] texture = textures[index].split("=");
					def.retexturesToFind[index] = (short) Integer.parseInt(texture[0]);
					def.retexturesToReplace[index] = (short) Integer.parseInt(texture[1]);
				}
			} else {
				def.retexturesToFind = null;
				def.retexturesToReplace = null;
			}
			
			definitions.set(def.id, def);
			
			saveClientScripts();
			
			if (def.save(cache))
				Dialogues.alert(AlertType.INFORMATION, "Success", null, "Your changes were saved successfully.");
			
			ItemLoader.getDefinitions()[def.id] = def;

		} catch (Exception e) {
			InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to save, please your check spelling.", "", StringUtilities.getStackTrace(e));
		}
		startup(tab, true, def.id);
	}

	@SuppressWarnings({ "unchecked" })
	private void saveClientScripts() {
		String text = clientScripts.getText();
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
	 * Adds context menu to item list.
	 */
	private void addContextMenu() {
		ContextMenu item_list_menu = new ContextMenu();
		
		MenuItem duplicate = new MenuItem("Duplicate");
		duplicate.setOnAction((event) -> {
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Create a duplicate");
			dialog.setHeaderText("");
			dialog.setContentText("Please enter the desired ID:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				ItemDefinition original = item_list.getSelectionModel().getSelectedItem();
				if (original != null) {
					ItemDefinition clone = (ItemDefinition) original.clone();
					clone.id = Integer.parseInt(result.get());
					item_list.getItems().add(clone.id, clone);
					def = clone;
					save();
					view_item();
				}
			}		
		});
		
//		MenuItem importDat = new MenuItem("Import dat");
//		importDat.setOnAction((event) -> {
//			FileChooser chooser = new FileChooser();
//			chooser.setTitle("Choose your import directory.");
//			Stage stage = Main.getPrimaryStage();
//			File default_dir = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator);
//			chooser.setInitialDirectory(default_dir);
//			File file = chooser.showOpenDialog(stage);
//			if (file == null)
//				return;	
//			String id = file.getName().replaceAll(".dat", "");
//			ItemDefinition item_def;
//			item_def = new ItemDefinition(Integer.parseInt(id));
//			try {
//				item_def.decode(new InputStream(Files.readAllBytes(file.toPath())));
//				ItemDefinition clone = (ItemDefinition) item_def.clone();
//				item_list.getItems().add(item_def.id, clone);
//				def = clone;
//				save();
//				view_item();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}				
//		});
		
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
				ItemDefinition selectedItem = item_list.getSelectionModel().getSelectedItem();
				byte[] data = selectedItem.encode();
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(directory + File.separator + selectedItem.id + ".dat"));
				dos.write(data);
				dos.close();
			} catch (IOException e) {
				Dialogues.alert(AlertType.ERROR, "Error", "An error has occured.", StringUtilities.getStackTrace(e));
			}
		});
		item_list_menu.getItems().addAll(duplicate, /*importDat,*/ export);
		item_list.setContextMenu(item_list_menu);
	}

	/**
	 * Add the listeners for when a field is changed.
	 */
	private void addChangeListeners() {
		try {
			search_bar.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					boolean byId = newValue.isEmpty() ? false : Character.isDigit(newValue.charAt(0));
					if (!byId) {
						if (oldValue != null && (newValue.length() < oldValue.length())) {
							item_list.setItems(definitions);
						}
						ObservableList<ItemDefinition> filtered = FXCollections.observableArrayList();
						for (ItemDefinition item : item_list.getItems()) {
							if (item != null && item.name.toUpperCase().contains(newValue.toUpperCase()))
								filtered.add(item);
						}
						item_list.setItems(filtered);
					} else {
						int id = Integer.parseInt(newValue);
						def = ItemLoader.getDefinitions()[id];
						if (Constants.settings.focusOnSearch) {
							item_list.getSelectionModel().select(id);
							item_list.scrollTo(id);
						}
						view_item();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
