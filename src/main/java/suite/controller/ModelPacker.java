package suite.controller;

import java.io.File;
import java.nio.file.Files;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import store.FileStore;
import store.codec.util.Constants;
import suite.Main;
import suite.dialogue.Dialogues;
import suite.dialogue.InformationDialogue;
import utility.StringUtilities;

public class ModelPacker {

	private static FileStore cache = Selection.cache;
	
	private File directory;
	private Stage stage;
	
	@FXML public TextField path;
	@FXML public TextArea console;
	@FXML public ProgressBar progress_bar;
	@FXML public Text progress_text;
	
	public void initialize(Stage stage) {
		this.stage = stage;
		progress_bar.setVisible(false);
		progress_text.setVisible(false);
		console.textProperty().addListener(new ChangeListener<Object>() {
		    @Override
		    public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
		        console.setScrollTop(Double.MAX_VALUE);
		    }
		});
	}
	
	@FXML public void choose_directory() {
		DirectoryChooser dir_chooser = new DirectoryChooser();
		dir_chooser.setTitle("Locate Model Directory..");
		Stage stage = Main.getPrimaryStage();
		File default_dir = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator);
		dir_chooser.setInitialDirectory(default_dir);
		File directory = dir_chooser.showDialog(stage);
		if (directory == null) {
			Dialogues.alert(AlertType.ERROR, "Error", null, "Failed to locate model directory.");
			return;
		}
		this.directory = new File(directory.toString());
		path.setText(directory.toString());  
		this.stage.toFront();
	}
	
	@FXML public void pack() {
		try {
			
			if (directory == null) {
				Dialogues.alert(AlertType.ERROR, "Error", null, "Model directory was not chosen.");
				return;
			}
		
			stage.setTitle(suite.Constants.WINDOW_NAME + " - Model Packer - Packing in progress...");
			
			Service<String> service = new Service<String>() {

				@Override
				protected Task<String> createTask() {
					return new Task<String>() {
						
						@Override
						protected String call() throws Exception {
							try {
						        File[] files = directory.listFiles();
						        String packed_models = "";
						        console.appendText("Packing " + files.length + " models...\n");
						        int[] model_ids = new int[files.length];
						        int archive = cache.getIndexes()[7].getLastArchiveId() + 1;
						        for (int index = 0; index < files.length; index++) {
						        	if (!files[index].getName().endsWith(".dat"))
						        		continue;
						            byte[] data = Files.readAllBytes(files[index].toPath());
						            cache.getIndexes()[7].putFile(archive, 0, Constants.GZIP_COMPRESSION, data, null, false, false, -1, -1);
						            String original_id = files[index].getName().substring(0, files[index].getName().length() - ".dat".length());
						            model_ids[index] = archive;
						            packed_models = packed_models + "Packed model " + original_id + " as model " + model_ids[index] + ".\n";						          
						            archive++;
						        }   
						        console.appendText(packed_models);
						        cache.getIndexes()[7].rewriteTable();	
						        return null;
							} catch (Throwable e) {
								throw new RuntimeException(e);
							}
						}
					};
				}
				
			};
			
			progress_bar.setVisible(true);
			progress_bar.progressProperty().unbind();
			progress_bar.progressProperty().bind(service.progressProperty());
			
			progress_text.setVisible(true);
			progress_text.textProperty().unbind();
			progress_text.textProperty().bind(service.messageProperty()); 
			
			service.setOnSucceeded(e -> {

				PauseTransition pause = new PauseTransition(Duration.seconds(1));

				pause.setOnFinished(event -> {
					progress_bar.setVisible(false);
					progress_text.textProperty().unbind();
					progress_text.setText("");
					stage.setTitle(suite.Constants.WINDOW_NAME + " - Model Packer - Packing completed");
					
				});

				pause.play();
			});
			
			service.setOnFailed(e -> {

				PauseTransition pause = new PauseTransition(Duration.seconds(1));

				pause.setOnFinished(event -> {
					progress_bar.setVisible(false);
					progress_text.textProperty().unbind();
					progress_text.setText("");
					stage.setTitle(suite.Constants.WINDOW_NAME + " - Model Packer");
				});

				pause.play();

			});
			
			service.start();
			
		} catch (Throwable e) {
			InformationDialogue.create("Error", "An error has occured while packing models.", AlertType.ERROR, "", "", StringUtilities.getStackTrace(e));
		}
	}
	
}
