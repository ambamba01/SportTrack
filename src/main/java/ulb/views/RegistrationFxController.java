package ulb.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ulb.exceptions.RepositoryException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class permits to display the form of registration and checks the responses of the user that's to say if all the
 * fields are completed and filled with the correct type asked.
 */
public class RegistrationFxController implements Initializable, Colorable {
    @FXML
    private AnchorPane parent;
    @FXML
    private Button register;
    @FXML
    private Button backToLogin;
    @FXML
    private TextField sizeText;

    @FXML
    private TextField weightText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField firstNameText;

    @FXML
    private TextField emailAddressText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField numberKilometersTravellingText;

    @FXML
    private TextField numberPumpsText;

    @FXML
    private TextField numberPullUpsText;

    @FXML
    private TextField numberAbsText;

    private ViewListener listener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        register.setOnAction(actionEvent -> {
            try {
                if (isAllFieldValid() && isAllFiedCompleted()) {
                    listener.askTextUserRegistration(nameText.getText(),
                            firstNameText.getText(), emailAddressText.getText(), passwordText.getText(), Integer.parseInt(sizeText.getText()),
                            Float.parseFloat(weightText.getText()), Float.parseFloat(numberKilometersTravellingText.getText()), Integer.parseInt(numberPumpsText.getText()),
                            Integer.parseInt(numberPullUpsText.getText()), Integer.parseInt(numberAbsText.getText()));
                } else {
                    listener.showErrorFailedRegistration("Attention, veuillez compléter tous les champs.");
                }
            } catch (RepositoryException e) {
                listener.showErrorFailedRegistration(e.getMessage());
            }
        });
        backToLogin.setOnAction(actionEvent -> listener.showLogin());
    }


    /**
     * This function puts the registrationController as a listener.
     * @param listener
     */
    public void setViewRegistrationListener(ViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }


    private boolean isAllFieldValid() {
        return FxUtils.isAllUserRegistrationFieldsValid(nameText.getText(), firstNameText.getText(), emailAddressText.getText(),
                passwordText.getText(), sizeText.getText(), weightText.getText(), numberKilometersTravellingText.getText(),
                numberPumpsText.getText(), numberPullUpsText.getText(), numberAbsText.getText());
    }

    private boolean isAllFiedCompleted() {
        return FxUtils.isAllUserRegistrationFieldsCompleted(nameText.getText(), firstNameText.getText(), emailAddressText.getText(),
                passwordText.getText(), sizeText.getText(), weightText.getText(), numberKilometersTravellingText.getText(),
                numberPumpsText.getText(), numberPullUpsText.getText(), numberAbsText.getText());
    }

    public interface ViewListener {
        void askTextUserRegistration(String name, String firstName, String email, String password,
                                     int size, float weight, float travelingKilometers, int pumps,
                                     int pullUps, int abs) throws RepositoryException;

        void showErrorFailedRegistration(String message);

        void showLogin();
    }

}