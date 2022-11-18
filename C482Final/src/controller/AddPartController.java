package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    public RadioButton AddPartScreenInHouse;
    public ToggleGroup tgroup;
    public RadioButton AddPartScreenOutsourced;
    public Label AddaPartIdLabel;
    public TextField AddPartScreenID;
    public TextField AddPartScreenName;
    public TextField AddPartScreenInv;
    public TextField AddPartScreenPrice;
    public TextField AddPartScreenMax;
    public TextField AddPartScreenMachineID;
    public TextField AddPartScreenMin;
    public Button AddPartScreenSave;
    public Button AddPartScreenCancel;
    private boolean inHouse;
    Stage stage;
    Parent scene;


    /** Method for InHouse radio button. When a user selects this, it shows the label as machine ID */
    public void OnActionAddPartSelectInHouse(ActionEvent event) {
        AddaPartIdLabel.setText("Machine ID");
        inHouse = true;

    }

    /** Method for Outsourced radio button. When a user selects this, it shows the label as company name */
    public void OnActionAddPartSelectOutsourced(ActionEvent event) {
        AddaPartIdLabel.setText("Company Name");
        inHouse = false;

    }

    /** This allows the user to save parts */
    public void OnActionAddPartSave(ActionEvent event) {
        try {
        int id = Inventory.getPartId();
        String name = AddPartScreenName.getText();
        int inventory = Integer.parseInt(AddPartScreenInv.getText());
        double price = Double.parseDouble(AddPartScreenPrice.getText());
        int max = Integer.parseInt(AddPartScreenMax.getText());
        int min = Integer.parseInt(AddPartScreenMin.getText());
        String source = AddPartScreenMachineID.getText();

        if (!(min<max)) {   //If the min is more than the max, an alert box will appear, indicating the error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parts");
            alert.setHeaderText("ADD");
            alert.setHeaderText("Min must be less than max.");
            alert.showAndWait();
            return;
        }

        if (!(min<=inventory && inventory <=max)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parts");
            alert.setHeaderText("ADD");
            alert.setHeaderText("Inventory must be between min and max.");
            alert.showAndWait(); //If the inventory is not between the min and max, an alert box will appear, indicating the error
            return;
        }

        if (inHouse) {
            int machineId;
            try {
                machineId = Integer.parseInt(source);
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Parts");
                alert.setHeaderText("ADD");
                alert.setHeaderText("Machine ID must be integer.");
                alert.showAndWait(); //If the input in the machine ID box is not an integer, an alert box will appear
                return;
            }
            Part part = new InHouse(id, name, price, inventory, min, max, machineId);
            Inventory.addPart(part);
        } else {
            Part part = new Outsourced(id, name, price, inventory, min, max, source);
            Inventory.addPart(part);
        }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();  //This will return to the main screen

    } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Parts");
        alert.setHeaderText("ADD");
        alert.setHeaderText("Invalid input type or empty field. Please try again.");
        alert.showAndWait(); //If fields are left empty or invalid input is typed, an alert box will appear
    }
    }

    /** This is the cancel button that takes the user back to the main screen without saving the input */
    public void OnActionAddPartCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This initializes the InHouse to true*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddPartScreenInHouse.setSelected(true);
        inHouse = true;
    }
}


