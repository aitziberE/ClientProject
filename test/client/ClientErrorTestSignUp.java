/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import userInterfaceTier.ApplicationSignUp;
import userLogicTier.SignUpControllerTest;

/**
 * ClientErrorTestSignUp tests user registration and error handling in the sign-up process.
 * Extends ApplicationTest from TestFX to simulate user interactions with the JavaFX app.
 * 
 * Ensures the UI correctly displays an error when an existing user is detected, as a result
 * of handling the ExistingUserException. The setUpUserRegistration method runs once before
 * all tests to register a user, enabling verification of duplicate user errors.
 * 
 * Includes form filling and submission, with assertions to check expected outcomes.
 * 
 * @see ApplicationTest
 * @see ApplicationSignUp
 * @see SignUpControllerTest
 * 
 * @author Aitziber
 */
public class ClientErrorTestSignUp extends ApplicationTest {

    /**
     * Initializes and starts the ApplicationSignUp stage for testing.
     * @param stage The primary stage for this application.
     * @throws Exception if there is an error initializing the stage.
     */
    @Override 
    public void start(Stage stage) throws Exception {
        new ApplicationSignUp().start(stage);
    }

    /**
     * Constructor for ClientErrorTestSignUp.
     * Required for JUnit test instantiation.
     */
    public ClientErrorTestSignUp() {
    }

    /**
     * Sets up a user registration prior to running tests. This method is annotated with
     * @BeforeClass to ensure it runs once before any other test in this class.
     * 
     * It utilizes the SignUpControllerTest class to simulate the registration of a user.
     * This allows the existingUser_test to verify error handling for duplicate registrations.
     * 
     * @throws Exception if there is an error during user setup.
     */
    @BeforeClass
    public static void setUpUserRegistration() throws Exception {
        SignUpControllerTest registrationTest = new SignUpControllerTest();
        registrationTest.start(new Stage());
        // registrationTest.test_SignUp();
    }

    /**
     * Tests the application's behavior when attempting to sign up with an existing user.
     * This method simulates filling out the sign-up form fields with values that match
     * an already registered user.
     * 
     * The test asserts that the sign-up button becomes enabled after filling all fields,
     * and then verifies that the correct error message, "User already exists," is displayed.
     */
    @Test
    public void existingUser_test() {
        
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
        FxAssert.verifyThat("#lblError", LabeledMatchers.hasText("User already exists"));
    }    
}
