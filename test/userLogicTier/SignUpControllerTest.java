/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import org.testfx.matcher.base.WindowMatchers;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import org.testfx.matcher.control.LabeledMatchers;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import userInterfaceTier.Application;

/**
 *
 * @author Aitziber
 * @author Ander
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new Application().start(stage);
    }

    public SignUpControllerTest() {
    }

    //  hacer también las validaciones, rellenar todos los campos y a ver si acepta el formato, no informa de error y activa el boton de registro
    @Ignore
    @Test
    public void testA_emailFormatError() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        clickOn("#tfEmail");
        write("notEmailAddress");
        clickOn("#tfName");

        FxAssert.verifyThat("#lblError", hasText("Incorrect email format"));
        FxAssert.verifyThat("#btnSignUp", isDisabled());
    }

    @Ignore
    @Test
    public void testB_zipFormatError() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        clickOn("#tfZip");
        write("1234");
        clickOn("#tfName");

        FxAssert.verifyThat("#lblError", hasText("Write a valid 5 digit ZIP"));
        FxAssert.verifyThat("#btnSignUp", NodeMatchers.isDisabled());
    }

    @Ignore
    @Test
    public void testC_passwordValidationError() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");
        // hay un problema con el foco 
        clickOn("#pfPassword");
        write("1");
        clickOn("#tfEmail");
        clickOn("#pfPassword");

        FxAssert.verifyThat("#lblError", hasText("Password must contain at least 8 characters"));
        FxAssert.verifyThat("#btnSignUp", NodeMatchers.isDisabled());
    }

    @Ignore
    @Test
    public void testD_btnSignUp_isDefaultButton() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        FxAssert.verifyThat("#btnSignUp", isDefaultButton());
    }

    @Ignore
    @Test
    public void testE_btnSignUp_enablesOnFill() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        clickOn("#tfName");
        write("name");

        clickOn("#tfEmail");
        write("example@email.com");

        clickOn("#pfPassword");
        write("abcd*1234");

        clickOn("#tfAddress");
        write("street");

        clickOn("#tfCity");
        write("city");

        clickOn("#tfZip");
        write("12345");

        clickOn("#cbActive");

        FxAssert.verifyThat("#btnSignUp", isEnabled());
    }

    @Test
    public void testF_SignUp() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        clickOn("#tfName");
        write("name");

        clickOn("#tfEmail");
        write("example@email.com");

        clickOn("#pfPassword");
        write("abcd*1234");

        clickOn("#tfAddress");
        write("street");

        clickOn("#tfCity");
        write("city");

        clickOn("#tfZip");
        write("12345");

        clickOn("#cbActive");

        FxAssert.verifyThat("#btnSignUp", isEnabled());
        clickOn("#btnSignUp");

        //hay problema con el boton
        FxAssert.verifyThat(window("SignIn"), WindowMatchers.isShowing());
    }
    
    @Test
    public void testG_UserAlreadyExists() {
        // sometimes bugs, of course, try again after deleting new user from db
        clickOn("#hlSignUp");
        clickOn("Aceptar");
        
        clickOn("#tfName");
        write("name");

        clickOn("#tfEmail");
        write("example@email.com");

        clickOn("#pfPassword");
        write("abcd*1234");

        clickOn("#tfAddress");
        write("street");

        clickOn("#tfCity");
        write("city");

        clickOn("#tfZip");
        write("12345");

        clickOn("#cbActive");
        
        // Click on the sign-up button to attempt registration.
        clickOn("#btnSignUp");
        
        // Verify that the error message "User already exists" is displayed.
        FxAssert.verifyThat("#lblErrorSignUp", LabeledMatchers.hasText("User already exists"));
    }    

    // Hyperlink and alert
    @Ignore
    @Test
    public void testH_hyperlink_showsAlert() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        clickOn("#hlSignIn");
        FxAssert.verifyThat(window("Confirmation"), WindowMatchers.isShowing());
        clickOn("Cancelar");

    }

    @Ignore
    @Test
    public void testI_confirmationAlert_OK_navigation() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        clickOn("#hlSignIn");
        FxAssert.verifyThat(window("Confirmation"), WindowMatchers.isShowing());

        clickOn("Aceptar");
        
        FxAssert.verifyThat(window("SignIn"), WindowMatchers.isShowing());
    }

    @Ignore
    @Test
    public void testJ_confirmationAlert_CANCEL() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        clickOn("#hlSignIn");
        FxAssert.verifyThat(window("Confirmation"), WindowMatchers.isShowing());
        FxAssert.verifyThat("Cancelar", isCancelButton());

        clickOn("Cancelar");

        FxAssert.verifyThat(window("SignUp"), WindowMatchers.isShowing());
    }
}
