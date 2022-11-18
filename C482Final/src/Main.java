import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

//javadoc is located in the javadoc folder
/** FUTURE ENHANCEMENT: A beneficial enhancement for the inventory system would be to add more functionality into the
 * search function. Rather than only being able to search by ID or name, users would also be able to filter searches
 * based on company and machine id.
 *
 * FUTURE ENHANCEMENT: Another enhancement that can be added is to have a clear button on the add and modify forms
 * to allow users to clear all the field easily.
 *
 * This is the main method */

public class Main extends Application {
    public static void main(String[] args) {

        int partId = Inventory.getPartId();
        InHouse Wheels = new InHouse(partId, "Pedal", 19.11, 10, 2, 10, 532);

        partId = Inventory.getPartId();
        InHouse Chain = new InHouse(partId, "Chain", 12.47, 25, 1, 30, 031);

        Inventory.addPart(Wheels);
        Inventory.addPart(Chain);


        int productId = Inventory.getProductId();
        Product MountainBike = new Product(productId, "Mountain Bike", 255.99, 15, 10, 20);

        int productId2 = Inventory.getProductId();
        Product KidsBike = new Product(productId2, "Kids Bike", 56.34, 20, 10, 20);

        Inventory.addProduct(MountainBike);
        Inventory.addProduct(KidsBike);

        {
            launch(args);

        }
    }

    /** This will load the main screen */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}