package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class display the screen of connection
 */
public class LoginFxController implements Initializable, Colorable {
    @FXML
    private AnchorPane parent;

    @FXML
    private TextField emailField;

    /**
     * PasswordField component for the password input.
     */
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button login;

    @FXML
    private Button inscription;

    private ViewListener listener;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.setOnAction(actionEvent -> {
            listener.verifyConnexion(emailField.getText(), passwordField.getText());
        });
        inscription.setOnAction(actionEvent -> {
            listener.showRegister();
        });
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }

    /**
     * Puts the LoginController as a listener of LoginFxController.
     * @param listener
     */
    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    public interface ViewListener {
        /**
         * Verify the credentials and try to get the user inside the db.
         *
         * @param email    email address
         * @param password password in clear
         */
        void verifyConnexion(String email, String password);

        /**
         * Go to the registration view.
         */
        void showRegister();
    }
}
