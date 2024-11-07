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
 * Controller class for the Home view.
 *
 * Manages user interaction within the Home view, handling user data display, logout operations, and contextual menu actions.
 * Provides functions for displaying user information, confirming logouts, and managing various input events.
 * 
 * This class is linked to the FXML Home view file, and it is initialized automatically when the view is loaded.
 * 
 * Usage Example:
 * 
 *     HomeController controller = new HomeController();
 *     controller.setUser(user);
 * 
 * @see User
 * @see WindowManager
 * 
 * @author Aitziber
 * @author Ander
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

    /**
     * Sets the user for the Home view and updates the displayed information.
     *
     * @param user the {@link User} object containing the user data to be displayed
     */
    void setUser(User user) {
        this.user = user;
        updateUserInfo();
    }

    /**
     * Initializes the controller class.
     *
     * Sets up event handlers for mouse and keyboard interactions, configures the contextual menu, 
     * and logs the initialization process.
     *
     */
    @FXML
    public void initialize() {
        logger.log(Level.INFO, "Initializing Home...");

        try {
            anchorPane.setOnMouseClicked(this::handleMouseClicked);
            anchorPane.setOnKeyPressed(this::handleKeyPress);

            btnLogOut.setOnAction(this::handleLogOutButtonAction);

            // Set the "Log out" button as the default button
            btnLogOut.setDefaultButton(true);

            configureContextMenu();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during initialization", e);
        }
    }

    /**
     * Updates the displayed user information based on the provided {@link User} data.
     */
    private void updateUserInfo() {
        try {
            logger.log(Level.INFO, "Updating user info");

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
            return (testData != null && !testData.trim().isEmpty()) ? testData : "unknown";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error validating string data", e);
            return "unknown";
        }
    }

    private int userDataExists(int testData) {
        logger.log(Level.INFO, "Validating data...");
        try {
            return (testData > 0) ? testData : 00000;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error validating numeric data", e);
            return 0;
        }
    }

    /**
     * Handles the logout button action, displaying a confirmation dialog and performing logout if confirmed.
     *
     * @param actionEvent the action event triggered by pressing the logout button
     */
    private void handleLogOutButtonAction(ActionEvent actionEvent) {
        logger.log(Level.INFO, "Log out action initiated");

        if (showLogoutConfirmation()) {
            try {
                clearUserFields();

                Stage home = (Stage) btnLogOut.getScene().getWindow();
                home.close();
                WindowManager.openWindow("/userInterfaceTier/SignIn.fxml", "SignIn");

                logger.log(Level.INFO, "Successfully navigated to Sign In screen.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Unexpected error during logout process.", e);
            }
        } else {
            logger.log(Level.INFO, "Log out cancelled by user.");
        }
    }

    /**
     * Displays a confirmation dialog for the logout action.
     *
     * @return {@code true} if the user confirms the logout, {@code false} otherwise
     */
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
            return false;
        }
    }

    /**
     * Clears user information fields on the view.
     */
    private void clearUserFields() {
        logger.log(Level.INFO, "Cleaning user fields");

        try {
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
        logger.log(Level.SEVERE, "Clicked on anchor pane");
        try {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(anchorPane, event.getScreenX(), event.getScreenY());
                logger.log(Level.SEVERE, "Context menu shown");
            } else {
                contextMenu.hide();
                logger.log(Level.SEVERE, "Context menu hidden");
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

                double menuX = home.getX() + (homeWidth / 2) - (contextMenu.getWidth() / 2);
                double menuY = home.getY() + (homeHeight / 2) - (contextMenu.getHeight() / 2);

                contextMenu.show(anchorPane, menuX, menuY);
                event.consume();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error handling key press", e);
        }
    }

    /**
     * Configures the context menu with options, including a "Log Out" item.
     */
    private void configureContextMenu() {
        try {
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

    void start(Stage stage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
