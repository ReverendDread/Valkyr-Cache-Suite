package suite.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import misc.CustomTab;
import store.FileStore;
import store.codec.ParticleDefinition;
import store.loader.LoaderManager;
import store.loader.ParticleLoader;
import suite.Constants;
import suite.dialogue.Dialogues;
import suite.dialogue.InformationDialogue;
import utility.StringUtilities;

public class ParticleEditor {

	private FileStore cache = Selection.cache;
	private CustomTab tab;	
	private ObservableList<ParticleDefinition> definitions;
	private ParticleDefinition def;
	
	@FXML private ListView<ParticleDefinition> particles;
	@FXML private TextField minimum_angle_h, maximum_angle_h, minimum_angle_v, maximum_angle_v, minimum_speed, maximum_speed, 
	minimum_lifespan, maximum_lifespan, minimum_spawn_rate, maximum_spawn_rate, minimum_size, maximum_size, lifetime, size_change, 
	change_speed, end_speed, fading_color, fading_alpha, anInt1873, anInt1855, anInt1819, anInt1847, anInt1850, anInt1851, anInt1852, anInt1840, search_bar;
	@FXML private CheckBox active_first, periodic, uniform_color_variance, aBoolean1877, aBoolean1863, aBoolean1818, aBoolean1862, aBoolean1874;
	@FXML private ColorPicker minimum_start_color, maximum_start_color;
	
