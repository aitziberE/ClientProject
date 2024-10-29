/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.LabeledMatchers;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import userInterfaceTier.Application;

/**
 *
 * @author Ander
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInControllerTest extends ApplicationTest {

    public SignInControllerTest() {
    }

    @Test
    public void testSomeMethod() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        new Application().start(stage);
    }
    
    @Test
    public void testA_AcceptUser() {
        clickOn("#tfUsername");
        write("test@jmail.com");
        clickOn("#pfPassword");
        write("test");
        clickOn("#btnSignIn");
        verifyThat("#btnLogOut", isVisible());   
    }
    
    @Test
    public void testB_ErrorIncorrectCredentials() {
        clickOn("#tfUsername");
        write("ander@moreno.net");
        clickOn("#pfPassword");
        write("mogeo");
        clickOn("#btnSignIn");
        verifyThat("#lblError", LabeledMatchers.hasText("ERROR"));
    }

    @Override
    public void stop() {

    }
}
