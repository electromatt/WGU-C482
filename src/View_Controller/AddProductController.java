package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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

public class AddProductController implements Initializable {

    private Inventory inventory;
    private Stage stage;
    private Parent scene;
    private int newId;

    @FXML
    private TableView<Part> availableParts;
    @FXML
    private TableView<Part> associatedParts;

    private ObservableList<Part> partList = FXCollections.observableArrayList();
    private ObservableList<Part> pickedParts = FXCollections.observableArrayList();

    public AddProductController(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generatePartsTable();
        newId = -1;
        for(Product p : inventory.getAllProducts()){
            if(p.getId() > newId) {
                newId = p.getId();
            }
        }
        if(newId > 0){
            newId++;
            id.setText(String.valueOf(newId));
        }
    }

    private void generatePartsTable(){
        partList.setAll(inventory.getAllParts());
        availableParts.setItems(partList);
        availableParts.refresh();
    }
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

    /**
     *
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

    @FXML
    void productCancel(MouseEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to cancel creating the product?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            returnToMainScreen(event);
        }
    }

    @FXML
    void removePart(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the selected Part?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            Part selected = associatedParts.getSelectionModel().getSelectedItem();
            pickedParts.remove(selected);
            associatedParts.refresh();
        }
    }

    @FXML
    void saveProduct(MouseEvent event) throws IOException {
        int productID = newId;
        String productName = name.getText();
        double productPrice = Double.valueOf(price.getText());
        int productStock = Integer.valueOf(stock.getText());
        int productMin = Integer.valueOf(min.getText());
        int productMax = Integer.valueOf(max.getText());

        // int id, String name, double price, int stock, int min, int max
        Product product = new Product(productID, productName, productPrice, productStock, productMin, productMax);
        for(Part p : pickedParts){
            product.addAssociatedPart(p);
        }
        inventory.addProduct(product);
        returnToMainScreen(event);
    }

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