	public void initialize(CustomTab tab, boolean refresh) {
		this.tab = tab;
		definitions = FXCollections.observableArrayList();
		definitions.setAll(ParticleLoader.getDefinitions());
		particles.getItems().setAll(definitions);
		search_bar.textProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals(""))
					return;
				int id = Integer.parseInt(newValue);
				def = ParticleLoader.getDefinitions()[id];
				if (Constants.settings.focusOnSearch) {
					particles.getSelectionModel().select(id);
					particles.scrollTo(id);
				}
				view_item();
			}
			
		});
		ContextMenu context_menu = new ContextMenu();
		particles.setContextMenu(context_menu);
		
		MenuItem create = new MenuItem("Create");
		create.setOnAction((ActionEvent event) -> {	
			ParticleDefinition blank_def = new ParticleDefinition(-1);
			try {
				blank_def.id = ParticleLoader.getDefinitions().length;
				blank_def.save(cache);
				LoaderManager.getLoader(LoaderManager.PARTICLE).reload();
				def = blank_def;
				initialize(tab, true);
				if (Constants.settings.focusOnSearch) {
					particles.getSelectionModel().select(blank_def.id);
					particles.scrollTo(blank_def.id);
				}
			} catch (NumberFormatException e) {
				InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to create new object.", "", 
						"Please notify ReverendDread about this error, and a way to reproduce it.");
			}
		});
		context_menu.getItems().add(create);
	}
	
	@FXML public void select_item() {
		int id = particles.getSelectionModel().getSelectedIndex();
		def = ParticleLoader.getDefinitions()[id];
		if (def != null)
		view_item();
	}
	
	@FXML public void view_item() {
		tab.setText("Particle Editor - ID: " + def.id);
		minimum_angle_h.setText("" + def.minimumAngleH);
		maximum_angle_h.setText("" + def.maximumAngleH);
		minimum_angle_v.setText("" + def.minimumAngleV);
		maximum_angle_v.setText("" + def.maximumAngleV);
		minimum_speed.setText("" + def.minimumSpeed);
		maximum_speed.setText("" + def.maximumSpeed);
		java.awt.Color min_color = new java.awt.Color(def.minimumStartColour);
		minimum_start_color.setValue(Color.rgb(min_color.getRed(), min_color.getGreen(), min_color.getBlue()));
		java.awt.Color max_color = new java.awt.Color(def.maximumStartColour);
		maximum_start_color.setValue(Color.rgb(max_color.getRed(), max_color.getGreen(), max_color.getBlue()));
		minimum_lifespan.setText("" + def.minimumLifetime);
		maximum_lifespan.setText("" + def.maximumLifetime);
		minimum_spawn_rate.setText("" + def.minimumParticleRate);
		maximum_spawn_rate.setText("" + def.maximumParticleRate);
		minimum_size.setText("" + def.minimumSize);
		maximum_size.setText("" + def.maximumSize);
		lifetime.setText("" + def.lifetime);
		size_change.setText("" + def.sizeChange);
		change_speed.setText("" + def.changeSpeed);
		end_speed.setText("" + def.endSpeed);
		fading_color.setText("" + def.fadingColour);
		fading_alpha.setText("" + def.fadingAlpha);
		anInt1873.setText("" + def.anInt1873);
		anInt1855.setText("" + def.anInt1855);
		anInt1819.setText("" + def.anInt1819);
		anInt1847.setText("" + def.anInt1847);
		anInt1850.setText("" + def.anInt1850);
		anInt1851.setText("" + def.anInt1851);
		anInt1852.setText("" + def.anInt1852);
		anInt1840.setText("" + def.texture);
		active_first.setSelected(def.activeFirst);
		periodic.setSelected(def.periodic);
		uniform_color_variance.setSelected(def.uniformColourVariance);
		aBoolean1877.setSelected(def.aBoolean1877);
		aBoolean1863.setSelected(def.aBoolean1863);
		aBoolean1818.setSelected(def.aBoolean1818);
		aBoolean1862.setSelected(def.aBoolean1862);
		aBoolean1874.setSelected(def.aBoolean1874);
	}
	
	public void save() {
		try {
			def.minimumAngleH = Short.parseShort(minimum_angle_h.getText());
			def.maximumAngleH = Short.parseShort(maximum_angle_h.getText());
			def.minimumAngleV = Short.parseShort(minimum_angle_v.getText());
			def.maximumAngleV = Short.parseShort(maximum_angle_v.getText());
			java.awt.Color min_color = new java.awt.Color((float) minimum_start_color.getValue().getRed(), 
														  (float) minimum_start_color.getValue().getGreen(), 
														  (float) minimum_start_color.getValue().getBlue(), 
														  (float) minimum_start_color.getValue().getOpacity());
			def.minimumStartColour = min_color.getRGB();
			java.awt.Color max_color = new java.awt.Color((float) maximum_start_color.getValue().getRed(), 
														  (float) maximum_start_color.getValue().getGreen(), 
														  (float) maximum_start_color.getValue().getBlue(), 
														  (float) maximum_start_color.getValue().getOpacity());
			def.maximumStartColour = max_color.getRGB();
			def.minimumSpeed = Integer.parseInt(minimum_speed.getText());
			def.maximumSpeed = Integer.parseInt(maximum_speed.getText());
			def.minimumLifetime = Integer.parseInt(minimum_lifespan.getText());
			def.maximumLifetime = Integer.parseInt(maximum_lifespan.getText());
			def.minimumParticleRate = Integer.parseInt(minimum_spawn_rate.getText());
			def.maximumParticleRate = Integer.parseInt(maximum_spawn_rate.getText());
			def.minimumSize = Integer.parseInt(minimum_size.getText());
			def.maximumSize = Integer.parseInt(maximum_size.getText());
			def.lifetime = Integer.parseInt(lifetime.getText());
			def.sizeChange = Integer.parseInt(size_change.getText());
			def.changeSpeed = Integer.parseInt(change_speed.getText());
			def.endSpeed = Integer.parseInt(end_speed.getText());
			def.fadingColour = Integer.parseInt(fading_color.getText());
			def.fadingAlpha = Integer.parseInt(fading_alpha.getText());
			def.anInt1873 = Integer.parseInt(anInt1873.getText());
			def.anInt1855 = Integer.parseInt(anInt1855.getText());
			def.anInt1819 = Integer.parseInt(anInt1819.getText());
			def.anInt1847 = Integer.parseInt(anInt1847.getText());
			def.anInt1850 = Integer.parseInt(anInt1850.getText());
			def.anInt1851 = Integer.parseInt(anInt1851.getText());
			def.anInt1852 = Integer.parseInt(anInt1852.getText());
			def.texture = Integer.parseInt(anInt1840.getText());
			def.activeFirst = active_first.isSelected();
			def.periodic = periodic.isSelected();
			def.uniformColourVariance = uniform_color_variance.isSelected();
			def.aBoolean1877 = aBoolean1877.isSelected();
			def.aBoolean1863 = aBoolean1863.isSelected();
			def.aBoolean1818 = aBoolean1818.isSelected();
			def.aBoolean1862 = aBoolean1862.isSelected();
			def.aBoolean1874 = aBoolean1874.isSelected();
			if (def.save(cache))
				Dialogues.alert(AlertType.INFORMATION, "Success", null, "Your changes were saved successfully.");
		} catch (Throwable e) {
			InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to save, please your check spelling.", "", StringUtilities.getStackTrace(e));
			e.printStackTrace();
		}
		initialize(tab, true);
	}
	
}
