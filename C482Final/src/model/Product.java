package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    /** Parts associated with products */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;

    private String name;

    private double price;

    private int stock;

    private int min;
    private int max;

    /** Constructor for product */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Getters are methods used to return a value, while setters are used to set or update a value
     * Below is the getter for ID*/
    public int getId() {
        return id;
    }

    /** Setter for ID */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for name */
    public String getName() {
        return name;
    }

    /** Setter for name */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for price */
    public double getPrice() {
        return price;
    }

    /** Setter for price */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter for stock (inventory) */
    public int getStock() {
        return stock;
    }

    /** Setter for stock (inventory) */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter for min */
    public int getMin() {
        return min;
    }

    /** Setter for min */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter for max */
    public int getMax() {
        return max;
    }

    /** Setter for max */
    public void setMax(int max) {
        this.max = max;
    }

    /** Adds associated parts */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Deletes associated parts */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Gets associated parts */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}