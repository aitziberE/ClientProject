/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import userLogicTier.model.User;

/**
 *
 * @author Ander
 */
public class SignInController {

    private Stage stage;

    //private static final Logger logger = Logger.getLogger(SignInController.class.getName());
    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfPassword;

    @FXML
    private Button btnShowPassword;

    @FXML
    private Button btnSignIn;

    @FXML
    private Label lblError;

    @FXML
    private Hyperlink hlSignUp;

    public void initialize() {
        tfUsername.focusedProperty().addListener(this::handleTfUsernameFocusProperyLost);
        btnSignIn.setOnAction(this::handleSignInButtonAction);
        hlSignUp.setOnAction(this::handleSignUpHyperlinkAction);

        // Establecer el botón de "Sign In" como predeterminado
        btnSignIn.setDefaultButton(true);
        // Botón de mostrar contraseña
        // Agrega el listener a armedProperty
        btnShowPassword.armedProperty().addListener(this::handleButtonPasswordVisibility);
    }

    private void handleTfUsernameFocusProperyLost(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        // Solo se ejecuta cuando se pierde el foco
        if (oldValue) {
            String email = tfUsername.getText();
            Pattern modelo = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
            Matcher matcher = modelo.matcher(email);

            if (!matcher.matches()) {
                lblError.setText("Incorrect email format");
                btnSignIn.setDisable(true);
            } else {
                lblError.setText("");
                btnSignIn.setDisable(false);
            }

        }
    }

    private void handleButtonPasswordVisibility(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            // Mostrar la contraseña cuando se presiona
            String password = pfPassword.getText();
            pfPassword.setVisible(false);
            tfPassword.setText(password);
            tfPassword.setVisible(true);
        } else {
            // Ocultar la contraseña cuando se suelta
            tfPassword.setVisible(false);
            pfPassword.setVisible(true);
        }
    }

    private void handleSignInButtonAction(ActionEvent actionEvent) {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Please fill out all fields.");
        } else {
            lblError.setText("");
            User user = new User(username, password);
            try {
                ClientFactory.getSignable().signIn(user);
            } catch (SQLException ex) {
                Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
            }
            User testUser = new User("test@jmail.com", "test");
            if (testUser.getEmail().equals(username) && testUser.getPassword().equals(password)) { //TODO Remove after window testing
                try {
                    ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                    WindowManager.openWindow("/userInterfaceTier/Home.fxml", "Home", user);
                } catch (Exception e) {
                    lblError.setText("Error opening Home window");
                    e.printStackTrace();
                }
            } else {
                lblError.setText("ERROR");
            }
        }
    }

    public void handleSignUpHyperlinkAction(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("You are about to exit");
            alert.setContentText("Are you sure you want to leave the sign in window and open the sign up window?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();

                FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/userInterfaceTier/SignUp.fxml"));
                Parent mainView = FXMLLoader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("SignUp");
                stage.setScene(new Scene(mainView));
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
