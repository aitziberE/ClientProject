/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import userLogicTier.model.User;

/**
 *
 * @author Ander
 */
public class SignInController {

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;
    
    @FXML
    private Button btnSignIn;
    
    
    
    
    

    public void initialize(Stage stage) {
        //Establecer el título de la ventana al valor “SignIn”.
        stage.setTitle("Sign In");
        //La ventana no debe ser redimensionable
        stage.setResizable(false);
        //Mostrar la ventana.
        stage.show();
        //En caso de producirse alguna excepción, se mostrará un mensaje al usuario con el texto de la misma.
        //TODO meter excepciones
        
        tfUsername.focusedProperty().addListener(this::handleTfUsernameFocusProperyLost);

    }
    
    private void handleTfUsernameFocusProperyLost(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        
    }

    private void handleSignUpButtonAction(ActionEvent actionEvent) {
        //ClientFactory.getSignable().signIn();

        String username = tfUsername.getText();
        String password = tfPassword.getText();

        if (username.isEmpty() || password.isEmpty()){
            // lblError.setText("Please fill out all fields.");
            return;
        }else {
            User user = new User(username, password);
            User testUser = new User("test","test");
            if (user.equals(testUser)) {
                //TODO abrir ventana Main            
            }else {
                //lblError.setText("ERROR");
            }
        }
    }
}
