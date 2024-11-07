/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Test class for the HomeController.
 * This class contains tests for the functionality of the HomeController in the application.
 * It extends the ApplicationTest class from TestFX to perform UI testing on the JavaFX application.
 * The tests are run in a specific order as defined by the FixMethodOrder annotation.
 * 
 * @author Usuario
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomeControllerTest extends ApplicationTest {

    /**
     * Initializes the application for testing by starting the HomeController.
     * This method is automatically called before each test to set up the application context.
     * 
     * @param stage the primary stage for the application, provided by TestFX framework.
     * @throws Exception if there is an error starting the application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        new HomeController().start(stage);
    }

    /**
     * Constructor for HomeControllerTest.
     * This constructor is called to create an instance of the test class.
     */
    public HomeControllerTest() {
    }

    /**
     * Test method to validate the behavior of the log out button.
     * This test simulates a click on the "Log Out" button and checks whether it remains visible
     * after the action, indicating that the log-out process was initiated correctly.
     */
    @Test
    public void testHomeLogOutOk() {
        clickOn("#btnLogOut");
        verifyThat("#btnLogOut", isVisible());
    }
}