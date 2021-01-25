package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;

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
        Inventory inventory = new Inventory();
        addTestData(inventory);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inventory);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main method.
     * @param args Arguments from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void addTestData(Inventory inventory){
        Part a1 = new InHouse(1, "Part A1", 2.99, 10, 5, 100, 101);
        Part a2 = new InHouse(2, "Part A2", 3.99, 15, 5, 100, 102);
        Part a3 = new Outsourced(3, "Part A4", 4.99, 20, 1, 100, "Company A");
        Part a4 = new Outsourced(4, "Part A4", 3.99, 20, 1, 100, "Company B");

        inventory.addPart(a1);
        inventory.addPart(a2);
        inventory.addPart(a3);
        inventory.addPart(a4);

        Product p1 = new Product(1, "Product P1", 9.99,10,1,100);
        p1.addAssociatedPart(a1);
        p1.addAssociatedPart(a2);
        Product p2 = new Product(2, "Product P2", 15.99,10,1,100);
        p2.addAssociatedPart(a1);
        p2.addAssociatedPart(a2);
        inventory.addProduct(p1);
        inventory.addProduct(p2);

    }
}
