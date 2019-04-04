package suite.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import misc.CustomTab;
import store.codec.image.SpriteContainer;
import store.codec.image.SpriteFrame;
import store.loader.LoaderManager;
import store.loader.SpriteLoader;
import suite.Main;
import suite.dialogue.InformationDialogue;
import utility.StringUtilities;

/**
 * 
 * @author ReverendDread
 * Jul 10, 2018
 */
public class SpriteEditor {
	
	private CustomTab tab;
	private SpriteContainer sprite;
	@FXML private ListView<SpriteContainer> archives;
	@FXML private ListView<SpriteFrame> frames;
	@FXML private Canvas canvas;
	@FXML private TextField search_bar;
	private int archive;
	private int frame;
	
	/**
	 * Loads item list.
	 * @param stage TODO
	 */
	public void startup(CustomTab tab, boolean refresh, int archive, int file) {
		this.tab = tab;
		this.archives.getItems().setAll(SpriteLoader.getSprites());
		SpriteLoader.getSprites()[archive].decodeImage(Selection.cache, 8, archive, 0);
		this.frames.getItems().setAll(SpriteLoader.getSprites()[archive].toSpriteFrames());
		addContextMenus();
	}

	@FXML
	private void select_frame() {
		SpriteFrame sprite_frame = frames.getSelectionModel().getSelectedItem();
		if (Objects.isNull(sprite_frame))
			return;
		Image image = sprite_frame.getImage();
		frame = frames.getSelectionModel().getSelectedIndex();
		if (image != null) {
			tab.setText("Sprites Editor - Viewing Sprite " + archive + ", " + frame);
			draw(image);
		} else {
			InformationDialogue.create("Error", "Unable to load sprite frame, no image exists.", AlertType.ERROR, "", "", null);
		}
	}
	
	@FXML
	private void select_archive() {
		
		sprite = archives.getSelectionModel().getSelectedItem();
		archive = archives.getSelectionModel().getSelectedIndex();
		
		if (!SpriteLoader.getSprites()[archive].isLoaded()) { //Decode the images in container for display.
			SpriteLoader.getSprites()[archive].decodeImage(Selection.cache, 8, archive, 0);
		}
		
		SpriteLoader.needsCleanup();		
		
		try {
			frames.getItems().clear();
			tab.setText("Sprites Editor - Viewing Sprite " + archive + ", 0");
			if (SpriteLoader.getSprites()[archive].getImages() != null) {			
				List<SpriteFrame> sprites = SpriteLoader.getSprites()[archive].toSpriteFrames();			
				if (sprites.get(0) == null) {
					draw(null);
					return;
				}
				frames.getItems().setAll(sprites);
				draw(frames.getItems().get(0).getImage());	
			}
		} catch (Exception e) {
			InformationDialogue.create("Error", "Unable to load sprite container.", AlertType.ERROR, "Sprite container has failed to decode.", "", StringUtilities.getStackTrace(e));
		}
		
	}
	
	/**
	 * Draws an image on the canvas.
	 * @param image
	 */
	private void draw(Image image) {
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.drawImage(image, 10, 10);
	}
	
