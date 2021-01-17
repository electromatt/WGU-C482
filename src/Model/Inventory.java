package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 *
 * @author Matthew Druckhammer
 */
public class Inventory {

    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    public Inventory() {
        // Part G:  The below 2 lines didn't exist and was causing runtime errors. This was because the variables "allParts" and "allProducts"
        //          were not actually being instantiated, just declared. I had to check back on creating an ObservableList.
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }

    public void addPart(Part newPart){
        if(newPart != null){
            allParts.add(newPart);
        }

    }

    public void addProduct(Product newProduct){
        if(newProduct == null){
            ;
        } else {
            allProducts.add(newProduct);
        }
    }
    public Part lookupPart(int partId){
        // TODO: implement me.
        return null;
    }
    public Product lookupProduct(int productId){
        // TODO: implement me.
        return null;
    }
    public ObservableList<Part> lookupPart(String partName){
        // TODO: implement me.
        return null;
    }
    public ObservableList<Product> lookupProduct(String productName){
        // TODO: implement me.
        return null;
    }
    public void updatePart(int index, Part selectedPart){
        // TODO: implement me.
    }
    public void updateProduct(int index, Product newProduct){
        // TODO: implement me.
    }
    public boolean deletePart(Part selectedPart){
        if(selectedPart != null){
            allParts.remove(selectedPart);
            return true;
        } else {
            // TODO: Display popup window saying no part selected.
            return false;
        }
    }
    public boolean deleteProduct(Product selectedProduct){
        if(selectedProduct != null){
            allProducts.remove(selectedProduct);
            return true;
        } else {
            // TODO: Display popup window saying no part selected.
            return false;
        }
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
