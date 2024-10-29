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
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Usuario
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomeControllerTest extends ApplicationTest {

    /**
     * Inicia la aplicación para la prueba.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        new HomeController().start(stage);
    }

    public HomeControllerTest() {
    }

    @Test
    public void testHomeLogOutOk() {
        clickOn("#btnLogOut");
        verifyThat("#btnLogOut", isVisible());
    }
}
    
    
 