package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Druckhammer
 */
public class Inventory {

    private final ObservableList<Part> allParts;
    private final ObservableList<Product> allProducts;

    /**
     * Inventory Constructor.
     * Instantiates a new part and product ObservableList.
     */
    public Inventory() {
        // Part G:
        //  The below 2 lines didn't exist and was causing runtime errors. This was because the variables "allParts" and "allProducts"
        //  were not actually being instantiated, just declared. I had to check back on creating an ObservableList.
        //
        //  The Inventory class can be upgraded in the future to allow for database integration.
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }

    /**
     * The addPart method adds a new part to the Inventory.
     * @param newPart The part to add to Inventory.
     */
    public void addPart(Part newPart){
        if(newPart != null){
            allParts.add(newPart);
        }
    }

    /**
     * The addProduct method adds a new product to the Inventory.
     * @param newProduct The product to add to Inventory.
     */
    public void addProduct(Product newProduct){
        if(newProduct != null){
            allProducts.add(newProduct);
        }
    }

    /**
     * The lookupPart method searches all parts that match by part ID and returns the result.
     * @param partId The part ID to search.
     * @return Returns the part found.
     */
    public Part lookupPart(int partId){
        for (Part p : this.getAllParts()) {
            if (p.getId() == partId) {
                return p;
            }
        }
        return null;
    }

    /**
     * The lookupProduct method searches all products that match by product ID and returns the result.
     * @param productId The product ID to search.
     * @return Returns the product found.
     */
    public Product lookupProduct(int productId){
        for (Product p : this.getAllProducts()) {
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    /**
     * The lookupPart method searches the list of parts that match by part name and returns the results.
     * @param partName The name of the part to search.
     * @return Returns the list of part found.
     */
    public ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        for (Part p : this.getAllParts()){
            if (p.getName().toLowerCase().contains(partName.toLowerCase())) {
                filteredParts.add(p);
            }
        }
        return filteredParts;
    }

    /**
     * The lookupProduct method searches the list of products that match by product name and returns the results.
     * @param productName The name of the product to search.
     * @return Returns the list of products found.
     */
    public ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        for (Product p : this.getAllProducts()){
            if (p.getName().toLowerCase().contains(productName.toLowerCase())) {
                filteredProducts.add(p);
            }
        }
        return filteredProducts;
    }

    /**
     * The updatePart method updates the inventory of parts by removing the old part and adding the new
     * part at the selected index.
     * @param index The index of the selected part.
     * @param selectedPart The selected part.
     */
    public void updatePart(int index, Part selectedPart){
        allParts.remove(index);
        allParts.add(index, selectedPart);
    }

    /**
     * The updateProduct method updates the inventory of products by removing the old product and adding the new
     * product at the selected index.
     * @param index The index of the selected product.
     * @param selectedProduct The selected product.
     */
    public void updateProduct(int index, Product selectedProduct){
        allProducts.remove(index);
        allProducts.add(index, selectedProduct);
    }

    /**
     * The deletePart method removes a part from Inventory.
     * @param selectedPart The part to delete.
     * @return Returns a boolean value of the result.
     */
    public boolean deletePart(Part selectedPart){
        if(selectedPart != null){
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * The deleteProduct method removes a product from Inventory.
     * @param selectedProduct The product to delete.
     * @return Returns a boolean value of the result.
     */
    public boolean deleteProduct(Product selectedProduct){
        if(selectedProduct != null){
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * The getAllParts method returns all the parts in Inventory.
     * @return Returns all of the parts in Inventory.
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * The getAllProducts method returns all the products in Inventory.
     * @return Returns all of the products in Inventory.
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
