package suite.controller;

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
import misc.CustomTab;
import store.FileStore;
import store.codec.BASDefinition;
import store.loader.BASLoader;
import store.loader.LoaderManager;
import suite.dialogue.Dialogues;
import suite.dialogue.InformationDialogue;
import utility.StringUtilities;

/**
 * 
 * @author ReverendDread
 * Jul 10, 2018
 */
public class RenderAnimations {

	private FileStore cache = Selection.cache;
	private BASDefinition def;
	private CustomTab tab;
	
	private ObservableList<BASDefinition> definitions;
	
	@FXML private ListView<BASDefinition> animation_list;	
	
	@FXML private CheckBox aBoolean3756;
	
	@FXML private TextField anIntArrayArray3710, idle, anIntArray3712, anIntArray3713, anInt3714,
	anInt3715, anInt3716, walking, strafe_right, anInt3720, running, anInt3722, 
	strafe_left, anInt3724, anInt3726, anInt3727, anInt3728, anInt3729, anInt3730, anInt3731, 
	anIntArray3732, anInt3733, anInt3734, anInt3735, anInt3736, anInt3737, anInt3738, anInt3739, anInt3740, 
	backwards, anIntArray3742, anInt3743, anInt3744, anInt3745, anInt3746, anInt3747, anInt3748, anInt3749, 
	anInt3750, anInt3751, anInt3752, anIntArrayArray3753, anInt3754, anInt3755, search_bar;
	
	@FXML
	public void select_animation() {
		def = animation_list.getSelectionModel().getSelectedItem();
		if (def != null)
			view_item();
	}
	
	@FXML
	public void save() {
		try {
			
			this.anIntArrayArray3710.setText("");	//TODO
			
			def.idle = Integer.parseInt(this.idle.getText());
			
			this.anIntArray3712.getText();	//TODO
			this.anIntArray3712.getText();	//TODO
			this.anIntArray3713.getText();	//TODO
			
			def.anInt3714 = Integer.parseInt(this.anInt3714.getText());
			def.anInt3715 = Integer.parseInt(this.anInt3715.getText());
			def.anInt3716 = Integer.parseInt(this.anInt3716.getText());
			def.walking = Integer.parseInt(this.walking.getText());
			def.strafe_right = Integer.parseInt(this.strafe_right.getText());
			def.anInt3720 = Integer.parseInt(this.anInt3720.getText());
			def.running = Integer.parseInt(this.running.getText());
			def.anInt3722 = Integer.parseInt(this.anInt3722.getText());
			def.strafe_left = Integer.parseInt(this.strafe_left.getText());		
			def.anInt3724 = Integer.parseInt(this.anInt3724.getText());
			def.anInt3726 = Integer.parseInt(this.anInt3726.getText());
			def.anInt3727 = Integer.parseInt(this.anInt3727.getText());
			def.anInt3728 = Integer.parseInt(this.anInt3728.getText());
			def.anInt3729 = Integer.parseInt(this.anInt3729.getText());
			def.anInt3730 = Integer.parseInt(this.anInt3730.getText());
			def.anInt3731 = Integer.parseInt(this.anInt3731.getText());
			def.anInt3733 = Integer.parseInt(this.anInt3733.getText());
			
			this.anIntArray3732.getText();	//TODO
			
			def.anInt3734 = Integer.parseInt(this.anInt3734.getText());
			def.anInt3735 = Integer.parseInt(this.anInt3735.getText());
			def.anInt3736 = Integer.parseInt(this.anInt3736.getText());
			def.anInt3737 = Integer.parseInt(this.anInt3737.getText());
			def.anInt3738 = Integer.parseInt(this.anInt3738.getText());
			def.anInt3739 = Integer.parseInt(this.anInt3739.getText());
			def.anInt3740 = Integer.parseInt(this.anInt3740.getText());
			def.backwards = Integer.parseInt(this.backwards.getText());
			
			this.anIntArray3742.getText();	//TODO
			
			def.anInt3743 = Integer.parseInt(this.anInt3743.getText());
			def.anInt3744 = Integer.parseInt(this.anInt3744.getText());
			def.anInt3745 = Integer.parseInt(this.anInt3745.getText());
			def.anInt3746 = Integer.parseInt(this.anInt3746.getText());
			def.anInt3747 = Integer.parseInt(this.anInt3747.getText());
			def.anInt3748 = Integer.parseInt(this.anInt3748.getText());
			def.anInt3749 = Integer.parseInt(this.anInt3749.getText());
			def.anInt3750 = Integer.parseInt(this.anInt3750.getText());
			def.anInt3751 = Integer.parseInt(this.anInt3751.getText());
			def.anInt3752 = Integer.parseInt(this.anInt3752.getText());
			
			this.anIntArrayArray3753.getText(); //TODO
			
			def.anInt3754 = Integer.parseInt(this.anInt3754.getText());
			def.anInt3755 = Integer.parseInt(this.anInt3755.getText());

			def.aBoolean3756 = this.aBoolean3756.isSelected();
			
			if (def.save(cache))
				Dialogues.alert(AlertType.INFORMATION, "Success", null, "Your changes were saved successfully.");
			
			startup(tab, true, def.id);
			
		} catch (Exception e) {
			InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to save, please your check spelling.", "", StringUtilities.getStackTrace(e));
		}	
	}
	
