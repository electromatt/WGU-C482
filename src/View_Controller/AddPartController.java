package View_Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    private final Inventory inventory;
    private int newId;

    @FXML
    private TextField partSource;

    @FXML
    private Label partSourceLabel;

    @FXML
    private RadioButton inHouse;

    @FXML
    private ToggleGroup partType;

    @FXML
    private RadioButton outsourced;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField stock;

    @FXML
    private TextField price;

    @FXML
    private TextField min;

    @FXML
    private TextField machineId;

    @FXML
    private TextField max;

    @FXML
    private Button saveNewPart;

    @FXML
    private Button cancel;

    /**
     * Add Part Controller Constructor. Takes in an inventory object.
     * @param inventory The inventory object.
     */
    public AddPartController(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * the initialize method generates a new ID for the part.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize newID as a negative number
        // Loop through all parts and set newID equal to the highest part ID.
        // Finally if newID is greater than the initialization value (a part exists), increment newID by 1
        newId = -1;
        for(Part p : inventory.getAllParts()){
            if(p.getId() > newId) {
                newId = p.getId();
            }
        }
        if(newId > 0){
            newId++;
            id.setText(String.valueOf(newId));
        }
    }

    /**
     * The savePart method validates all the input fields and creates the part. All fields are required and must
     * match the specified type.
     * Name = String
     * Price = Double
     * Stock = Integer
     * Min = Integer
     * Max = Integer
     * Machine ID = Integer
     * Company Name = String
     * @param event
     * @throws IOException
     */
    @FXML
    void savePart(MouseEvent event) throws IOException {
        try {
            // Set constructor variables from input fields.
            int partID = newId;
            String partName = name.getText();
            double partPrice = Double.parseDouble(price.getText());
            int partStock = Integer.parseInt(stock.getText());
            int partMin = Integer.parseInt(min.getText());
            int partMax = Integer.parseInt(max.getText());

            // Make sure the min inventory is less than the max.
            if(partMin <= partMax) {
                // Make sure the part stock is between the min and the max inventory.
                if ((partMin <= partStock) && (partStock <= partMax)) {
                    // Create new InHouse part if the In-House Radio Button is selected.
                    if (inHouse.isSelected()) {
                        int partMachineID = Integer.parseInt(partSource.getText());
                        InHouse newPart = new InHouse(partID, partName, partPrice, partStock, partMin, partMax, partMachineID);
                        inventory.addPart(newPart);
                    // Create new Outsourced part if the Outsourced Radio Button is selected.
                    } else if (outsourced.isSelected()) {
                        String partCompanyName = partSource.getText();
                        Outsourced newPart = new Outsourced(partID, partName, partPrice, partStock, partMin, partMax, partCompanyName);
                        inventory.addPart(newPart);
                    }
                    // Return back home.
                    returnToMainScreen(event);
                } else {
                    throw new IllegalStateException("Item inventory should be within the Min and Max values.");
                }
            } else {
                throw new IllegalStateException("Min value should be less than Max.");
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding part");
            alert.setContentText("Please enter valid inputs for all fields.");
            alert.showAndWait();
        } catch (IllegalStateException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error adding part");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * The partCancel method returns to the main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void partCancel (MouseEvent event) throws IOException {
        returnToMainScreen(event);
    }

    /**
     * The selectInHouse method updates the part source label when the InHouse Radio Button is selected.
     */
    @FXML
    void selectInHouse(){
        if(inHouse.isSelected()){
            partSourceLabel.setText("Machine ID");
        }
    }

    /**
     * The selectOutsourced method updates the part source label when the Outsourced Radio Button is selected.
     */
    @FXML
    void selectOutsourced(){
        if(outsourced.isSelected()){
            partSourceLabel.setText("Company Name");
        }
    }

    /**
     * The returnToMainScreen method sets the scene and controller back to the Main Screen.
     * @param event
     * @throws IOException
     */
    private void returnToMainScreen(MouseEvent event) throws IOException {
        // Setup loader and controller for the main screen. Pass inventory back to main screen.
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View_Controller/MainScreen.fxml"));
        MainScreenController controller = new MainScreenController(inventory);
        fxmlLoader.setController(controller);
        fxmlLoader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
