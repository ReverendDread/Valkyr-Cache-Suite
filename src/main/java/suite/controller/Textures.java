package suite.controller;

import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import misc.CustomTab;
import store.codec.TextureDefinition;
import store.loader.TextureDiffuseLoader;

/**
 * 
 * @author ReverendDread
 * Jul 10, 2018
 */
public class Textures {
	
	private CustomTab tab;
	
	@FXML private Canvas canvas;
	
	@FXML private ListView<TextureDefinition> textures;
	
	@FXML private TextField search_bar;
	
	@FXML private TextField rgb, field1778, fileIds, field1780, field1781, field1786, field1782, field1783;
	
	private TextureDefinition texture;
	
	/**
	 * Loads item list.
	 * @param stage TODO
	 */
	public void startup(CustomTab tab, boolean refresh, int texture_id) {
		this.tab = tab;
		this.textures.getItems().setAll(TextureDiffuseLoader.getDefinitions());
		addContextMenus();
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

		});
		
		/* Add create option to context menu */
		archiveContext.getItems().add(create);
		
		search_bar.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int id = Integer.parseInt(newValue);
				texture = TextureDiffuseLoader.getDefinitions()[id];
				textures.getSelectionModel().select(id);
				textures.scrollTo(id);
				select_texture();			
			}
		});
		
	}
	
	@FXML
	private void select_texture() {
		TextureDefinition definition = this.textures.getSelectionModel().getSelectedItem();
		if (definition == null)
			return;
		rgb.setText("" + definition.field1777);
		field1778.setText("" + definition.field1778);
		fileIds.setText(Arrays.toString(definition.field1779));
		field1780.setText(Arrays.toString(definition.field1780));
		field1781.setText(Arrays.toString(definition.field1781));
		field1786.setText(Arrays.toString(definition.field1786));
		field1782.setText("" + definition.field1782);
		field1783.setText("" + definition.field1783);
		draw(null);
	}
	
	/**
	 * Draws an image on the canvas.
	 * @param image
	 */
	private void draw(Image image) {
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        //gc.setFill();
	}
	
}
