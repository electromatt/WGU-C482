package View_Controller;

import Model.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.w3c.dom.ranges.RangeException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    private Inventory inventory;
    private Stage stage;
    private Parent scene;
    private int newId;

    public AddPartController(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * This generates the data tables using the test data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
     * The savePart method validates all the inputs and
     * @param event
     * @throws IOException
     */
    @FXML
    void savePart(MouseEvent event) throws IOException {
        try {
            int partID = newId;
            String partName = name.getText();
            double partPrice = Double.valueOf(price.getText());
            int partStock = Integer.valueOf(stock.getText());
            int partMin = Integer.valueOf(min.getText());
            int partMax = Integer.valueOf(max.getText());

            if(partMin <= partMax) {
                if ((partMin <= partStock) && (partStock <= partMax)) {
                    if (inHouse.isSelected()) {
                        int partMachineID = Integer.valueOf(partSource.getText());
                        InHouse newPart = new InHouse(partID, partName, partPrice, partStock, partMin, partMax, partMachineID);
                        inventory.addPart(newPart);
                    } else if (outsourced.isSelected()) {
                        String partCompanyName = partSource.getText();
                        Outsourced newPart = new Outsourced(partID, partName, partPrice, partStock, partMin, partMax, partCompanyName);
                        inventory.addPart(newPart);
                    }
                    returnToMainScreen(event);
                } else {
                    throw new IllegalStateException("Item inventory should be within the Min and Max values.");
                }
            } else {
                throw new IllegalStateException("Min value should be less than Max.");
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding Part");
            alert.setContentText("Please enter valid inputs for all fields.");
            alert.showAndWait();
        } catch (IllegalStateException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error adding Part");
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
     * @throws IOException
     */
    @FXML
    void selectInHouse () throws IOException {
        if(inHouse.isSelected()){
            partSourceLabel.setText("Machine ID");
        }
    }

    /**
     * The selectOutsourced method updates the part source label when the Outsourced Radio Button is selected.
     * @throws IOException
     */
    @FXML
    void selectOutsourced () throws IOException {
        if(outsourced.isSelected()){
            partSourceLabel.setText("Company Name");
        }
    }

    /**
     * The isInteger method checks to see if the string parameter is an Integer.
     * @param string The string to validate.
     * @return Returns a boolean value of whether the string parameter is an Integer or not.
     */
    private boolean isInteger(String string){
        try {
            Integer.parseInt(string);
            return true;
        }
        catch( NumberFormatException e) {
            return false;
        }
    }

    /**
     * The returnToMainScreen method sets the scene and controller back to the Main Screen.
     * @param event
     * @throws IOException
     */
    private void returnToMainScreen(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View_Controller/MainScreen.fxml"));
        MainScreenController controller = new MainScreenController(inventory);
        fxmlLoader.setController(controller);
        fxmlLoader.load();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
