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
 * Test class for the SignUpController. This class contains tests for the functionality of the SignUpController in the application. It extends the ApplicationTest class from TestFX to perform UI testing on the JavaFX application. The tests are run in a specific order as defined by the FixMethodOrder annotation.
 *
 * The tests include: - Validations for email, zip code, password format, and enabling the registration button when fields are filled. - Attempting user registration and verifying successful registration. - Verifying error handling when a user already exists. - Verifying alert and confirmation dialogs behavior.
 *
 * @author Aitziber
 * @author Ander
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpControllerTest extends ApplicationTest {

    /**
     * Default constructor for SignUpControllerTest. Initializes the test class without any specific setup. This constructor is required by JUnit, but does not need to perform any actions.
     */
    public SignUpControllerTest() {
    }

    /**
     * Initializes the application for testing by starting the Application. This method is automatically called before each test to set up the application context.
     *
     * @param stage the primary stage for the application, provided by TestFX framework.
     * @throws Exception if there is an error starting the application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        new Application().start(stage);
    }

    /**
     * Test method to simulate a successful sign-up process. This test verifies that a user can successfully register by filling out the sign-up form and clicking the sign-up button, which should navigate to the "SignIn" window.
     */
    @Test
    public void testA_SignUp() {
        clickOn("#hlSignUp");

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
     * Test method to handle the scenario where a user already exists. This test checks if an error message is displayed when attempting to register an already existing user.
     */
    @Test
    public void testB_UserAlreadyExists() {
        // sometimes bugs, of course, try again after deleting new user from db
        clickOn("#hlSignUp");

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

}
