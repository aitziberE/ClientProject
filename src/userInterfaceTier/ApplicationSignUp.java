/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterfaceTier;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main application class responsible for initializing and displaying the Sign Up window.
 * This class extends {@link javafx.application.Application} and overrides the {@code start} method
 * to set up the primary stage with the Sign Up interface.
 *
 * @author Aitziber
 * @author Ander
 */
public class ApplicationSignUp extends javafx.application.Application {
    
    /**
     * Starts the application by setting up the primary stage.
     * Loads the SignUp FXML layout, applies the stylesheet, and displays the stage to the user.
     *
     * @param stage the primary stage for this application
     * @throws Exception if an error occurs during loading of the FXML file
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sign Up");
        stage.show();
    }

    /**
     * The main entry point for the application.
     * Launches the application with the given command line arguments.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
