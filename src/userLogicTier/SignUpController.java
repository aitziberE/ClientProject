package userLogicTier;

import exceptions.ExistingUserException;
import exceptions.ServerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import userLogicTier.model.User;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Controller class for the Sign Up screen. Manages user registration, input validation, and navigation actions. This class is responsible for handling user interactions on the sign-up screen.
 * <p>
 * It includes:
 * <ul>
 * <li>Field validation methods</li>
 * <li>Password visibility toggle</li>
 * <li>Sign-up logic with error handling for various exceptions</li>
 * </ul>
 * </p>
 *
 * @see userLogicTier.model.User
 * @see javafx.beans.value.ObservableValue
 * @see javafx.event.ActionEvent
 * @see java.util.logging.Logger
 *
 * @authors Pablo
 * @authors Ander
 * @authors Aitziber
 */
public class SignUpController {

    /**
     * Logger to track the class activity and handle debugging information.
     */
    private static final Logger logger = Logger.getLogger(SignUpController.class.getName());

    /**
     * TextField for user name input.
     */
    @FXML
    private TextField tfName;

    /**
     * TextField for user email input.
     */
    @FXML
    private TextField tfEmail;

    /**
     * TextField for displaying the plain text password.
     */
    @FXML
    private TextField tfPassword;

    /**
     * TextField for user address input.
     */
    @FXML
    private TextField tfAddress;

    /**
     * TextField for user city input.
     */
    @FXML
    private TextField tfCity;

    /**
     * TextField for user ZIP code input.
     */
    @FXML
    private TextField tfZip;

    /**
     * Button to submit registration data.
     */
    @FXML
    private Button btnSignUp;

    /**
     * Hyperlink to navigate to the Sign In screen.
     */
    @FXML
    private Hyperlink hlSignIn;

    /**
     * Button to toggle password visibility.
     */
    @FXML
    private Button btnShowPassword;

    /**
     * Label to display error messages.
     */
    @FXML
    private Label lblError;

    /**
     * CheckBox to set the user's active status.
     */
    @FXML
    private CheckBox cbActive;

    /**
     * PasswordField for password input (hidden by default).
     */
    @FXML
    private PasswordField pfPassword;

    /**
     * Initializes the controller by setting up event listeners and button properties. This method is automatically called after the FXML file is loaded. It configures the default state of the sign-up fields and assigns listeners for validation and actions.
     */
    public void initialize() {
        logger.log(Level.INFO, "Initializing SignUpController...");

        btnSignUp.setDisable(true);
        lblError.setText("");

        // Add listeners for input field focus loss to validate data before enabling the SignUp button
        tfName.focusedProperty().addListener(this::handleFocusLost);
        tfEmail.focusedProperty().addListener(this::handleFocusLost);
        tfPassword.focusedProperty().addListener(this::handleFocusLost);
        tfAddress.focusedProperty().addListener(this::handleFocusLost);
        tfCity.focusedProperty().addListener(this::handleFocusLost);
        tfZip.focusedProperty().addListener(this::handleFocusLost);

        // !!!!!!!!!!!!!! El boton solo se pulsa si le das en el borde superior muy justo !!!!!!!!!!!!!
        btnSignUp.setOnAction(this::handleSignUpButtonAction);
        hlSignIn.setOnAction(this::handleSignInHyperLinkAction);

        // Set the "Sign Up" button as the default button
        btnSignUp.setDefaultButton(true);
        // Toggle password visibility button listener
        btnShowPassword.armedProperty().addListener(this::handleButtonPasswordVisibility);

    }

    /**
     * Validates input fields when they lose focus. Enables the Sign Up button if all required fields are valid.
     *
     * @param observable the observable property of the input field's focus.
     * @param oldValue the previous focus state.
     * @param newValue the new focus state.
     */
    private void handleFocusLost(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (oldValue) {
            // Validate email format
            if (!tfEmail.getText().isEmpty()) {
                if (!validateEmail()) {
                    lblError.setText("Incorrect email format");
                    btnSignUp.setDisable(true);
                    return;
                } else {
                    lblError.setText("");
                }
            }

            // Validate zip code format
            if (!tfZip.getText().isEmpty()) {
                if (!validateZip()) {
                    lblError.setText("Write a valid 5 digit ZIP");
                    btnSignUp.setDisable(true);
                    return;
                } else {
                    lblError.setText("");
                }
            }

            // Validate password length
            if (!pfPassword.getText().isEmpty()) {
                if (!validatePassword()) {
                    lblError.setText("Password must contain at least 8 characters");
                    btnSignUp.setDisable(true);
                    return;
                } else {
                    lblError.setText("");
                }
            }

            // Check that all fields are completed
            if (tfName.getText().isEmpty() || tfEmail.getText().isEmpty() || pfPassword.getText().isEmpty() || tfAddress.getText().isEmpty() || tfCity.getText().isEmpty() || tfZip.getText().isEmpty()) {
                btnSignUp.setDisable(true);
            } else {
                btnSignUp.setDisable(false);
            }
        }
    }

