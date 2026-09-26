package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class retrieves the data of the user puts on the modification forms and check the validity of his data.
 */
public class MyProfileFxController implements Initializable, Colorable {
    @FXML
    private Label userSizeUnit;
    @FXML
    private Label userMaxRunUnit;
    @FXML
    private Label userWeightUnit;
    @FXML
    private AnchorPane parent;
    @FXML
    private Button modifyProfile;
    @FXML
    private Button backHome;
    @FXML
    private TextField name;
    @FXML
    private TextField firstName;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField size;
    @FXML
    private TextField weight;
    @FXML
    private TextField maxPumps;
    @FXML
    private TextField maxKmTravelling;
    @FXML
    private TextField maxPullUps;
    @FXML
    private TextField maxAbs;
    private ViewListener viewListener;


    /**
     * Check the completeness of the form.
     */
    public void checkModifyData() {
        if (isAllFieldsCompleted(name.getText(), firstName.getText(), email.getText(),
                password.getText(), newPassword.getText(), size.getText(), weight.getText(),
                maxKmTravelling.getText(), maxPumps.getText(), maxPullUps.getText(), maxAbs.getText()) &&
                isAllFieldValid(name.getText(), firstName.getText(), email.getText(),
                        password.getText(), newPassword.getText(), size.getText(), weight.getText(),
                        maxKmTravelling.getText(), maxPumps.getText(), maxPullUps.getText(), maxAbs.getText())) {
            viewListener.askModificationProfileUser(name.getText(), firstName.getText(), email.getText(),
                    password.getText(), newPassword.getText(), Float.parseFloat(size.getText()), Float.parseFloat(weight.getText()),
                    Float.parseFloat(maxKmTravelling.getText()), Integer.parseInt(maxPumps.getText()),
                    Integer.parseInt(maxPullUps.getText()), Integer.parseInt(maxAbs.getText()));
        } else {
            viewListener.displayModificationUserFailed();
        }

    }

    /**
     * This function allows to user to change the weight unit of the application.
     */
    public void changeTheWeightUnit(){
        userWeightUnit.setText("pounds");
    }

    /**
     * This function allows to user to change the length unit of the application.
     */
    public void changeTheLengthUnit(){
        userMaxRunUnit.setText("miles");
        userSizeUnit.setText("feet");
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProfile.setOnAction(actionEvent -> checkModifyData());
        backHome.setOnAction(actionEvent -> viewListener.showMainMenu());
    }

    /**
     * This function allows to user to see these actual data before modification.
     * @param name
     * @param firstName
     * @param emailAddress
     * @param maxAbdo
     * @param maxKmCourse
     * @param maxTraction
     * @param maxPompe
     * @param taille
     * @param poids
     */
    public void setFields(String name, String firstName, String emailAddress, String maxAbdo, String maxKmCourse,
                          String maxTraction, String maxPompe, String taille, String poids) {
        this.name.setText(name);
        this.firstName.setText(firstName);
        this.email.setText(emailAddress);
        this.maxAbs.setText(maxAbdo);
        this.maxKmTravelling.setText(maxKmCourse);
        this.maxPullUps.setText(maxTraction);
        this.maxPumps.setText(maxPompe);
        this.size.setText(taille);
        this.weight.setText(poids);
    }

    /**
     * This function permits to MyProfileController to become a listener.
     * @param viewListener
     */
    public void setListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    /**
     * This function checks if all fields are completed to do the modification.
     * @param name
     * @param firstName
     * @param email
     * @param password
     * @param newPassword
     * @param size
     * @param weight
     * @param travelingKilometers
     * @param pumps
     * @param pullUps
     * @param abs
     * @return
     */
    public boolean isAllFieldsCompleted(String name, String firstName, String email, String password, String newPassword,
                                        String size, String weight, String travelingKilometers, String pumps,
                                        String pullUps, String abs) {
        return FxUtils.isAllUserRegistrationFieldsCompleted(name, firstName, email, password, size, weight,
                travelingKilometers, pumps, pullUps, abs) && !newPassword.isBlank();
    }

    /**
     * This function checks if the data given have a correct type.
     * @param name
     * @param firstName
     * @param email
     * @param password
     * @param newPassword
     * @param size
     * @param weight
     * @param travelingKilometers
     * @param pumps
     * @param pullUps
     * @param abs
     * @return
     */
    public boolean isAllFieldValid(String name, String firstName, String email, String password, String newPassword,
                                   String size, String weight, String travelingKilometers, String pumps, String pullUps,
                                   String abs) {
        return FxUtils.isAllUserRegistrationFieldsValid(name, firstName, email, password, size,
                weight, travelingKilometers, pumps, pullUps, abs) && FxUtils.validatePassword(newPassword)
                && Objects.equals(password, newPassword);
    }


    public interface ViewListener {
        void askModificationProfileUser(String name, String firstName, String email, String password, String newPassword,
                                        float size, float weight, float travelingKilometers, int pumps,
                                        int pullUps, int abs);

        void showMainMenu();

        void displayModificationUserFailed();
    }

}