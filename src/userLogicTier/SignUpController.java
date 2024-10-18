package userLogicTier;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpController {

    // 
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfAddess;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfZip;

    @FXML
    private Button btnSignUp;

    @FXML
    private Hyperlink hlSignIn;

    @FXML
    private Label lblError;

    @FXML
    private CheckBox cbActive;

    @FXML
    @SuppressWarnings("Convert2Lambda")
    public void initialize() {
        lblError.setText("");

        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SignUp();
            }
        });

    }

    private void SignUp() {
        String name = tfName.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        String address = tfAddess.getText();
        String city = tfCity.getText();
        String zip = tfZip.getText();

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
