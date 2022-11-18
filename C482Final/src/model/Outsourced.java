package model;

/** Extension of part model */
public class Outsourced extends Part {
    private String CompanyName;

    /** Constructors are used to initialize an object. Below is the constructor for Outsourced */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        CompanyName = companyName;
    }

    /** Accessors (getters) */
    public String getCompanyName() {
        return CompanyName;
    }

    /** Mutators (setters) */
    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}

