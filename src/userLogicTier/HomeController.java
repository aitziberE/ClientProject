/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userLogicTier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import userLogicTier.model.User;

/**
 * FXML Controller class
 *
 * @author Aitziber
 */
public class HomeController {

    private User user;

    @FXML
    private Text lblUserName;

    public HomeController() {

    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        System.out.println("initializing...");

        lblUserName.setText(user.getName());
    }
}

/**
 *
 * @param url
 * @param rb
 * @Override public void initialize(URL url, ResourceBundle rb) { // circleClip.centerXProperty().bind(imgProfile.fitWidthProperty().divide(2)); // circleClip.centerYProperty().bind(imgProfile.fitHeightProperty().divide(2)); // circleClip.radiusProperty().bind(imgProfile.fitWidthProperty().divide(2)); }
 */
