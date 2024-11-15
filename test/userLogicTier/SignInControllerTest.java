/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import javafx.geometry.VerticalDirection;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.LabeledMatchers;
import userInterfaceTier.Application;

/**
 * Test class for the SignInController.
 * This class contains tests for the functionality of the SignInController in the application.
 * It extends the ApplicationTest class from TestFX to perform UI testing on the JavaFX application.
 * The tests are run in a specific order as defined by the FixMethodOrder annotation.
 * 
 * Tests include:
 * - Valid user login and log out
 * - Login attempt for inactive user
 * - Login attempt with incorrect username/password
 * 
 * @author Ander
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInControllerTest extends ApplicationTest {

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
     * Test method to simulate a successful login for a valid user and log out after.
     * This test checks the process of logging in with correct credentials and verifying 
     * the presence of the "Log Out" button, then performing the log out action.
     */
    @Ignore
    @Test
    public void testA_AcceptUser() {
        clickOn("#tfUsername");
        write("pablo@paia.com");

        clickOn("#pfPasswd");
        write("12345678");

        clickOn("#btnSignIn");
        verifyThat("#btnLogOut", isVisible());

        scroll(5, VerticalDirection.DOWN);

        clickOn("#btnLogOut");
        clickOn("Aceptar");
    }

    /**
     * Test method to simulate a login attempt for an inactive user.
     * This test verifies that the application correctly handles login attempts for inactive users
     * and shows the appropriate confirmation to proceed or not.
     */
    @Test
    public void testB_InactiveUser() {
        // Sometimes bugs, sometimes doesnt, this is nonsense
        clickOn("#tfUsername");
        write("ander@ander.com");

        clickOn("#pfPasswd");
        write("12345678");

        clickOn("#btnSignIn");
        verifyThat("Aceptar", isVisible());

        sleep(1000);
        clickOn("Aceptar");
    }

    /**
     * Test method to simulate a login attempt with an incorrect username.
     * This test verifies that the application displays an error message when the user
     * enters incorrect login credentials (username or password).
     */
    @Test
    public void testC_WrongUser() {
        clickOn("#tfUsername");
        write("wrong@user.com");

        clickOn("#pfPasswd");
        write("12345678");

        clickOn("#btnSignIn");

        // Verify that the error message for incorrect username or password is shown
        FxAssert.verifyThat("#lblError", LabeledMatchers.hasText("Incorrect username or password."));
    }
}