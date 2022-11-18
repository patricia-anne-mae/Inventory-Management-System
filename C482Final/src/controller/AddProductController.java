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

public class AddProductController implements Initializable {

    public TextField AddProductScreenID;
    public TextField AddProductScreenName;
    public TextField AddProductScreenInv;
    public TextField AddProductScreenPrice;
    public TextField AddProductScreenMax;
    public TextField AddProductScreenMin;
    public TableView<Part> AddProductScreenAddTable;
    public TableColumn<Part, Integer> AddProdScreenAddID;
    public TableColumn<Part, String> AddProdScreenAddName;
    public TableColumn<Part, Integer> AddProdScreenAddInv;
    public TableColumn<Part, Double> AddProdScreenAddPrice;
    public TableView<Part> AddProductScreenRemoveTable;
    public TableColumn<Part, Integer> AddProdScreenRemoveID;
    public TableColumn<Part, String> AddProdScreenRemoveName;
    public TableColumn<Part, Integer> AddProdScreenRemoveInv;
    public TableColumn<Part, Double> AddProdScreenRemovePrice;
    public Button AddProductScreenAdd;
    public Button AddProdScreenRemove;
    public Button AddProdScreenSave;
    public Button AddProdScreenCancel;
    public TextField AddProductScreenSearchBox;
    public Button AddProductScreenSearch;
    Stage stage;
    Parent scene;

    /** Associated parts list */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** This adds the selected part to the bottom table. If no part is selected, an alert box will appear. */
    public void OnActionAddProdAdd(ActionEvent event) {
        Part selectedPart = AddProductScreenAddTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parts");
            alert.setHeaderText("ADD");
            alert.setHeaderText("No part selected.");
            alert.showAndWait();
            return;
        }
        associatedParts.add(selectedPart);
        AddProductScreenRemoveTable.setItems(associatedParts);
    }

    /** This removes parts from the bottom table. If no part was selected, an alert box will appear */
    public void OnActionAddProdRemove(ActionEvent event) {
        Part selectedPart = AddProductScreenRemoveTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parts");
            alert.setHeaderText("REMOVE");
            alert.setHeaderText("No part selected.");
            alert.showAndWait();
            return;
        }
        associatedParts.remove(selectedPart);
    }

    /** This allows users to input new data, and save it. After saving, it redirects user back to main screen
     * If there are invalid inputs or empty fields, an error box will appear.*/
    public void OnActionAddProdSave(ActionEvent event) {
        try {
            int id = Inventory.getProductId();
            String name = AddProductScreenName.getText();
            int inventory = Integer.parseInt(AddProductScreenInv.getText());
            double price = Double.parseDouble(AddProductScreenPrice.getText());
            int max = Integer.parseInt(AddProductScreenMax.getText());
            int min = Integer.parseInt(AddProductScreenMin.getText());

            if (!(min<max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Products");
                alert.setHeaderText("ADD");
                alert.setHeaderText("Min must be less than max.");
                alert.showAndWait(); //If min is more than the max, an alert box will appear indicating the error
                return;
            }

            if (!(min<=inventory && inventory <=max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Products");
                alert.setHeaderText("ADD");
                alert.setHeaderText("Inventory must be between min and max.");
                alert.showAndWait(); //If inventory is not between the min and max, an alert box will appear
                return;
            }

            Product product = new Product(id, name, price, inventory, min, max);

            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }

            Inventory.addProduct(product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Products");
            alert.setHeaderText("ADD");
            alert.setHeaderText("Invalid input type or empty field.");
            alert.showAndWait(); //If user types invalid input or left a field empty, an alert box will appear
        }
    }

    /** When a user clicks cancel, the user will be redirected back to main screen and input will not be saved */
    public void OnActionAddProdCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /** This is the search function that allows users to search for parts name or ID
     * If there are no matching product found, an alert box will appear */
    public void OnActionAddProductSearch(ActionEvent event) {
        String searchText = AddProductScreenSearchBox.getText();
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
            alert.showAndWait(); //If no match is found, an error will appear

        }
        AddProductScreenAddTable.setItems(partReturn);
        AddProductScreenSearchBox.setText("");
    }



    /** Initializes the table */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        AddProductScreenAddTable.setItems(Inventory.getAllParts());
        AddProdScreenAddID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdScreenAddName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdScreenAddInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdScreenAddPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        AddProdScreenRemoveID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdScreenRemoveName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdScreenRemoveInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdScreenRemovePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

