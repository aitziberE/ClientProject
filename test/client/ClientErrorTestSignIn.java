/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import userInterfaceTier.Application;
import userInterfaceTier.ApplicationSignUp;

/**
 * ClientErrorTestSignIn tests the sign-in process and error handling for inactive users and invalid credentials. It ensures the correct error messages are displayed for inactive accounts and incorrect login attempts, verifying proper handling of the InactiveUserException and UserCredentialException in the UI layer.
 *
 * The setUpUserRegistration method registers an inactive user before all tests, enabling verification of errors related to inactive users during sign-in.
 *
 * The tests cover user registration, sign-in for inactive users, and invalid credentials.
 *
 * @see ApplicationTest
 * @see ApplicationSignUp
 * @see SignUpControllerTest
 *
 * @author Aitziber
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientErrorTestSignIn extends ApplicationTest {

    /**
     * Starts the application for testing the sign-in process.
     *
     * @param stage The primary stage for the application.
     * @throws Exception If the application fails to start.
     */
    @Override
    public void start(Stage stage) throws Exception {
        new Application().start(stage);
    }

    /**
     * Registers an inactive user in the system by simulating the sign-up process.
     */
    @Test
    public void test1_registerInactiveUser() {
        clickOn("#hlSignUp");
        clickOn("Aceptar");

        clickOn("#tfName");
        write("inactive");

        clickOn("#tfEmail");
        write("inactive@email.com");

        clickOn("#pfPassword");
        write("abcd*1234");

        clickOn("#tfAddress");
        write("street");

        clickOn("#tfCity");
        write("city");

        clickOn("#tfZip");
        write("12345");

        clickOn("#tfCity");

        clickOn("#btnSignUp");
    }
}
