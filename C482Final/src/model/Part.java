package model;

public abstract class Part {
    private int id;

    private String name;

    private double price;

    private int stock;

    private int min;

    private int max;

    /** Constructors are used to initialize an object. Below is the constructor for part */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Getters are methods used to return a value, while setters are used to set or update a value
     * Below is the getter for ID */
    public int getId() {
        return id;
    }

    /** Getter for name */
    public String getName() {
        return name;
    }

    /** Getter for price */
    public double getPrice() {
        return price;
    }

    /** Getter for stock (inventory) */
    public int getStock() {
        return stock;
    }

    /** Getter for min */
    public int getMin() {
        return min;
    }

    /** Getter for max */
    public int getMax() {
        return max;
    }



    /** Setter for ID */
    public void setId(int id) {
        this.id = id;
    }

    /** Setter for name */
    public void setName(String name) {
        this.name = name;
    }

    /** Setter for price */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Setter for stock (inventory) */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Setter for min */
    public void setMin(int min) {
        this.min = min;
    }

    /** Setter for max */
    public void setMax(int max) {
        this.max = max;
    }
}