	private void view_item() {
		tab.setText("Render Editor - ID: " + def.id);
		this.anIntArrayArray3710.setText("");
		this.idle.setText("" + def.idle);
		this.anIntArray3712.setText("");
		this.anIntArray3712.setText("");
		this.anIntArray3713.setText("");
		this.anInt3714.setText("" + def.anInt3714);
		this.anInt3715.setText("" + def.anInt3715);
		this.anInt3716.setText("" + def.anInt3716);
		this.walking.setText("" + def.walking);
		this.strafe_right.setText("" + def.strafe_right);
		this.anInt3720.setText("" + def.anInt3720);
		this.running.setText("" + def.running);
		this.anInt3722.setText("" + def.anInt3722);
		this.strafe_left.setText("" + def.strafe_left);
		this.anInt3724.setText("" + def.anInt3724);
		this.anInt3726.setText("" + def.anInt3726);
		this.anInt3727.setText("" + def.anInt3727);
		this.anInt3728.setText("" + def.anInt3728);
		this.anInt3729.setText("" + def.anInt3729);
		this.anInt3730.setText("" + def.anInt3731);
		this.anInt3731.setText("" + def.anInt3731);
		this.anIntArray3732.setText("");
		this.anInt3733.setText("" + def.anInt3733);
		this.anInt3734.setText("" + def.anInt3735);
		this.anInt3735.setText("" + def.anInt3735);
		this.anInt3736.setText("" + def.anInt3736);
		this.anInt3737.setText("" + def.anInt3737);
		this.anInt3738.setText("" + def.anInt3738);
		this.anInt3739.setText("" + def.anInt3739);
		this.anInt3740.setText("" + def.anInt3740);
		this.backwards.setText("" + def.backwards);
		this.anIntArray3742.setText("");
		this.anInt3743.setText("" + def.anInt3743);
		this.anInt3744.setText("" + def.anInt3744);
		this.anInt3745.setText("" + def.anInt3745);
		this.anInt3746.setText("" + def.anInt3746);
		this.anInt3747.setText("" + def.anInt3747);
		this.anInt3748.setText("" + def.anInt3748);
		this.anInt3749.setText("" + def.anInt3749);
		this.anInt3750.setText("" + def.anInt3750);
		this.anInt3751.setText("" + def.anInt3751);
		this.anInt3752.setText("" + def.anInt3752);
		this.anIntArrayArray3753.setText("");
		this.anInt3754.setText("" + def.anInt3754);
		this.anInt3755.setText("" + def.anInt3755);
		this.aBoolean3756.setSelected(def.aBoolean3756);
	}
	
	/**
	 * Loads render anim list.
	 * @param stage TODO
	 */
	public void startup(CustomTab tab, boolean refresh, int lastid) {
		this.tab = tab;
		definitions = FXCollections.observableArrayList();
		definitions.setAll(BASLoader.getDefinitions());
		animation_list.getItems().setAll(definitions);
		def = BASLoader.getDefinitions()[lastid];
		view_item();	
		search_bar.textProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals(""))
					return;
				int id = Integer.parseInt(newValue);
				def = BASLoader.getDefinitions()[id];
				view_item();
			}
			
		});
		
		ContextMenu context_menu = new ContextMenu();
		animation_list.setContextMenu(context_menu);
		
		MenuItem create = new MenuItem("Create");
		create.setOnAction((ActionEvent event) -> {	
			BASDefinition blank_def = new BASDefinition(-1);
			try {
				blank_def.id = BASLoader.getDefinitions().length;
				if (blank_def.save(cache)) {
					LoaderManager.getLoader(LoaderManager.BAS).reload();
					startup(tab, true, blank_def.id);
				}
			} catch (Throwable e) {
				InformationDialogue.create("Error", "An error has occured.", AlertType.ERROR, "Unable to create new render animation.", "", StringUtilities.getStackTrace(e));
				e.printStackTrace();
			}
		});
		context_menu.getItems().add(create);
	}
	
}