	/**
	 * Adds context menus to the lists.
	 */
	private void addContextMenus() {
		
		/* The archive ListView context menu. */
		ContextMenu archiveContext = new ContextMenu();
		
		/* The create menu item for creating new sprite collections. */
		MenuItem create = new MenuItem("Create");
		
		/* Create function for create option */
		create.setOnAction((action) -> {
			try {
				/* Default image added to sprite collection upon creating one.*/
				BufferedImage placeholder = ImageIO.read(Main.class.getResource("assets/hijabnigga.jpeg"));
				SpriteContainer sprite = new SpriteContainer(placeholder);
				int id = SpriteLoader.getSprites().length + 1;
				if (Selection.cache.getIndexes()[8].putFile(id, 0, sprite.encode())) {
					LoaderManager.getLoader(LoaderManager.SPRITE).reload();
					startup(tab, true, id, 0);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		/* Add create option to context menu */
		archiveContext.getItems().add(create);
		
		/* The dump frames menu item for dumping sprite collections frames. */
		MenuItem dumpFrames = new MenuItem("Dump Frames");
		
		/* Create function for dump frames option */
		dumpFrames.setOnAction((action) -> {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Choose your dump directory.");
			File directory = chooser.showDialog(Main.getPrimaryStage());
			if (directory == null)
				return;
			for (int index = 0; index < sprite.getImages().size(); index++) {
				try {
					File output = new File(directory + File.separator + archive + "-" + index + ".png");
					ImageIO.write(sprite.getImages().get(index), "png", output);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		/* Add dump frames option to context menu */
		archiveContext.getItems().add(dumpFrames);
		
		/* Adds the context menu to archives ListView. */
		archives.setContextMenu(archiveContext);
		
		/* The frames ListView context menu */
		ContextMenu frameContext = new ContextMenu();
		
		/* The add frame menu item for adding new frames to a sprite collection. */
		MenuItem add = new MenuItem("Add Frame");
		
		add.setOnAction((action) -> {
			try {
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Choose sprite to pack.");
				chooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
				File file = chooser.showOpenDialog(Main.getPrimaryStage());
				if (file != null) {
					SpriteLoader.getSprites()[archive].addImage(ImageIO.read(file));
					frames.getItems().setAll(SpriteLoader.sprites[archive].toSpriteFrames());
					Selection.cache.getIndexes()[8].putFile(archive, 0, SpriteLoader.getSprites()[archive].encode());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		frameContext.getItems().add(add);
		
		MenuItem replace = new MenuItem("Replace Frame");
		replace.setOnAction((action) -> {
			try {
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Choose sprite to pack.");
				chooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
				File file = chooser.showOpenDialog(Main.getPrimaryStage());
				if (file != null) {
					BufferedImage image = ImageIO.read(file);
					SpriteLoader.getSprites()[archive].replaceImage(image, frame);
					if (image.getHeight() > SpriteLoader.getSprites()[archive].getBiggestHeight()) {
						SpriteLoader.getSprites()[archive].setBiggestHeight(image.getHeight());
					}
					if (image.getWidth() > SpriteLoader.getSprites()[archive].getBiggestWidth()) {
						SpriteLoader.getSprites()[archive].setBiggestWidth(image.getWidth());
					}
					frames.getItems().setAll(SpriteLoader.sprites[archive].toSpriteFrames());
					Selection.cache.getIndexes()[8].putFile(archive, 0, SpriteLoader.getSprites()[archive].encode());
				}
				select_archive();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		frameContext.getItems().add(replace);	
		
		MenuItem delete = new MenuItem("Delete Frame");
		delete.setOnAction((action) -> {
			if (frame < 0 || frame > SpriteLoader.getSprites()[archive].getImages().size())
				return;
			SpriteLoader.getSprites()[archive].removeImage(frame);
			Selection.cache.getIndexes()[8].putFile(archive, 0, SpriteLoader.getSprites()[archive].encode());
			frames.getItems().setAll(SpriteLoader.sprites[archive].toSpriteFrames());
		});
		frameContext.getItems().add(delete);
		frames.setContextMenu(frameContext);
		
		search_bar.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int id = Integer.parseInt(newValue);
				sprite = SpriteLoader.getSprites()[id];
				archives.getSelectionModel().select(id);
				archives.scrollTo(id);
				select_archive();			
			}
		});
		
		archives.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				archive = archives.getSelectionModel().getSelectedIndex();
				select_archive();	
			}
				
		});
		
		frames.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				frame = frames.getSelectionModel().getSelectedIndex();
				select_frame();
			}
				
		});
	}
	
	@FXML
	private void dumpAllSprites() {
		boolean confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to dump all sprite frames? This may take awhile, but will happen in the background.", "Confirmation", JOptionPane.YES_NO_OPTION) == 0;
		if (confirmed) {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Select your dumping location.");
			File directory = chooser.showDialog(Main.getPrimaryStage());
			if (directory != null) {
				Main.getSelection().createTask("Dumping Sprites...", false, new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						try {
							int size = SpriteLoader.getSprites().length;
							for (int index = 0; index < size; index++) {
								SpriteContainer sprite = SpriteLoader.getSprites()[index];
								if (sprite == null)
									continue;
								if (sprite.decodeImage(Selection.cache, 8, index, 0) && sprite.getImages() != null) {
									for (int image = 0; image < sprite.getImages().size(); image++) {
										if (sprite.getImages().get(image) == null)
											continue;
										File file = new File(directory + File.separator + index + "-" + image + ".png");
										ImageIO.write(sprite.getImages().get(image), "png", file);
									}
								}
							}
							System.gc();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}
					
				});
			}
		}
	}
	
}