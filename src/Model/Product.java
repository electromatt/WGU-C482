package Model;

import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Druckhammer
 */
public class Product {

    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     *
     * @param id The id of the product.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The amount of products in stock.
     * @param min The minimum amount of products.
     * @param max The maximum amount of products.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void addAssociatedPart(Part part){

        // TODO: implement me.
    }
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        // TODO: implement me.
        return false;
    }
    public ObservableList<Part> getAllAssociatedParts(){
        // TODO: implement me.
        return null;
    }
}
