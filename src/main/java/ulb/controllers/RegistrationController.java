package controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import database.repository.ExerciseRepository;
import database.repository.UserRepository;
import exceptions.AlreadyExistException;
import exceptions.FXMLException;
import exceptions.RepositoryException;
import models.Exercise;
import models.User;
import utils.LogManager;
import utils.Utils;
import views.FXMLController;
import views.RegistrationFxController;

import java.util.logging.Level;

/**
 * This class pass the information between RegistrationFxController and the layer service like the information providing
 * by the form and that are given to the UserRepository to be saved in the database.
 */
public class RegistrationController extends FXMLController implements RegistrationFxController.ViewListener {

    private final Stage stage;
    private final ControllerListener controllerListener;
    private RegistrationFxController registrationFxController;
    private UserRepository userRep;

    /**
     * This Constructor is used to obtain the stage that will permit to display the screen  and the presenter that will
     * act as a listener.
     * @param stage
     * @param controllerListener
     */
    RegistrationController(Stage stage, ControllerListener controllerListener) {
        this.stage = stage;
        this.controllerListener = controllerListener;
        super.setBundle("fr");
        try {
            this.userRep = new UserRepository();
        }catch (RepositoryException e){
            LogManager.getInstance().getLogger().log(Level.WARNING,e.getMessage());
            Utils.displayPopup(super.getBundleString("loginError"));
        }
    }

    /**
     * This Constructor is used to obtain the stage that will permit to display the screen, the repository of user
     * for to be able to connect with the db and the presenter that will act as a listener.
     * @param stage
     * @param rep
     * @param controllerListener
     */
    public RegistrationController(Stage stage, ControllerListener controllerListener, UserRepository rep){
        this.stage = stage;
        this.controllerListener = controllerListener;
        this.userRep = rep;
    }

    /**
     * function that displays the registration form
     *
     * @throws FXMLException
     */
    void show() throws FXMLException {
        FXMLLoader loader = super.show("Register",this.stage);
        this.registrationFxController = loader.getController();
        this.registrationFxController.setViewRegistrationListener(this);
        this.registrationFxController.changeMode(false);
    }

    /**
     * function that displays an error screen to say that we have failed the registration
     * @param message message to show
     */
    public void showErrorFailedRegistration(String message) {
        Utils.displayPopup(message);
    }

    /**
     * function that displays a screen to say that we have succeed the registration
     * @param message message to show
     */
    public void showSuccessfulRegistration(String message) {
        Utils.displayPopup(message);
    }


    /**
     * function that ask the user information of the registration form providing by the file RegistrationFXController
     * @param name user name
     * @param firstName user first name
     * @param email user email
     * @param password user password
     * @param size user size
     * @param weight user weight
     * @param travelingKilometers user max km run
     * @param pushUps user push-ups
     * @param pullUps user pull ups
     * @param abs user abs
     */
    public void askTextUserRegistration(String name,
                                        String firstName,
                                        String email,
                                        String password,
                                        int size,
                                        float weight,
                                        float travelingKilometers,
                                        int pushUps,
                                        int pullUps,
                                        int abs) {

        User newUser = new User(name, firstName, email, User.hashPassword(password), pushUps,
                pullUps, abs, travelingKilometers, size, weight, false, "fr", true, true);
        if (!this.create(newUser)) {
            return;
        }
        addPauses(newUser);
        showSuccessfulRegistration(super.getBundleString("registrationOk"));
        showLogin();
    }

    /**
     * Add all the default pauses of a user into the db. If an error occur the user is deleted.
     *
     * @param user new user.
     */
    private void addPauses(User user) {
        Exercise pause15 = new Exercise(user.getId(), "Pause 15s", "Une pause de 15s", "Pause", 1,
                "/", "0A0A0A".getBytes(), 15, 0);
        Exercise pause30 = new Exercise(user.getId(), "Pause 30s", "Une pause de 30s", "Pause", 1,
                "/", "0A0A0A".getBytes(), 30, 0);
        Exercise pause60 = new Exercise(user.getId(), "Pause 1min", "Une pause de 1min", "Pause", 1,
                "/", "0A0A0A".getBytes(), 60, 0);
        try {
            this.addPauses(pause15, pause30, pause60);
        } catch (RepositoryException | AlreadyExistException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("registrationError"));
            this.delete(user.getId());
        }
    }

    /**
     * add the user to the database through userRepository
     *
     * @return a boolean if successful
     */
    private boolean create(User user) {
        try{
            int key = userRep.add(user);
            user.setId(key);
            return true;
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, "Error to insert the new user Registration Screen: " + e.getMessage());
            Utils.displayPopup(super.getBundleString("registrationError"));
        } catch (IllegalArgumentException e) {
            Utils.displayPopup(super.getBundleString("registrationError"));
        } catch (AlreadyExistException e) {
            Utils.displayPopup(super.getBundleString("MailAddressAlreadyTaken"));
        }
        return false;
    }

    private void  delete(int userId){
        try{
            userRep.remove(userId);
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("contactAdmin"));
        }
    }

    @Override
    public void showLogin() {
        this.stage.hide();
        this.controllerListener.showLogin();
    }


    /**
     * Add into the db all 3 kind of pauses.
     *
     * @param pause15 pause of 15 sec
     * @param pause30 pause of 30 sec
     * @param pause60 pause of 60 sec
     * @throws RepositoryException
     */
    private void addPauses(Exercise pause15, Exercise pause30, Exercise pause60) throws RepositoryException, AlreadyExistException {
        ExerciseRepository exerciseRepository = new ExerciseRepository();
        exerciseRepository.add(pause15);
        exerciseRepository.add(pause30);
        exerciseRepository.add(pause60);
    }


    public interface ControllerListener {
        void showLogin();
    }
}
