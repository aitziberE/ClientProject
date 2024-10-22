package userLogicTier;

import java.io.IOException;
import java.util.IllegalFormatException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

   public void setStage(Stage stage) {
        //Establecer el título de la ventana al valor “SignUp”.
        stage.setTitle("Sign Up");

        //La ventana no debe ser redimensionable
        stage.setResizable(false);

        //Mostrar la ventana.
        stage.show();

        //Borrar texto en lblError
        lblError.setText("");
    }

    public void initialize() {
        System.out.println("initializing...");

        // Cuando pierden el foco
        tfEmail.focusedProperty().addListener(this::handleFocusPropertyLostEmail);
        tfZip.focusedProperty().addListener(this::handleFocusProperyLostZip);

        // Cuando se pulsan
        // El fokin boton solo se pulsa si le das en el borde superior muy justo
        btnSignUp.setOnAction(this::handleSignUpButtonAction);
        hlSignIn.setOnAction(this::handleSignInHyperLinkAction);

    }

// Al perder el foco del campo de texto, se realiza una comprobación para determinar si el texto introducido
// cumple con el formato requerido (email@dominio.extensión).
// Si no es válido, producir una IllegalFormatException que se atrapará al final de esta respuesta para mostrar 
// el mensaje “Introduzca un email válido”.
    private void handleFocusPropertyLostEmail(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (oldValue) {  // Solo se ejecuta cuando se pierde el foco
            String email = tfEmail.getText();

            // Patrón para validar el formato de email
            Pattern modelo = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
            Matcher matcher = modelo.matcher(email);

            if (!matcher.matches()) {
                lblError.setText("Incorrect email format");
                // Si el email no cumple con el formato, se lanza la excepción
            } else {
                lblError.setText("");
            }
        }
    }

    // Faltan manejo de erroresssss   
    //Al perder el foco del campo de texto, validar que el campo contiene un valor java.lang.Integer con una longitud de 5 caracteres.
    //Si no es válido, producir una NumberFormatException que se atrapará al final de esta respuesta para mostrar el mensaje “Introduzca un código postal válido”.
private void handleFocusProperyLostZip(ObservableValue observable, Boolean oldValue, Boolean newValue) {

    if (!oldValue) { 
        String zipCode = tfZip.getText();

        try {
            // Verificar que el valor pueda ser convertido a un Integer y tenga 5 caracteres
            if (zipCode.length() != 5 || !zipCode.matches("\\d+")) {
                System.out.println("Introduzca un ZIP válido de 5 dígitos");
            } else {

                System.out.println("");
            }

        } catch (NumberFormatException e) {
            // Manejar el caso donde no se puede convertir a un número entero
            System.out.println("El código postal no es un número válido.");
        }
    }
}


    //Si está seleccionada, el valor de la checkbox será true, y en caso contrario false. 
    private void handleActiveCheckboxSelectedProperty() {
        boolean isSelected;
    }

    //Pedir confirmación al usuario para salir de la ventana SignUp y abrir la ventana SignIn:
    //Si el usuario confirma, se redirigirá a la ventana SignIn.
    //Si no confirma, mantenerse en la ventana.
    private void handleSignInHyperLinkAction(ActionEvent actionEvent) {

        // Crear un cuadro de diálogo de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("Estás a punto de salir");
        alert.setContentText("¿Estás seguro de que deseas salir de la ventana de registro y volver a la ventana de inicio de sesión?");

        // Obtener la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();

        // Si el usuario confirma la salida
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Cerrar la ventana actual de SignUp
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

            // Abrir la ventana de SignIn
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("SignIn");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Si el usuario cancela, no hacemos nada y permanecemos en la ventana actual
    }
     
    
    
    // mostrar la contraseña en el tfPassword solo cuando el el botón esté cargado
    private void handleShowPasswordButtonArmedProperty() {

    }

    //Validar que todos los campos han sido rellenados.
    //Si falta alguno por rellenar, lanzar una excepción con el mensaje “Por favor, rellene todos los campos para completar el registro”.
    //Crear un objeto Cliente y establecer sus propiedades name, login, password, street, city, zip, active, company_id y notification_type. Los valores provienen de los siguientes campos:
    //Name: campo Name
    //Login: campo Email
    //Password: campo Password
    //Street: campo Address
    //City: campo City
    //ZIP: campo ZIP
    //Active: checkbox Active
    //Company_id: valor por defecto (1)
    //Notification_type: valor por defecto (“email”)
    //Llamar al método de lógica signUp pasándole el objeto creado en el punto anterior.
    //Si se produce alguna excepción en esta respuesta atraparla y mostrar un mensaje de alerta con el mensaje de la excepción.
    private void handleSignUpButtonAction(ActionEvent actionEvent) {

        //User user = new User();
        //ClientFactory.getSignable().signUp();
        String name = tfName.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        String address = tfAddress.getText();
        String city = tfCity.getText();
        String zip = tfZip.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || city.isEmpty() || zip.isEmpty()) {
            lblError.setText("Please fill out all fields.");
            return;
        }
    }


}
