package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class displays the main menu by showing the different functionalities. This class will send the wish of the user
 * to go on an especially functionality.
 */
public class MainMenuFxController implements Initializable, Colorable {

    @FXML
    private Label userName;
    @FXML
    private Button createSession;
    @FXML
    private Button addExercice;
    @FXML
    private Button showProgramme;
    @FXML
    private Button profileModify;
    @FXML
    private Button disconnect;
    @FXML
    private Button SettingsButton;
    @FXML
    private AnchorPane parent;

    private ViewListener listener;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.createSession.setOnAction(actionEvent -> listener.goNextMenu("addSession"));
        this.addExercice.setOnAction(actionEvent -> listener.goNextMenu("addExercice"));
        this.showProgramme.setOnAction(actionEvent -> listener.goNextMenu("showProgramme"));
        this.disconnect.setOnAction(actionEvent -> listener.disconnect());
        this.profileModify.setOnAction(actionEvent -> listener.goNextMenu("profileUser"));
        this.SettingsButton.setOnAction(actionvEvent -> listener.goNextMenu("settings"));
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }

    /**
     * This function permits to display the lastname of user on the top corner left on the screen.
     * @param userName
     */
    public void setName(String userName) {
        this.userName.setText(userName);
    }

    /**
     * Puts the MainMenuController as a listener.
     * @param listener
     */
    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    public interface ViewListener {
        /**
         * Change view with given one
         *
         * @param nextMenu the name of the view
         */
        void goNextMenu(String nextMenu);

        /**
         * Disconnect the user by setting its value to null and changing the view to the connexion one.
         */
        void disconnect();
    }
}

