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
 * Test class for the SignUpController.
 * This class contains tests for the functionality of the SignUpController in the application.
 * It extends the ApplicationTest class from TestFX to perform UI testing on the JavaFX application.
 * The tests are run in a specific order as defined by the FixMethodOrder annotation.
 * 
 * The tests include:
 * - Validations for email, zip code, password format, and enabling the registration button when fields are filled.
 * - Attempting user registration and verifying successful registration.
 * - Verifying error handling when a user already exists.
 * - Verifying alert and confirmation dialogs behavior.
 * 
 * @author Aitziber
 * @author Ander
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerTest extends ApplicationTest {

    /**
     * Default constructor for SignUpControllerTest.
     * Initializes the test class without any specific setup. 
     * This constructor is required by JUnit, but does not need to perform any actions.
     */
    public SignUpControllerTest() {
    }
    
    /**
     * Initializes the application for testing by starting the Application.
     * This method is automatically called before each test to set up the application context.
     * 
     * @param stage the primary stage for the application, provided by TestFX framework.
     * @throws Exception if there is an error starting the application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        new Application().start(stage);
    }

     /**
     * Test method to validate the email format during registration.
     * This test verifies that an error message is shown when an invalid email format is entered
     * and ensures the sign-up button remains disabled.
     */
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

    /**
     * Test method to validate the zip code format during registration.
     * This test verifies that an error message is shown when an invalid zip code (less than 5 digits)
     * is entered, and ensures the sign-up button remains disabled.
     */
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

    /**
     * Test method to validate the password requirements during registration.
     * This test verifies that the password input is validated and an error message is shown
     * when the password is too short.
     */
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

    /**
     * Test method to verify that the "Sign Up" button is set as the default button.
     * This test verifies that the "Sign Up" button has the default button style when the registration page is loaded.
     */
    @Ignore
    @Test
    public void testD_btnSignUp_isDefaultButton() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        FxAssert.verifyThat("#btnSignUp", isDefaultButton());
    }

    /**
     * Test method to verify that the "Sign Up" button becomes enabled when all fields are correctly filled.
     * This test checks that the sign-up button is enabled when all required fields (name, email, password, etc.)
     * are filled correctly.
     */
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

    /**
     * Test method to simulate a successful sign-up process.
     * This test verifies that a user can successfully register by filling out the sign-up form and clicking the sign-up button,
     * which should navigate to the "SignIn" window.
     */
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

        FxAssert.verifyThat(window("SignIn"), WindowMatchers.isShowing());
    }
    
    /**
     * Test method to handle the scenario where a user already exists.
     * This test checks if an error message is displayed when attempting to register an already existing user.
     */
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

    /**
     * Test method to verify the behavior of hyperlinks and alert windows during registration.
     * This test checks if clicking the hyperlink triggers an alert window and verifies that it can be canceled.
     */
    @Ignore
    @Test
    public void testH_hyperlink_showsAlert() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        clickOn("#hlSignIn");
        FxAssert.verifyThat(window("Confirmation"), WindowMatchers.isShowing());
        clickOn("Cancelar");

    }

    /**
     * Test method to verify the behavior of the confirmation alert and navigation.
     * This test ensures that clicking "Aceptar" on the confirmation window navigates the user to the "SignIn" window.
     */
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

    /**
     * Test method to verify the behavior of the confirmation alert when "Cancelar" is clicked.
     * This test ensures that clicking "Cancelar" on the confirmation window cancels the action and keeps the "SignUp" window visible.
     */
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
