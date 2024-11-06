/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import userLogicTier.model.User;

/**
 *
 * @author inifr
 * @author Aitziber
 */
public class WindowManager {
 
    public static void openWindow(String fxmlFilePath, String title, User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image("resources/logo.png"));
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            
        }
    }
    
    public static void openWindow(String fxmlFilePath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.getIcons().add(new Image("resources/logo.png"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            
        }
    }
}