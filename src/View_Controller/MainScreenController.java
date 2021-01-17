package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import com.sun.webkit.dom.KeyboardEventImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    Inventory inventory;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableView<Product> productTableView;

    private ObservableList<Part> partList = FXCollections.observableArrayList();
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    public MainScreenController(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generatePartsTable();
        generateProductsTable();
    }

    private void generatePartsTable(){
        partList.setAll(inventory.getAllParts());
        partTableView.setItems(partList);
        partTableView.refresh();
    }
    private void generateProductsTable(){
        productList.setAll(inventory.getAllProducts());
        productTableView.setItems(productList);
        productTableView.refresh();
    }

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

    @FXML
    void addPart(MouseEvent event) {

    }

    @FXML
    void addProduct(MouseEvent event) {

    }

    @FXML
    void deletePart(MouseEvent event) {
        Part selected = partTableView.getSelectionModel().getSelectedItem();
        partList.remove(selected);
        partTableView.refresh();
    }

    @FXML
    void deleteProduct(MouseEvent event) {
        Product selected = productTableView.getSelectionModel().getSelectedItem();
        productList.remove(selected);
        productTableView.refresh();
    }

    @FXML
    void exitApplication(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void modifyPart(MouseEvent event) {

    }

    @FXML
    void modifyProduct(MouseEvent event) {

    }

    @FXML
    void searchPart(KeyEvent event){
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        String partToSearch = partSearchField.getText();
        if(partToSearch.isEmpty()){
            partTableView.setItems(partList);
        } else {
            for (Part p : inventory.getAllParts()) {
                if ((Integer.toString(p.getId()).contains(partToSearch)) || p.getName().toLowerCase().contains(partToSearch.toLowerCase())) {
                    filteredParts.add(p);
                }
            }
            partTableView.setItems(filteredParts);
        }
        partTableView.refresh();
    }

    @FXML
    void searchProduct(KeyEvent event){
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        String productToSearch = productSearchField.getText();
        if(productToSearch.isEmpty()){
            productTableView.setItems(productList);
        } else {
            for (Product p : inventory.getAllProducts()) {
                if ((Integer.toString(p.getId()).contains(productToSearch)) || p.getName().toLowerCase().contains(productToSearch.toLowerCase())) {
                    filteredProducts.add(p);
                }
            }
            productTableView.setItems(filteredProducts);
        }
        productTableView.refresh();
    }
}
