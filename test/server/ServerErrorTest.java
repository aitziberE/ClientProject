/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import userInterfaceTier.Application;

/**
 * ServerErrorTest verifies the handling of server connection issues and user capacity limits
 * during sign-in and sign-up processes. It ensures that the appropriate exceptions, 
 * specifically UserCapException and ServerException are properly managed within the UI layer,
 * displaying the correct error messages to the user.
 * 
 * This test class simulates scenarios where the server is unreachable and where user capacity 
 * is exceeded, triggering the respective exceptions.
 * 
 * @see Application
 * @see UserCapException
 * @see ServerException
 * 
 * @author Aitziber
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServerErrorTest extends ApplicationTest {

    @Override 
    public void start(Stage stage) throws Exception {
        new Application().start(stage);
    }

    public ServerErrorTest() {
    }

    /**
     * Tests the sign-in process with valid user credentials.
     * This method simulates a user attempting to sign in successfully.
     */
    @Test
    public void test1_signIn() {
        clickOn("#tfUsername");
        write("example@email.com");
        
        clickOn("#pfPassword");
        write("abcd*1234");
        
        clickOn("#btnSignIn");
    }

    /**
     * Tests the sign-in process when the user capacity limit is exceeded.
     * Expects a UserCapException to be handled and an appropriate message displayed.
     */
    @Test
    public void test2_userCapExceeded() {
        // Simulate user capacity being reached by USER_CAP = 1
        
        clickOn("#tfUsername");
        write("example@email.com");

        clickOn("#pfPassword");
        write("abcd*1234");

        clickOn("#btnSignIn");

        FxAssert.verifyThat("#lblError", LabeledMatchers.hasText("Cannot process request, please try again later."));
    }

    /**
     * Tests the sign-in process when the server is not connected.
     * Expects a ServerException to be handled and an appropriate message displayed.
     */
    @Test
    public void test3_serverNotConnected() {
        // Simulate the server being disconnected

        clickOn("#tfUsername");
        write("example@email.com");

        clickOn("#pfPassword");
        write("abcd*1234");

        clickOn("#btnSignIn");

        FxAssert.verifyThat("#lblError", LabeledMatchers.hasText("There was an error on the server, please contact support."));
    }
}