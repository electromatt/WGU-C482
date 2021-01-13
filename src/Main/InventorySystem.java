package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Matthew Druckhammer
 */
public class InventorySystem extends Application {

    /**
     * Starts the JavaFX application.
     * @param primaryStage The default JavaFX stage.
     * @throws Exception Throws exception. TODO: replace with better description.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /**
     * The main method.
     * @param args Arguments from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
