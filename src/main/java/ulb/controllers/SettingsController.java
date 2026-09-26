package controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import database.repository.UserRepository;
import exceptions.FXMLException;
import exceptions.RepositoryException;
import models.User;
import utils.LogManager;
import utils.Utils;
import views.FXMLController;
import views.SettingsFxController;

import java.util.logging.Level;

/**
 * This class permits to pass information between the settings view and the service layer so, in this case, the
 * UserRepository. It implements certain functions of SettingsFxController like changeColors and permits to display the
 * settings view.
 */
public class SettingsController extends FXMLController implements SettingsFxController.ViewListener {
    private final ControllerListener listener;
    private SettingsFxController settingsFxController;
    private final User currentUser;
    private final Stage stage;


    /**
     * This Constructor is used to obtain the stage that will permit to display the screen, the user concerned by the
     * session and the presenter that will act as a listener.
     * @param stage
     * @param user
     * @param listener
     */
    public SettingsController(Stage stage, User user, ControllerListener listener) {
        this.listener = listener;
        this.currentUser = user;
        this.stage = stage;
        super.setBundle(this.currentUser.getLanguage());
    }

    /**
     * display the settings view who will be managed by SettingsFxController
     * @throws FXMLException
     */
    public void show() throws FXMLException {
        FXMLLoader loader = super.show("Settings", this.stage );
        this.settingsFxController = loader.getController();
        this.settingsFxController.setListener(this);
        this.settingsFxController.changeLengthButtonText(currentUser.isMetreLengthUnit());
        this.settingsFxController.changeWeightButtonText(currentUser.isKiloWeightUnit());
        this.settingsFxController.changeMode(this.currentUser.getColorMode());
    }

    /**
     * Update the user color mode field inside the db
     *
     * @throws RepositoryException
     * @throws IllegalArgumentException
     */
    private void updateUserColorModeDB(User user) throws RepositoryException {
        UserRepository userRepository = new UserRepository();
        userRepository.updateColorMode(user);
    }

    /**
     * Update the user language field inside the db
     *
     * @throws RepositoryException
     * @throws IllegalArgumentException
     */
    private void updateUserLanguageDB(User user) throws RepositoryException, IllegalArgumentException {
        UserRepository userRepository = new UserRepository();
        userRepository.updateLanguage(user);
    }


    /**
     * This function ask update of preference length unit.
     */
    public void askUpdateLenghtUnitPreferences(){
        updateLengthUnitPreferences();
    }

    /**
     * Update the height unit preferences
     */
    private void updateLengthUnitPreferences() {
        try{
            UserRepository userRepository = new UserRepository();
            userRepository.setSizePreferences(currentUser.getId(),!currentUser.isMetreLengthUnit());
            currentUser.setLengthUnit(!currentUser.isMetreLengthUnit());
            settingsFxController.changeLengthButtonText(currentUser.isMetreLengthUnit());
        }catch(RepositoryException e){
            Utils.displayPopup(super.getBundleString("settingsError"));
        }
    }


    @Override
    public void goBack() {
        this.stage.hide();
        listener.showMainMenu();
    }

    @Override
    public void askUpdateWeightUnitPreferences() {
        updateWeigthUnitPreferences();
    }

    /**
     * Update the weigth unit preferences
     */
    private void updateWeigthUnitPreferences() {
        try{
            UserRepository userRepository = new UserRepository();
            userRepository.setWeightPreferences(currentUser.getId(), !currentUser.isKiloWeightUnit());
            currentUser.setWeightUnit(!currentUser.isKiloWeightUnit());
            settingsFxController.changeWeightButtonText(currentUser.isKiloWeightUnit());
        }catch(RepositoryException e){
            Utils.displayPopup(super.getBundleString("settingsError"));
        }
    }

    /**
     * Update the color mode
     */
    public void changeColors(){
        this.currentUser.setColorMode(!this.currentUser.getColorMode());
        try {
            this.updateUserColorModeDB(this.currentUser);
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(e.getMessage());
        }
        this.settingsFxController.changeMode(this.currentUser.getColorMode());
    }

    @Override
    public void changeLanguage(String language){
        try {
            this.currentUser.setLanguage(language);
            this.updateUserLanguageDB(currentUser);
        }catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(e.getMessage());
        } catch (IllegalArgumentException e) {
            Utils.displayPopup(e.getMessage());
        }
        this.stage.hide();
        listener.showSettings();
    }


    public interface ControllerListener {
        /**
         * Change stage to the main menu view
         */
        void showMainMenu();
        void showSettings();

    }
}
