package Model;

import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Druckhammer
 */
public class Inventory {

    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    public void addPart(Part newPart){
        // TODO: implement me.
    }
    public void addProduct(Product newProduct){
        // TODO: implement me.
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
        // TODO: implement me.
        return false;
    }
    public boolean deleteProduct(Product selectedProduct){
        // TODO: implement me.
        return false;
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
