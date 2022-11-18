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

public class ModifyProductController implements Initializable {

    public TextField ModifyProductScreenID;
    public TextField ModifyProductScreenName;
    public TextField ModifyProductScreenInv;
    public TextField ModifyProductScreenPrice;
    public TextField ModifyProductScreenMax;
    public TextField ModifyProductScreenMin;
    public TableView<Part> ModifyProductScreenAddTable;
    public TableColumn<Part, Integer> ModifyProdAddID;
    public TableColumn<Part, String> ModifyProdAddName;
    public TableColumn<Part, Integer> ModifyProdAddInv;
    public TableColumn<Part, Double> ModifyProdAddPrice;
    public TableView<Part> ModifyProductScreenRemoveTable;
    public TableColumn<Part, Integer> ModifyProdRemoveID;
    public TableColumn<Part, String> ModifyProdRemoveName;
    public TableColumn<Part, Integer> ModifyProdRemoveInv;
    public TableColumn<Part, Double> ModifyProdRemovePrice;
    public Button ModifyProductScreenAdd;
    public Button ModifyProductScreenRemove;
    public Button ModifyProductScreenSave;
    public Button ModifyProductScreenCancel;
    public TextField ModifyProductScreenSearchBox;
   
    public Button ModifyProductScreenSearch;

    /** Associated parts list */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Stage stage;
    Parent scene;

    /** Product index */
    private static int productIndex = 0;

    private static Product product = null;

    /** Updates product */
    public static void setProduct(int index, Product updatedProduct) {
        productIndex = index;
        product = updatedProduct;
    }

    /** This allows a part to be selected and added to the bottom table */
    public void OnActionModProdAdd(ActionEvent event) {
        Part selectedPart = ModifyProductScreenAddTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parts");
            alert.setHeaderText("ADD");
            alert.setHeaderText("No part selected.");
            alert.showAndWait();
            return;
        }
        associatedParts.add(selectedPart);
        ModifyProductScreenRemoveTable.setItems(associatedParts);
    }

    /** This removes a part from the bottom table, a confirmation box will appear to confirm */
    public void OnActionModProdRemove(ActionEvent event) {
        Part selectedPart = ModifyProductScreenRemoveTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parts");
            alert.setHeaderText("REMOVE");
            alert.setHeaderText("No part selected.");
            alert.showAndWait(); //If no part is selected, an alert box will appear
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Parts");
        alert.setHeaderText("REMOVE");
        alert.setContentText("Do you want to remove this associated part?"); //This is a confirmation box asking user to confirm action
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
        }
        ModifyProductScreenRemoveTable.setItems(associatedParts);
    }

    /** This allows users to modify input and save it. If the there are invalid inputs or empty text fields,
     * an alert will appear */
    public void OnActionModProdSave(ActionEvent event) {
        try {
            int id = Integer.parseInt(ModifyProductScreenID.getText());
            String name = ModifyProductScreenName.getText();
            int inventory = Integer.parseInt(ModifyProductScreenInv.getText());
            double price = Double.parseDouble(ModifyProductScreenPrice.getText());
            int max = Integer.parseInt(ModifyProductScreenMax.getText());
            int min = Integer.parseInt(ModifyProductScreenMin.getText());

            if (!(min<max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Products");
                alert.setHeaderText("ADD");
                alert.setHeaderText("Min must be less than max.");
                alert.showAndWait();
                return;
            }

            if (!(min<=inventory && inventory <=max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Products");
                alert.setHeaderText("ADD");
                alert.setHeaderText("Inventory must be between min and max.");
                alert.showAndWait();
                return;
            }

            Product product = new Product(id, name, price, inventory, min, max);

            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }

            Inventory.updateProduct(productIndex, product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Products");
            alert.setHeaderText("ADD");
            alert.setHeaderText("Invalid input type or empty field.");
            alert.showAndWait();
        }
    }

    /** This is the cancel button that redirects users back to main screen */
    public void OnActionModProdcancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This is the search function that allows users to search for parts using name or ID */
    public void OnActionModifyProdSearchButton(ActionEvent event) {
        String searchText = ModifyProductScreenSearchBox.getText();
        ObservableList<Part> partReturn = Inventory.lookupPart(searchText);

        if (partReturn.isEmpty()) {
            try {
                int idNumber = Integer.parseInt(searchText);
                Part partName = Inventory.lookupPart(idNumber);
                if (partName != null) {
                    partReturn.add(partName);
                }
            } catch (NumberFormatException e) {

            }
        }
        if (partReturn.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR: No Product Found");
            alert.setContentText("No matching product was found.");
            alert.showAndWait();

        }
        ModifyProductScreenAddTable.setItems(partReturn);
        ModifyProductScreenSearchBox.setText("");
    }


    /** This refreshes the table when the search field is cleared */
    public void OnKeyModifyProductSearch(KeyEvent event) {
        if(ModifyProductScreenSearchBox.getText().isEmpty()){
            ModifyProductScreenAddTable.setItems(Inventory.getAllParts());
        }
    }


    /** Initializes the table */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ModifyProductScreenAddTable.setItems(Inventory.getAllParts());
        ModifyProdAddID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProdAddName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProdAddInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProdAddPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedParts = product.getAllAssociatedParts();
        ModifyProductScreenRemoveTable.setItems(associatedParts);
        ModifyProdRemoveID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProdRemoveName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProdRemoveInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProdRemovePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        ModifyProductScreenID.setText(Integer.toString(product.getId()));
        ModifyProductScreenName.setText(product.getName());
        ModifyProductScreenPrice.setText(Double.toString(product.getPrice()));
        ModifyProductScreenInv.setText(Integer.toString(product.getStock()));
        ModifyProductScreenMin.setText(Integer.toString(product.getMin()));
        ModifyProductScreenMax.setText(Integer.toString(product.getMax()));
    }
}