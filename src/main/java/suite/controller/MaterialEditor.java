package suite.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import misc.CustomTab;
import store.codec.image.MaterialLoader;
import store.codec.image.MaterialRaw;
import suite.Constants;
import suite.dialogue.Dialogues;

/**
 * 
 * @author ReverendDread
 * Jul 10, 2018
 */
public class MaterialEditor {

	@FXML private ListView<MaterialRaw> mat_list;
	@FXML private TextField search_bar;
	private MaterialRaw material;
	
	@FXML private TextField aByte534, aByte533, effectId, effectParam1, color, speedU, speedV, aByte537, anInt519, effectParam2, blending;
	@FXML private CheckBox isDetailMaterial, aBoolean518, aBoolean538, aBoolean527, aBoolean544, aBoolean540, aBoolean541, aBoolean543;
	
	private CustomTab tab;
	private MaterialLoader mat_loader;
	
	@FXML public void select() {
		material = mat_list.getSelectionModel().getSelectedItem();
		if (material != null)
			view();
	}
	
	private void view() {
	
		tab.setText("Materials - " + material.id);
		
		aByte534.setText("" + material.shadow_factor);
		aByte533.setText("" + material.brightness);
		effectId.setText("" + material.effectId);
		effectParam1.setText("" + material.effectParam1);
		effectParam2.setText("" + material.effectParam2);
		color.setText("" + material.color);
		speedU.setText("" + material.speedHorizontal);
		speedV.setText("" + material.speedVertical);
		aByte537.setText("" + material.aByte537);
		anInt519.setText("" + material.anInt519);
		blending.setText("" + material.blendType);
		
		isDetailMaterial.setSelected(material.hdr);
		aBoolean518.setSelected(material.aBoolean518); //nothing noticeable
		aBoolean527.setSelected(material.aBoolean527); //nothing noticeable
		aBoolean538.setSelected(material.transparent);
		aBoolean540.setSelected(material.aBoolean540); //nothing noticeable
		aBoolean541.setSelected(material.repeatTexture);
		aBoolean543.setSelected(material.aBoolean543); //nothing noticeable
		aBoolean544.setSelected(material.aBoolean544); //nothing noticeable
		
	}

	public void startup(CustomTab tab) {
		this.tab = tab;
		mat_loader = new MaterialLoader(Selection.cache);
		mat_list.getItems().addAll(mat_loader.getMaterials());
		search_bar.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if (newValue.isEmpty())
					return;			
				int id = Integer.parseInt(newValue);
				material = mat_loader.getMaterials().get(id);
				if (Constants.settings.focusOnSearch) {
					mat_list.getSelectionModel().select(id);
					mat_list.scrollTo(id);
				}
				view();
			}
			
		});
		
		ContextMenu menu = new ContextMenu();
		
		MenuItem add = new MenuItem("Add");
		add.setOnAction(event -> {
			mat_loader.addMaterial(new MaterialRaw(mat_loader.getMaterials().size()));
			mat_loader.save();
			startup(tab);
		});
		menu.getItems().add(add);
		
		mat_list.setContextMenu(menu);
		
	}
	
	@FXML public void save() {
		
		material.hdr = isDetailMaterial.isSelected();	
		material.aBoolean518 = aBoolean518.isSelected();
		material.transparent = aBoolean538.isSelected();
		material.aBoolean527 = aBoolean527.isSelected();
		material.aBoolean544 = aBoolean544.isSelected();
		material.aBoolean540 = aBoolean540.isSelected();
		material.repeatTexture = aBoolean541.isSelected();
		material.aBoolean543 = aBoolean543.isSelected();
		
		material.shadow_factor = Integer.parseInt(aByte534.getText());
		material.brightness = Integer.parseInt(aByte533.getText());
		material.aByte537 = Integer.parseInt(aByte537.getText());
		
		material.anInt519 = Integer.parseInt(anInt519.getText());
		
		material.effectId = Integer.parseInt(effectId.getText());
		material.effectParam1 = Integer.parseInt(effectParam1.getText());
		material.effectParam2 = Integer.parseInt(effectParam2.getText());
		
		material.color = Integer.parseInt(color.getText());
		material.speedHorizontal = Integer.parseInt(speedU.getText());
		material.speedVertical = Integer.parseInt(speedV.getText());
		material.blendType = Integer.parseInt(blending.getText());
		
		if (mat_loader.save())
			Dialogues.alert(AlertType.INFORMATION, "Success", null, "Your changes were saved successfully.");
	}
	
}
