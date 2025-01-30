package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Stage stage = new Stage();
		    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainpage.fxml"));
		    
		    Scene scene = new Scene(fxmlLoader.load(), 939,684);
		    stage.setTitle("Visualisierung");
		    stage.setScene(scene);
		    stage.setResizable(false); 
		    stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
