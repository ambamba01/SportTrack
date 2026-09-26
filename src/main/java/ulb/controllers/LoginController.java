package ulb.controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import ulb.database.repository.UserRepository;
import ulb.exceptions.FXMLException;
import ulb.exceptions.RepositoryException;
import ulb.models.User;
import ulb.utils.LogManager;
import ulb.utils.Utils;
import ulb.views.FXMLController;
import ulb.views.LoginFxController;

import java.util.Objects;
import java.util.logging.Level;
/**
 * Controller class for the login view.
 * Extends FXMLController and implements ViewListener from LoginFxController.
 */
public class LoginController extends FXMLController implements LoginFxController.ViewListener {

    private final Stage stage;
    private final ControllerListener controllerListener;
    private LoginFxController loginFxController;
    private UserRepository rep;

    /**
     * Constructor for LoginController.
     * Initializes the controller with the application stage and a controller listener.
     *
     * @param stage               The primary stage of the application.
     * @param controllerListener  A listener for controller events.
     */
    LoginController(Stage stage, ControllerListener controllerListener) {
        this.stage = stage;
        this.controllerListener = controllerListener;
        super.setBundle("fr");
        try {
            this.rep = new UserRepository();
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
    public LoginController(Stage stage, ControllerListener controllerListener, UserRepository rep){
        this.stage = stage;
        this.controllerListener = controllerListener;
        this.rep = rep;
    }


    /**
     * Displays the login view.
     * Loads the "Connexion" FXML file and sets up the loginFxController.
     * Also applies the current color mode to the anchor pane of the login view.
     *
     * @throws FXMLException If there is an issue loading the FXML.
     */
    public void show() throws FXMLException {
        FXMLLoader loader = super.show("Connexion", this.stage);
        this.loginFxController = loader.getController();
        this.loginFxController.setListener(this);
        this.loginFxController.changeMode(false);
    }

    @Override
    public void verifyConnexion(String email, String password) {
        if (Objects.equals(email, "") || Objects.equals(password, "")) {
            Utils.displayPopup(super.getBundleString("FormPopup"));
            return;
        }
        try {
            User user = this.findUser(email, User.hashPassword(password));
            if (user == null) {
                Utils.displayPopup(super.getBundleString("AccountNotExist"));
                return;
            }
            controllerListener.setCurrentUser(user);
            this.stage.hide();
            controllerListener.showHome();
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("loginError"));
        }
    }

    /**
     * Check through the repository if a User exists.
     *
     * @param email:        the email of the user
     * @param hashPassword: the hash password of the user
     * @return the id of the user
     * @throws RepositoryException: if something went wrong with the repository
     */
    private User findUser(String email, String hashPassword) throws RepositoryException {
        return rep.exist(email, hashPassword);
    }


    @Override
    public void showRegister() {
        this.stage.hide();
        controllerListener.showRegister();
    }

    public interface ControllerListener {
        /**
         * Show registration view
         */
        void showRegister();

        /**
         * Show home view (main menu)
         */
        void showHome();

        /**
         * Set the current user
         * @param user the user
         */
        void setCurrentUser(User user);
    }
}
