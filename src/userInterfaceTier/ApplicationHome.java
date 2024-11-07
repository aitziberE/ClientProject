package userInterfaceTier;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main application class responsible for initializing and displaying the Home window.
 * 
 * This class extends {@link javafx.application.Application} and overrides the {@code start} method
 * to set up the primary stage with the Home interface.
 *
 * @author Pablo
 */
public class ApplicationHome extends Application {

    /**
     * Initializes and displays the primary stage with the Home interface.
     *
     * @param stage the main stage for this application
     * @throws Exception if an error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/userInterfaceTier/Home.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
