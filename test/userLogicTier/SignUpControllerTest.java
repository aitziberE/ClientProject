/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import org.testfx.matcher.control.LabeledMatchers;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import userInterfaceTier.ApplicationSignUp;

/**
 *
 * @author inifr
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Esto es como un método @Before en JUnit
public class SignUpControllerTest extends ApplicationTest{
    
    @Override 
    public void start(Stage stage) throws Exception {
        new ApplicationSignUp().start(stage);
    }
    
    public SignUpControllerTest() {
    }
    
//    @Ignore
//    @Test
//    public void test1_SignUpOK(){
//        clickOn("#tfName");
//        write("Test");
//        clickOn("#tfEmail");
//        write("test@jmail.com");
//        clickOn("#tfPassword");
//        write("12345678"); 
//    }
    
    @Test
    public void test_EmailFormatOK(){
        clickOn("#tfEmail");
        write("paco@mail.com");
        clickOn("#tfName");
        FxAssert.verifyThat("#btnSignUp", isEnabled());
    }
    
    @Test
    public void test_EmailFormatERROR(){
        clickOn("#tfEmail");
        write("pacomail.com");
        clickOn("#tfName");
        FxAssert.verifyThat("#lblError", LabeledMatchers.hasText("Incorrect email format"));
    }
    
}
