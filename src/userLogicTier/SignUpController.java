package userLogicTier;

import java.io.IOException;
import java.util.Optional;
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
 *
 * @author Pablo
 * @author Ander
 * @author Aitziber
 */
public class SignUpController {

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfZip;

    @FXML
    private Button btnSignUp;

    @FXML
    private Hyperlink hlSignIn;

    @FXML
    private Button btnShowPassword;

    @FXML
    private Label lblError;

    @FXML
    private CheckBox cbActive;

    @FXML
    private PasswordField pfPassword;

    public void initialize() {
        System.out.println("initializing...");
        
        btnSignUp.setDisable(true);

        //Borrar texto en lblError
        lblError.setText("");
        // Cuando pierden el foco. los añado todos para hacer las validaciones antes de activar el btn
        tfName.focusedProperty().addListener(this::handleFocusLost);
        tfEmail.focusedProperty().addListener(this::handleFocusLost);
        tfPassword.focusedProperty().addListener(this::handleFocusLost);
        tfAddress.focusedProperty().addListener(this::handleFocusLost);
        tfCity.focusedProperty().addListener(this::handleFocusLost);
        tfZip.focusedProperty().addListener(this::handleFocusLost);
        
        // aplicar tambien .textProperty().addListener()? o quedarnos solo con la perdida de foco

        // Cuando se pulsan
        // El boton solo se pulsa si le das en el borde superior muy justo
        btnSignUp.setOnAction(this::handleSignUpButtonAction);
        hlSignIn.setOnAction(this::handleSignInHyperLinkAction);

        // Establecer el botón de "Sign Up" como predeterminado
        btnSignUp.setDefaultButton(true);
        // Botón de mostrar contraseña
        // Agrega el listener a armedProperty
        btnShowPassword.armedProperty().addListener(this::handleButtonPasswordVisibility);

    }
    
    private void handleFocusLost(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (oldValue) {
            // validar email
            if (!tfEmail.getText().isEmpty()) {
                if (!validateEmail()) {
                    lblError.setText("Incorrect email format");
                    btnSignUp.setDisable(true);
                    return;
                } else {
                    lblError.setText("");
                }
            }

            //validar zip
            if (!tfZip.getText().isEmpty()) {
                if (!validateZip()) {
                    lblError.setText("Write a valid 5 digit ZIP");
                    btnSignUp.setDisable(true);
                    return;
                } else {
                    lblError.setText("");
                }
            }
            
            //validar contraseña
            if (!tfPassword.getText().isEmpty()) {
                if (!validatePassword()) {
                    lblError.setText("Password must contain at least 8 characters");
                    btnSignUp.setDisable(true);
                    return;
                } else {
                    lblError.setText("");
                }
            }

            //comprobar que todos los campos estén completados
            if (tfName.getText().isEmpty() || tfEmail.getText().isEmpty() || tfPassword.getText().isEmpty() || tfAddress.getText().isEmpty() || tfCity.getText().isEmpty() || tfZip.getText().isEmpty()) {
                btnSignUp.setDisable(true);
            } else {
                btnSignUp.setDisable(false);
            }
        }
    }
    
    public boolean validateZip(){
        return tfZip.getText().matches("^\\d{5}$");
    }
    
    public boolean validateEmail(){
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
    
    public boolean validatePassword(){
        return tfPassword.getText().matches("^.{8,}$");
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
    
    
    //Pedir confirmación al usuario para salir de la ventana SignUp y abrir la ventana SignIn:
    //Si el usuario confirma, se redirigirá a la ventana SignIn.
    //Si no confirma, mantenerse en la ventana.
    private void handleSignInHyperLinkAction(ActionEvent actionEvent) {

        // Crear un cuadro de diálogo de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You are about to exit");
        alert.setContentText("Are you sure you want to leave the registration window and return to the Sign In window?");

        // Obtener la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();

        // Si el usuario confirma la salida
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Cerrar la ventana actual de SignUp
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                // Abrir la ventana de SignIn
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/userInterfaceTier/SignIn.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("SignIn");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                lblError.setText("Error opening Sign In window");
            }

        }
        // Si el usuario cancela, no hacemos nada y permanecemos en la ventana actual
    }

    private void handleSignUpButtonAction(ActionEvent actionEvent) {
        // Creamos el usuario pasando los datos
        User user = new User(tfName.getText().trim(), tfEmail.getText().trim(), tfPassword.getText().trim(), tfAddress.getText().trim(), tfCity.getText().trim(), tfZip.getText().trim(), cbActive.isSelected());
        // Llamamos al metodo sign Up del cliente que implementa signable y pasa por la factoría
        ClientFactory.getSignable().signUp(user);

        try {
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            WindowManager.openWindow("/userInterfaceTier/SignIn.fxml", "SignIn", user);
        } catch (Exception e) {
            lblError.setText("Error opening SignIn window");
            e.printStackTrace();
        }
    }
}
