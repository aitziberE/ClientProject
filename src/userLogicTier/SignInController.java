/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import exceptions.InactiveUserException;
import exceptions.ServerException;
import exceptions.UserCapException;
import exceptions.UserCredentialException;
import java.io.IOException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import userLogicTier.model.User;

/**
 * Controller class for the Sign In screen. 
 * Manages user authentication, navigation actions, and input validation.
 * This class is responsible for handling user interactions on the sign-in screen.
 * It includes:
 * - Field validation methods
 * - Password visibility toggle
 * - Sign-in logic with error handling for various exceptions
 * 
 * @see userLogicTier.model.User
 * @see javafx.beans.value.ObservableValue
 * @see javafx.event.ActionEvent
 * @see java.util.logging.Logger
 * 
 * @author Ander
 * @author Aitziber
 */
public class SignInController {

    /**
     * Logger to track the class activity and handle debugging information.
     */
    private static final Logger logger = Logger.getLogger(SignInController.class.getName());

    /**
     * TextField for entering the username, expected to be an email.
     */
    @FXML
    private TextField tfUsername;

    /**
     * PasswordField for entering the password in a concealed form.
     */
    @FXML
    private PasswordField pfPasswd;

    /**
     * TextField for showing the password in plain text when visibility is toggled.
     */
    @FXML
    private TextField tfPasswd;

    /**
     * Button to toggle the visibility of the password.
     */
    @FXML
    private Button btnShowPassword;

    /**
     * Button to initiate the sign-in process.
     */
    @FXML
    private Button btnSignIn;

    /**
     * Label to display error messages, such as input validation issues or login failures.
     */
    @FXML
    private Label lblError;

    /**
     * Hyperlink to navigate to the Sign Up screen.
     */
    @FXML
    private Hyperlink hlSignUp;
    
    private Signable signable = null;

    /**
     * Initializes the controller by setting up event listeners and button properties. This method is automatically called after the FXML file is loaded.
     */
    public void initialize() {
        logger.log(Level.INFO, "Initilizing sign in controller");
        
        signable = ClientFactory.getSignable();
        
        tfUsername.focusedProperty().addListener(this::handleTfUsernameFocusProperyLost);
        btnSignIn.setOnAction(this::handleSignInButtonAction);
        hlSignUp.setOnAction(this::handleSignUpHyperlinkAction);

        // Set the "Sign In" button as the default
        btnSignIn.setDefaultButton(true);
        // Button for showing password
        btnShowPassword.armedProperty().addListener(this::handleButtonPasswordVisibility);
    }

    /**
     * Validates the email format in the username field when it loses focus. Disables the Sign In button if the email format is incorrect.
     *
     * @param observable the observable property of the TextField's focus.
     * @param oldValue the previous focus state.
     * @param newValue the new focus state.
     */
    private void handleTfUsernameFocusProperyLost(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (oldValue) {
            String email = tfUsername.getText();
            Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                lblError.setText("Incorrect email format");
                btnSignIn.setDisable(true);
            } else {
                lblError.setText("");
                btnSignIn.setDisable(false);
            }
        }
    }

    /**
     * Toggles the visibility of the password between a concealed PasswordField and a plain text TextField. Called when the "Show Password" button is pressed or released.
     *
     * @param observable the observable property of the button's armed status.
     * @param oldValue the previous armed status.
     * @param newValue the new armed status.
     */
    private void handleButtonPasswordVisibility(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            // Show the password when pressed
            String password = pfPasswd.getText();
            pfPasswd.setVisible(false);
            tfPasswd.setText(password);
            tfPasswd.setVisible(true);
        } else {
            // Hide the password when released
            tfPasswd.setVisible(false);
            pfPasswd.setVisible(true);
        }
    }

    /**
     * Handles the Sign In button action, validating the fields and performing user authentication. Displays appropriate error messages or navigates to the home screen upon successful authentication.
     *
     * @param actionEvent Action event triggered by the Sign In button.
     */
    private void handleSignInButtonAction(ActionEvent actionEvent) {
        String username = tfUsername.getText();
        String password = pfPasswd.getText();

        try {
            if (username.isEmpty() || password.isEmpty()) {
                throw new EmptyFieldsException("Please fill out all fields.");
            } else {
                lblError.setText("");
                User user = new User(username, password);
                user = signable.signIn(user);
                // Successfully authenticated; proceed to home screen
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                WindowManager.openWindow("/userInterfaceTier/Home.fxml", "Home", user);
            }
        } catch (UserCredentialException ex) {
            lblError.setText("Incorrect username or password.");
            logger.log(Level.WARNING, null, ex);
        } catch (ServerException ex) {
            showErrorAlert("Server error", "There was an error on the server, please contact support.");
            logger.log(Level.WARNING, null, ex);
        } catch (InactiveUserException ex) {
            showErrorAlert("User error", "Your account is deactivated, please contact support.");
            logger.log(Level.WARNING, null, ex);
        } catch (UserCapException ex) {
            showErrorAlert("Capacity limit", "Cannot process request, please try again later.");
            logger.log(Level.WARNING, null, ex);
        } catch (EmptyFieldsException ex) {
            lblError.setText(ex.getMessage());
            logger.log(Level.WARNING, null, ex);
        }
    }

    /**
     * Opens the Sign Up screen when the Sign Up hyperlink is clicked, prompting for confirmation.
     *
     * @param actionEvent action event triggered by the Sign Up hyperlink.
     */
    public void handleSignUpHyperlinkAction(ActionEvent actionEvent) {
        try {

                ((Node) actionEvent.getSource()).getScene().getWindow().hide();

                FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/userInterfaceTier/SignUp.fxml"));
                Parent mainView = FXMLLoader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.getIcons().add(new Image("resources/logo.png"));
                stage.setTitle("SignUp");
                stage.setScene(new Scene(mainView));
                stage.show();
           
        } catch (IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    /**
     * Helper method to display an error alert with a custom title and message.
     *
     * @param title title of the alert dialog.
     * @param message message content of the alert dialog.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
