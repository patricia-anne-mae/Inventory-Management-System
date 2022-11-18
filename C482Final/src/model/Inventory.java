package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    /** List of all the parts */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** Lists of all the products */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /** Part ID */
    private static int partId = 0;

    /** Product ID */
    private static int productId = 0;

    /** Adds a part */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Adds a product */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** This will look up parts using the part ID/integer */
    public static Part lookupPart(int partId) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partId) {
                    return allParts.get(i);
                }
            }
        }

        return null;
    }

    /** This will look up parts using the part name/string */
    public static ObservableList<Part> lookupPart(String partName) {
        if (!allParts.isEmpty()) {
            ObservableList partSearchList = FXCollections.observableArrayList();
            for (Part p : getAllParts()) {
                if (p.getName().toLowerCase().contains(partName.toLowerCase())) {
                    partSearchList.add(p);

                }
            }
            return partSearchList;
        }
        return null;
    }

    /** This will look up products using product ID/integer */
    public static Product lookupProduct(int productId) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getId() == productId) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    /** This will look up products using product name/string */
    public static ObservableList<Product> lookupProduct(String productName) {
        if (!allProducts.isEmpty()) {
            ObservableList productSearchList = FXCollections.observableArrayList();
            for (Product p : getAllProducts()) {
                if (p.getName().toLowerCase().contains(productName.toLowerCase())) {
                    productSearchList.add(p);
                }
            }
            return productSearchList;
        }
        return null;
    }

    /** This will update a part */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** This will update a product */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /** This will delete the selected part */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /** This will delete the selected product */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /** This will return all parts on the list */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** This will return all products on the list */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** This will return the part ID in an increment of 1 */
    public static int getPartId() {
        partId++;
        return partId;
    }

    /** This will return product ID in an increment of 1 */
    public static int getProductId() {
        productId++;
        return productId;
    }
}