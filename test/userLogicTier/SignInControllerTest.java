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
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.LabeledMatchers;
import userInterfaceTier.Application;

/**
 *
 * @author Ander
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new Application().start(stage);
    }

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
