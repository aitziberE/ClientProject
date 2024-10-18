package userLogicTier;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField zipField;

    @FXML
    private Button signUpButton;

    @FXML
    private Hyperlink signInLink;

    @FXML
    private Label lblError;

    @FXML
    public void initialize() {
        lblError.setText("");
        
        signUpButton.setOnAction(event -> SignUp());

        signInLink.setOnAction(event -> SignInRedirect());
    }

    private void SignUp() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String zip = zipField.getText();

        // Validar que todos los campos estén llenos
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || city.isEmpty() || zip.isEmpty()) {
            lblError.setText("Please fill out all fields.");
            return;
        }
        
        if (!email.contains("@")) {
            lblError.setText("Please enter a valid email address.");
            return;
        }
                
    }

    // Método que redirige a la ventana de "Sign In"
    private void SignInRedirect() {
        
    }
}
