package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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

public class ModifyPartController implements Initializable {

    private final Inventory inventory;
    private final int selectedIndex;

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
    private TextField partSource;

    @FXML
    private TextField max;

    @FXML
    private Label partSourceLabel;

    @FXML
    private Button savePart;

    @FXML
    private Button cancel;

    /**
     * Modify Part Controller Constructor. Takes in an inventory object and the index of the selected part.
     * @param inventory The inventory object.
     * @param index The index of the selected part from the main screen.
     */
    public ModifyPartController(Inventory inventory, int index) {
        this.inventory = inventory;
        this.selectedIndex = index;
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the product at the selected index.
        Part part = inventory.getAllParts().get(selectedIndex);

        // Populate data from the part.
        id.setText(String.valueOf(part.getId()));
        name.setText(part.getName());
        price.setText(String.valueOf(part.getPrice()));
        stock.setText(String.valueOf(part.getStock()));
        min.setText(String.valueOf(part.getMin()));
        max.setText(String.valueOf(part.getMax()));

        if(part instanceof InHouse){
            inHouse.setSelected(true);
            partSource.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else if(part instanceof Outsourced){
            outsourced.setSelected(true);
            partSource.setText(((Outsourced) part).getCompanyName());
        }
    }

    @FXML
    void savePart(MouseEvent event) throws IOException {
        try {
            // Set constructor variables from input fields.
            int partID = Integer.parseInt(id.getText());
            String partName = name.getText();
            double partPrice = Double.parseDouble(price.getText());
            int partStock = Integer.parseInt(stock.getText());
            int partMin = Integer.parseInt(min.getText());
            int partMax = Integer.parseInt(max.getText());

            // Make sure the min inventory is less than the max.
            if(partMin <= partMax) {
                // Make sure the part stock is between the min and the max inventory.
                if ((partMin <= partStock) && (partStock <= partMax)) {
                    // Update the InHouse part if the In-House Radio Button is selected.
                    if (inHouse.isSelected()) {
                        int partMachineID = Integer.parseInt(partSource.getText());
                        InHouse newPart = new InHouse(partID, partName, partPrice, partStock, partMin, partMax, partMachineID);
                        inventory.updatePart(selectedIndex, newPart);
                    // Update the Outsourced part if the Outsourced Radio Button is selected.
                    } else if (outsourced.isSelected()) {
                        String partCompanyName = partSource.getText();
                        Outsourced newPart = new Outsourced(partID, partName, partPrice, partStock, partMin, partMax, partCompanyName);
                        inventory.updatePart(selectedIndex, newPart);
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
    void selectInHouse () {
        if(inHouse.isSelected()){
            partSourceLabel.setText("Machine ID");
        }
    }

    /**
     * The selectOutsourced method updates the part source label when the Outsourced Radio Button is selected.
     */
    @FXML
    void selectOutsourced () {
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
