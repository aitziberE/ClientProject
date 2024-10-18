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

public class SignUpController {

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

    public void initialize(Stage stage) {

        //Establecer el título de la ventana al valor “SignUp”.
        stage.setTitle("Sign Up");
        //La ventana no debe ser redimensionable
        stage.setResizable(false);
        //Mostrar la ventana.
        stage.show();
        //Borrar texto en lblError
        lblError.setText("");
        //En caso de producirse alguna excepción, se mostrará un mensaje al usuario con el texto de la misma.
        
        //Cuando pierden el foco
        tfEmail.focusedProperty().addListener(this::handleFocusProperyLostEmail);
        tfZip.focusedProperty().addListener(this::handleFocusProperyLostZip);
        
        //Validar que todos los campos han sido rellenados.
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
    
    //Si está seleccionada, el valor de la checkbox será true, y en caso contrario false. 
    private void handleActiveCheckboxSelectedProperty() {
        
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

        //User user = new User();

        //ClientFactory.getSignable().signUp();

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
    }
    
    private void handleStageOnCloseRequest() {
        
    }
}
