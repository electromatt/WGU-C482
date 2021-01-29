package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    final Inventory inventory;
    Stage stage;

    private final ObservableList<Part> partList = FXCollections.observableArrayList();
    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private HBox mainHBox;

    @FXML
    private AnchorPane partPane;

    @FXML
    private TextField partSearchField;

    @FXML
    private Button partSearchButton;

    @FXML
    private Button partAddButton;

    @FXML
    private Button partModifyButton;

    @FXML
    private Button partDeleteButton;

    @FXML
    private AnchorPane productPane;

    @FXML
    private TextField productSearchField;

    @FXML
    private Button productSearchButton;

    @FXML
    private Button productAddButton;

    @FXML
    private Button productModifyButton;

    @FXML
    private Button productDeleteButton;

    @FXML
    private Button exitAppButton;

    /**
     * Main Screen Controller Constructor. Takes in an inventory object.
     * @param inventory The inventory object.
     */
    public MainScreenController(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * This generates the data tables using the test data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generatePartsTable();
        generateProductsTable();
    }

    /**
     * the generatePartsTable method uses the Inventory object to populate the parts table with the parts in inventory.
     */
    private void generatePartsTable(){
        // Populate the parts table view by getting all the parts in inventory.
        partList.setAll(inventory.getAllParts());
        partTableView.setItems(partList);
        partTableView.refresh();
    }

    /**
     * the generateProductsTable method uses the Inventory object to populate the products table with the products in inventory.
     */
    private void generateProductsTable(){
        // Populate the products table view by getting all the products in inventory.
        productList.setAll(inventory.getAllProducts());
        productTableView.setItems(productList);
        productTableView.refresh();
    }

    /**
     * The addPart method passes the inventory to the Add part page where the user can create new parts.
     * @param event
     * @throws IOException
     */
    @FXML
    void addPart(MouseEvent event) throws IOException {
        // Setup loader and controller for the add part screen. Pass inventory to the controller.
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View_Controller/AddPartScreen.fxml"));
        AddPartController controller = new AddPartController(inventory);
        fxmlLoader.setController(controller);
        fxmlLoader.load();

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * The addProduct method passes the inventory to the add product page where the user can create new products.
     * @param event
     * @throws IOException
     */
    @FXML
    void addProduct(MouseEvent event) throws IOException {
        // Setup loader and controller for the add product screen. Pass inventory to the controller.
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View_Controller/AddProductScreen.fxml"));
        AddProductController controller = new AddProductController(inventory);
        fxmlLoader.setController(controller);
        fxmlLoader.load();

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * The deletePart method removes the selected part from Inventory.
     * @param event
     */
    @FXML
    void deletePart(MouseEvent event) {
        // Get the selected part.
        Part selected = partTableView.getSelectionModel().getSelectedItem();

        // If no part is selected display a warning. Otherwise confirm deletion and remove the part from the list
        // of parts.
        if(selected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No part is selected!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if(inventory.deletePart(selected)) {
                    partList.remove(selected);
                    partTableView.refresh();
                }
            }
        }
    }

    /**
     * The deleteProduct method removes the selected product from Inventory.
     * @param event
     */
    @FXML
    void deleteProduct(MouseEvent event) {
        // Get the selected product.
        Product selected = productTableView.getSelectionModel().getSelectedItem();

        // If no product is selected display a warning. Otherwise confirm deletion and remove the product from the list
        // of product.
        if(selected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No product is selected!");
            alert.showAndWait();
        } else {
            // If the product has parts associated with it, display warning saying the product cannot be deleted.
            // Otherwise delete the product.
            if (productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().size() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error deleting product");
                alert.setContentText("Cannot delete a product that has a part associated with it!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected product?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if(inventory.deleteProduct(selected)) {
                        productList.remove(selected);
                        productTableView.refresh();
                    }
                }
            }
        }
    }

    /**
     * The modifyPart method passes the inventory and the index of the selected part to the modify part page where the
     * user can modify the part.
     * @param event
     * @throws IOException
     */
    @FXML
    void modifyPart(MouseEvent event) throws IOException {
        // Get the selected part index.
        int selected = partTableView.getSelectionModel().getSelectedIndex();

        // If no part is selected display a warning. Otherwise pass the inventory and the selected part index to the
        // modify part controller.
        if(selected < 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No part is selected!");
            alert.showAndWait();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/View_Controller/ModifyPartScreen.fxml"));
            ModifyPartController controller = new ModifyPartController(inventory, selected);
            fxmlLoader.setController(controller);
            fxmlLoader.load();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = fxmlLoader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * The modifyProduct method passes the inventory and the index of the selected product to the modify product
     * page where the user can modify the product.
     * @param event
     * @throws IOException
     */
    @FXML
    void modifyProduct(MouseEvent event) throws IOException {
        // Get the selected product index.
        int selected = productTableView.getSelectionModel().getSelectedIndex();

        // If no product is selected display a warning. Otherwise pass the inventory and the selected product index
        // to the modify product controller.
        if(selected < 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No product is selected!");
            alert.showAndWait();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/View_Controller/ModifyProductScreen.fxml"));
            ModifyProductController controller = new ModifyProductController(inventory, selected);
            fxmlLoader.setController(controller);
            fxmlLoader.load();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = fxmlLoader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * The searchPart method searches for parts by partId and partName and displays matching results.
     * @param event
     */
    @FXML
    void searchPart(KeyEvent event){
        String partToSearch = partSearchField.getText();
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        // If the search field is empty, show all the parts.
        if(partToSearch.isEmpty()) {
            partTableView.setItems(partList);
        }
        // If the search string is an Integer, lookup all parts by partId and partName.
        else if(isInteger(partToSearch)){
            filteredParts.addAll(inventory.lookupPart(partToSearch));
            // Skip adding the part if it already exists from the previous add.
            if(!filteredParts.contains(inventory.lookupPart(Integer.parseInt(partToSearch)))){
                filteredParts.add(inventory.lookupPart(Integer.parseInt(partToSearch)));
            }
            partTableView.setItems(filteredParts);
        }
        // Otherwise, lookup all parts containing the search string.
        else{
            filteredParts.addAll(inventory.lookupPart(partToSearch));
            partTableView.setItems(filteredParts);
        }

        partTableView.refresh();
    }

    /**
     * The searchProduct method searches for products by productId and productName and displays matching results.
     * @param event
     */
    @FXML
    void searchProduct(KeyEvent event){
        String productToSearch = productSearchField.getText();
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        // If the search field is empty, show all the products.
        if(productToSearch.isEmpty()) {
            productTableView.setItems(productList);
        }
        // If the search string is an Integer, lookup all products by productId and productName.
        else if(isInteger(productToSearch)){
            filteredProducts.addAll(inventory.lookupProduct(productToSearch));
            // Skip adding the product if it already exists from the previous add.
            if(!filteredProducts.contains(inventory.lookupProduct(Integer.parseInt(productToSearch)))){
                filteredProducts.add(inventory.lookupProduct(Integer.parseInt(productToSearch)));
            }
            productTableView.setItems(filteredProducts);
        }
        // Otherwise, lookup all products containing the search string.
        else{
            filteredProducts.addAll(inventory.lookupProduct(productToSearch));
            productTableView.setItems(filteredProducts);
        }

        productTableView.refresh();
    }

    /**
     * the exitApplication method closes the program.
     * @param event
     */
    @FXML
    void exitApplication(MouseEvent event) {
        Platform.exit();
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
}
