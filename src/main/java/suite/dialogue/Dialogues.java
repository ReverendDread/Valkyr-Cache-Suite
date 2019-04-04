package suite.dialogue;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import suite.Constants;

/**
 * 
 * @author ReverendDread
 * Jul 10, 2018
 */
public class Dialogues {

	public static final void alert(AlertType type, String title, String header, String content) {
		if (Constants.settings.notifications)
			return;
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Constants.FAVICON));
		alert.showAndWait();
	}
	
}
