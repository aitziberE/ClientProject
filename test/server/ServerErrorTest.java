/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.LabeledMatchers;
import userInterfaceTier.Application;

/**
 * ServerErrorTest verifies the handling of server connection issues and user capacity limits during sign-in and sign-up processes. It ensures that the appropriate exceptions, specifically UserCapException and ServerException are properly managed within the UI layer, displaying the correct error messages to the user.
 *
 * This test class simulates scenarios where the server is unreachable and where user capacity is exceeded, triggering the respective exceptions.
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
     * Tests the sign-in process when the server is not connected. Expects a ServerException to be handled and an appropriate message displayed.
     */
    @Test
    public void test3_serverNotConnectedSignIn() {
        // Simulate sign in with the server being disconnected

        clickOn("#tfUsername");
        write("example@email.com");

        clickOn("#pfPasswd");
        write("abcd*1234");

        clickOn("#btnSignIn");

        verifyThat("Server error", isVisible());
        
        clickOn("Aceptar");
    }
    
    @Test
    public void test4_serverNotConnectedSignUp() {
        // Simulate sign up with the server being disconnected

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

        verifyThat("Server error", isVisible());
    }
}
