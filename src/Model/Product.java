package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Druckhammer
 */
public class Product {

    private final ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Product Constructor.
     * @param id The id of the product.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The amount of products in stock.
     * @param min The minimum amount of products.
     * @param max The maximum amount of products.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        associatedParts = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * The setId method sets the ID of the product.
     * @param id The id of the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The setName method sets the name of the product.
     * @param name The name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The setPrice method sets the price of the product.
     * @param price The price of the product.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * The setStock method sets the inventory stock of the product.
     * @param stock The amount of products in stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * The setMin method sets the minimum amount of inventory stock for the product.
     * @param min The minimum amount of products.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * The setMax method sets the maximum amount of inventory stock for the product.
     * @param max The maximum amount of products.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * The getId method returns the id of the product.
     * @return Returns the id of the product.
     */
    public int getId() {
        return id;
    }

    /**
     * The getName method returns the name of the product.
     * @return Returns the name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * The getPrice method returns the price of the product.
     * @return Returns the price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * The getStock method returns the current inventory stock of the product.
     * @return Returns the amount of current inventory stock of the product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * The getMin method returns the minimum amount of inventory stock for the product.
     * @return Returns the minimum amount of inventory stock for the product.
     */
    public int getMin() {
        return min;
    }

    /**
     * The getMax method returns the maximum amount of inventory stock for the product.
     * @return Returns the maximum amount of inventory stock for the product.
     */
    public int getMax() {
        return max;
    }

    /**
     * The addAssociatedPart method adds a specified part to the list of associated parts of a product.
     * @param part The part to add.
     */
    public void addAssociatedPart(Part part){
        if(part != null){
            associatedParts.add(part);
        }
    }

    /**
     * The deleteAssociatedPart method deletes a specified part from the list of associated parts of a product.
     * @param selectedAssociatedPart The selected part to delete.
     * @return Returns a boolean value of the result.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(selectedAssociatedPart != null){
            return associatedParts.remove(selectedAssociatedPart);
        } else {
            return false;
        }
    }

    /**
     * The getAllAssociatedParts method returns all parts that were added to the list of associated parts of a product.
     * @return Returns an ObservableList of type part.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
