/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import userLogicTier.model.User;

/**
 * FXML Controller class
 *
 * @author Aitziber
 */
public class HomeController {

    private static final Logger logger = Logger.getLogger(HomeController.class.getName());

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Button btnLogOut;

    @FXML
    private Text lblUserName;

    @FXML
    private Text lblUserEmail;

    @FXML
    private Text lblUserStreet;

    @FXML
    private Text lblUserZip;

    @FXML
    private Text lblUserCity;

    private User user;

    private Stage stage;

    void setUser(User user) {
        this.user = user;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        logger.log(Level.INFO, "Initializing Home...");

        btnLogOut.setOnAction(this::handleLogOutButtonAction);

        if (user != null) {
            updateUserInfo();
        }
    }

    private void updateUserInfo() {
        try {
            logger.log(Level.INFO, "Updating user info");

            // Verificar si los datos existen antes de mostrarlos
            lblUserName.setText(userDataExists(user.getName()));
            lblUserEmail.setText(userDataExists(user.getEmail()));
            lblUserStreet.setText(userDataExists(user.getStreet()));
            lblUserZip.setText(userDataExists(user.getZip()));
            lblUserCity.setText(userDataExists(user.getCity()));

            logger.log(Level.INFO, "Successful user info update");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating user info", e);
        }
    }

    private String userDataExists(String testData) {
        logger.log(Level.INFO, "Validating data...");
        // Si el dato no existe o está vacío, devolver "unknown"
        return (testData != null && !testData.trim().isEmpty()) ? testData : "unknown";

    }

    private int userDataExists(int testData) {
        logger.log(Level.INFO, "Validating data...");
        // Comprobar que el número no sea negativo o cero, devolver "00000" si es así
        return (testData > 0) ? testData : 00000;
    }

    private void handleLogOutButtonAction(ActionEvent actionEvent) {
        logger.log(Level.INFO, "Log out action initiated");
        try {
            // Limpiar campos del usuario antes de cerrar sesión
            clearUserFields();

            // Cargar la ventana de Sign In
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/userInterfaceTier/SignIn.fxml"));
            Parent root = loader.load();

            // Crear una nueva ventana para la pantalla de inicio de sesión
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Sign In");
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana Home
            Stage home = (Stage) btnLogOut.getScene().getWindow();
            home.close();

            logger.log(Level.INFO, "Successfully navigated to SignIn screen");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while trying to load SignIn screen", e);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unexpected error during logout process", ex);
        }
    }

    // Vacía los campos que pudiesen contener información del usuario
    private void clearUserFields() {
        logger.log(Level.INFO, "Cleaning user fields");

        try {
            // escalable pero más lento :(
//        for (Node node : AnchorPane.getChildren()) {
//            // Verifica si el nodo es un Text y contiene "lblUser" en su fx:id
//            if (node instanceof Text && ((Text) node).getId() != null && ((Text) node).getId().contains("lblUser")) {
//                ((Text) node).setText("");
//            }
//        }
            lblUserName.setText("");
            lblUserEmail.setText("");
            lblUserStreet.setText("");
            lblUserZip.setText("");
            lblUserCity.setText("");

            logger.log(Level.INFO, "Successful user field cleanup");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error cleaning user fields", e);
        }
    }
}
