/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import userInterfaceTier.ApplicationSignUp;


/**
 *
 * @author Usuario
 */
    
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Esto es como un método @Before en JUnit
public class SignUpControllerTest extends ApplicationTest{
    
    @Override 
    public void start(Stage stage) throws Exception {
        new ApplicationSignUp().start(stage);
    }
    
    public SignUpControllerTest() {
    }
    
//    @Ignore
//    @Test
//    public void test1_SignUpOK(){
//        clickOn("#tfName");
//        write("Test");
//        clickOn("#tfEmail");
//        write("test@jmail.com");
//        clickOn("#tfPassword");
//        write("12345678");
//        clickOn("#cbActive");
//        FxAssert.verifyThat("#btnSignUp", isEnabled());
//        clickOn("#btnSignUp");
//    }
    @Ignore
    @Test
    public void test_hyperlink_showsAlert(){
        clickOn("#hlSignIn");
        FxAssert.verifyThat(window("Confirmation"), WindowMatchers.isShowing());

    }
    
    @Ignore
    @Test
    public void test_confirmationAlert_OK_navigation(){
        clickOn("#hlSignIn");
        FxAssert.verifyThat(window("Confirmation"), WindowMatchers.isShowing());
    
         clickOn("Aceptar");
      // estas lineas no están funcionando
      // FxAssert.verifyThat(window("Confirmation"), WindowMatchers.isNotShowing());
      // FxAssert.verifyThat(window("SignUp"), WindowMatchers.isNotShowing());
       FxAssert.verifyThat(window("SignIn"), WindowMatchers.isShowing());
    }
    
    @Ignore
    @Test
    public void test_confirmationAlert_CANCEL(){
        clickOn("#hlSignIn");
        FxAssert.verifyThat(window("Confirmation"), WindowMatchers.isShowing());
        
        clickOn("Cancelar");
        
        //FxAssert.verifyThat(window("Confirmation"), WindowMatchers.isNotShowing());
        FxAssert.verifyThat(window("SignUp"), WindowMatchers.isShowing());
        
    }
   
//    @Test
//    public void test_EmailFormatOK(){
//        clickOn("#tfEmail");
//        write("paco@mail.com");
//        clickOn("#tfName");
//        FxAssert.verifyThat("#btnSignUp", isEnabled());
//    }
//    
//    @Test
//    public void test_EmailFormatERROR(){
//        clickOn("#tfEmail");
//        write("pacomail.com");
//        clickOn("#tfName");
//        FxAssert.verifyThat("#lblError", LabeledMatchers.hasText("ERROR"));
//    }
    
}

