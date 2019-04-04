package misc;

import javafx.scene.control.Tab;
import lombok.Getter;

/**
 * 
 * @author ReverendDread
 * Jul 10, 2018
 */
@Getter
public class CustomTab extends Tab {

	/**
	 * EditorType of this tab.
	 */
	public EditorType type;
	
	/**
	 * Controller object for this tab (Will be casted later on depending on the {@code EditorType}.
	 */
	public Object controller;
	
	/**
	 * Creates a new {@code CustomTab}.
	 * @param controller
	 * 				the fxml controller used for the tab.
	 * @param type
	 * 				the editor type of this tab.
	 */
	public CustomTab(Object controller, EditorType type) {
		this.controller = controller;
		this.type = type;
	}
	
}
