package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

    /**
     * The addPart method adds a new Part to the Inventory.
     * @param newPart The Part to add to Inventory.
     */
    public void addPart(Part newPart){
        if(newPart != null){
            allParts.add(newPart);
        }
    }

    /**
     * The addProduct method adds a new Product to the Inventory.
     * @param newProduct The Product to add to Inventory.
     */
    public void addProduct(Product newProduct){
        if(newProduct != null){
            allProducts.add(newProduct);
        }
    }


    public Part lookupPart(int partId){
        for (Part p : this.getAllParts()) {
            if (p.getId() == partId) {
                return p;
            }
        }
        return null;
    }

    public Product lookupProduct(int productId){
        for (Product p : this.getAllProducts()) {
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    public ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        for (Part p : this.getAllParts()){
            if (p.getName().toLowerCase().contains(partName.toLowerCase())) {
                filteredParts.add(p);
            }
        }
        return filteredParts;
    }

    public ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        for (Product p : this.getAllProducts()){
            if (p.getName().toLowerCase().contains(productName.toLowerCase())) {
                filteredProducts.add(p);
            }
        }
        return filteredProducts;
    }
    public void updatePart(int index, Part selectedPart){
        allParts.remove(index);
        allParts.add(index, selectedPart);
    }
    public void updateProduct(int index, Product newProduct){
        // TODO: implement me.
    }

    /**
     * The deletePart method removes a Part from Inventory.
     * @param selectedPart The Part to delete.
     * @return Returns a boolean value of the result.
     */
    public boolean deletePart(Part selectedPart){
        if(selectedPart != null){
            allParts.remove(selectedPart);
            return true;
        } else {
            // TODO: Display popup window saying no part selected.
            return false;
        }
    }

    /**
     * The deleteProduct method removes a Product from Inventory.
     * @param selectedProduct The Product to delete.
     * @return Returns a boolean value of the result.
     */
    public boolean deleteProduct(Product selectedProduct){
        if(selectedProduct != null){
            allProducts.remove(selectedProduct);
            return true;
        } else {
            // TODO: Display popup window saying no part selected.
            return false;
        }
    }

    /**
     * The getAllParts method returns all the Parts in Inventory.
     * @return Returns all of the Parts in Inventory.
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * The getAllProducts method returns all the Products in Inventory.
     * @return Returns all of the Products in Inventory.
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
