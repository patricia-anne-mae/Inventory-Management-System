package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    public TableView<Part> MainPartsTable;
    public TableColumn<Part, Integer> MainPartID;
    public TableColumn<Part, String> MainPartName;
    public TableColumn<Part, Integer> MainPartInv;
    public TableColumn<Part, Double> MainPartPrice;
    public TextField MainScreenPartSearchBox;
    public Button MainPartAddButton;
    public Button MainPartModifyButton;
    public Button MainPartDeleteButton;
    public Button MainScreenPartSearch;
    public TableView<Product> MainProductsTable;
    public TableColumn<Product, Integer> MainProductID;
    public TableColumn<Product, String> MainProductName;
    public TableColumn<Product, Integer> MainProductInv;
    public TableColumn<Product, Double> MainProductPrice;
    public Button MainProductAddButton;
    public Button MainProductModifyButton;
    public Button MainProductDeleteButton;
    public TextField MainScreenProductSearchBox;
    public Button MainScreenProductSearch;
    public Button MainScreenExit;
    Stage stage;
    Parent scene;


    /** This directs users to the add part screen when button is clicked */
    public void OnActionMainPartAdd(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** When a user selects a part, and clicks modify, the user will be redirected to the
     * modify part screen with the existing data */
    public void OnActionMainPartModify(ActionEvent event) throws IOException {

        int selectedPartIndex = MainPartsTable.getSelectionModel().getSelectedIndex();
        Part selectedPart = MainPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parts");
            alert.setHeaderText("MODIFY");
            alert.setHeaderText("No part selected. Please try again");
            alert.showAndWait(); //If no part was selected, an alert box will appear
            return;
        }
        ModifyPartController.setPart(selectedPartIndex, selectedPart);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Deletes a part from the table. If no part is selected, an error will appear.
     * Otherwise, a confirmation box will appear to confirm deletion */
    public void OnActionMainPartDelete(ActionEvent event) {
        int selectedPartIndex = MainPartsTable.getSelectionModel().getSelectedIndex();
         Part selectedPart = MainPartsTable.getSelectionModel().getSelectedItem();
         if (selectedPart == null) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Parts");
             alert.setHeaderText("DELETE");
             alert.setHeaderText("No part selected. Please try again.");
             alert.showAndWait();
             return;
         }

         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Parts");
         alert.setHeaderText("DELETE");
         alert.setContentText("Do you want to delete this part?");
         alert.showAndWait();

         if (alert.getResult() == ButtonType.OK) {
             Inventory.deletePart(selectedPart);
             MainPartsTable.setItems(Inventory.getAllParts());
         }

     }

    /** This is the search function for the parts table */
    public void OnActionPartSearchButton(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String search = MainScreenPartSearchBox.getText();

        for(Part part : allParts)
        {
            if(String.valueOf(part.getId()).contains(search) ||
                    part.getName().toLowerCase().contains(search.toLowerCase()))
            {
                foundParts.add(part);
            }
        }
        MainPartsTable.setItems(foundParts);
        if(foundParts.size() == 0)
        {
            infoDialog("INFORMATION", "Part not found", "Please choose a part from the list");
            return;
        }
    }


    /** This refreshes the table when the search field is cleared */
    public void OnKeyPartSearch(KeyEvent event) {
        if(MainScreenPartSearchBox.getText().isEmpty()){
            MainPartsTable.setItems(Inventory.getAllParts());
        }
    }

    /** This refreshes the table when the search field is cleared */
    public void OnActionMainProductAdd(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** When a product is selected, and a user clicks modify, it opens modify
     * product form with the existing input transferred over to the new screen */
    public void OnActionMainProductModify(ActionEvent event) throws IOException {
        int selectedProductIndex = MainProductsTable.getSelectionModel().getSelectedIndex();
        Product selectedProduct = MainProductsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Products");
            alert.setHeaderText("MODIFY");
            alert.setHeaderText("No product selected. Please try again.");
            alert.showAndWait(); //If no product was selected, an error will appear
            return;
        }
        ModifyProductController.setProduct(selectedProductIndex, selectedProduct);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    /** When a user selects a product, and clicks delete a confirmation will appear */
    public void OnActionMainProductDelete(ActionEvent event) {
        int selectedProductIndex = MainProductsTable.getSelectionModel().getSelectedIndex();
        Product selectedProduct = MainProductsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Products");
            alert.setHeaderText("DELETE");
            alert.setHeaderText("No product selected. Please try again.");
            alert.showAndWait(); //If no product is selected, an alert box will appear
            return;
        }
        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Products");
            alert.setHeaderText("DELETE");
            alert.setHeaderText("Product has associated parts; cannot be deleted.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Products");
        alert.setHeaderText("DELETE");
        alert.setContentText("Do you want to delete this product?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            Inventory.deleteProduct(selectedProduct);
            MainProductsTable.setItems(Inventory.getAllProducts());
        }
    }

    /** This is the search function for product */
    public void OnActionProductSearchButton(ActionEvent event) {
        ObservableList<Product> allProduct = Inventory.getAllProducts();
        ObservableList<Product> foundProd = FXCollections.observableArrayList();
        String search = MainScreenProductSearchBox.getText();

        for(Product prod : allProduct)
        {
            if(String.valueOf(prod.getId()).contains(search) ||
                    prod.getName().toLowerCase().contains(search.toLowerCase()))
            {
                foundProd.add(prod);
            }
        }
        MainProductsTable.setItems(foundProd);
        if(foundProd.size() == 0)
        {
            infoDialog("Information", "Product not found", "Please choose a product from the list");
            return;
        }
    }

    /** This allows the table to be refreshed for the product table, when the search field is cleared */
    public void OnKeyProductSearch(KeyEvent event) {
        if (MainScreenProductSearchBox.getText().isEmpty()) {
            MainProductsTable.setItems(Inventory.getAllProducts());
        }
    }


    /** This is the alert box for the search errors */
    private void infoDialog(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /** Exit button that closes the application, with an alert confirming exit */
    public void OnActionMainExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }


    /** Initializes the table */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainPartsTable.setItems(Inventory.getAllParts());
        MainPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        MainProductsTable.setItems(Inventory.getAllProducts());
        MainProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainProductInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
