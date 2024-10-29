package userLogicTier;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.input.MouseEvent;
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

        //Borrar texto en lblError
        lblError.setText("");
        // Cuando pierden el foco
        tfEmail.focusedProperty().addListener(this::handleFocusPropertyLostEmail);
        tfZip.focusedProperty().addListener(this::handleFocusProperyLostZip);

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

    //Al perder el foco del campo de texto, se realiza una comprobación para determinar si el texto introducido cumple con el formato requerido (email@dominio.extensión).
    //Si no es válido, producir una IllegalFormatException que se atrapará al final de esta respuesta para mostrar el mensaje “Introduzca un email válido”.
    private void handleFocusPropertyLostEmail(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (oldValue) {  // Solo se ejecuta cuando se pierde el foco
            String email = tfEmail.getText();
            // Patrón para validar el formato de email
            Pattern modelo = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
            Matcher matcher = modelo.matcher(email);
            if (!matcher.matches()) {
                lblError.setText("Incorrect email format");
                btnSignUp.setDisable(true);
                // Si el email no cumple con el formato, se lanza la excepción
            } else {
                lblError.setText("");
                btnSignUp.setDisable(false);
            }
        }
    }

    //Al perder el foco del campo de texto, validar que el campo contiene un valor java.lang.Integer con una longitud de 5 caracteres.
    //Si no es válido, producir una NumberFormatException que se atrapará al final de esta respuesta para mostrar el mensaje “Introduzca un código postal válido”.
    private void handleFocusProperyLostZip(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (oldValue) {
            String zipCode = tfZip.getText();

            // Verificar que el valor pueda ser convertido a un Integer y tenga 5 caracteres
            if (zipCode.length() != 5 || !zipCode.matches("\\d+")) {
                lblError.setText("Write a valid 5 digit ZIP");
                btnSignUp.setDisable(true);
            } else {
                lblError.setText("");
                btnSignUp.setDisable(false);
            }
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

    //Si falta alguno por rellenar, lanzar una excepción con el mensaje “Por favor, rellene todos los campos para completar el registro”.
    //Crear un objeto Usuario y establecer sus propiedades name, login, password, street, city, zip, active. Los valores provienen de los siguientes campos:
    //Name: campo Name
    //Login: campo Email
    //Password: campo Password
    //Street: campo Address
    //City: campo City
    //ZIP: campo ZIP
    //Llamar al método de lógica signUp pasándole el objeto creado en el punto anterior.
    //Si se produce alguna excepción en esta respuesta atraparla y mostrar un mensaje de alerta con el mensaje de la excepción.
    private void handleSignUpButtonAction(ActionEvent actionEvent) {

        //Comprobar que todo esté lleno
        if (tfName.getText().isEmpty() || tfEmail.getText().isEmpty() || pfPassword.getText().isEmpty() || tfAddress.getText().isEmpty() || tfCity.getText().isEmpty() || tfZip.getText().isEmpty()) {
            lblError.setText("Please fill out all fields");
            //Se pasan los valores a strings y boolean
        } else {
            // Compruebo de nuevo que el zip esté bien puesto (Puede entrar sin haberlo puesto bien)
            String zipCode = tfZip.getText();
            if (zipCode.length() != 5 || !zipCode.matches("\\d+")) {
                lblError.setText("Write a valid 5 digit ZIP");
            } else {
                // Lo mismo con el email
                String email = tfEmail.getText();
                // Patrón para validar el formato de email
                Pattern modelo = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
                Matcher matcher = modelo.matcher(email);
                if (!matcher.matches()) {
                    lblError.setText("Incorrect email format");
                    // Si el email no cumple con el formato, se lanza la excepción
                } else {
                    // Si todo es correcto procede
                    String name = tfName.getText();
                    String password = tfPassword.getText();
                    String address = tfAddress.getText();
                    String city = tfCity.getText();
                    boolean isActive = cbActive.isSelected();

                    // Creamos el usuario pasando los datos
                    User user = new User(name, email, password, address, city, zipCode, isActive);
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
        }
    }
}
