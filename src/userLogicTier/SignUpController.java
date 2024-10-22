package userLogicTier;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import userLogicTier.model.User;

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
        tfEmail.focusedProperty().addListener(this::handleFocusProperyLostEmail);
        tfZip.focusedProperty().addListener(this::handleFocusProperyLostZip);

        // Cuando se pulsan
        // El fokin boton solo se pulsa si le das en el borde superior muy justo
        btnSignUp.setOnAction(this::handleSignUpButtonAction);
        hlSignIn.setOnAction(this::handleSignInHyperLinkAction);

    }

    //Al perder el foco del campo de texto, se realiza una comprobación para determinar si el texto introducido cumple con el formato requerido (email@dominio.extensión).
    //Si no es válido, producir una IllegalFormatException que se atrapará al final de esta respuesta para mostrar el mensaje “Introduzca un email válido”.
    private void handleFocusProperyLostEmail(ObservableValue observable, Boolean oldValue, Boolean newValue) {

    }

    //Al perder el foco del campo de texto, validar que el campo contiene un valor java.lang.Integer con una longitud de 5 caracteres.
    //Si no es válido, producir una NumberFormatException que se atrapará al final de esta respuesta para mostrar el mensaje “Introduzca un código postal válido”.
    private void handleFocusProperyLostZip(ObservableValue observable, Boolean oldValue, Boolean newValue) {

    }

    //Pedir confirmación al usuario para salir de la ventana SignUp y abrir la ventana SignIn:
    //Si el usuario confirma, se redirigirá a la ventana SignIn.
    //Si no confirma, mantenerse en la ventana.
    private void handleSignInHyperLinkAction(ActionEvent actionEvent) {

    }

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

            //Comprobar que todo esté lleno
            if (tfName.getText().isEmpty() || tfEmail.getText().isEmpty() || tfPassword.getText().isEmpty() || tfAddress.getText().isEmpty() || tfCity.getText().isEmpty() || tfZip.getText().isEmpty()) {
                lblError.setText("Please fill out all fields");
                //Se pasan los valores a strings y boolean
            } else {
                String name = tfName.getText();
                String email = tfEmail.getText();
                String password = tfPassword.getText();
                String address = tfAddress.getText();
                String city = tfCity.getText();
                String zip = tfZip.getText();
                boolean isActive = cbActive.isSelected();

                //creamos el usuario pasando los datos
                User user = new User(name, email, password, address, city, zip, isActive);
                ClientFactory.getSignable().signUp(user);
            }
        }
}
