/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import userLogicTier.model.User;
//import java.util.Iterator;
//import javafx.scene.Node;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Aitziber
 */
public class HomeController {

    private static final Logger logger = Logger.getLogger(HomeController.class.getName());
    private final ContextMenu contextMenu = new ContextMenu();
    
    @FXML
    private AnchorPane anchorPane;

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

    void setUser(User user) {
        this.user = user;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        logger.log(Level.INFO, "Initializing Home...");
        
       try {
            anchorPane.setOnMouseClicked(this::handleMouseClicked);
            anchorPane.setOnKeyPressed(this::handleKeyPress);
        
            btnLogOut.setOnAction(this::handleLogOutButtonAction);
        
            // Establecer el botón de "Log out" como predeterminado
            btnLogOut.setDefaultButton(true);
        
            configureContextMenu();
        
            if (user != null) {
                updateUserInfo();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during initialization", e);
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
         try {
            // Si el dato no existe o está vacío, devolver "unknown"
            return (testData != null && !testData.trim().isEmpty()) ? testData : "unknown";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error validating string data", e);
            // si se produce error también devuelve unknown
            return "unknown";
        }
    }

    private int userDataExists(int testData) {
        logger.log(Level.INFO, "Validating data...");
        try {
            // Comprobar que el número no sea negativo o cero, devolver "00000" si es así
            return (testData > 0) ? testData : 00000;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error validating numeric data", e);
            // si se produce error también devuelve 0
            return 0;
        }
    }

    private void handleLogOutButtonAction(ActionEvent actionEvent) {
        logger.log(Level.INFO, "Log out action initiated");

        // Mostrar el cuadro de diálogo de confirmación
        if (showLogoutConfirmation()) {
            try {
                // Limpiar campos del usuario antes de cerrar sesión
                clearUserFields();

                // Cerrar la ventana Home
                Stage home = (Stage) btnLogOut.getScene().getWindow();
                home.close();
                WindowManager.openWindow("/userInterfaceTier/SignIn.fxml", "SignIn", user);

                logger.log(Level.INFO, "Successfully navigated to Sign In screen.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Unexpected error during logout process.", e);
            }
        } else {
            logger.log(Level.INFO, "Log out cancelled by user.");
        }
    }

    // Muestra alert de confirmación y devuelve respuesta positiva en caso de que el usuario seleccione OK
    private boolean showLogoutConfirmation() {
          try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Log Out");
            alert.setHeaderText("This window will be closed.");
            alert.setContentText("Are you sure you want to log out?");

            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error showing logout confirmation", e);
            // si se produce error no cierra sesión
            return false;
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

    private void handleMouseClicked(MouseEvent event) {
// añadir logger? demasiada información igual        
        try {
            // al hacer click derecho
            if (event.getButton() == MouseButton.SECONDARY) {
                // desplegar el menú contextual en la posición del cursor
                contextMenu.show(anchorPane, event.getScreenX(), event.getScreenY());
            } else {
                // si se presiona otro botón, ocultar el menú contextual
                contextMenu.hide();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error handling mouse click", e);
        }
    }
    
    private void handleKeyPress(KeyEvent event) {
         try {
            if (event.getCode() == KeyCode.F10 && event.isShiftDown()) {
                Stage home = (Stage) anchorPane.getScene().getWindow();
                double homeWidth = home.getWidth();
                double homeHeight = home.getHeight();
                
                // recoge las coordenadas de la ventana y calcula el centro de cada eje
                double menuX = home.getX() + (homeWidth / 2) - (contextMenu.getWidth() / 2);
                double menuY = home.getY() + (homeHeight / 2) - (contextMenu.getHeight() / 2);
                
                // muestra el menú contextual en el centro de la ventana
                contextMenu.show(anchorPane, menuX, menuY);
                event.consume(); 
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error handling key press", e);
        }
    }
    
     private void configureContextMenu() {
        try {
            // añadir elemento LogOut al menú contextual
            MenuItem itemLogOut = new MenuItem("Log Out");
            itemLogOut.setOnAction((ActionEvent e) -> {
                handleLogOutButtonAction(e);
            });
        
            contextMenu.getItems().addAll(itemLogOut);
            logger.log(Level.INFO, "Context menu configured successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error configuring context menu", e);
        }
    }
}
