package suite.controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import misc.CustomTab;
import misc.EditorType;
import persistable.Persistable;
import store.FileStore;
import store.loader.LoaderManager;
import suite.Constants;
import suite.Main;
import suite.dialogue.Dialogues;

public class Selection {
	
	public static boolean debug;
	public static boolean loaded_cache;
	public static FileStore cache;
	public Stage stage;
	
	@FXML public TabPane tabs;
	@FXML private ChoiceBox<String> toolChoice = new ChoiceBox<String>();
	@FXML private ProgressBar progress_bar;
	@FXML private Button open;
	
	public void inititalize(Stage stage) throws IOException {
		try {
			this.stage = stage;
			Persistable persistable = Constants.settings;
			if (persistable != null && persistable.cacheDir != null) {
				File dir = new File(persistable.cacheDir);
				if (dir.exists()) {
					Selection.cache = new FileStore(persistable.cacheDir);
					load_definitions();
				} else 
					open_cache();
			} else 
				open_cache();
			toolChoice.getItems().setAll(
					FXCollections.observableArrayList("Items", "NPCs", "Objects", "Model Dumper", 
							"Model Packer", "Render Animations", "Spot Animations", "Materials", "Particles", "Sprites", "Textures", "Hitmark"));
			stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					if (event.isControlDown() && event.getCode() == KeyCode.S) {
						save();
					}
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
			Platform.exit();
		}
	}
	
