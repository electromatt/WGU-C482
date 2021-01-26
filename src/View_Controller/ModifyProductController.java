package View_Controller;

import Model.*;
import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    //region Variables
    private Inventory inventory;
    private Stage stage;
    private Parent scene;
    private int selectedIndex;

    private ObservableList<Part> partList = FXCollections.observableArrayList();
    private ObservableList<Part> pickedParts = FXCollections.observableArrayList();
    //endregion

    //region FXML variables
    @FXML
    private TableView<Part> availableParts;

    @FXML
    private TableView<Part> associatedParts;

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
    private TextField max;

    @FXML
    private Button saveNewPart;

    @FXML
    private Button cancel;

    @FXML
    private AnchorPane partPane;

    @FXML
    private TextField partSearchField;

    @FXML
    private Button partAddButton;

    @FXML
    private AnchorPane partPane1;

    @FXML
    private Button partAddButton1;
    //endregion

    /**
     * Constructor
     * @param inventory
     */
    public ModifyProductController(Inventory inventory, int index) {
        this.inventory = inventory;
        this.selectedIndex = index;
    }

    /**
     * The initialize method populates the Part table and generates a new ID for the product.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the product at the selected index.
        Product product = inventory.getAllProducts().get(selectedIndex);

        // Populate data from the product.
        id.setText(String.valueOf(product.getId()));
        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        stock.setText(String.valueOf(product.getStock()));
        min.setText(String.valueOf(product.getMin()));
        max.setText(String.valueOf(product.getMax()));

        // Get all available parts.
        partList.setAll(inventory.getAllParts());
        availableParts.setItems(partList);
        availableParts.refresh();

        // Get all associated parts.
        pickedParts.setAll(product.getAllAssociatedParts());
        associatedParts.setItems(pickedParts);
        associatedParts.refresh();
    }

    /**
     * The addPart method adds the selected Part to the list of associated Parts. If the Part is already added,
     * an error alert will appear.
     * @param event
     */
    @FXML
    void addPart(MouseEvent event) {
        if(!pickedParts.contains(availableParts.getSelectionModel().getSelectedItem())){
            pickedParts.add(availableParts.getSelectionModel().getSelectedItem());
            associatedParts.setItems(pickedParts);
            associatedParts.refresh();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding Part");
            alert.setContentText("Cannot add selected part to the product, the part has already been added.");
            alert.showAndWait();
        }
    }

    /**
     * The productCancel method returns to the main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void productCancel(MouseEvent event) throws IOException {
        returnToMainScreen(event);
    }

    /**
     * The removePart method removes the selected Part from the list of associated Parts.
     * @param event
     */
    @FXML
    void removePart(MouseEvent event) {
        Part selected = associatedParts.getSelectionModel().getSelectedItem();

        if(selected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No part is selected!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the selected Part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                pickedParts.remove(selected);
                associatedParts.refresh();
            }
        }
    }

    /**
     * The saveProduct method validates all the input fields and creates the Product. All fields are required and must
     * match the specified type.
     * Name = String
     * Price = Double
     * Stock = Integer
     * Min = Integer
     * Max = Integer
     * @param event
     * @throws IOException
     */
    @FXML
    void saveProduct(MouseEvent event) throws IOException {
        try {
            int productID = Integer.valueOf(id.getText());
            String productName = name.getText();
            double productPrice = Double.valueOf(price.getText());
            int productStock = Integer.valueOf(stock.getText());
            int productMin = Integer.valueOf(min.getText());
            int productMax = Integer.valueOf(max.getText());

            if(productMin <= productMax) {
                if ((productMin <= productStock) && (productStock <= productMax)) {
                    // int id, String name, double price, int stock, int min, int max
                    Product product = new Product(productID, productName, productPrice, productStock, productMin, productMax);
                    for(Part p : pickedParts){
                        product.addAssociatedPart(p);
                    }
                    inventory.updateProduct(selectedIndex, product);
                    returnToMainScreen(event);
                } else {
                    throw new IllegalStateException("Item inventory should be within the Min and Max values.");
                }
            } else {
                throw new IllegalStateException("Min value should be less than Max.");
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error adding Product");
            alert.setContentText("Please enter valid inputs for all fields.");
            alert.showAndWait();
        } catch (IllegalStateException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error adding Product");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * The searchPart method reads the search text box, locates all matching items (by name or by ID), and returns an
     * ObservableList of type Part.
     * @param event
     */
    @FXML
    void searchPart(KeyEvent event) {
        String partToSearch = partSearchField.getText();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        // If the search field is empty, show all the Parts.
        if(partToSearch.isEmpty()) {
            availableParts.setItems(partList);
        }
        // If the search string is an Integer, lookup all Parts by partId and partName.
        else if(isInteger(partToSearch)){
            filteredParts.addAll(inventory.lookupPart(partToSearch));
            // Skip adding the Part if it already exists from the previous add.
            if(!filteredParts.contains(inventory.lookupPart(Integer.parseInt(partToSearch)))){
                filteredParts.add(inventory.lookupPart(Integer.parseInt(partToSearch)));
            }
            availableParts.setItems(filteredParts);
        }
        // Otherwise, lookup all Parts containing the search string.
        else{
            filteredParts.addAll(inventory.lookupPart(partToSearch));
            availableParts.setItems(filteredParts);
        }

        availableParts.refresh();
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
