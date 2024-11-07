/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import userLogicTier.model.User;

/**
 * Utility class for managing the opening of new windows in the application.
 * This class provides methods to open different windows (scenes) based on the 
 * provided FXML file path and title. It also allows passing a user object 
 * to the new window for context.
 * 
 * @author inifr
 * @author Aitziber
 */
public class WindowManager {
    
    /**
     * Logger to track the activity and handle debugging information.
     */
    private static final Logger logger = Logger.getLogger(WindowManager.class.getName());

    /**
     * Opens a new window with the provided FXML file, title, and user context.
     * The user object is passed to the controller of the new window.
     *
     * @param fxmlFilePath the path to the FXML file to load for the new window.
     * @param title the title to set for the new window.
     * @param user the user object to pass to the controller of the new window.
     */
    public static void openWindow(String fxmlFilePath, String title, User user) {
        try {
             // Load the FXML file and its controller
            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();

            // Get the controller and set the user context
            HomeController homeController = fxmlLoader.getController();
            homeController.setUser(user);

            // Set up the stage (window)
            Stage stage = new Stage();
            stage.getIcons().add(new Image("resources/logo.png"));
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
             logger.log(Level.SEVERE, "Error opening window: {0}", e.getMessage());
        }
    }

    /**
     * Opens a new window with the provided FXML file and title.
     * This method does not pass any user context to the new window.
     *
     * @param fxmlFilePath the path to the FXML file to load for the new window.
     * @param title the title to set for the new window.
     */
    public static void openWindow(String fxmlFilePath, String title) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();

            // Set up the stage (window)
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.getIcons().add(new Image("resources/logo.png"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
             logger.log(Level.SEVERE, "Error opening window: {0}", e.getMessage());
        }
    }
}
