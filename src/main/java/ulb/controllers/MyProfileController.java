package controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import database.repository.UserRepository;
import exceptions.AlreadyExistException;
import exceptions.FXMLException;
import exceptions.RepositoryException;
import models.User;
import utils.LogManager;
import utils.Utils;
import views.FXMLController;
import views.MyProfileFxController;

import java.util.logging.Level;

/**
 * This class pass the information between MyProfileFxController and layer service, in this case, UserRepository. This
 * information will modify the information of user already existing.
 */
public class MyProfileController extends FXMLController implements MyProfileFxController.ViewListener {

    private final Stage stage;
    private final ControllerListener controllerListener;
    private MyProfileFxController myProfileFxController;
    private User currentUser;

    /**
     * Constructor for MyProfileController.
     * Initializes the controller with the application stage, the current user, and a controller listener.
     *
     * @param stage             The primary stage of the application.
     * @param currentUser       The currently logged-in user.
     * @param controllerListener A listener for controller events.
     */
    MyProfileController(Stage stage, User currentUser, ControllerListener controllerListener) {
        this.stage = stage;
        this.controllerListener = controllerListener;
        this.currentUser = currentUser;
        super.setBundle(this.currentUser.getLanguage());
    }

    /**
     * Call the function show of MyProfileFxController having loaded all information necessaries.
     * @throws FXMLException
     */
    public void show() throws FXMLException {
        FXMLLoader loader = super.show("MyProfile", this.stage );
        this.myProfileFxController = loader.getController();
        this.myProfileFxController.setListener(this);
        this.myProfileFxController.setFields(currentUser.getLastName(), currentUser.getName(),
            currentUser.getMailAddress(), Integer.toString(currentUser.getMaxAbs()),
                Float.toString(currentUser.getMaxKmRunWithConversion()), Integer.toString(currentUser.getMaxPullups()),
            Integer.toString(currentUser.getMaxPushUps()), Float.toString(currentUser.getHeightWithConversion()),
                Float.toString(currentUser.getWeightWithConversion()));
        if(!currentUser.isKiloWeightUnit()){
            myProfileFxController.changeTheWeightUnit();
        }
        if(!currentUser.isMetreLengthUnit()){
            myProfileFxController.changeTheLengthUnit();
        }
        this.myProfileFxController.changeMode(this.currentUser.getColorMode());
    }


    @Override
    public void askModificationProfileUser(String name, String firstName, String email, String password,
                                           String rePassword, float size, float weight, float travelingKilometers,
                                           int pumps, int pullUps, int abs) {
        if (this.currentUser.checkPassword(User.hashPassword(rePassword))) {
            User temp = new User(this.currentUser);
            try {
                temp.updateUser(name, firstName, email, size, weight,
                        travelingKilometers, pumps, pullUps, abs, this.currentUser.getPassword());
                this.updateUserDB(temp);
                this.currentUser = temp;
                controllerListener.setCurrentUser(this.currentUser);
                Utils.displayPopup(super.getBundleString("AccountModified"));
            } catch (RepositoryException e) {
                LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
                Utils.displayPopup(e.getMessage());
            } catch (IllegalArgumentException e) {
                Utils.displayPopup(e.getMessage());
            } catch (AlreadyExistException e) {
                Utils.displayPopup(super.getBundleString("MailAddressAlreadyTaken"));
            }
        } else {
            displayModificationUserFailed();
        }
    }
    @Override
    public void displayModificationUserFailed() {
        Utils.displayPopup(super.getBundleString("ModifyProfileFormError"));
    }


    /**
     * Update the user inside the db
     *
     * @throws RepositoryException
     * @throws IllegalArgumentException
     */
    private void updateUserDB(User user) throws RepositoryException, IllegalArgumentException, AlreadyExistException {
        UserRepository userRepository = new UserRepository();
        userRepository.update(user);
    }

    /**
     * Triggers the transition to the main menu view.
     * Delegates the action to the controllerListener to handle the actual navigation.
     */
    public void showMainMenu() {
        controllerListener.goToMainMenu();
    }

    /**
     * Interface defining the contract for communication between controllers.
     * Specifies methods for navigating to the main menu and setting the current user.
     */
    public interface ControllerListener {
        void goToMainMenu();

        void setCurrentUser(User user);
    }
}
