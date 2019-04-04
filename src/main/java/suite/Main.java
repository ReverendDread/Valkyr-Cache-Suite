package suite;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import suite.controller.Selection;

public class Main extends Application {

    private static Stage primary;
    private static BorderPane root;
    private static Selection selection;
	
	@Override
	public void start(Stage primary) {		
		this.primary = primary;
		this.primary.setTitle(Constants.WINDOW_NAME);		
		this.primary.getIcons().add(new Image(Constants.FAVICON));	
		initRootLayout();
		initController();
	}
	
    public void initController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/Selection.fxml"));
            VBox vbox = (VBox) loader.load();
            Selection selection = loader.getController();
            selection.inititalize(primary);
            this.selection = selection;
            primary.setResizable(false);
            root.setCenter(vbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/RootLayout.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root);
            primary.setScene(scene);
            primary.show();         
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Selection getSelection() {
    	return selection;
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public static Stage getPrimaryStage() {
        return primary;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