    public void load_definitions() {
    	if (cache == null || cache.getIndexes().length <= 0) {
    		Dialogues.alert(AlertType.ERROR, "An error has occured.", null, "There has been an error trying to load the cache.");
    		return;
    	}
    	createTask("Preloading data...", true, new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				new LoaderManager(cache);
				return null;
			}
    		
    	});
    }

	@FXML public void openTool() {
		int selected = toolChoice.getSelectionModel().getSelectedIndex();
		if (cache == null) {
			Dialogues.alert(AlertType.ERROR, "An error has occured.", null, "Please load a cache before attempting to open an editor.");
			return;
		}
		switch (selected) {
		
			case 0:
				item_editor();
				break;
				
			case 1:
				npc_editor();
				break;
				
			case 2:
				object_editor();
				break;
				
			case 3:
				model_dumper();
				break;
				
			case 4:
				model_packer();
				break;
				
			case 5:
				render_anim_editor();
				break;
				
			case 6:
				spot_anim_editor();
				break;
				
			case 7:
				material_editor();
				break;
				
			case 8:
				particle_editor();
				break;
				
			case 9:
				sprite_editor();
				break;
				
			case 10:
				//texture_editor();
				break;
				
			case 11:
				hitmark_editor();
				break;
				
			default:
				Dialogues.alert(AlertType.ERROR, "An error has occured.", null, "Please select a valid editor.");
				
		}
	}
	
	/**
	 * Opens the hitmark editor.
	 */
	private void hitmark_editor() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Hitmark.fxml"));
			Node node = loader.load();			
			HitmarkEditor hitmark_editor = (HitmarkEditor) loader.getController();
			CustomTab tab = new CustomTab(hitmark_editor, EditorType.HITMARK);			
			hitmark_editor.initialize(tab, false);
			tab.setText("Hitmark Editor");
			tab.setContent(node);			
			addCloseContext(tab);		
			tabs.getTabs().add(tab);    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the spot animation editor.
	 */
	private void spot_anim_editor() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/SpotAnimations.fxml"));
			Node node = loader.load();			
			SpotAnimations particle_editor = (SpotAnimations) loader.getController();
			CustomTab tab = new CustomTab(particle_editor, EditorType.SPOT);			
			particle_editor.startup(tab, false, 0);	
			tab.setText("Spot Editor");
			tab.setContent(node);			
			addCloseContext(tab);		
			tabs.getTabs().add(tab);    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the particle editor.
	 */
	private void particle_editor() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ParticleEditor.fxml"));
			Node node = loader.load();			
			ParticleEditor particle_editor = (ParticleEditor) loader.getController();
			CustomTab tab = new CustomTab(particle_editor, EditorType.PARTICLE);			
			particle_editor.initialize(tab, false);		
			tab.setText("Particle Editor");
			tab.setContent(node);			
			addCloseContext(tab);		
			tabs.getTabs().add(tab); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the render animation editor.
	 */
	private void render_anim_editor() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/RenderAnimations.fxml"));
			Node node = loader.load();			
			RenderAnimations render_editor = (RenderAnimations) loader.getController();
			CustomTab tab = new CustomTab(render_editor, EditorType.RENDER);			
			render_editor.startup(tab, false, 0);			
			tab.setText("Render Editor");
			tab.setContent(node);			
			addCloseContext(tab);		
			tabs.getTabs().add(tab);      
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens the object editor.
	 */
	private void object_editor() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ObjectEditor.fxml"));
			Node node = loader.load();			
			ObjectEditor object_editor = (ObjectEditor) loader.getController();
			CustomTab tab = new CustomTab(object_editor, EditorType.OBJECT);			
			object_editor.startup(tab, false, 0);			
			tab.setText("Object Editor");
			tab.setContent(node);			
			addCloseContext(tab);		
			tabs.getTabs().add(tab);    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens the npc editor.
	 */
	private void npc_editor() {
		try {		
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/NPCEditor.fxml"));	
			Node node = loader.load();			
			NPCEditor npc_editor = (NPCEditor) loader.getController();
			CustomTab tab = new CustomTab(npc_editor, EditorType.NPC);			
			npc_editor.startup(tab, false, 0);			
			tab.setText("NPC Editor");
			tab.setContent(node);			
			addCloseContext(tab);		
			tabs.getTabs().add(tab);        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens the item editor.
	 */
	public void item_editor() {
		try {		
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ItemEditor.fxml"));			
			Node node = loader.load();			
			ItemEditor item_editor = (ItemEditor) loader.getController();
			CustomTab tab = new CustomTab(item_editor, EditorType.ITEM);		
			item_editor.startup(tab, false, 0);	
			tab.setText("Item Editor");
			tab.setContent(node);	
			addCloseContext(tab);		
			tabs.getTabs().add(tab);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the material editor.
	 */
	public void material_editor() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Materials.fxml"));
			Node node = loader.load();			
			MaterialEditor mat_editor = (MaterialEditor) loader.getController();
			CustomTab tab = new CustomTab(mat_editor, EditorType.MATERIAL);		
			mat_editor.startup(tab);		
			tab.setText("Materials");
			tab.setContent(node);		
			addCloseContext(tab);		
			tabs.getTabs().add(tab);       
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the material editor.
	 */
	public void sprite_editor() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/SpriteEditor.fxml"));
			Node node = loader.load();			
			SpriteEditor sprite_editor = (SpriteEditor) loader.getController();
			CustomTab tab = new CustomTab(sprite_editor, EditorType.SPRITE);		
			sprite_editor.startup(tab, true, 0, 0);		
			tab.setText("Sprites Editor");
			tab.setContent(node);		
			addCloseContext(tab);		
			tabs.getTabs().add(tab);       
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the material editor.
	 */
	public void texture_editor() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Textures.fxml"));
			Node node = loader.load();			
			Textures sprite_editor = (Textures) loader.getController();
			CustomTab tab = new CustomTab(sprite_editor, EditorType.TEXTURE);		
			sprite_editor.startup(tab, true, 0);		
			tab.setText("Texture Editor");
			tab.setContent(node);		
			addCloseContext(tab);		
			tabs.getTabs().add(tab);       
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the model packer.
	 */
	public void model_packer() {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ModelPacker.fxml"));
			root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Constants.FAVICON));
            stage.setTitle(Constants.WINDOW_NAME + " - Model Packer");
            stage.setScene(new Scene(root, 600, 200));
            stage.setResizable(false);
            ModelPacker packer = (ModelPacker) loader.getController();
            packer.initialize(stage);
            stage.show();     
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the model dumper.
	 */
	public void model_dumper() {
		ModelDumper.dump_models(null);
	}
	
	/**
	 * Saves content from current tab.
	 */
	@FXML public void save() {
		
		if (tabs.getTabs().isEmpty()) {
			Dialogues.alert(AlertType.ERROR, "An error has occured.", null, "Please select an editor before attempting to save.");
			return;
		}
		
		CustomTab tab = (CustomTab) tabs.getSelectionModel().getSelectedItem();
		
		switch (tab.getType()) {
		
			case ITEM:
				ItemEditor item_editor = (ItemEditor) tab.getController();
				item_editor.save();
				break;
				
			case MATERIAL:
				MaterialEditor mat_editor = (MaterialEditor) tab.getController();
				mat_editor.save();
				break;
				
			case NPC:
				NPCEditor npc_editor = (NPCEditor) tab.getController();
				npc_editor.save();
				break;
				
			case OBJECT:
				ObjectEditor object_editor = (ObjectEditor) tab.getController();
				object_editor.save();
				break;
				
			case RENDER:
				RenderAnimations render_editor = (RenderAnimations) tab.getController();
				render_editor.save();
				break;
				
			case SPOT:
				SpotAnimations spot_editor = (SpotAnimations) tab.getController();
				spot_editor.save();
				break;
				
			case PARTICLE:
				ParticleEditor particle_editor = (ParticleEditor) tab.getController();
				particle_editor.save();
				break;
				
			case HITMARK:
				HitmarkEditor hitmark_editor = (HitmarkEditor) tab.getController();
				hitmark_editor.save();
				break;
				
			default:
				break;
		
		}
	}
	
	/**
	 * Adds close context menus to tool tabs.
	 * @param tab
	 */
	public void addCloseContext(Tab tab) {
		ContextMenu menu = new ContextMenu();
		MenuItem close = new MenuItem("Close");
		close.setOnAction(e -> {
			tabs.getTabs().remove(tab);
		});
		MenuItem closeAll = new MenuItem("Close All");
		closeAll.setOnAction(e -> {
			Object[] options = { "Yes", "No" };
			int option = JOptionPane.showOptionDialog(null, "Are you sure you want to close all tabs?", "Confirmation", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			if (option == 0)
				tabs.getTabs().clear();
		});
		menu.getItems().add(close);
		menu.getItems().add(closeAll);
		tab.setContextMenu(menu);
	}
	
	/**
	 * Opens settings window.
	 */
	@FXML public void open_settings() {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Settings.fxml"));
			root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Constants.FAVICON));
            stage.setTitle(Constants.WINDOW_NAME + " - Settings");
            stage.setScene(new Scene(root, 600, 150));
            stage.setResizable(false);
            Settings settings = (Settings) loader.getController();
            settings.startup();
            stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the cache from a directory.
	 */
	@FXML
	public void open_cache() {
		try {
			tabs.getTabs().clear();
			DirectoryChooser dir_chooser = new DirectoryChooser();
			dir_chooser.setTitle("Locate Cache Directory..");
			Stage stage = Main.getPrimaryStage();
			File default_dir = new File(System.getProperty("user.home") + "\\Desktop\\");
			dir_chooser.setInitialDirectory(default_dir);
			File directory = dir_chooser.showDialog(stage);
			if (directory != null) {
				Selection.cache = new FileStore(directory.toString() + "\\");
				load_definitions();
				return;
			}
			Dialogues.alert(AlertType.ERROR, "Error", null, "Failed to locate cache directory.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Safely exits the program.
	 */
	@FXML
	private void quit() {
		System.exit(0);
	}
	
	/**
	 * Creates a new task on a new thread.
	 * @param task
	 * 			the task to start.
	 */
	public void createTask(String task_title, boolean locks, Task<?> task) {

		stage.setTitle(Constants.WINDOW_NAME + " - " + task_title);
		
		progress_bar.setVisible(true);
		open.setDisable(true);
		progress_bar.progressProperty().unbind();
		progress_bar.progressProperty().bind(task.progressProperty());

		new Thread(task).start();

		task.setOnSucceeded(e -> {
			stage.setTitle(Constants.WINDOW_NAME);
			progress_bar.setVisible(false);
			open.setDisable(false);
		});

		task.setOnFailed(e -> {
			stage.setTitle(Constants.WINDOW_NAME);
			progress_bar.setVisible(false);
			open.setDisable(false);
		});
	}

}