    /**
     * Validates the ZIP code format (must be exactly 5 digits).
     *
     * @return true if the ZIP code format is valid, false otherwise.
     */
    public boolean validateZip() {
        return tfZip.getText().matches("^\\d{5}$");
    }

    /**
     * Validates the email format.
     *
     * @return true if the email format is valid, false otherwise.
     */
    public boolean validateEmail() {
        boolean correct = false;
        String email = tfEmail.getText();
        // Patrón para validar el formato de email
        Pattern modelo = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        Matcher matcher = modelo.matcher(email);
        if (matcher.matches()) {
            correct = true;
        }
        return correct;
    }

    /**
     * Validates the password length (must be at least 8 characters).
     *
     * @return true if the password meets the minimum length requirement, false otherwise.
     */
    public boolean validatePassword() {
        return pfPassword.getText().matches("^.{8,}$");
    }

    /**
     * Toggles the visibility of the password between plain text and hidden.
     *
     * @param observable the observable property of the button.
     * @param oldValue the previous state of the button.
     * @param newValue the new state of the button.
     */
    private void handleButtonPasswordVisibility(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            // Show the password when pressed
            String password = pfPassword.getText();
            pfPassword.setVisible(false);
            tfPassword.setText(password);
            tfPassword.setVisible(true);
            logger.log(Level.INFO, "Password visibility toggled to show");
        } else {
            // Hide the password when released
            tfPassword.setVisible(false);
            pfPassword.setVisible(true);
            logger.log(Level.INFO, "Password visibility toggled to hyde");
        }
    }

    /**
     * Handles the action of the Sign In hyperlink. Prompts for confirmation to leave the registration screen and navigates to the Sign In screen if confirmed.
     *
     * @param actionEvent the action event triggered by clicking the hyperlink.
     */
    private void handleSignInHyperLinkAction(ActionEvent actionEvent) {

        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You are about to exit");
        alert.setContentText("Are you sure you want to leave the registration window and return to the Sign In window?");
        Optional<ButtonType> result = alert.showAndWait();

        // If the user confirms the exit
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Close the current SignUp window
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                logger.log(Level.INFO, "Closing SignUp window");

                // Open the SignIn window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/userInterfaceTier/SignIn.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("SignIn");
                stage.setScene(new Scene(root));
                stage.show();
                logger.log(Level.INFO, "Opened SignIn window");
            } catch (IOException ex) {
                lblError.setText("Error opening Sign In window");
            }
        }
    }

    /**
     * Handles the action of the Sign Up button. Creates a user, attempts registration, and navigates to the Sign In screen on success.
     *
     * @param actionEvent the action event triggered by clicking the Sign Up button.
     * @throws ExistingUserException if the user already exists.
     * @throws ServerException if a server error occurs during registration.
     */
    private void handleSignUpButtonAction(ActionEvent actionEvent) {
        // Create the user by passing the data
        tfPassword.setText(pfPassword.getText());
        User user = new User(tfName.getText().trim(), tfEmail.getText().trim(), tfPassword.getText().trim(), tfAddress.getText().trim(), tfCity.getText().trim(), tfZip.getText().trim(), cbActive.isSelected());
        logger.log(Level.INFO, "Creating user");
        // Call the signUp method of the client that implements signable and goes through the factory
        try {
            ClientFactory.getSignable().signUp(user);
            logger.log(Level.INFO, "User signed up successfully");

            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            WindowManager.openWindow("/userInterfaceTier/SignIn.fxml", "SignIn");
        } catch (ExistingUserException ex) {
            lblError.setText("User already exists");
            logger.log(Level.INFO, "Error during SignUp:", ex.getMessage());
        } catch (ServerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Server error");
            alert.setContentText("There was an error in the server, please contact the responsible technician");
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
