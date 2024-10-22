/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.awt.event.MouseEvent;
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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import userLogicTier.model.User;

/**
 *
 * @author Ander
 */
public class SignInController{

    private Stage stage;
    private final Logger logger;
    
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

    @FXML
    public void initialize(Stage stage) {
        tfUsername.focusedProperty().addListener(this::handleTfUsernameFocusProperyLost);
        btnSignIn.setOnAction(this::handleSignInButtonAction);
        hlSignUp.setOnAction(this::handleSignUpHyperlinkAction);
        btnShowPassword.setOnMousePressed(this::handleButtonPasswordPressed);
        btnShowPassword.setOnMouseReleased(this::handleButtonPasswordReleased);
        tfPassword.setVisible(false);
    }
    
    public void initStage(Stage stage, Parent root){
        logger.info("Initializing SignIn phase...");
        //Establecer el título de la ventana al valor “SignIn”.
        stage.setTitle("Sign In");
        //La ventana no debe ser redimensionable
        stage.setResizable(false);
        //Mostrar la ventana.
        stage.show();
        //En caso de producirse alguna excepción, se mostrará un mensaje al usuario con el texto de la misma.
        lblError.setText("ERROR");
        //TODO meter excepciones
    }
    
    
    private void handleTfUsernameFocusProperyLost(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        // Solo se ejecuta cuando se pierde el foco
        if (oldValue) {
            String email = tfUsername.getText();

            // Patrón para validar el formato de email
            Pattern modelo = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$"); //TODO contrabarra (\) da error
            Matcher matcher = modelo.matcher(email);

            if (!matcher.matches()) {
                lblError.setText("Incorrect email format");
                // Si el email no cumple con el formato, se lanza la excepción
            } else {
                lblError.setText("");
            }
        }
    }
    
   private void handleButtonPasswordPressed(MouseEvent event) {
        tfPassword.setText(pfPassword.getText());
        tfPassword.setVisible(true);
    }

    private void handleButtonPasswordReleased(MouseEvent event) {
        tfPassword.setVisible(false);
        pfPassword.setVisible(true);
    }
    
    @FXML
    private void handleSignInButtonAction(ActionEvent actionEvent) {
        //ClientFactory.getSignable().signIn();

        String username = tfUsername.getText();
        String password = tfPassword.getText();

        if (username.isEmpty() || password.isEmpty()){
            lblError.setText("Please fill out all fields.");
            return;
        }else {
            User user = new User(username, password);
            User testUser = new User("test","test");
            if (user.equals(testUser)) {
                
                try{
                    FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/userInterfaceTier/Home.fxml"));
                    Parent mainView = FXMLLoader.load();
        
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    
                    Scene scene = new Scene(mainView);
                    stage.setScene(scene);
                    stage.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                lblError.setText("ERROR");
            }
        }
    }

    @FXML
    public void handleSignUpHyperlinkAction(ActionEvent actionEvent) {
        try{
            FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("/userInterfaceTier/SignUp.fxml"));
            Parent mainView = FXMLLoader.load();
            
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            
            Scene scene = new Scene(mainView);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public Stage getStage(){
        return stage;
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    public SignInController(){
        logger = Logger.getLogger(SignInController.class.getName());
    }
}
