package controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import exceptions.FXMLException;
import models.Session;
import models.User;
import views.FXMLController;
import views.RunSessionFxController;

/**
 * This class passes the information of object Session (provided by the model) about the current exercise( its image,
 * remaining duration and its description).
 */
public class RunSessionController extends FXMLController implements RunSessionFxController.ViewListener {

    private final RunSessionController.ControllerListener listener;

    private RunSessionFxController runSessionFxController;
    private final Stage stage;

    private final Session session;
    private User currentUser;

    /**
     * This Constructor is used to obtain the stage that will permit to display the screen, the session object concerned
     * by the run application and the presenter that will act as a listener.
     * @param stage
     * @param session
     * @param listener
     */
    public RunSessionController(Stage stage, RunSessionController.ControllerListener listener, Session session) {
        this.listener = listener;
        this.stage = stage;
        this.session = session;
    }

    /**
     * This function permit to the RunSessionFxController to display the screen.
     * @param user
     * @throws FXMLException
     */
    public void show(User user) throws FXMLException {
        this.currentUser = user;
        super.setBundle(this.currentUser.getLanguage());
        FXMLLoader loader = super.show("RunSession", this.stage );
        this.runSessionFxController = loader.getController();
        this.runSessionFxController.setListener(this);
        this.runSessionFxController.changeMode(this.currentUser.getColorMode());
        session.startSession();
        displayExerciseInformation();
    }

    /**
     * Allows to move to the previous exercise during a session
     */
    @Override
    public void getPrevious() {
        this.session.getPrevious();
        this.session.updateCurrentExercise();
        displayExerciseInformation();
    }

    private void displayExerciseInformation() {
        this.runSessionFxController.setInformation(this.session.getCurrentExerciseTitle(),
                this.session.getCurrentExerciseDescription(), this.session.getCurrentExerciseDuration(),
                this.session.getCurrentExerciseImage());
    }

    /**
     * Allows to go to the next exercise during a session
     */
    @Override
    public void getNext() {
        if (this.session.isLastExercise()) {
            showHome();
        } else {
            this.session.getNext();
            this.session.updateCurrentExercise();
            displayExerciseInformation();
        }
    }

    /**
     * Allows user to go the Program selection page
     */
    @Override
    public void showHome() {
        this.stage.hide();
        listener.showSession();
    }


    public interface ControllerListener {
        void showSession();
    }

}
