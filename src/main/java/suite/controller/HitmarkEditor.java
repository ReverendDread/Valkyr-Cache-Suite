/**
 * 
 */
package suite.controller;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import misc.CustomTab;
import store.FileStore;
import store.Indices;
import store.codec.HitmarkDefinition;
import store.codec.image.SpriteFrame;
import store.loader.HitmarkLoader;
import store.loader.LoaderManager;
import store.loader.SpriteLoader;
import suite.Constants;
import suite.dialogue.Dialogues;
import suite.dialogue.InformationDialogue;
import utility.StringUtilities;

/**
 * @author ReverendDread
 * Jan 14, 2019
 */
public class HitmarkEditor {

	private FileStore cache = Selection.cache;
	private CustomTab tab;
	private HitmarkDefinition def;
	private ObservableList<HitmarkDefinition> definitions;
	
	@FXML private ListView<HitmarkDefinition> hitmarks;
	@FXML private TextField font, style, left, middle, right, hspeed, vspeed, markText, vTextOffset, fadeSpeed, anInt3861, anInt3863, search_bar;
	@FXML private ColorPicker fontColor;
	@FXML private Canvas canvas;
	@FXML private Slider slider;
	
	public void initialize(CustomTab tab, boolean refresh) {
		this.tab = tab;
		definitions = FXCollections.observableArrayList();
		definitions.addAll(HitmarkLoader.getDefinitions());
		hitmarks.getItems().setAll(definitions);
		search_bar.textProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals(""))
					return;
				int id = Integer.parseInt(newValue);
				def = HitmarkLoader.getDefinitions()[id];
				if (Constants.settings.focusOnSearch) {
					hitmarks.getSelectionModel().select(id);
					hitmarks.scrollTo(id);
				}
				view_item();
			}
			
		});
		ContextMenu context_menu = new ContextMenu();
		hitmarks.setContextMenu(context_menu);		
		MenuItem create = new MenuItem("Create");
		create.setOnAction((ActionEvent event) -> {	
			try {
				HitmarkDefinition blank_def = new HitmarkDefinition(-1);
				blank_def.id = HitmarkLoader.getDefinitions().length + 1;
				blank_def.save(cache);	
				def = blank_def;
				LoaderManager.getLoader(LoaderManager.HITMARK).reload();
				definitions.setAll(HitmarkLoader.getDefinitions());
				hitmarks.getItems().setAll(definitions);
				if (Constants.settings.focusOnSearch) {
					hitmarks.getSelectionModel().select(blank_def.id);
					hitmarks.scrollTo(blank_def.id);
				}
			} catch (Throwable e) {
				InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to create new object.", "", 
						"Please notify ReverendDread about this error, and a way to reproduce it.");
			}
		});
		context_menu.getItems().add(create);
		slider.setMax(10);
		slider.setMin(1);
	}
	
	@FXML public void select_item() {
		int id = hitmarks.getSelectionModel().getSelectedIndex();
		if (id == -1 || id > HitmarkLoader.getDefinitions().length)
			return;
		def = HitmarkLoader.getDefinitions()[id];
		if (def != null) {
			view_item();
		} else {
			System.err.println("Definition is null for id " + id);
		}
	}
	 
	@FXML public void slider() {
		renderSprites();
	}
	
	@FXML public void view_item() {
		tab.setText("Hitmark Editor - ID: " + def.id);	
		java.awt.Color color = new java.awt.Color(def.fontColor);
		fontColor.setValue(Color.rgb(color.getRed(), color.getGreen(), color.getBlue()));		
		font.setText("" + def.fontId);
		style.setText("" + def.attackStyle);
		left.setText("" + def.leftSprite);
		middle.setText("" + def.middleSprite);
		right.setText("" + def.rightSprite);
		hspeed.setText("" + def.horizontalSpeed);
		vspeed.setText("" + def.verticalSpeed);
		fadeSpeed.setText("" + def.fadeSpeed);
		anInt3861.setText("" + def.anInt3861);
		anInt3863.setText("" + def.anInt3863);
		markText.setText("" + def.text);
		vTextOffset.setText("" + def.verticalTextOffset);
		renderSprites();
	}
	
	/**
	 * Draws an image of the hitmark on the canvas.
	 */
	private void renderSprites() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());	
		List<SpriteFrame> frames;
		if (def.leftSprite > -1) { //4x24 pixels
			SpriteLoader.getSprites()[def.leftSprite].decodeImage(Selection.cache, 
					Indices.SPRITES.getIndex(), def.leftSprite, 0);
			frames = SpriteLoader.getSprites()[def.leftSprite].toSpriteFrames();
			if (frames.get(0) == null)
				return;
			gc.drawImage(frames.get(0).getImage(), 22, 5);
		}
		if (def.middleSprite > -1) {
			SpriteLoader.getSprites()[def.middleSprite].decodeImage(Selection.cache, 
					Indices.SPRITES.getIndex(), def.middleSprite, 0);
			frames = SpriteLoader.getSprites()[def.middleSprite].toSpriteFrames();
			if (frames.get(0) == null)
				return;
			for (int index = 0; index < slider.getValue(); index++) {
				gc.drawImage(frames.get(0).getImage(), 26 + (int) (4 * index), 5);
			}
		}
		if (def.rightSprite > -1) {
			SpriteLoader.getSprites()[def.rightSprite].decodeImage(Selection.cache, 
					Indices.SPRITES.getIndex(), def.rightSprite, 0);
			frames = SpriteLoader.getSprites()[def.rightSprite].toSpriteFrames();
			if (frames.get(0) == null)
				return;	
			gc.drawImage(frames.get(0).getImage(), (int) (26 + (slider.getValue() * 4)), 5);
		}
		if (def.attackStyle > -1) { //17x17 pixels
			SpriteLoader.getSprites()[def.attackStyle].decodeImage(Selection.cache, 
					Indices.SPRITES.getIndex(), def.attackStyle, 0);
			frames = SpriteLoader.getSprites()[def.attackStyle].toSpriteFrames();
			if (frames.get(0) == null)
				return;		
			gc.drawImage(frames.get(0).getImage(), 5, 5);
		}	
	}
	
	public void save() {
		try {
			java.awt.Color color = new java.awt.Color((float) fontColor.getValue().getRed(), 
					  (float) fontColor.getValue().getGreen(), 
					  (float) fontColor.getValue().getBlue(), 
					  (float) fontColor.getValue().getOpacity());
			def.fontColor = color.getRGB();
			def.fontId = Integer.parseInt(font.getText());
			def.attackStyle = Integer.parseInt(style.getText());
			def.leftSprite = Integer.parseInt(left.getText());
			def.middleSprite = Integer.parseInt(middle.getText());
			def.rightSprite = Integer.parseInt(right.getText());
			def.horizontalSpeed = Integer.parseInt(hspeed.getText());
			def.verticalSpeed = Integer.parseInt(vspeed.getText());
			def.fadeSpeed = Integer.parseInt(fadeSpeed.getText());
			def.anInt3861 = Integer.parseInt(anInt3861.getText());
			def.anInt3863 = Integer.parseInt(anInt3863.getText());
			def.text = markText.getText();
			def.verticalTextOffset = Integer.parseInt(vTextOffset.getText());
			if (def.save(cache)) {
				Dialogues.alert(AlertType.INFORMATION, "Success", null, "Your changes were saved successfully.");
				renderSprites();
			}
		} catch (Throwable e) {
			InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to save, please your check spelling.", "", StringUtilities.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
}
