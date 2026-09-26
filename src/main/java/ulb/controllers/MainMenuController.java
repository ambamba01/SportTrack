package ulb.controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import ulb.exceptions.FXMLException;
import ulb.models.User;
import ulb.views.FXMLController;
import ulb.views.MainMenuFxController;

/**
 * Controller for handling the main menu actions within the application.
 * It is responsible for loading the main menu view, handling user actions, and navigating to other menus.
 */
public class MainMenuController extends FXMLController implements MainMenuFxController.ViewListener {

    private final ControllerListener listener;
    private MainMenuFxController mainMenuFxController;
    private final Stage stage;
    private User currentUser;

    /**
     * Constructs a MainMenuController with the specified stage and listener.
     *
     * @param stage    The primary stage of the application where the main menu will be displayed.
     * @param listener The listener that handles navigation to different parts of the application from the main menu.
     */
    public MainMenuController(Stage stage, User currentUser, ControllerListener listener) {
        this.listener = listener;
        this.stage = stage;
        this.currentUser = currentUser;
        super.setBundle(this.currentUser.getLanguage());
    }

    /**
     * Displays the main menu for the specified user.
     * Initializes the main menu view, sets up necessary controllers, and shows the main menu on the stage.
     *
     * @throws FXMLException If there is an error loading the FXML file for the main menu.
     */
    public void show() throws FXMLException {
        FXMLLoader loader = super.show("MainMenu", this.stage );
        this.mainMenuFxController = loader.getController();
        this.mainMenuFxController.setListener(this);
        this.mainMenuFxController.setName(super.bundle.getString("welcome")+" "+currentUser.getName());
        this.mainMenuFxController.changeMode(this.currentUser.getColorMode());
    }

    /**
     * Handles navigation from the main menu to other menus within the application based on the user's selection.
     * Navigation options include adding sessions, exercises, programmes, returning to the start screen, or quitting the application.
     *
     * @param nextMenu A string identifying the next menu to navigate to. This should match one of the predefined options.
     */
    @Override
    public void goNextMenu(String nextMenu) {
        switch (nextMenu) {
            case "addSession":
                listener.showSession();
                // Logic to navigate to the "Add Session" menu
                break;
            case "addExercice":
                this.stage.hide();
                listener.showListExercises();
                break;
            case "showProgramme":
                this.stage.hide();
                listener.showProgramme();
                break;
            case "profileUser":
                this.stage.hide();
                listener.showMyProfile();
                // Logic to navigate to the "userModification" menu
                break;
            case "quit":
                this.stage.hide();
                // Logic to quit the application
                break;
            case "settings":
                this.stage.hide();
                listener.showSettings();
            default:
                // Default case for handling unexpected values of nextMenu
                // Optionally, you can throw an exception or log an error here
                break;
        }
    }

    @Override
    public void disconnect() {
        listener.setCurrentUser(null);
        stage.hide();
        listener.showHome();
    }

    /**
     * Listener interface for handling navigation requests from the main menu.
     * Implementations should provide methods for navigating to specific parts of the application.
     */
    public interface ControllerListener {
        void showListExercises();

        void showProgramme();

        void showMyProfile();

        void showSession();

        void showSettings();

        /**
         * Set the currentUser value
         */
        void setCurrentUser(User user);

        /**
         * Go to home view
         */
        void showHome();
    }
}
