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
import java.util.Optional;
import java.util.ResourceBundle;


public class ModifyPartController implements Initializable {

    public RadioButton ModifyPartScreenInHouse;
    public ToggleGroup ModifyPartToggle;
    public RadioButton ModifyPartScreenOutsourced;
    public Label IDLabel;
    public TextField ModifyPartScreenID;
    public TextField ModifyPartScreenName;
    public TextField ModifyPartScreenInv;
    public TextField ModifyPartScreenPrice;
    public TextField ModifyPartScreenMax;
    public TextField ModifyPartScreenMachineID;
    public TextField ModifyPartScreenMin;
    public Button ModifyPartScreenSave;
    public Button ModifyPartScreenCancel;
    private boolean inHouse;

    private Part partSelected;
    Stage stage;
    Parent scene;


    /**
     * LOGICAL ERROR: I ran into an error when I would select a part and click modify part, the inputted data did not
     * match the value originally inserted. I noticed that when I modify the part, the inputs are scrambled around.
     * The inventory input would end up at the price field, the price would be in the inventory field. After carefully
     * looking over my code, I realized that when I initialized my table, it did not correspond. So, I had to swap
     * the fields in the correct place.
     *
     * LOGICAL ERROR: When a part is selected, then modified and saved, when trying to modify again, the radio button
     * switches from InHouse to Outsourced even when originally InHouse was saved. To fix this issue, I used two
     * else if statement to check if the boolean for the radio buttons are true or false, which will then update the
     * part depending on which radio button is selected.
     *
     *
     *
     * Index position of part */
    private static int partIndex = 0;
    private static Part part = null;



    /**
     * Updates part
     */
    public static void setPart(int index, Part updatedPart) {
        partIndex = index;
        part = updatedPart;
    }

    /**
     * When InHouse is selected, the label will be machine ID
     */
    public void OnActionSelectInHouse(ActionEvent event) {
        IDLabel.setText("Machine ID");
        inHouse = true;
    }

    /**
     * When Outsourced is selected, the label will change to company name
     */
    public void OnActionSelectOutsourced(ActionEvent event) {
        IDLabel.setText("Company name");
        inHouse = false;

    }

    /**
     * LOGICAL ERROR: When a part is selected, then modified and saved, when trying to modify again, the radio button
     * switches from InHouse to Outsourced even when originally InHouse was saved. To fix this issue, I used two
     * else if statement to check if the boolean for the radio buttons are true or false, which will then update the
     * part depending on which radio button is selected.
     *
     *
     * This allows the input to be updated and saved
     */
    public void OnActionModPartSave(ActionEvent event) throws IOException {
         try {
            int id = Integer.parseInt(ModifyPartScreenID.getText());
            String name = ModifyPartScreenName.getText();
            int inventory = Integer.parseInt(ModifyPartScreenInv.getText());
            double price = Double.parseDouble(ModifyPartScreenPrice.getText());
            int max = Integer.parseInt(ModifyPartScreenMax.getText());
            int min = Integer.parseInt(ModifyPartScreenMin.getText());
            String source = ModifyPartScreenMachineID.getText();

            if (!(min < max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Parts");
                alert.setHeaderText("ADD");
                alert.setHeaderText("Min must be less than max.");
                alert.showAndWait();
                return;
            }

            if (!(min <= inventory && inventory <= max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Parts");
                alert.setHeaderText("ADD");
                alert.setHeaderText("Inventory must be between min and max.");
                alert.showAndWait();
                return;
            }
                else if (ModifyPartScreenInHouse.isSelected())
            {
                Part updateParts1 = new InHouse(id, name, price, inventory, min, max, Integer.parseInt(source));
                Inventory.updatePart(partIndex, updateParts1);
            }
                else if(ModifyPartScreenOutsourced.isSelected())
            {
                Part updateParts2 = new Outsourced(id, name, price, inventory, min, max, source);
                Inventory.updatePart(partIndex, updateParts2);
            }

            /* THIS CODE HAS BEEN EDITED DUE TO ERROR WHEN MODIFYING PARTS

            Part updatedPart;
            if (inHouse) {
                updatedPart = new InHouse(id, name, price, inventory, min, max, Integer.parseInt(source));
            } else {
                updatedPart = new Outsourced(id, name, price, inventory, min, max, source);
            }
            Inventory.updatePart(partIndex, updatedPart); */

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parts");
            alert.setHeaderText("ADD");
            alert.setHeaderText("Invalid input type or empty field.");
            alert.showAndWait();
        }
    }

    /** This is for the cancel button, that will redirect user back to main screen without saving the input */
    public void OnActionModPartCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    /** Initializes controller */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (part instanceof InHouse) {
            ModifyPartScreenInHouse.setSelected(true);
            IDLabel.setText("Machine ID");
            ModifyPartScreenMachineID.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else if (part instanceof Outsourced) {
            ModifyPartScreenOutsourced.setSelected(true);
            IDLabel.setText("Company Name");
            ModifyPartScreenMachineID.setText(((Outsourced) part).getCompanyName());
        }

        ModifyPartScreenID.setText(Integer.toString(part.getId()));
        ModifyPartScreenName.setText(part.getName());
        ModifyPartScreenPrice.setText(Double.toString(part.getPrice()));
        ModifyPartScreenInv.setText(Integer.toString(part.getStock()));
        ModifyPartScreenMin.setText(Integer.toString(part.getMin()));
        ModifyPartScreenMax.setText(Integer.toString(part.getMax()));
    }
}